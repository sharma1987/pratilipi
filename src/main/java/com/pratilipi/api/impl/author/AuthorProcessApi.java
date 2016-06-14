package com.pratilipi.api.impl.author;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.author.shared.AuthorProcessPostRequest;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.util.AuthorDataUtil;
import com.pratilipi.data.util.PratilipiDataUtil;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/author/process" )
public class AuthorProcessApi extends GenericApi {

	private static final Logger logger =
			Logger.getLogger( AuthorProcessApi.class.getName() );
	
	
	@Get
	public GenericResponse getAuthorProcess( GenericRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		// Fetching AppProperty
		String appPropertyId = "Api.AuthorProcess.ValidateData";
		AppProperty appProperty = dataAccessor.getAppProperty( appPropertyId );
		if( appProperty == null ) {
			appProperty = dataAccessor.newAppProperty( appPropertyId );
			appProperty.setValue( new Date( 0 ) );
		}

		// Fetching list of author ids.
		AuthorFilter authorFilter = new AuthorFilter();
		authorFilter.setMinLastUpdate( (Date) appProperty.getValue(), false );
		List<Long> authorIdList = dataAccessor.getAuthorIdList( authorFilter, null, 10000 ).getDataList();

		// Creating one task per author id.
		List<Task> taskList = new ArrayList<>( authorIdList.size() );
		for( Long authorId : authorIdList ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/author/process" )
					.addParam( "authorId", authorId.toString() )
					.addParam( "validateData", "true" );
			taskList.add( task );
		}
		TaskQueueFactory.getAuthorOfflineTaskQueue().addAll( taskList );
		logger.log( Level.INFO, "Added " + taskList.size() + " tasks." );

		// Updating AppProperty.
		if( authorIdList.size() > 0 ) {
			appProperty.setValue( dataAccessor.getAuthor( authorIdList.get( authorIdList.size() - 1 ) ).getLastUpdated() );
			appProperty = dataAccessor.createOrUpdateAppProperty( appProperty );
		}
		
		return new GenericResponse();
		
	}
	
	@Post
	public GenericResponse postAuthorProcess( AuthorProcessPostRequest request )
			throws InvalidArgumentException, UnexpectedServerException {

		if( request.validateData() )
			validateAuthorData( request.getAuthorId() );
		
		if( request.processData() ) {
			boolean changed = AuthorDataUtil.createOrUpdateAuthorPageUrl( request.getAuthorId() );
//			AuthorDataUtil.createOrUpdateAuthorDashboardPageUrl( request.getAuthorId() );
			if( changed ) {
				// TODO: Update all Pratilipi's PageUrl ( where AUTHOR_ID == request.getAuthorId() )
			}
			AuthorDataUtil.updateAuthorSearchIndex( request.getAuthorId() );
			PratilipiDataUtil.updatePratilipiSearchIndex( null, request.getAuthorId() );
		}
		
		if( request.updateStats() ) {
			boolean changed = AuthorDataUtil.updateAuthorStats( request.getAuthorId() );
			if( changed )
				AuthorDataUtil.updateAuthorSearchIndex( request.getAuthorId() );
		}

		if( request.updateUserAuthorStats() )
			PratilipiDataUtil.updateUserPratilipiStats( request.getAuthorId() );
		
		return new GenericResponse();
	}

	private void validateAuthorData( Long authorId ) throws InvalidArgumentException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = dataAccessor.getAuthor( authorId );
		Page page = dataAccessor.getPage( PageType.AUTHOR, authorId );

		
		// DELETED Author can not have a Page entity linked.
		if( author.getState() == AuthorState.DELETED && page != null )
			throw new InvalidArgumentException( "DELETED Author has a non-deleted Page entity." );
	
		// Non-DELETED Author must have a page entity linked.
		if( author.getState() != AuthorState.DELETED && page == null )
			throw new InvalidArgumentException( "Page entity is missing for the Author." );
		
		
		// Pratilipi Entities linked with the Author.
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setAuthorId( authorId );
		pratilipiFilter.setState( PratilipiState.PUBLISHED );
		
		List<Pratilipi> pratilipiList = dataAccessor.getPratilipiList( pratilipiFilter, null, null ).getDataList();

		// DELETED Author cannot have non-DELETED Pratilipi entities linked.
		if( author.getState() == AuthorState.DELETED ) {
			if( pratilipiList.size() != 0 )
				throw new InvalidArgumentException( "DELETED Author has " + pratilipiList.size() + " non-deleted Pratilipi Entities linked." );
			return;
		}

		// Author having Pratilipi entites linked, can not have his/her language set to null.
		if( author.getLanguage() == null && pratilipiList.size() != 0 )
			throw new InvalidArgumentException( "Author has " + pratilipiList.size() + " non-deleted Pratilipi Entities linked but his/her language is not set." );
		
		
		// Count of Pratilipi Entities in each language.
		Map<Language, Integer> langCount = new HashMap<>();
		for( Pratilipi pratilipi : pratilipiList ) {
			if( pratilipi.getState() != PratilipiState.PUBLISHED )
				continue;
			Integer count = langCount.get( pratilipi.getLanguage() );
			count = count == null ? 1 : count++;
			langCount.put( pratilipi.getLanguage(), count );
		}


		// Author, having Pratilipi entites in just one language, must have the same set as his/her profile langauge.
		if( langCount.keySet().size() == 1 ) {
			Language language = langCount.keySet().iterator().next();
			if( langCount.get( language ) > 1 && author.getLanguage() != language )
				throw new InvalidArgumentException( "Author has " + author.getLanguage() + " as his/her profile langauge but all his/her content pieces are in " + language + "." );
		}
			
		
		// At least one of four name fields must be set.
		if( author.getFirstName() == null && author.getLastName() == null && author.getFirstNameEn() == null && author.getLastNameEn() == null )
			throw new InvalidArgumentException( "Author name is missing." );

	}
	
}