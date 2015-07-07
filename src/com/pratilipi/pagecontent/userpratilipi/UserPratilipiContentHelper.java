package com.pratilipi.pagecontent.userpratilipi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.shared.NotificationType;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.data.transfer.AccessToken;
import com.claymus.data.transfer.User;
import com.claymus.pagecontent.PageContentHelper;
import com.claymus.taskqueue.Task;
import com.claymus.taskqueue.TaskQueue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pratilipi.commons.shared.BookmarkRequestType;
import com.pratilipi.commons.shared.UserPratilipiFilter;
import com.pratilipi.commons.shared.UserReviewState;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.UserPratilipi;
import com.pratilipi.data.transfer.shared.UserPratilipiData;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.userpratilipi.gae.UserPratilipiContentEntity;
import com.pratilipi.pagecontent.userpratilipi.shared.UserPratilipiContentData;
import com.pratilipi.taskqueue.TaskQueueFactory;

public class UserPratilipiContentHelper extends PageContentHelper<
		UserPratilipiContent,
		UserPratilipiContentData,
		UserPratilipiContentProcessor>{
	
	private static final Gson gson = new GsonBuilder().create();
	

	@Override
	public String getModuleName() {
		return "User-Pratilipi";
	}

	@Override
	public Double getModuleVersion() {
		return 5.0;
	}
	

	public static UserPratilipiContent newUserPratilipiContent() {
		return new UserPratilipiContentEntity();
	}

	
	public static Boolean hasRequestAccessToAddBookmarks( HttpServletRequest request ){
		return ClaymusHelper.get( request ).isUserLoggedIn();
	}

	public static Boolean hasRequestAccessToAddToLibrary( HttpServletRequest request ){
		return ClaymusHelper.get( request ).isUserLoggedIn();
	}
	

	public static UserPratilipiData createUserPratilipiData( Long pratilipiId, HttpServletRequest request ){
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
		UserPratilipi userPratilipi = dataAccessor.getUserPratilipi( accessToken.getUserId(), pratilipiId );
		
		UserPratilipiData userPratilipiData = createUserPratilipiData( userPratilipi, request );
		
		return userPratilipiData;
	}
	
	public static UserPratilipiData createUserPratilipiData( UserPratilipi userPratilipi, HttpServletRequest request ){
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		User user = dataAccessor.getUser( userPratilipi.getUserId() );
		
		UserPratilipiData userPratilipiData = new UserPratilipiData();
		userPratilipiData.setUserId( user.getId() );
		userPratilipiData.setUserName( user.getFirstName() + " " + user.getLastName() );
		userPratilipiData.setPratilipiId( userPratilipi.getPratilipiId() );
		userPratilipiData.setRating( userPratilipi.getRating() );
		userPratilipiData.setReview( userPratilipi.getReview() );
		userPratilipiData.setReviewState( userPratilipi.getReviewState() );
		userPratilipiData.setReviewDate( userPratilipi.getReviewDate() );
		userPratilipiData.setReviewLastUpdatedDate( userPratilipi.getReviewLastUpdateDate() );
		userPratilipiData.setBookmarks( userPratilipi.getBookmarks() );
		userPratilipiData.setAddedToLib( userPratilipi.isAddedtoLib() );
		
		return userPratilipiData;
	}
	
	
	public static UserPratilipiData saveUserPratilipi( UserPratilipiData userPratilipiData, HttpServletRequest request )
			throws InsufficientAccessException, InvalidArgumentException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		AccessToken accessToken = ( AccessToken ) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
		
		Pratilipi pratilipi = dataAccessor.getPratilipi( userPratilipiData.getPratilipiId() );
		if( pratilipi == null )
			throw new InvalidArgumentException( "Pratilipi Id is not valid" );
		
		UserPratilipi userPratilipi = dataAccessor.getUserPratilipi( accessToken.getUserId(), userPratilipiData.getPratilipiId() );
		
		if( userPratilipi == null ) {	//new record
			userPratilipi = dataAccessor.newUserPratilipi();
			userPratilipi.setUserId( accessToken.getUserId() );
			userPratilipi.setPratilipiId( userPratilipiData.getPratilipiId() );
		}
		
		if( userPratilipiData.hasReview() ){
			
			if( !PratilipiContentHelper.hasRequestAccessToAddPratilipiReview( request, pratilipi ) )
				throw new InsufficientAccessException();

			String notificationType = null;
	
			if( userPratilipi.getReviewDate() == null ){
				userPratilipi.setReviewDate( new Date() );
				userPratilipi.setReviewState( UserReviewState.PENDING_APPROVAL );
				
				notificationType  = NotificationType.REVIEW_ADD.toString();
			} else
				notificationType = NotificationType.REVIEW_UPDATE.toString();
			
			userPratilipi.setReview( userPratilipiData.getReview() );
			userPratilipi.setReviewLastUpdatedDate( new Date() );
	
			userPratilipi = dataAccessor.createOrUpdateUserPratilipi( userPratilipi );
			
			Task task = TaskQueueFactory.newTask();
			Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
			task.addParam( "userId", userPratilipi.getUserId().toString() );
			task.addParam( "recipientId", author.getUserId().toString() );
			task.addParam( "pratilipiId", pratilipi.getId().toString() );
			task.addParam( "notificationType", notificationType );
			
			TaskQueue taskQueue = TaskQueueFactory.getNotificationTaskQueue();
			taskQueue.add( task );
		}
		
		if( userPratilipiData.hasBookmarks() ) {
			if( !hasRequestAccessToAddBookmarks( request ))
				throw new InsufficientAccessException();
			
			if( userPratilipiData.getBookmarkRequestType() == null )
				throw new InvalidArgumentException( "Bookmark request type is not specified" );
			
			String bookmarkString = userPratilipi.getBookmarks();
			JsonArray bookmarkGson = userPratilipi.getBookmarks() != null
					? gson.fromJson( bookmarkString, JsonElement.class ).getAsJsonArray()
					: new JsonArray();
			
			if( userPratilipiData.getBookmarkRequestType() == BookmarkRequestType.ADD ){
				for( int i = 0; i < bookmarkGson.size(); i++ ){
					String bookmark = bookmarkGson.get( i ).toString();
					if( bookmark.contains( userPratilipiData.getBookmarks() ))
							return createUserPratilipiData( userPratilipi, request );
				}
				
				JsonObject newBookmark = new JsonObject();
				newBookmark.addProperty( "title", "Page " + userPratilipiData.getBookmarks() );
				newBookmark.addProperty( "pageNo", userPratilipiData.getBookmarks() );
				bookmarkGson.add( newBookmark );
			} else if( userPratilipiData.getBookmarkRequestType() == BookmarkRequestType.REMOVE ){
				JsonArray newBookmarkArray = new JsonArray();
				for( int i = 0; i < bookmarkGson.size(); i++ ){
					String bookmark = bookmarkGson.get( i ).toString();
					if( !bookmark.contains( userPratilipiData.getBookmarks() ))
						newBookmarkArray.add( bookmarkGson.get( i ).getAsJsonObject() );
				}
				bookmarkGson = newBookmarkArray;
			}
			
			userPratilipi.setBookmarks( bookmarkGson.toString() );
		}
		
		if( userPratilipiData.hasAddedToLib() ){
			if( !hasRequestAccessToAddToLibrary( request ) )
				throw new InsufficientAccessException();
			
			userPratilipi.setAddedToLib( userPratilipiData.isAddedToLib() );
			
		}
		
		userPratilipi = dataAccessor.createOrUpdateUserPratilipi( userPratilipi );
		
		return createUserPratilipiData( userPratilipi, request );
	}
	
	public static List<UserPratilipiData> getReviewDataList( UserPratilipiFilter userPratilipiFilter, HttpServletRequest request ){
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		List<UserPratilipi> userPratilipiList = dataAccessor.getUserPratilipiList( userPratilipiFilter );
		
		List<UserPratilipiData> userPratilipiDataList = new ArrayList<>();
		
		for( UserPratilipi userPratilipi : userPratilipiList ){
			if( userPratilipi.getReview() != null && !userPratilipi.getReview().isEmpty() )
				userPratilipiDataList.add( createUserPratilipiData( userPratilipi, request ) );
		}
		
		return userPratilipiDataList;
	}
	
	public static List<Long> getUserLibrary( UserPratilipiFilter userPratilipiFilter, HttpServletRequest request ){
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		List<UserPratilipi> userPratilipiList = dataAccessor.getUserPratilipiList( userPratilipiFilter );
		
		List<Long> pratilipiIdList = new ArrayList<>();
		
		for( UserPratilipi userPratilipi : userPratilipiList ){
			if( userPratilipi.isAddedtoLib() != null && userPratilipi.isAddedtoLib() )
				pratilipiIdList.add( userPratilipi.getPratilipiId() );
		}
		
		return pratilipiIdList;
	}
}
