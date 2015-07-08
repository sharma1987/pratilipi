package com.pratilipi.pagecontent.pratilipicategory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.AccessToken;
import com.claymus.pagecontent.PageContentHelper;
import com.claymus.pagecontent.user.UserContentHelper;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.shared.CategoryFilter;
import com.pratilipi.commons.shared.CategoryType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Category;
import com.pratilipi.data.transfer.PratilipiCategory;
import com.pratilipi.data.transfer.shared.CategoryData;
import com.pratilipi.data.transfer.shared.PratilipiCategoryData;
import com.pratilipi.data.transfer.shared.PratilipiData;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.pagecontent.category.CategoryContentHelper;
import com.pratilipi.pagecontent.genres.GenresContentProcessor;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipicategory.shared.PratilipiCategoryContentData;

public class PratilipiCategoryContentHelper extends PageContentHelper<
		PratilipiCategoryContent,
		PratilipiCategoryContentData,
		PratilipiCategoryContentProcessor>{

	public static final String ACCESS_ID_PRATILIPI_CATEGORY_ADD = "pratilipi_category_add";
	
	
	@Override
	public String getModuleName() {
		return "PratilipiCategory";
	}

	@Override
	public Double getModuleVersion() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Boolean hasAccessRequestToAddOrRemovePratilipiCategory( Pratilipi pratilipi, HttpServletRequest request ){
		return UserContentHelper.isAdmin( request ) || isAuthor( pratilipi, request );
	}
	
	private static Boolean isAuthor( Pratilipi pratilipi, HttpServletRequest request ){
		AccessToken accessToken = ( AccessToken ) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
		Author author = com.pratilipi.data.access.DataAccessorFactory.getDataAccessor( request )
								.getAuthorByUserId( accessToken.getUserId() );
		
		if( author != null && author.getId().equals( pratilipi.getAuthorId() ))
			return true;
		
		return false;
	}
	
	public static PratilipiCategoryData createPratilipiCategoryData( PratilipiCategory pratilipiCategory ){
		
		PratilipiCategoryData pratilipiCategoryData = new PratilipiCategoryData();
		pratilipiCategoryData.setId( pratilipiCategory.getId() );
		pratilipiCategoryData.setCategoryId( pratilipiCategory.getCategoryId() );
		pratilipiCategoryData.setPratilipiId( pratilipiCategory.getPratilipiId() );
		pratilipiCategoryData.setCreationDate( pratilipiCategory.getCreationDate() );
		
		return pratilipiCategoryData;
	}
	
	@Deprecated
	public static List<CategoryData> getCategoryList( HttpServletRequest request )
			throws InsufficientAccessException {
		
		PratilipiHelper pratilipiHelper =
				PratilipiHelper.get( request );
		
		if( ! pratilipiHelper.hasUserAccess( GenresContentProcessor.ACCESS_ID_GENRE_LIST, false ) )
			throw new InsufficientAccessException();
		
		CategoryFilter categoryFilter = new CategoryFilter();
		categoryFilter.setType( CategoryType.GENRE );
		List<CategoryData> categoryDataList = 
				CategoryContentHelper.getCategoryDataList( categoryFilter, request );
			
		return categoryDataList;
	}
	
	public static List<CategoryData> getCategoryList( Pratilipi pratilipi, HttpServletRequest request ) 
			throws InsufficientAccessException{
		
		AccessToken accessToken = ( AccessToken ) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
		Author author = com.pratilipi.data.access.DataAccessorFactory.getDataAccessor( request )
								.getAuthorByUserId( accessToken.getUserId() );
		
		List<CategoryData> categoryDataList = new ArrayList<>();
		
		if( author != null && author.getId().equals( pratilipi.getAuthorId() )){
			CategoryFilter categoryFilter = new CategoryFilter();
			categoryFilter.setType( CategoryType.GENRE );
			categoryDataList = CategoryContentHelper.getCategoryDataList( categoryFilter, request );
		} else if( UserContentHelper.isAdmin( request ))
			categoryDataList = CategoryContentHelper.getCategoryDataList( request );
		else 
			throw new InsufficientAccessException( "Insufficient Access Exception" );
		
		return categoryDataList;
	}
	
	public static List<Category> getPratilipiCategoryList( Long pratilipiId, HttpServletRequest request ){
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		List<PratilipiCategory> pratilipiCategoryList = dataAccessor.getPratilipiCategoryList( pratilipiId );
		if( pratilipiCategoryList == null )
			pratilipiCategoryList = new ArrayList<PratilipiCategory>( 0 );
		List<Category> categoryList = new ArrayList<Category>( pratilipiCategoryList.size() );
		for( PratilipiCategory pratilipiCategory : pratilipiCategoryList ){
			Category category = dataAccessor.getCategory( pratilipiCategory.getCategoryId() );
			if( category.getType() == CategoryType.GENRE )
				categoryList.add( category );
		}
		
		return categoryList;
	}
	
	public static DataListCursorTuple<PratilipiData> getCategoryPratilipiList( 
					Long categoryId, Integer resultCount, String cursor, HttpServletRequest request ){
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		DataListCursorTuple<PratilipiCategory> categoryPratilipiTupleList =
					dataAccessor.getCategoryPratilipiList( categoryId, resultCount, cursor );
		List<PratilipiCategory> categoryPratilipiList;
		if( categoryPratilipiTupleList == null )
			categoryPratilipiList = new ArrayList<PratilipiCategory>( 0 );
		else
			categoryPratilipiList = categoryPratilipiTupleList.getDataList();
		Logger.getLogger( PratilipiCategoryContentHelper.class.getName() )
				.log( Level.INFO, "Response List : " + categoryPratilipiList.size() );
		
		List<PratilipiData> pratilipiDataList = new ArrayList<PratilipiData>( categoryPratilipiList.size() );
		for( PratilipiCategory pratilipiCategory : categoryPratilipiTupleList.getDataList() ){
			Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiCategory.getPratilipiId() );
			if( pratilipi.getState() == PratilipiState.PUBLISHED || pratilipi.getState() == PratilipiState.PUBLISHED_PAID ){
				Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
				List<Category> categoryList = getPratilipiCategoryList( pratilipi.getId(), request );
				PratilipiData pratilipiData = PratilipiContentHelper.createPratilipiData( 
						pratilipi, null, author, categoryList, request );

				pratilipiDataList.add( pratilipiData );
			}
		}
		
		return new DataListCursorTuple<PratilipiData>( 
				pratilipiDataList, 
				categoryPratilipiTupleList == null ? null : categoryPratilipiTupleList.getCursor() );
	}
	
	public static PratilipiCategoryData addPratilipiCategory( PratilipiCategoryData pratilipiCategoryData, HttpServletRequest request ) 
			throws InsufficientAccessException{
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiCategoryData.getPratilipiId() );
		
		if( !hasAccessRequestToAddOrRemovePratilipiCategory( pratilipi, request ))
			throw new InsufficientAccessException( "User doesn't has privilege to add category to content" );
		
		PratilipiCategory pratilipiCategory = dataAccessor.newPratilipiCategory();
		pratilipiCategory.setCategoryId( pratilipiCategoryData.getCategoryId() );
		pratilipiCategory.setPratilipiId( pratilipiCategoryData.getPratilipiId() );
		pratilipiCategory.setCreationDate( new Date() );
		
		pratilipiCategory = dataAccessor.createPratilipiCategory( pratilipiCategory );
		
		return createPratilipiCategoryData( pratilipiCategory );
	}
	
	public static void removePratilipiCategory( PratilipiCategoryData pratilipiCategoryData, HttpServletRequest request )
			throws InsufficientAccessException{
	
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiCategoryData.getPratilipiId() );
		
		if( !hasAccessRequestToAddOrRemovePratilipiCategory( pratilipi, request ))
			throw new InsufficientAccessException( "User doesn't has privilege to remove category form content" );
		
		dataAccessor.deletePratilipiCategory( pratilipiCategoryData.getPratilipiId(), pratilipiCategoryData.getCategoryId() );

	}
}
