package com.pratilipi.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;
import com.claymus.commons.shared.UserStatus;

@SuppressWarnings( "serial" )
public class PutRegisterRequest extends GenericRequest {

	@Validate( required = true )
	private String name;
	
	@Validate( required = true, regEx = REGEX_EMAIL )
	private String email;
	
	@Validate( required = true )
	private String password;
	
	private String campaign;
	private boolean hasCampaign;
	
	private String referer;
	private boolean hasReferer;
	
	private UserStatus status;
	private boolean hasStatus;
	
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
	
	public boolean hasCampaign(){
		return hasCampaign;
	}
	
	public String getReferer(){
		return referer;
	}
	
	public boolean hasReferer(){
		return hasReferer;
	}
	
	public UserStatus getStatus(){
		return status;
	}
	
	public boolean hasStatus(){
		return hasStatus;
	}
}
