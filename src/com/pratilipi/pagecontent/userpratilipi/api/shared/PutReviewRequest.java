package com.pratilipi.pagecontent.userpratilipi.api.shared;

import com.claymus.api.annotation.Validate;

public class PutReviewRequest {
	
	private String review;
	
	@Validate( required = true )
	private String accessToken;
	
	@Validate( required = true )
	private Long pratilipiId;
	
	public String getReview(){
		return review;
	}
	
	public String getAccessToken(){
		return accessToken;
	}
	
	public Long getPratilipiId(){
		return pratilipiId;
	}
}
