package com.pratilipi.api.shared;

import java.util.Date;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;
import com.pratilipi.common.type.Gender;

@SuppressWarnings( "serial" )
public class PutUserUpdateRequest extends GenericRequest {
	
	@Validate( required = true )
	private String accessToken;
	
	private String name;
	private boolean hasName;
	
	@Validate( regEx = REGEX_EMAIL )
	private String email;
	private boolean hasEmail;
	
	private String password;
	private boolean hasPassword;
	
	private Date dateOfBirth;
	private boolean hasDateOfBirth;
	
	private Gender sex;
	private boolean hasSex;
	
	
	public String getAccessToken(){
		return accessToken;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean hasName() {
		return hasName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public boolean hasEmail() {
		return hasEmail;
	}
	
	public String getPassword(){
		return password;
	}
	
	public boolean hasPassword(){
		return hasPassword;
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	
	public boolean hasDateOfBirth() {
		return hasDateOfBirth;
	}
	
	public Gender getSex() {
		return sex;
	}
	
	public boolean hasSex() {
		return hasSex;
	}
	
}
