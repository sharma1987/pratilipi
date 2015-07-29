package com.pratilipi.api;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Post;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.data.transfer.AccessToken;
import com.claymus.data.transfer.shared.UserData;
import com.claymus.pagecontent.user.UserContentHelper;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.pratilipi.api.shared.PostOAuthRequest;
import com.pratilipi.api.shared.PostOAuthResponse;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;


@SuppressWarnings("serial")
@Bind( uri= "/oauth" )
public class OAuthApi extends GenericApi {

	private final Logger logger = Logger.getLogger( OAuthApi.class.getName() );
	
	@Post
	public PostOAuthResponse getOAuth( PostOAuthRequest request )
			throws InvalidArgumentException, InsufficientAccessException, 
					GeneralSecurityException, IOException, JSONException {
		logger.log( Level.INFO, "Credentials : " + request.getUserId() + "/" + request.getUserSecret() );
		if( request.getLoginType() == null && request.getUserId() == null )
			throw new InvalidArgumentException( "User Id cannot be null" );
		
		if( request.getLoginType() == null && request.getUserSecret() == null )
			throw new InvalidArgumentException( "User Secret Cannot be null for non social login" );
		
		if( request.getLoginType() != null && ( request.getToken() == null || request.getSocialId() == null ))
			throw new InvalidArgumentException( "Access Token or Social Id is missing" );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		AccessToken accessToken = dataAccessor.newAccessToken();
		UserData userData = new UserData();
		userData.setEmail( request.getUserId() );
		if( request.hasUserSecret() )
			userData.setPassword( request.getUserSecret() );
		
		if( request.getLoginType() != null && request.getLoginType().toLowerCase().equals( "facebook" ) ){
			accessToken = UserContentHelper.facebookLogin( request.getSocialId(), request.getToken(), this.getThreadLocalRequest() );
		} else if( request.getLoginType() != null && request.getLoginType().toLowerCase().equals( "google" ) ) {
			accessToken = UserContentHelper.googleLogin( request.getToken(), request.getSocialId(), this.getThreadLocalRequest() );
		} else {
			accessToken = UserContentHelper.userLogin( userData, this.getThreadLocalRequest() );
		}
		
		if( accessToken == null )
			return null;
		
		userData = UserContentHelper.createUserData( accessToken.getUserId(), this.getThreadLocalRequest() );

		
		return new PostOAuthResponse( accessToken.getId(), 
										userData.getName(),
										"",
										accessToken.getExpiry().getTime() );
	}
	
}
