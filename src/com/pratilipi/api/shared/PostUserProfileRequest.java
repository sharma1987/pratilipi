package com.pratilipi.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings( "serial" )
public class PostUserProfileRequest extends GenericRequest {

	@Validate( required = true )
	private String accessToken;
	
	public String getAccessToken(){
		return accessToken;
	}
}
