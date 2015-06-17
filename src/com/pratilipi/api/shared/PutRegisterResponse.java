package com.pratilipi.api.shared;

import com.claymus.api.shared.GenericResponse;

@SuppressWarnings( "serial" )
public class PutRegisterResponse extends GenericResponse {

	@SuppressWarnings( "unused" )
	private String accessToken;
	
	@SuppressWarnings( "unused" )
	private Long expiry;
	
	public PutRegisterResponse( String accessToken, Long expiry ){
		this.accessToken = accessToken;
		this.expiry = expiry;
	}
}
