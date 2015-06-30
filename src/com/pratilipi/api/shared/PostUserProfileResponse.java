package com.pratilipi.api.shared;

import com.claymus.api.shared.GenericResponse;
import com.claymus.data.transfer.shared.UserData;

@SuppressWarnings( "serial" )
public class PostUserProfileResponse extends GenericResponse {

	@SuppressWarnings( "unused" )
	private UserData userData;
	
	public PostUserProfileResponse( UserData userData ){
		this.userData = userData;
	}
}
