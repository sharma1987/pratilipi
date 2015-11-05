package com.pratilipi.api.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.user.shared.PostUserPasswordUpdateRequest;
import com.pratilipi.api.user.shared.UserResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.client.UserData;
import com.pratilipi.data.util.UserDataUtil;

@SuppressWarnings("serial")
@Bind( uri= "/user/passwordupdate" )
public class UserPasswordUpdateApi extends GenericApi {
	
	@Post
	public static UserResponse post( PostUserPasswordUpdateRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {
		
		Gson gson = new Gson();

		JsonObject errorMessages = new JsonObject();
		
		if( ! request.getNewPassword().equals( request.getNewPassword2() ) ) {
			errorMessages.addProperty( "password2", GenericRequest.ERR_PASSWORD2_MISMATCH );
			throw new InvalidArgumentException( errorMessages );
		}
		
		// Verifying email and Updating password.
		if( request.getEmail() != null && request.getVerificationToken() != null ) {
			UserDataUtil.verifyUserEmail( request.getEmail(), request.getVerificationToken() );
			UserDataUtil.updateUserPassword( request.getEmail(), request.getVerificationToken(), request.getNewPassword() );
		} else if( request.getPassword() != null ) {
			UserDataUtil.updateUserPassword( request.getPassword(), request.getNewPassword() );
		} else {
			throw new InvalidArgumentException( GenericRequest.ERR_INSUFFICIENT_ARGS );
		}
		
		// Logging-in the user.
		UserData userData = UserDataUtil.loginUser(
				request.getEmail(),
				request.getVerificationToken() != null ? request.getVerificationToken() : request.getNewPassword() );
		
		return gson.fromJson( gson.toJson( userData ), UserResponse.class );
	}
	
}