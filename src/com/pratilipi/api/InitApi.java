package com.pratilipi.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.api.shared.GenericRequest;
import com.claymus.api.shared.GenericResponse;
import com.claymus.commons.shared.ClaymusPageType;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.transfer.AppProperty;
import com.claymus.data.transfer.Page;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.UserRole;
import com.claymus.taskqueue.Task;
import com.pratilipi.commons.shared.AuthorFilter;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.commons.shared.PratilipiPageType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Event;
import com.pratilipi.data.transfer.Pratilipi;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipis.PratilipisContent;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri= "/init" )
public class InitApi extends GenericApi {

	@Get
	public GenericResponse getInit( GenericRequest request ) throws InvalidArgumentException, UnexpectedServerException {

		updateHomePageContent( request );

		return new GenericResponse();

	}


	private void updateHomePageContent( GenericRequest request ) throws InvalidArgumentException, UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
//		SearchAccessor searchAccessor = DataAccessorFactory.getSearchAccessor();
//		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		
		
		List<Long> bookIdList = new ArrayList<>( 6 );
//		pratilipiFilter.setType( PratilipiType.BOOK );
//
//		pratilipiFilter.setLanguageId( 5130467284090880L );
//		bookIdList.addAll( searchAccessor.searchPratilipi( pratilipiFilter, null, 2 ).getDataList() );
//		pratilipiFilter.setLanguageId( 5965057007550464L );
//		bookIdList.addAll( searchAccessor.searchPratilipi( pratilipiFilter, null, 2 ).getDataList() );
//		pratilipiFilter.setLanguageId( 6319546696728576L );
//		bookIdList.addAll( searchAccessor.searchPratilipi( pratilipiFilter, null, 2 ).getDataList() );
		
		//Hindi Book
		bookIdList.add( 5345197126844416L );
		bookIdList.add( 5636953047302144L );
		//Gujarati Book
		bookIdList.add( 5733614071316480L );
		bookIdList.add( 6214904767840256L );
		//Tamil Book
		bookIdList.add( 5447462990905344L );
		bookIdList.add( 5151970692169728L );

		
		List<Long> storyIdList = new ArrayList<>( 6 );
//		pratilipiFilter.setType( PratilipiType.STORY );
//		
//		pratilipiFilter.setLanguageId( 5130467284090880L );
//		storyIdList.addAll( searchAccessor.searchPratilipi( pratilipiFilter, null, 2 ).getDataList() );
//		pratilipiFilter.setLanguageId( 5965057007550464L );
//		storyIdList.addAll( searchAccessor.searchPratilipi( pratilipiFilter, null, 2 ).getDataList() );
//		pratilipiFilter.setLanguageId( 6319546696728576L );
//		storyIdList.addAll( searchAccessor.searchPratilipi( pratilipiFilter, null, 2 ).getDataList() );
		
		//Hindi Story
		storyIdList.add( 6483041857830912L );
		storyIdList.add( 5120605368090624L );
		//Gujarati Story
		storyIdList.add( 5682850309341184L );
		storyIdList.add( 6013538430615552L );
		//Tamil Story
		storyIdList.add( 6304067080945664L );
		storyIdList.add( 4673321551527936L );

		
		List<Long> poemIdList = new ArrayList<>( 6 );
//		pratilipiFilter.setType( PratilipiType.POEM );
//		
//		pratilipiFilter.setLanguageId( 5130467284090880L );
//		poemIdList.addAll( searchAccessor.searchPratilipi( pratilipiFilter, null, 2 ).getDataList() );
//		pratilipiFilter.setLanguageId( 5965057007550464L );
//		poemIdList.addAll( searchAccessor.searchPratilipi( pratilipiFilter, null, 2 ).getDataList() );
//		pratilipiFilter.setLanguageId( 6319546696728576L );
//		poemIdList.addAll( searchAccessor.searchPratilipi( pratilipiFilter, null, 2 ).getDataList() );
		
		//Hindi Poem
		poemIdList.add( 5121659782561792L );
		poemIdList.add( 4812721711218688L );
		//Gujarati Poem
		poemIdList.add( 5733593619890176L );
		poemIdList.add( 6577280218300416L );
		//Tamil Poem
		poemIdList.add( 4922215912964096L );
		poemIdList.add( 4837289477799936L );

		
		PratilipisContent pratilipisContent = (PratilipisContent) dataAccessor.getPageContent( 5197655504322560L );
		pratilipisContent.setPratilipiIdList( bookIdList );
		pratilipisContent.setLastUpdated( new Date() );
		dataAccessor.createOrUpdatePageContent( pratilipisContent );

		pratilipisContent = (PratilipisContent) dataAccessor.getPageContent( 5742692155785216L );
		pratilipisContent.setPratilipiIdList( storyIdList );
		pratilipisContent.setLastUpdated( new Date() );
		dataAccessor.createOrUpdatePageContent( pratilipisContent );

