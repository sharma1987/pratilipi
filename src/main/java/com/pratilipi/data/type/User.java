package com.pratilipi.data.type;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.common.type.UserStatus;

public interface User extends Serializable {

	Long getId();

	String getPassword();

	void setPassword( String password );

	String getFirstName();

	void setFirstName( String firstName );

	String getLastName();

	void setLastName( String lastName );

	String getNickName();

	void setNickName( String nickName );

	String getEmail();

	void setEmail( String email );

	String getPhone();

	void setPhone( String phone );

	String getCampaign();

	void setCampaign( String campaign );
	
	String getReferer();

	String setReferer( String referer );
	
	Date getSignUpDate();
	
	void setSignUpDate( Date date );

	UserStatus getStatus();
	
	void setStatus( UserStatus userStatus );
	
}
