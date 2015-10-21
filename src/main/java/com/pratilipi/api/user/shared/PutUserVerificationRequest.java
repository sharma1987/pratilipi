package com.pratilipi.api.user.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class PutUserVerificationRequest extends GenericRequest {
	
	@Validate( required = true, regEx = REGEX_EMAIL )
	private String email;
	
	@Validate( required = true )
	private String verificationToken;
	
	
	public String getEmail() {
		return email;
	}
	
	public String getVerificationToken() {
		return verificationToken;
	}
}
