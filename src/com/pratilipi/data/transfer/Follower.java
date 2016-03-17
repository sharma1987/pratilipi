package com.pratilipi.data.transfer;

import java.io.Serializable;
import java.util.Date;

public interface Follower  extends Serializable {
	
	String getId();
	
	void setId( String id );

	Long getAuthorId();
	
	void setAuthorId( Long authorId );
	
	Long getUserId();
	
	void setUserId( Long userId );
	
	Date getCreationDate();
	
	void setCreationDate( Date creationDate );
}
