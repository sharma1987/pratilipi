package com.pratilipi.api.shared;

import com.claymus.api.shared.GenericResponse;

@SuppressWarnings("serial")
public class PostOAuthResponse extends GenericResponse { 
	
	@SuppressWarnings("unused")
	private String accessToken;
	
	@SuppressWarnings("unused")
	private String userName;
	
	@SuppressWarnings("unused")
	private String profileImageUrl;
	
	@SuppressWarnings("unused")
	private Long expiry;

	
	@SuppressWarnings("unused")
	private PostOAuthResponse() {}
	
	public PostOAuthResponse( String accessToken, String userName, String profileImageUrl, Long expiry ) {
		this.accessToken = accessToken;
		this.userName = userName;
		this.profileImageUrl = profileImageUrl;
		this.expiry = expiry;
	}
	
}
