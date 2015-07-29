package com.pratilipi.pagecontent.userpratilipi.api.shared;

import com.claymus.api.shared.GenericRequest;

@SuppressWarnings( "serial" )
public class GetUserLibRequest extends GenericRequest{

	private String accessToken;
	
	public String getAccessToken(){
		return accessToken;
	}
}
