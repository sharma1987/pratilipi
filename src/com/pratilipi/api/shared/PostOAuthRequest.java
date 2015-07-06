package com.pratilipi.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class PostOAuthRequest extends GenericRequest {

	@Validate( regEx = REGEX_EMAIL )
	private String userId;
	
	private String userSecret;
	private boolean hasUserSecret;
	
	private Long publisherId;
	private String publisherSecret;
	
	private String loginType;
	
	private String socialId;
	
	private String token;
	private boolean hasToken;
	

	public String getUserId() {
		return userId;
	}

	public String getUserSecret() {
		return userSecret;
	}
	
	public boolean hasUserSecret(){
		return hasUserSecret;
	}
	
	public Long getPublisherId() {
		return publisherId;
	}

	public String getPublisherSecret() {
		return publisherSecret;
	}
	
	public String getLoginType(){
		return loginType;
	}
	
	public String getSocialId(){
		return socialId;
	}
	
	public String getToken(){
		return token;
	}
	
	public boolean hasToken(){
		return hasToken;
	}
	
}
