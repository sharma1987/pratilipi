package com.pratilipi.pagecontent.category.api.shared;

import com.claymus.api.shared.GenericRequest;
import com.pratilipi.commons.shared.CategoryType;

@SuppressWarnings("serial")
public class GetCategoryListRequest extends GenericRequest {

	private Long languageId;
	private boolean hasLanguageId;
	
	private CategoryType type;
	private boolean hasType;
	
	public Long getLanguageId(){
		return languageId;
	}
	
	public boolean hasLanguageId(){
		return hasLanguageId;
	}
	
	public CategoryType getType(){
		return type;
	}
	
	public boolean hasType(){
		return hasType;
	}
}
