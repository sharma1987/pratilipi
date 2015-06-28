package com.pratilipi.api;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Post;
import com.claymus.api.annotation.Put;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.AccessToken;
import com.claymus.data.transfer.shared.UserData;
import com.claymus.pagecontent.user.UserContentHelper;
import com.pratilipi.api.shared.PostUserProfileRequest;
import com.pratilipi.api.shared.PostUserProfileResponse;
import com.pratilipi.api.shared.PutUserUpdateRequest;
import com.pratilipi.api.shared.PutUserUpdateResponse;

@SuppressWarnings( "serial" )
@Bind( uri = "/userprofile" )
public class UserProfileApi extends GenericApi {

	@Post
	public PostUserProfileResponse getUserProfile( PostUserProfileRequest request ) 
			throws InvalidArgumentException{
		
		AccessToken accessToken = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() )
										.getAccessToken( request.getAccessToken() );
		if( accessToken == null )
			throw new InvalidArgumentException( "Invalid Access Token" );
		if( accessToken.getUserId() == 0L )
			throw new InvalidArgumentException( "User is not logged in!" );
		
		UserData userData = UserContentHelper.createUserData( accessToken.getUserId(), this.getThreadLocalRequest() );
		
		return new PostUserProfileResponse( userData );
	}
	
	@Put
	public PutUserUpdateResponse updateUserProfile( PutUserUpdateRequest request ) 
			throws InsufficientAccessException, InvalidArgumentException{
		
		AccessToken accessToken = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() )
												.getAccessToken( request.getAccessToken() );
		if( accessToken == null )
			throw new InvalidArgumentException( "Access Token Is Invalid" );
		if( accessToken.getUserId() == 0L )
			throw new InsufficientAccessException( "User is not logged in" );
		
		UserData userData = new UserData();
		userData.setId( accessToken.getUserId() );
		if( request.hasName() )
			userData.setName( request.getName() );
		if( request.hasEmail() )
			userData.setEmail( request.getEmail() );
		if( request.hasPassword() )
			userData.setPassword( request.getPassword() );
		if( request.hasDateOfBirth() )
			userData.setDateOfBirth( request.getDateOfBirth() );
		if( request.hasSex() )
			userData.setSex( request.getSex() );

		userData = UserContentHelper.updateUserProfile( userData, this.getThreadLocalRequest() );
		
		return new PutUserUpdateResponse( userData );
	}
	
}
