package com.pratilipi.data.transfer.shared;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.commons.shared.CategoryType;

@SuppressWarnings("serial")
public class CategoryData implements Serializable {

	private Long id;
	
	private String name;
	private Boolean hasName;
	
	private Long languageId;
	private Boolean hasLanguageId;
	
	private CategoryType type;
	private Boolean hasType;
	
	private Date creationDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.hasName = true;
	}

	public Boolean hasName() {
		return hasName;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
		this.hasLanguageId = true;
	}

	public Boolean getHasLanguageId() {
		return hasLanguageId;
	}

	public CategoryType getType() {
		return type;
	}

	public void setType(CategoryType type) {
		this.type = type;
		this.hasType = true;
	}

	public Boolean getHasType() {
		return hasType;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
}
