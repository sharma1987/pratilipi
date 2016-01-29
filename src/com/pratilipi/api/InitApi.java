package com.pratilipi.api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
import com.claymus.commons.shared.CommentFilter;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.AppProperty;
import com.claymus.data.transfer.Comment;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.User;
import com.claymus.data.transfer.UserRole;
import com.claymus.taskqueue.Task;
import com.pratilipi.common.type.PageType;
import com.pratilipi.commons.shared.AuthorFilter;
import com.pratilipi.commons.shared.CategoryType;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.commons.shared.UserPratilipiFilter;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Category;
import com.pratilipi.data.transfer.Event;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.PratilipiCategory;
import com.pratilipi.data.transfer.UserPratilipi;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipis.PratilipisContent;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri= "/init" )
public class InitApi extends GenericApi {
	
	private static final Logger logger = 
			Logger.getLogger( InitApi.class.getName() );

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
		bookIdList.add( 5940551941619712L );
		bookIdList.add( 4704962953084928L );
		//Gujarati Book
		bookIdList.add( 4964375890755584L );
		bookIdList.add( 4889317092622336L );
		//Tamil Book
		bookIdList.add( 5673056773079040L );
		bookIdList.add( 5715876565221376L );

		
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
		storyIdList.add( 4742219462344704L );
		//Gujarati Story
		storyIdList.add( 6315613869834240L );
		storyIdList.add( 6490974469488640L );
		//Tamil Story
		storyIdList.add( 5121346084274176L );
		storyIdList.add( 4777177878888448L );

		
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
		poemIdList.add( 5722154834329600L );
		poemIdList.add( 5728633066881024L );
		//Gujarati Poem
		poemIdList.add( 5133093258133504L );
		poemIdList.add( 5742782224269312L );
		//Tamil Poem
		poemIdList.add( 4548768693223424L );
		poemIdList.add( 5082598573342720L );

		
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
			page.setType( PageType.GENERIC.toString() );
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
	private void createEvent( String nameEn, Long languageId ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		Event event = dataAccessor.newEvent();
		event.setNameEn( nameEn );
		event.setLanguageId( languageId );
		event.setStartDate( new Date() );
		event.setEndDate( new Date() );
		event.setCreationDate( new Date() );
		event = dataAccessor.createOrUpdateEvent( event );
		
		Page page = dataAccessor.newPage();
		page.setType( PageType.EVENT.toString() );
		page.setUri( PageType.EVENT.getUrlPrefix() + event.getId() );
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

	@SuppressWarnings( "unused" )
	private Boolean deleteUser( String email ){
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		User user = dataAccessor.getUserByEmail( email );
		if( user == null ){
			logger.log( Level.INFO, "User " + email + " is not present in database");
			return false;
		}
		//DELETE ALL COMMENT ENTRIES
		CommentFilter commentFilter = new CommentFilter();
		commentFilter.setUserId( user.getId() );
		DataListCursorTuple<Comment> commentCursorList = dataAccessor.getCommentList(commentFilter, null, 200 );
		for( Comment comment : commentCursorList.getDataList() ){
			Boolean isDeleted = dataAccessor.deleteComment( comment.getId() );
			if( isDeleted ){
				logger.log(Level.INFO, "Comment Deleted " + comment.getId() );
			} else{
				logger.log(Level.INFO, "Unable to delete comment : " + comment.getId() );
				return false;
			}
		}

		UserPratilipiFilter userPratilipiFilter = new UserPratilipiFilter();
		userPratilipiFilter.setUserId( user.getId() );
		List<UserPratilipi> userPratilipiList = dataAccessor.getUserPratilipiList( userPratilipiFilter );
		logger.log(Level.INFO, "UserPratilipi List Size : " + userPratilipiList.size() );
		int countUserPratilipiDeleted=0;
		//DELETE USER_PRATILIPI ENTRIES	
		for( UserPratilipi userPratilipi : userPratilipiList ){
			if( deleteUserPratilipi( userPratilipi ) )
				countUserPratilipiDeleted++;
			else{
				logger.log(Level.SEVERE, "Unable to delete UserPratilipi : " + userPratilipi.getId() );
				return false;
			}
		}
		logger.log(Level.INFO, "NUMBER OF USER_PRATILIPI DELETED : " + countUserPratilipiDeleted );
		
		//DELETE AUTHOR PROFILE
		Author author = dataAccessor.getAuthorByEmailId( user.getEmail() );
		if( author != null ){
			if( !deleteAuthor( author.getEmail()) )
				return false;
		}

		//DELETE PAGE ENTRY
		Page page = dataAccessor.getPage( "USER", user.getId() );
		if( page != null ){
			if( !deletePage( page ))
				return false;
		}
		
		//DELETE USER_ROLES
		List<UserRole> userRoleList = dataAccessor.getUserRoleList( user.getId());
		for( UserRole userRole : userRoleList ){
			Boolean isDeleted = dataAccessor.deleteUserRole( userRole.getId() );
			if( isDeleted ){
				logger.log(Level.INFO, "UserRole deleted : " + userRole.getId() );
			} else{
				logger.log(Level.INFO, "Unable to delete UserRole : " + userRole.getId() );
				return false;
			}
		}
		//DELETE USER
		Boolean isUserDeleted = dataAccessor.deleteUser( user.getId() );
		if( isUserDeleted ){
			logger.log( Level.INFO, "User Deleted : " + user.getEmail() );
			return true;
		} else{
			logger.log( Level.SEVERE, "Unable to delete User : " + user.getEmail() );
			return false;
		}
		
	}
	
	private Boolean deleteAuthor(String email){
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		Author author = dataAccessor.getAuthorByEmailId( email );
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setAuthorId( author.getId() );
		DataListCursorTuple<Pratilipi> pratilipiCursorList = dataAccessor.getPratilipiList( pratilipiFilter, null, 200 );
		int contentsCount = pratilipiCursorList.getDataList().size();
		logger.log(Level.INFO, "Number of content peices : " + contentsCount );
		int deletedPratilipiCount = 0;
		//DELETE PRATILIPI ENTRIES
		for( Pratilipi pratilipi : pratilipiCursorList.getDataList() ){
			if( deletePratilipi( pratilipi.getId() ) )
				deletedPratilipiCount++;
			else{
				logger.log(Level.SEVERE, "Unable to delete Pratilipi : " + pratilipi.getTitleEn() );
				return false;
			}
		}
		logger.log(Level.INFO, "Number of content peices deleted : " + deletedPratilipiCount );
		//DELETE PAGE ENTRIES
		Page page = dataAccessor.getPage( "AUTHOR", author.getId() );
		if( !deletePage( page ))
			return false;
		Page authorDashboardPage = dataAccessor.getPage( "AUTHOR_DASHBOARD", author.getId());
		if( !deletePage( authorDashboardPage ))
			return false;
		
		//DELETE AUTHOR
		if( dataAccessor.deleteAuthor( author.getId() )){
			logger.log(Level.INFO, "Author Deleted : " + author.getEmail() );
			return true;
		} else{
			logger.log(Level.INFO, "Unable to delete Author : " + author.getEmail() );
			return false;
		}
		
	}
	
	private Boolean deletePratilipi(Long pratilipiId){
		Boolean isDeleted = false;
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		List<UserPratilipi> userPratilipiList = dataAccessor.getUserPratilipiList( pratilipiId );
		logger.log(Level.INFO, "UserPratilipi List Size : " + userPratilipiList.size() );
		int countUserPratilipiDeleted=0;
		//DELETE USER_PRATILIPI ENTRIES	
		for( UserPratilipi userPratilipi : userPratilipiList ){
			isDeleted = deleteUserPratilipi( userPratilipi );
			if( isDeleted )
				countUserPratilipiDeleted++;
			else{
				logger.log(Level.SEVERE, "Unable to delete UserPratilipi : " + userPratilipi.getId() );
				return false;
			}
		}
		logger.log(Level.INFO, "NUMBER OF USER_PRATILIPI DELETED : " + countUserPratilipiDeleted );
		
		
		//DELETE PAGE ENTRY
		Page page = dataAccessor.getPage( "PRATILIPI", pratilipiId );
		if( !deletePage( page ))
			return false;
		
		isDeleted = dataAccessor.deletePratilipi( pratilipiId );
		if( isDeleted )
			logger.log(Level.INFO, "Pratilipi Deleted : " + pratilipiId );
		else{
			logger.log(Level.INFO, "Unable to deleted pratilipi : " + pratilipiId );
			return false;
		}
		return true;
	}
	
	private Boolean deleteUserPratilipi( UserPratilipi userPratilipi ){
		Boolean isDeleted = false;
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		//DELETE COMMENT ENTRIES
		CommentFilter commentFilter = new CommentFilter();
		commentFilter.setParentId( userPratilipi.getId() );
		DataListCursorTuple<Comment> commentTupleList = dataAccessor.getCommentList(commentFilter, null, 200);
		logger.log(Level.INFO, "Comment List Size : " + commentTupleList.getDataList().size() );
		int countCommentDeleted = 0;
		for( Comment comment : commentTupleList.getDataList() ){
			isDeleted = dataAccessor.deleteComment( comment.getId() );
			if( isDeleted ){
				logger.log(Level.INFO, "Comment Deleted : " + comment.getId() );
				countCommentDeleted++;
			}
			else{
				logger.log(Level.SEVERE, "Unable to delete comment " + comment.getId() + " of parent " + comment.getParentId() );
				return false;
			}
		}
		logger.log(Level.INFO, "NUMBER OF COMMENT DELETED : " + countCommentDeleted );
		isDeleted = dataAccessor.deleteUserPratilipi( userPratilipi );
		logger.log(Level.INFO, "UserPratilipi " + userPratilipi.getId() + " isDeleted : " + isDeleted );
		if( isDeleted ){
			logger.log(Level.INFO, "UserPratilipi Deleted : " + userPratilipi.getId() );
			return true;
		} else{
			logger.log(Level.SEVERE, "Unable to delete UserPratilipi " + userPratilipi.getId() );
			return false;
		}
	}
	
	private Boolean deletePage( Page page ){
		if( page == null )
			return false;
		
		Boolean isDeleted = false;
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		//TODO : DELETE PAGE_CONTENT ENTRIES
		List<PageContent> pageContentList = dataAccessor.getPageContentList( page.getId() );
		logger.log(Level.INFO, "PageContent List Size : " + pageContentList.size() );
		int countPageContentDeletd = 0;
		for( PageContent pageContent : pageContentList ){
			isDeleted = dataAccessor.deletePageContent( pageContent.getId() );
			if( isDeleted ){
				logger.log(Level.INFO, "PageContent Deleted : " + pageContent.getId() );
				countPageContentDeletd++;
			} else{
				logger.log(Level.SEVERE, "Unable to delete PageContent " + pageContent.getId() + " of page " + pageContent.getPageId() );
				return false;
			}
		}
		logger.log(Level.INFO, "Number of PageContent Deleted : " + countPageContentDeletd );
		isDeleted = dataAccessor.deletePage( page.getId() );
		if( isDeleted ){
			logger.log(Level.INFO, "Page Deleted : " + page.getId() );
		} else{
			logger.log(Level.SEVERE, "Unable to delete Page " + page.getId() + " of pratilipi " + page.getPrimaryContentId() );
			return false;
		}
		return true;
	}

	@SuppressWarnings( "unused" )
	private void eventEntries(String filename){
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor(this.getThreadLocalRequest());
		BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filename));
            try {
                String x;
                while ( (x = br.readLine()) != null ) {
                    // printing out each line in the file
                    Page page = dataAccessor.getPage(x);
                    if( page == null ){
                    	logger.log(Level.SEVERE, "URI ALIAS : " + x);
                    } else{
	                    Long pratilipiId = page.getPrimaryContentId();
	                    String message = "pratilipiIdList.add( " + pratilipiId + "L );";
	                    logger.log(Level.SEVERE, message);
                    }
                } 
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }
	}
}
