package com.pratilipi.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;
import com.claymus.commons.shared.UserStatus;

@SuppressWarnings( "serial" )
public class PutRegisterRequest extends GenericRequest {

	@Validate( required = true )
	private String name;
	
	@Validate( required = true )
	private String email;
	
	@Validate( required = true )
	private String password;
	
	private String campaign;
	
	private String referer;
	
	private UserStatus status;
	
	public String getName(){
		return name;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getCampaign(){
		return campaign;
	}
	
	public String getReferer(){
		return referer;
	}
	
	public UserStatus getStatus(){
		return status;
	}
}
