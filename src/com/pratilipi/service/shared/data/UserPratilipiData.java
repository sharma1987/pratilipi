package com.pratilipi.service.shared.data;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.commons.shared.SellerType;
import com.pratilipi.commons.shared.UserReviewState;

public class UserPratilipiData implements IsSerializable {

	private String id;
	
	private Long userId;
	
	private String userName;
	
	private Long pratilipiId;
	
	private SellerType purchasedFrom;
	
	private Date purchaseDate;
	
	private Integer rating;
	
	private String review;
	
	private UserReviewState reviewState;
	
	private Date reviewDate;
	
	
	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId( Long userId ) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName( String userName ) {
		this.userName = userName;
	}

	public Long getPratilipiId() {
		return pratilipiId;
	}

	public void setPratilipiId( Long pratilipiId ) {
		this.pratilipiId = pratilipiId;
	}

	public SellerType getPurchasedFrom() {
		return purchasedFrom;
	}

	public void setPurchasedFrom(SellerType purchasedFrom) {
		this.purchasedFrom = purchasedFrom;
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate( Date purchaseDate ) {
		this.purchaseDate = purchaseDate;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating( Integer rating ) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview( String review ) {
		this.review = review;
	}

	public UserReviewState getReviewState() {
		return reviewState;
	}

	public void setReviewState( UserReviewState reviewState ) {
		this.reviewState = reviewState;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate( Date reviewDate ) {
		this.reviewDate = reviewDate;
	}
	
}
