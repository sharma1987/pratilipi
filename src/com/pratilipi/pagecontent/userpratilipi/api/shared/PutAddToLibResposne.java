package com.pratilipi.pagecontent.userpratilipi.api.shared;

import com.claymus.api.shared.GenericResponse;

@SuppressWarnings( "serial" )
public class PutAddToLibResposne extends GenericResponse {

	@SuppressWarnings( "unused" )
	private Boolean addedToLib;
	
	public PutAddToLibResposne( Boolean addedToLib ){
		this.addedToLib = addedToLib;
	}
}
