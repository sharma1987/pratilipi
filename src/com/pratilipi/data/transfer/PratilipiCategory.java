package com.pratilipi.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface PratilipiCategory extends Serializable {

	String getId();
	
	Long getPratilipiId();
	
	void setPratilipiId( Long pratilipiId );
	
	Long getCategoryId();
	
	void setCategoryId( Long CategoryId );
	
	Date getCreationDate();
	
	void setCreationDate( Date creationDate );
	
}
