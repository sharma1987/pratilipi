package com.pratilipi.pagecontent.userpratilipi.api.shared;

import com.claymus.api.shared.GenericRequest;

@SuppressWarnings( "serial" )
public class PutAddToLibRequest extends GenericRequest {

	private String accessToken;
	
	private Long pratilipiId;
	
	private Boolean addToLib;
	
	public String getAccessToken(){
		return accessToken;
	}
	
	public Long getPratilipiId(){
		return pratilipiId;
	}
	
	public Boolean getAddToLib(){
		return addToLib;
	}
}
