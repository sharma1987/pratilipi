package com.pratilipi.data.type;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.common.type.UserCampaign;
import com.pratilipi.common.type.UserSignUpSource;
import com.pratilipi.common.type.UserState;

public interface User extends GenericOfyType, Serializable {
	
	Long getId();
	
	String getFacebookId();
	
	void setFacebookId( String facebookId );

	String getGoogleId();
	
	void setGoogleId( String googleId );
	
	String getPassword();
	
	void setPassword( String password );
	
	
	String getEmail();
	
	void setEmail( String email );
	
	String getPhone();
	
	void setPhone( String phone );
	
	
	String getVerificationToken();
	
	void setVerificationToken( String verificationToken );
	
	UserState getState();
	
	void setState( UserState userState );
	
	
	UserCampaign getCampaign();
	
	void setCampaign( UserCampaign campaign );
	
	String getReferrer();
	
	void setReferrer( String referrer );
	
	Date getSignUpDate();
	
	void setSignUpDate( Date date );
	
	UserSignUpSource getSignUpSource();
	
	void setSignUpSource( UserSignUpSource signUpSource );
	
	Date getLastUpdated();
	
	void setLastUpdated( Date lastUpdated );

	
	Long getFollowCount();
	
	void setFollowCount( Long followCount );
		
}
