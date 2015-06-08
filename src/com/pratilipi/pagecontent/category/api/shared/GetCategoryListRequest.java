package com.pratilipi.pagecontent.category.api.shared;

import com.claymus.api.shared.GenericRequest;
import com.pratilipi.commons.shared.CategoryType;

@SuppressWarnings("serial")
public class GetCategoryListRequest extends GenericRequest {

	private Long languageId;
	private Boolean hasLanguageId;
	
	private CategoryType type;
	private Boolean hasType;
	
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
