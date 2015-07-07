package com.pratilipi.data.transfer.shared;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.commons.shared.CategoryType;

@SuppressWarnings("serial")
public class CategoryData implements Serializable {

	private Long id;
	
	private String name;
	private boolean hasName;
	
	private String plural;
	private boolean hasPlural;
	
	private Long languageId;
	private boolean hasLanguageId;
	
	private CategoryType type;
	private boolean hasType;
	
	private Date creationDate;
	
	private Boolean hidden;
	private boolean hasHidden;

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

	public boolean hasName() {
		return hasName;
	}
	
	public String getPlural(){
		return plural;
	}
	
	public void setPlural( String plural ){
		this.plural = plural;
		this.hasPlural = true;
	}
	
	public boolean hasPlural(){
		return hasPlural;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
		this.hasLanguageId = true;
	}

	public boolean getHasLanguageId() {
		return hasLanguageId;
	}

	public CategoryType getType() {
		return type;
	}

	public void setType(CategoryType type) {
		this.type = type;
		this.hasType = true;
	}

	public boolean getHasType() {
		return hasType;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Boolean isHidden(){
		return hidden;
	}
	
	public void setHidden( Boolean hidden ){
		this.hidden = hidden;
		this.hasHidden = true;
	}
	
	public boolean hasHidden(){
		return hasHidden;
	}
}
