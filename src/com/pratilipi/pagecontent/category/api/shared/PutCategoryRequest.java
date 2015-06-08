package com.pratilipi.pagecontent.category.api.shared;

import com.claymus.api.shared.GenericRequest;
import com.pratilipi.commons.shared.CategoryType;

@SuppressWarnings("serial")
public class PutCategoryRequest extends GenericRequest {

	private Long id;
	private Boolean hasId;
	
	private String name;
	private Boolean hasName;
	
	private Long languageId;
	private Boolean hasLanguageId;
	
	private CategoryType type;
	private Boolean hasType;
	
	
	public Long getId(){
		return id;
	}
	
	public Boolean hasId(){
		return hasId;
	}
	
	public String getName(){
		return name;
	}
	
	public Boolean hasName(){
		return hasName;
	}
	
	public Long getLanguageId(){
		return languageId;
	}
	
	public Boolean hasLanguageId(){
		return hasLanguageId;
	}
	
	public CategoryType getType(){
		return type;
	}
	
	public Boolean hasType(){
		return hasType;
	}
}
