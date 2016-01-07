package com.pratilipi.api.impl.user;

import com.google.gson.JsonObject;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.impl.user.shared.PostUserEmailRequest;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.util.UserDataUtil;

@SuppressWarnings("serial")
@Bind( uri= "/user/email" )
public class UserEmailApi extends GenericApi {
	
	@Post
	public static GenericResponse putUserEmail( PostUserEmailRequest request )
			throws InvalidArgumentException, UnexpectedServerException {

		JsonObject errorMessages = new JsonObject();

		if( request.getUserId() == null && ( request.sendWelcomeMail() || request.sendEmailVerificationMail() || request.sendBirthdayMail() ) )
			errorMessages.addProperty( "userId", GenericRequest.ERR_USER_ID_REQUIRED );
			
		if( request.sendPasswordResetMail() && ( request.getEmail() == null || request.getEmail().trim().isEmpty() ) )
			errorMessages.addProperty( "email", GenericRequest.ERR_EMAIL_REQUIRED );

		if( errorMessages.entrySet().size() > 0 )
			throw new InvalidArgumentException( errorMessages );


		if( request.sendWelcomeMail() )
			UserDataUtil.sendWelcomeMail( request.getUserId() );
		
		if( request.sendEmailVerificationMail() )
			UserDataUtil.sendEmailVerificationMail( request.getUserId() );

		if( request.sendPasswordResetMail() )
			UserDataUtil.sendPasswordResetMail( request.getEmail() );
		
		if( request.sendBirthdayMail() )
			throw new InvalidArgumentException( "Feature not yet supported !" );

		
		return new GenericResponse();
		
	}
	
}
