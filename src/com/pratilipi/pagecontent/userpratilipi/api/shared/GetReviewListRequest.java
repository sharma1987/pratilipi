package com.pratilipi.pagecontent.userpratilipi.api.shared;

import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class GetReviewListRequest extends GenericRequest {
	
	private Long pratilipiId;
	private Boolean hasPratilipiId;
	
	private Long userId;
	private Boolean hasUserId;
	
	public Long getPratilipiId(){
		return pratilipiId;
	}
	
	public Boolean hasPratilipiId(){
		return hasPratilipiId;
	}
	
	public Long getUserId(){
		return userId;
	}
	
	public Boolean hasUserId(){
		return hasUserId;
	}
	
}
