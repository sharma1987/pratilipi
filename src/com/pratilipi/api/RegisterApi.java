package com.pratilipi.api;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Put;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.data.transfer.AccessToken;
import com.claymus.data.transfer.shared.UserData;
import com.claymus.pagecontent.user.UserContentHelper;
import com.pratilipi.api.shared.PutRegisterRequest;
import com.pratilipi.api.shared.PutRegisterResponse;

@SuppressWarnings( "serial" )
@Bind( uri = "/register" )
public class RegisterApi extends GenericApi {
	
	@Put
	public PutRegisterResponse registerUser( PutRegisterRequest request ) 
			throws InvalidArgumentException{
		
		UserData userData = new UserData();
		userData.setName( request.getName() );
		userData.setEmail( request.getEmail() );
		userData.setCampaign( request.getCampaign() );
		userData.setStatus( request.getStatus() );
		userData.setPassword( request.getPassword() );
		userData.setReferer( request.getReferer() );
		
		AccessToken accessToken = UserContentHelper.registerUser( userData, this.getThreadLocalRequest() );

		
		return new PutRegisterResponse( accessToken.getId(), accessToken.getExpiry().getTime() );
	}
}
