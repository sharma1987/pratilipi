package com.pratilipi.pagecontent.category.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;
import com.pratilipi.commons.shared.CategoryType;

@SuppressWarnings("serial")
public class GetCategoryListRequest extends GenericRequest {

	@Validate( required = true )
	private Long languageId;
	
	private CategoryType type;
	private boolean hasType;
	
	public Long getLanguageId(){
		return languageId;
	}
	
	public CategoryType getType(){
		return type;
	}
	
	public boolean hasType(){
		return hasType;
	}
}
