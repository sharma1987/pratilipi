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
	
	private String firstName;
	private boolean hasFirstName;
	
	private String lastName;
	private boolean hasLastName;
	
	private String campaign;
	private boolean hasCampaign;
	
	private String referer;
	private boolean hasReferer;
	
	private String loginType;
	
	private String accessToken;
	private boolean hasAccessToken;
	

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
	
	public String getFirstName(){
		return firstName;
	}
	
	public boolean hasFirstName(){
		return hasFirstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public boolean hasLastName(){
		return hasLastName;
	}
	
	public String getCampaign(){
		return campaign;
	}
	
	public boolean hasCampaign(){
		return hasCampaign;
	}
	
	public String getReferer(){
		return referer;
	}
	
	public boolean hasReferer(){
		return hasReferer;
	}
	
	public String getLoginType(){
		return loginType;
	}
	
	public String getAccessToken(){
		return accessToken;
	}
	
	public boolean hasAccessToken(){
		return hasAccessToken;
	}
	
}
