package com.pratilipi.pagecontent.category.api.shared;

import com.claymus.api.shared.GenericResponse;
import com.pratilipi.data.transfer.shared.CategoryData;

@SuppressWarnings("serial")
public class PutCategoryResponse extends GenericResponse {

	@SuppressWarnings( "unused" )
	private CategoryData categoryData;
	
	public PutCategoryResponse( CategoryData categoryData ){
		this.categoryData = categoryData;
	}
}
