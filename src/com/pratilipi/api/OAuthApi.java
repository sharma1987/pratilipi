package com.pratilipi.api;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Post;
import com.claymus.commons.server.ValidateFbAccessToken;
import com.claymus.commons.server.ValidateGoogleAccessToken;
import com.claymus.commons.shared.UserStatus;
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
		if( request.getUserId() == null )
			throw new InvalidArgumentException( "User Id cannot be null" );
		
		if( request.getLoginType() == null && request.getUserSecret() == null )
			throw new InvalidArgumentException( "User Secret Cannot be null for non social login" );
		
		if( request.getLoginType() != null && request.getAccessToken() == null )
			throw new InvalidArgumentException( "Access Token cannot be null for login type " + request.getLoginType() );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		AccessToken accessToken = dataAccessor.newAccessToken();
		UserData userData = new UserData();
		userData.setEmail( request.getUserId() );
		if( request.hasFirstName() )
			userData.setFirstName( request.getFirstName() );
		if( request.hasLastName() )
			userData.setLastName( request.getLastName() );
		if( request.hasUserSecret() )
			userData.setPassword( request.getUserSecret() );
		if( request.hasCampaign() )
			userData.setCampaign( request.getCampaign() );
		if( request.hasReferer() )
			userData.setReferer( request.getReferer() );
		
		if( request.getLoginType() != null && request.getLoginType().toLowerCase().equals( "facebook" ) ){
			ValidateFbAccessToken validateToken = new ValidateFbAccessToken( request.getAccessToken() );
			Map<String, String> facebookCredentials = dataAccessor.getAppProperty( "Facebook.Credentials" ).getValue();
			if( validateToken.isValid( facebookCredentials ) ) {
				userData.setStatus( UserStatus.POSTLAUNCH_SIGNUP_SOCIALLOGIN );
				accessToken = UserContentHelper.socialLogin( userData, this.getThreadLocalRequest() );
				logger.log( Level.INFO, "Facebook User Id " + accessToken.getUserId() );
			} else
				throw new InvalidArgumentException( "Invalid Facebook Access Token" );
		} else if( request.getLoginType() != null && request.getLoginType().toLowerCase().equals( "google" ) ) {
			ValidateGoogleAccessToken validateGoogleAccessToken = new ValidateGoogleAccessToken();
			if( validateGoogleAccessToken.isValid( request.getAccessToken() )){
				userData.setStatus( UserStatus.ANDROID_SIGNUP_GOOGLE );
				accessToken = UserContentHelper.socialLogin( userData, this.getThreadLocalRequest() );
				logger.log( Level.INFO, "Google User Id " + accessToken.getUserId() );
			} else
				throw new InvalidArgumentException( "Invalid Google Access Token" );
		} else {
			accessToken = UserContentHelper.userLogin( userData, this.getThreadLocalRequest() );
			logger.log( Level.INFO, "Pratilipi User Id " + accessToken.getUserId() );
		}

		
		return new PostOAuthResponse( accessToken.getId() , accessToken.getExpiry().getTime() );
	}
	
}
