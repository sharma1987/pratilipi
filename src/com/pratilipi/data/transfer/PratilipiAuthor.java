package com.pratilipi.data.transfer;

import java.io.Serializable;

import com.pratilipi.common.type.PratilipiType;

public interface PratilipiAuthor extends Serializable {

	Long getId();
	
	Long getPratilipiId();
	
	void setPratilipiId( Long bookId );
	
	PratilipiType getPratilipiType();
	
	void setPratilipiType( PratilipiType pratilipiType );
	
	Long getAuthorId();
	
	void setAuthorId( Long authorId );
	
}
