package com.pratilipi.pagecontent.category.api;

import java.util.ArrayList;
import java.util.List;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.api.annotation.Put;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.pratilipi.commons.shared.CategoryFilter;
import com.pratilipi.data.transfer.shared.CategoryData;
import com.pratilipi.pagecontent.category.CategoryContentHelper;
import com.pratilipi.pagecontent.category.api.shared.GetCategoryListRequest;
import com.pratilipi.pagecontent.category.api.shared.GetCategoryListResponse;
import com.pratilipi.pagecontent.category.api.shared.PutCategoryRequest;
import com.pratilipi.pagecontent.category.api.shared.PutCategoryResponse;

@SuppressWarnings( "serial" )
@Bind( uri = "/category" )
public class CategoryApi extends GenericApi {

	@Get
	public GetCategoryListResponse getCategoryList( GetCategoryListRequest request ){
		
		CategoryFilter categoryFilter = new CategoryFilter();
		if( request.hasType() )
			categoryFilter.setType( request.getType() );
		categoryFilter.setHidden( false );
		
		List<CategoryData> categoryDataList = CategoryContentHelper.getCategoryDataList( categoryFilter, this.getThreadLocalRequest() );
		List<CategoryData> finalCategoryDataList = new ArrayList<>( categoryDataList.size() );
		for( CategoryData categoryData : categoryDataList ){
			if( categoryData.getLanguageId() == null ){
				finalCategoryDataList.add( categoryData );
			} else {
				if( request.getLanguageId().equals( categoryData.getLanguageId() )){
					finalCategoryDataList.add( categoryData );
				}
			}
				
		}
		
		return new GetCategoryListResponse( finalCategoryDataList );
	}
	
	@Put
	public PutCategoryResponse putCategory( PutCategoryRequest request )
			throws InsufficientAccessException{
		
		CategoryData categoryData = new CategoryData();
		
		if( request.hasId() )
			categoryData.setId( request.getId() );
		if( request.hasSerialNumber() )
			categoryData.setSerialNumber( request.getSerialNumber() );
		if( request.hasName() )
			categoryData.setName( request.getName() );
		if( request.hasPlural() )
			categoryData.setPlural( request.getPlural() );
		if( request.hasLanguageId() )
			categoryData.setLanguageId( request.getLanguageId() );
		if( request.hasType() )
			categoryData.setType( request.getType() );
		
		categoryData = CategoryContentHelper.saveCategory( categoryData, this.getThreadLocalRequest() );
		
		return new PutCategoryResponse( categoryData );
	}
	
}
