package com.pratilipi.api.shared;

import com.claymus.api.shared.GenericResponse;
import com.claymus.data.transfer.shared.UserData;

@SuppressWarnings( "serial" )
public class PutUserUpdateResponse extends GenericResponse {

	@SuppressWarnings( "unused" )
	private UserData userData;
	
	public PutUserUpdateResponse( UserData userData ){
		this.userData = userData;
	}
}
