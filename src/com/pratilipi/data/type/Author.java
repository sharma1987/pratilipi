package com.pratilipi.data.type;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.common.type.Language;

public interface Author extends Serializable {
	
	Long getId();

	Long getUserId();

	void setUserId( Long userId );

	String getFirstName();
	
	void setFirstName( String firstName );
	
	String getLastName();
	
	void setLastName( String lastName );
	
	String getPenName();
	
	void setPenName( String penName );
	
	String getFirstNameEn();
	
	void setFirstNameEn( String firstNameEn );
	
	String getLastNameEn();
	
	void setLastNameEn( String lastNameEn );
	
	String getPenNameEn();
	
	void setPenNameEn( String penNameEn );
	
	String getEmail();
	
	void setEmail( String email );
	
	Language getLanguage();
	
	void setLanguage( Language language );
	
	Long getLanguageId();

	void setLanguageId( Long languageId );

	String getSummary();
	
	void setSummary( String summary );
	

	Boolean hasCustomCover();

	void setCustomCover( Boolean customCover );

	Date getRegistrationDate();
	
	void setRegistrationDate( Date registrationDate );

	Date getLastUpdated();
	
	void setLastUpdated( Date lastUpdated );

	
	Long getContentPublished();
	
	void setContentPublished( Long contentPublished );
	
	Long getTotalReadCount();
	
	void setTotalReadCount( Long totalReadCount );
	
	
	Date getLastProcessDate();

	void setLastProcessDate( Date lastProcessDate );

	Date getNextProcessDate();

	void setNextProcessDate( Date nextProcessDate );

}
