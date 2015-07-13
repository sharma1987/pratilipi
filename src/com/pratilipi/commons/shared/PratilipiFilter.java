package com.pratilipi.commons.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;

public class PratilipiFilter implements IsSerializable {
	
	private PratilipiType type;

	private Long languageId;
	
	private Long authorId;

	private Long publisherId;

	private PratilipiState state;
	
	private Date nextProcessDateEnd;

	private Boolean orderByReadCount;
	
	private Boolean orderByListingDate;
	
	
	public PratilipiType getType() {
		return type;
	}

	public void setType( PratilipiType pratilipiType ) {
		this.type = pratilipiType;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId( Long languageId ) {
		this.languageId = languageId;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId( Long authorId ) {
		this.authorId = authorId;
	}
	
	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId( Long publisherId ) {
		this.publisherId = publisherId;
	}
	
	public PratilipiState getState() {
		return state;
	}

	public void setState( PratilipiState state ) {
		this.state = state;
	}
	
	public Date getNextProcessDateEnd() {
		return nextProcessDateEnd;
	}

	public void setNextProcessDateEnd( Date nextProcessDateEnd ) {
		this.nextProcessDateEnd = nextProcessDateEnd;
	}
	
	public Boolean getOrderByReadCount() {
		return orderByReadCount;
	}

	public void setOrderByReadCount( Boolean orderByReadCount ) {
		this.orderByReadCount = orderByReadCount;
	}
	
	public Boolean getOrderByListingDate(){
		return orderByListingDate;
	}
	
	public void setOrderByListingDate( Boolean orderByListingDate ){
		this.orderByListingDate = orderByListingDate;
	}
	
}
