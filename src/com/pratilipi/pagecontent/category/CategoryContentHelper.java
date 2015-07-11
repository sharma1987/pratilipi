package com.pratilipi.pagecontent.category;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.data.transfer.AccessToken;
import com.claymus.data.transfer.UserRole;
import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.commons.shared.CategoryFilter;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Category;
import com.pratilipi.data.transfer.shared.CategoryData;
import com.pratilipi.pagecontent.category.shared.CategoryContentData;

public class CategoryContentHelper extends PageContentHelper<
		CategoryContent,
		CategoryContentData,
		CategoryContentProcessor> {

	@Override
	public String getModuleName() {
		return "Category";
	}

	@Override
	public Double getModuleVersion() {
		return 5.3;
	}
	
	public static Boolean hasRequestAccessToAddOrUpdateCategory( HttpServletRequest request ){
		AccessToken accessToken = ( AccessToken ) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
		List<UserRole> userRoleList = DataAccessorFactory.getDataAccessor( request ).getUserRoleList( accessToken.getUserId() );
		
		for( UserRole userRole : userRoleList ){
			if( userRole.getRoleId().equals( "administrator" ))
				return true;
		}
		return false;
	}
	
	public static CategoryData createCategoryData( Long categoryId, HttpServletRequest request ){
		
		Category category = DataAccessorFactory.getDataAccessor( request )
								.getCategory( categoryId );
		return createCategoryData( category );
	}
	
	public static CategoryData createCategoryData( Category category ){
		CategoryData categoryData = new CategoryData();
		categoryData.setId( category.getId() );
		categoryData.setSerialNumber( category.getSerialNumber() );
		categoryData.setName( category.getName() );
		categoryData.setPlural( category.getPlural() );
		categoryData.setLanguageId( category.getLanguageId() );
		categoryData.setType( category.getType() );
		categoryData.setCreationDate( category.getCreationData() );
		
		return categoryData;
	}
	
	public static List<CategoryData> getCategoryDataList( HttpServletRequest request ){
		
		List<Category> categoryList = DataAccessorFactory.getDataAccessor( request )
											.getCategoryList();
		return createCategoryDataList( categoryList );
	}
	
	public static List<CategoryData> getCategoryDataList( CategoryFilter categoryFilter, HttpServletRequest request ){
		
		List<Category> categoryList = DataAccessorFactory.getDataAccessor( request )
											.getCategoryList( categoryFilter );
		
		return createCategoryDataList( categoryList );
	}
	
	public static List<CategoryData> createCategoryDataList( List<Category> categoryList ){
		List<CategoryData> categoryDataList = new ArrayList<>( categoryList.size() );
		for( Category category : categoryList )
			categoryDataList.add( createCategoryData( category ));
		
		return categoryDataList;
	}
	
	public static CategoryData saveCategory( CategoryData categoryData, HttpServletRequest request )
			throws InsufficientAccessException{
		
		if( !hasRequestAccessToAddOrUpdateCategory( request )){
			Logger.getLogger( CategoryContentHelper.class.getName() ).log( Level.SEVERE, "Insufficient Access Exception" );
			throw new InsufficientAccessException( "Insufficient Access"  );
		}
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Category category = dataAccessor.getCategory( categoryData.getId() );
		
		if( category == null ){
			category = dataAccessor.newCategory();
			category.setCreationDate( new Date() );
		}
		
		if( categoryData.hasSerialNumber() )
			category.setSerialNumber( categoryData.getSerialNumber() );
		if( categoryData.hasName() )
			category.setName( categoryData.getName() );
		if( categoryData.hasPlural() )
			category.setPlural( categoryData.getPlural() );
		if( categoryData.getHasLanguageId() )
			category.setLangugeId( categoryData.getLanguageId() );
		if( categoryData.getHasType() )
			category.setType( categoryData.getType() );
		if( categoryData.hasHidden() )
			category.setHidden( categoryData.isHidden() );
		
		category = dataAccessor.createOrUpdateCategory( category );
		
		return CategoryContentHelper.createCategoryData( category );
	}

}
