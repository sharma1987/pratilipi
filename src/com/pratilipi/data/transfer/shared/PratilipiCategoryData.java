package com.pratilipi.data.transfer.shared;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class PratilipiCategoryData implements Serializable  {

	private String id;
	
	private Long pratilipiId;
	
	private Long categoryId;
	
	private Date creationDate;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getPratilipiId() {
		return pratilipiId;
	}

	public void setPratilipiId(Long pratilipiId) {
		this.pratilipiId = pratilipiId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
}
