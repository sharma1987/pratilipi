package com.pratilipi.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.api.shared.GenericRequest;
import com.claymus.api.shared.GenericResponse;
import com.claymus.commons.shared.ClaymusPageType;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.AppProperty;
import com.claymus.data.transfer.Page;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.UserRole;
import com.claymus.taskqueue.Task;
import com.pratilipi.commons.shared.AuthorFilter;
import com.pratilipi.commons.shared.CategoryType;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.commons.shared.PratilipiPageType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Category;
import com.pratilipi.data.transfer.Event;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.PratilipiCategory;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipis.PratilipisContent;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri= "/init" )
public class InitApi extends GenericApi {

	@Get
	public GenericResponse getInit( GenericRequest request ) 
			throws InvalidArgumentException, UnexpectedServerException, InsufficientAccessException {

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
		bookIdList.add( 5977456922066944L );
		bookIdList.add( 6322534794395648L );
		//Gujarati Book
		bookIdList.add( 6317853942743040L );
		bookIdList.add( 5997080149491712L );
		//Tamil Book
		bookIdList.add( 5421718361341952L );
		bookIdList.add( 6142931610107904L );

		
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
		storyIdList.add( 4957339794800640L );
		storyIdList.add( 5669071953592320L );
		//Gujarati Story
		storyIdList.add( 5135031164993536L );
		storyIdList.add( 5443418582941696L );
		//Tamil Story
		storyIdList.add( 6006301888872448L );
		storyIdList.add( 5596903794278400L );

		
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
		poemIdList.add( 6681820720529408L );
		poemIdList.add( 4836881011310592L );
		//Gujarati Poem
		poemIdList.add( 5675485371564032L );
		poemIdList.add( 5638054622527488L );
		//Tamil Poem
		poemIdList.add( 4578245915705344L );
		poemIdList.add( 6201158381076480L );

		
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
	private void createUserRole( String email ) 
			throws InvalidArgumentException{
		com.claymus.data.access.DataAccessor dataAccessor = 
				com.claymus.data.access.DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		User user = dataAccessor.getUserByEmail( email );
		if( user == null )
			throw new InvalidArgumentException( "Email is not registered" );
		UserRole userRole = dataAccessor.newUserRole();
		userRole.setUserId( user.getId() );
		userRole.setRoleId( "administrator" );
		
		userRole = dataAccessor.createOrUpdateUserRole( userRole );
	}

	@SuppressWarnings( "unused" )
	private void createLanguage( String name ){
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		Language language = dataAccessor.newLanguage();
		language.setHidden( false );
		language.setCreationDate( new Date() );
		language.setNameEn( name );
		
		language = dataAccessor.createOrUpdateLanguage( language );
	}
	
	@SuppressWarnings( "unused" )
	private void createCategory( String name, String plural, CategoryType categoryType, Boolean isHidden ){
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		Category category = dataAccessor.newCategory();
		category.setName( name );
		category.setPlural( plural );
		category.setType( categoryType );
		category.setCreationDate( new Date() );
		category.setHidden( isHidden );
		dataAccessor.createOrUpdateCategory( category );
	}

	@SuppressWarnings( "unused" )
	private void updateSearchIndex( Long categoryId ) throws InvalidArgumentException, UnexpectedServerException{
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		
		Logger logger = Logger.getLogger( InitApi.class.getName() );
		
		DataListCursorTuple<PratilipiCategory> pratilipiCategoryCursorTupleList = 
				dataAccessor.getCategoryPratilipiList( categoryId, 1000, null );
		
		logger.log( Level.INFO, "Number of results : " + pratilipiCategoryCursorTupleList.getDataList().size() );
		
		for( PratilipiCategory pratilipiCategory : pratilipiCategoryCursorTupleList.getDataList() ){
			PratilipiContentHelper.updatePratilipiSearchIndex( pratilipiCategory.getPratilipiId(), null, this.getThreadLocalRequest() );
		}
	}
}
