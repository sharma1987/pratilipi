package com.pratilipi.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface Genre extends Serializable {

	Long getId();
	
	String getName();
	
	void setName( String name );
	
	Date getCreationDate();
	
	void setCreationDate( Date creationDate );
	
}
