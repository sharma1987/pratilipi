package com.pratilipi.pagecontent.pratilipicategory.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings( "serial" )
public class GetCategoryPratilipiRequest extends GenericRequest {

	@Validate( required = true )
	private Long languageId;
	
	@Validate( required = true )
	private Long categoryId;
	
	private Integer resultCount;
	
	private String cursor;
	
	public Long getLanguageId(){
		return languageId;
	}
	
	public Long getCategoryId(){
		return categoryId;
	}
	
	public Integer getResultCount(){
		return resultCount;
	}
	
	public String getCursor(){
		return cursor;
	}
}
