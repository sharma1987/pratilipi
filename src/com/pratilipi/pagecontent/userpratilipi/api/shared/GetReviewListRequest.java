package com.pratilipi.pagecontent.userpratilipi.api.shared;

import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class GetReviewListRequest extends GenericRequest {
	
	private Long pratilipiId;
	private boolean hasPratilipiId;
	
	private Long userId;
	private boolean hasUserId;
	
	public Long getPratilipiId(){
		return pratilipiId;
	}
	
	public boolean hasPratilipiId(){
		return hasPratilipiId;
	}
	
	public Long getUserId(){
		return userId;
	}
	
	public boolean hasUserId(){
		return hasUserId;
	}
	
}
