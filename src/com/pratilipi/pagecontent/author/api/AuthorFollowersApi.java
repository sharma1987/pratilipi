package com.pratilipi.pagecontent.author.api;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Put;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.taskqueue.Task;
import com.pratilipi.data.transfer.shared.FollowerData;
import com.pratilipi.pagecontent.author.AuthorContentHelper;
import com.pratilipi.pagecontent.author.api.shared.PutAuthorFollowerRequest;
import com.pratilipi.pagecontent.author.api.shared.PutAuthorFollowerResponse;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/author/followers" )
public class AuthorFollowersApi extends GenericApi {
	
	@Put
	public PutAuthorFollowerResponse doPost( PutAuthorFollowerRequest request )
		throws InsufficientAccessException {

		if(request.getUserId().equals( 0L ))
			throw new InsufficientAccessException( "User is not logged in" );
		
		FollowerData followerData = new FollowerData();
		followerData.setAuthorId( request.getAuthorId() );
		followerData.setUserId( request.getUserId() );
		
		int followerCount = 
				AuthorContentHelper.saveFollower(followerData, request.isFollowing(), this.getThreadLocalRequest());
		
		if(request.isFollowing()){
			Logger.getLogger(AuthorFollowersApi.class.getSimpleName())
				.log(Level.SEVERE, "Creating Task");
			Task followeeTask = TaskQueueFactory.newTask()
					.addParam( "followerId", request.getUserId().toString() )
					.addParam( "recipientId", request.getAuthorId().toString() )
					.addParam( "notificationType", "FOLLOWEE" );
			TaskQueueFactory.getNotificationTaskQueue().add( followeeTask );
			
			Task followerTask = TaskQueueFactory.newTask()
					.addParam( "recipientId", request.getUserId().toString() )
					.addParam( "authorId", request.getAuthorId().toString() )
					.addParam( "notificationType", "FOLLOWER" );
			TaskQueueFactory.getNotificationTaskQueue().add( followerTask );
		}
		return new PutAuthorFollowerResponse( followerCount );
	}
}
