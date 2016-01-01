package com.pratilipi.api.impl.userpratilipi.shared;

import java.util.Date;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;
import com.pratilipi.data.client.UserPratilipiData;

@SuppressWarnings("unused")
public class GenericReviewResponse extends GenericResponse {
	
	private Long userId;
	private String userName;
	
	private Integer rating;
	private String reviewTitle;
	private String review;
	private Long reviewDateMillis;

	
	private GenericReviewResponse() { }
	
	public GenericReviewResponse( UserPratilipiData userPratilipiData ) {
		
		this.userId = userPratilipiData.getUserId();
		this.userName = userPratilipiData.getUserName();
		this.rating = userPratilipiData.getRating();
		this.reviewTitle = userPratilipiData.getReviewTitle();
		this.review = userPratilipiData.getReview();
		this.reviewDateMillis = userPratilipiData.getReviewDate() == null ? null : userPratilipiData.getReviewDate().getTime();
		
	}
	
}