		pratilipisContent = (PratilipisContent) dataAccessor.getPageContent( 5747690960846848L );
		pratilipisContent.setPratilipiIdList( poemIdList );
		pratilipisContent.setLastUpdated( new Date() );
		dataAccessor.createOrUpdatePageContent( pratilipisContent );
		
		
		for( Long pratilipiId : bookIdList ) {
			Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
			pratilipi.setRelevanceOffset( pratilipi.getRelevanceOffset() - 5 );
			dataAccessor.createOrUpdatePratilipi( pratilipi );
			PratilipiContentHelper.updatePratilipiSearchIndex( pratilipi.getId(), null, this.getThreadLocalRequest() );
		}
		
		for( Long pratilipiId : storyIdList ) {
			Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
			pratilipi.setRelevanceOffset( pratilipi.getRelevanceOffset() - 5 );
			dataAccessor.createOrUpdatePratilipi( pratilipi );
			PratilipiContentHelper.updatePratilipiSearchIndex( pratilipi.getId(), null, this.getThreadLocalRequest() );
		}

		for( Long pratilipiId : poemIdList ) {
			Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
			pratilipi.setRelevanceOffset( pratilipi.getRelevanceOffset() - 5 );
			dataAccessor.createOrUpdatePratilipi( pratilipi );
			PratilipiContentHelper.updatePratilipiSearchIndex( pratilipi.getId(), null, this.getThreadLocalRequest() );
		}
		
	}

	@SuppressWarnings("unused")
	private void createOrUpdateFacebookCredentials( String appId, String appSecret ) {
		Map<String, String> map = new HashMap<>();
		map.put( "appId", appId );
		map.put( "appSecret", appSecret );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		AppProperty appProperty = dataAccessor.newAppProperty( "Facebook.Credentials" );
		appProperty.setValue( map );
		dataAccessor.createOrUpdateAppProperty( appProperty );
	}

	@SuppressWarnings("unused")
	private void batchProcessPratilipi( String processType ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		List<Long> pratilipiIdList = dataAccessor.getPratilipiIdList( new PratilipiFilter(), null, null ).getDataList();
		List<Task> taskList = new ArrayList<>( pratilipiIdList.size() );
		for( Long pratilipiId : pratilipiIdList ) {
			Task task = TaskQueueFactory.newTask()
					.addParam( "pratilipiId", pratilipiId.toString() )
					.addParam( processType, "true" )
					.setUrl( "/pratilipi/process" );
			taskList.add( task );
		}
		TaskQueueFactory.getPratilipiTaskQueue().addAll( taskList );
	}
	
	@SuppressWarnings("unused")
	private void batchProcessAuthor( String processType ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		List<Long> authorIdList = dataAccessor.getAuthorIdList( new AuthorFilter(), null, null ).getDataList();
		List<Task> taskList = new ArrayList<>( authorIdList.size() );
		for( Long authorId : authorIdList ) {
			Task task = TaskQueueFactory.newTask()
					.addParam( "authorId", authorId.toString() )
					.addParam( processType, "true" )
					.setUrl( "/author/process" );
			taskList.add( task );
		}
		TaskQueueFactory.getPratilipiTaskQueue().addAll( taskList );
	}
	
	@SuppressWarnings("unused")
	private void createPage( String pageUriAlias, PageContent pageContent ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		Page page = dataAccessor.getPage( pageUriAlias );
		if( page == null ) {
			page = dataAccessor.newPage();
			page.setType( ClaymusPageType.GENERIC.toString() );
			page.setUriAlias( pageUriAlias );
			page.setCreationDate( new Date() );
			page = dataAccessor.createOrUpdatePage( page );
			
			pageContent.setCreationDate( new Date() );
			pageContent.setLastUpdated( new Date() );
			pageContent.setPageId( page.getId() );
			pageContent = dataAccessor.createOrUpdatePageContent( pageContent );
			
			page.setPrimaryContentId( pageContent.getId() );
			page = dataAccessor.createOrUpdatePage( page );
		}
	}

	@SuppressWarnings("unused")
	private void createEvent( String nameEn ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		Event event = dataAccessor.newEvent();
		event.setNameEn( nameEn );
		event.setStartDate( new Date() );
		event.setEndDate( new Date() );
		event.setCreationDate( new Date() );
		event = dataAccessor.createOrUpdateEvent( event );
		
		Page page = dataAccessor.newPage();
		page.setType( PratilipiPageType.EVENT.toString() );
		page.setUri( PratilipiPageType.EVENT.getUrlPrefix() + event.getId() );
		page.setPrimaryContentId( event.getId() );
		page.setCreationDate( new Date() );
		page = dataAccessor.createOrUpdatePage( page );
	}
	
	@SuppressWarnings("unused")
	private void createUserRole( Long userId ){
		com.claymus.data.access.DataAccessor dataAccessor = 
				com.claymus.data.access.DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		UserRole userRole = dataAccessor.newUserRole();
		userRole.setUserId( userId );
		userRole.setRoleId( "administrator" );
		
		userRole = dataAccessor.createOrUpdateUserRole( userRole );
	}
}
