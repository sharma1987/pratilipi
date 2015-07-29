package com.pratilipi.commons.shared;

public class UserPratilipiFilter {
	
	private Long pratilipiId;
	
	private Long userId;
	
	private Boolean orderByReviewDate;
	
	private Boolean orderByAddedToLibDate; 

	public Long getPratilipiId() {
		return pratilipiId;
	}

	public void setPratilipiId(Long pratilipiId) {
		this.pratilipiId = pratilipiId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Boolean getOrderByReviewDate() {
		return orderByReviewDate;
	}

	public void setOrderByReviewDate(Boolean orderByReviewDate) {
		this.orderByReviewDate = orderByReviewDate;
	}
	
	public Boolean getOrderByAddedToLibDate(){
		return orderByAddedToLibDate;
	}
	
	public void setOrderByAddedToLibDate( Boolean orderByAddedToLibDate ){
		this.orderByAddedToLibDate = orderByAddedToLibDate;
	}
	
}
