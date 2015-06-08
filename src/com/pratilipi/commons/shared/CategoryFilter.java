package com.pratilipi.commons.shared;

public class CategoryFilter {
	
	private Long languageId;
	
	private CategoryType type;
	
	private Boolean hidden;
	
	public Long getLanguageId() {
		return languageId;
	}
	
	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}
	
	public CategoryType getType() {
		return type;
	}
	
	public void setType(CategoryType type) {
		this.type = type;
	}
	
	public Boolean getHidden(){
		return hidden;
	}
	
	public void setHidden( Boolean hidden ){
		this.hidden = hidden;
	}

}
