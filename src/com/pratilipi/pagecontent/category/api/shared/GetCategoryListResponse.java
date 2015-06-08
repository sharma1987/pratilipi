package com.pratilipi.pagecontent.category.api.shared;

import java.util.List;

import com.claymus.api.shared.GenericResponse;
import com.pratilipi.data.transfer.shared.CategoryData;

@SuppressWarnings("serial")
public class GetCategoryListResponse extends GenericResponse {

	@SuppressWarnings("unused")
	private List<CategoryData> categoryDataList;
	
	public GetCategoryListResponse( List<CategoryData> categoryDataList ){
		this.categoryDataList = categoryDataList;
	}
}
