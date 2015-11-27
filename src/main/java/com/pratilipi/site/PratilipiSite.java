package com.pratilipi.site;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.google.gson.Gson;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.PratilipiType;
import com.pratilipi.common.util.FreeMarkerUtil;
import com.pratilipi.common.util.LanguageUtil;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.common.util.ThirdPartyResource;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.client.UserPratilipiData;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.util.AuthorDataUtil;
import com.pratilipi.data.util.PratilipiDataUtil;
import com.pratilipi.data.util.UserDataUtil;
import com.pratilipi.data.util.UserPratilipiDataUtil;
import com.pratilipi.filter.UxModeFilter;

@SuppressWarnings("serial")
public class PratilipiSite extends HttpServlet {
	
	private static final Logger logger =
			Logger.getLogger( PratilipiSite.class.getName() );

	private static final String templateFilePrefix = "com/pratilipi/site/page/";
	private static final String dataFilePrefix = "page/data/";
	private static final String languageFilePrefix = "WEB-INF/classes/com/pratilipi/site/i18n/language.";
	
	
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response ) throws IOException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		// Page entity
		String uri = request.getRequestURI();
		Page page = dataAccessor.getPage( uri );

		// Language
		Language lang = UxModeFilter.getUserLanguage();
		boolean basicMode = UxModeFilter.isBasicMode();
		
		// Common resource list
		List<String> resourceList = new LinkedList<>();
		if( basicMode ) {
			resourceList.add( ThirdPartyResource.FONT_AWESOME.getTag() );
		} else {
			resourceList.add( ThirdPartyResource.JQUERY.getTag() );
			resourceList.add( ThirdPartyResource.BOOTSTRAP.getTag() );
			resourceList.add( ThirdPartyResource.FONT_AWESOME.getTag() );
			resourceList.add( ThirdPartyResource.POLYMER.getTag() );
			resourceList.add( ThirdPartyResource.POLYMER_IRON_AJAX.getTag() );
			resourceList.add( ThirdPartyResource.POLYMER_IRON_OVERLAY_BEHAVIOR.getTag() );
			resourceList.add( ThirdPartyResource.POLYMER_IRON_RESIZABLE_BEHAVIOR.getTag() );
		}

		
		// Data Model for FreeMarker
		Map<String, Object> dataModel = null;
		String html = "";
		
		try {
			String templateName = null;
			
			if( uri.equals( "/" ) ) {
				dataModel = createDataModelForHomePage( lang );
				templateName = templateFilePrefix + "Home.ftl";
			
			} else if( page != null && page.getType() == PageType.PRATILIPI ) {
				resourceList.add( ThirdPartyResource.POLYMER_IRON_COLLAPSE.getTag() );
				dataModel = createDataModelForPratilipiPage( page.getPrimaryContentId(), basicMode );
				templateName = templateFilePrefix + ( basicMode ? "PratilipiBasic.ftl" : "Pratilipi.ftl" );
				
			} else if( page != null && page.getType() == PageType.AUTHOR ) {
				dataModel = createDataModelForAuthorPage( page.getPrimaryContentId(), basicMode );
				templateName = templateFilePrefix + ( basicMode ? "AuthorBasic.ftl" : "Author.ftl" );
			
			} else if( uri.equals( "/search" ) ) {
				dataModel = createDataModelForSearchPage( lang, request );
				templateName = templateFilePrefix + "Search.ftl";
				
			} else if( uri.equals( "/books" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.BOOK, lang );
				templateName = templateFilePrefix + "List.ftl";
				
			} else if( uri.equals( "/stories" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.STORY, lang );
				templateName = templateFilePrefix + "List.ftl";
				
			} else if( uri.equals( "/poems" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.POEM, lang );
				templateName = templateFilePrefix + "List.ftl";
				
			} else if( uri.equals( "/articles" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.ARTICLE, lang );
				templateName = templateFilePrefix + "List.ftl";
				
			} else if( uri.equals( "/magazines" ) ) {
				dataModel = createDataModelForListPage( PratilipiType.MAGAZINE, lang );
				templateName = templateFilePrefix + "List.ftl";
			
			} else if( uri.equals( "/read" ) ) {
				resourceList.add( ThirdPartyResource.POLYMER_IRON_A11Y_KEYS.getTag() );
				resourceList.add( ThirdPartyResource.POLYMER_IRON_FLEX_LAYOUT.getTag() );
				resourceList.add( ThirdPartyResource.POLYMER_IRON_ICONS.getTag() );
				resourceList.add( ThirdPartyResource.POLYMER_PAPER_FAB.getTag() );
				resourceList.add( ThirdPartyResource.POLYMER_PAPER_SLIDER.getTag() );
				dataModel = createDataModelForReadPage( null );
				templateName = templateFilePrefix + "Read.ftl";
			
			} else if( uri.matches( "^/[a-z0-9-]+$" ) && ( dataModel = createDataModelForListPage( uri.substring( 1 ), lang ) ) != null ) {
				templateName = templateFilePrefix + "List.ftl";
				
			} else if( uri.matches( "^/[a-z0-9-/]+$" ) && ( dataModel = createDataModelForStaticPage( uri.substring( 1 ).replaceAll( "/", "_" ), lang ) ) != null ) {
				templateName = templateFilePrefix + "Static.ftl";
				
			} else if( uri.matches( "^/[a-z0-9-/]+$" ) && ( dataModel = createDataModelForStaticPage( uri.substring( 1 ).replaceAll( "/", "_" ), Language.ENGLISH ) ) != null ) {
				templateName = templateFilePrefix + "Static.ftl";
				
			} else {
				dataModel = new HashMap<String, Object>();
				templateName = templateFilePrefix + "error/PageNotFound.ftl";
				response.setStatus( HttpServletResponse.SC_NOT_FOUND );

			}

			// Adding common data to the Data Model
			dataModel.put( "userJson", new Gson().toJson( UserDataUtil.getCurrentUser() ) );
			dataModel.put( "lang", lang != null ? lang.getCode() : Language.ENGLISH.getCode() );
			dataModel.put( "_strings", LanguageUtil.getStrings(
					languageFilePrefix + (lang != null ? lang.getCode() : Language.ENGLISH.getCode()),
					languageFilePrefix + Language.ENGLISH.getCode() ) );
			dataModel.put( "resourceList", resourceList );
			
			// The magic
			html = FreeMarkerUtil.processTemplate( dataModel, templateName );

		} catch( UnexpectedServerException e ) {
			response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
			try {
				html = FreeMarkerUtil.processTemplate( dataModel, templateFilePrefix + "error/ServerError.ftl" );
			} catch( UnexpectedServerException ex ) { }
		}

		
		// Dispatching response
		response.setCharacterEncoding( "UTF-8" );
		response.getWriter().write( html );
		response.getWriter().close();
	}
	
	
	private String createPratilipiPageTitle( PratilipiData pratilipiData ) {
		if( pratilipiData == null )
			return null;
		
		String title = createAuthorPageTitle( pratilipiData.getAuthor() );
		title = title == null ? "" : " « " + title;
		
		if( pratilipiData.getTitle() != null && pratilipiData.getTitleEn() == null )
			return pratilipiData.getTitle() + title;
		else if( pratilipiData.getTitle() == null && pratilipiData.getTitleEn() != null )
			return pratilipiData.getTitleEn() + title;
		else if( pratilipiData.getTitle() != null && pratilipiData.getTitleEn() != null )
			return pratilipiData.getTitle() + " / " + pratilipiData.getTitleEn() + title;
		return null;
	}

	private String createAuthorPageTitle( AuthorData authorData ) {
		if( authorData == null )
			return null;
		
		if( authorData.getName() != null && authorData.getNameEn() == null )
			return authorData.getName();
		else if( authorData.getName() == null && authorData.getNameEn() != null )
			return authorData.getNameEn();
		else if( authorData.getName() != null && authorData.getNameEn() != null )
			return authorData.getName() + " / " + authorData.getNameEn();
		return null;
	}
	
	
	public Map<String, Object> createDataModelForHomePage( Language lang )
			throws UnexpectedServerException {

		Map<String, Object> featuredListDataModel = createDataModelForListPage( "featured", lang );

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "featuredTitle", featuredListDataModel.get( "title" ) );
		dataModel.put( "featuredListJson", featuredListDataModel.get( "pratilipiListJson" ) );
		return dataModel;
	}

	public Map<String, Object> createDataModelForPratilipiPage( Long pratilipiId, boolean basicMode ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
		Author author = dataAccessor .getAuthor( pratilipi.getAuthorId() );
		PratilipiData pratilipiData = PratilipiDataUtil.createPratilipiData( pratilipi, author, true, false );
		UserPratilipiData userPratilipiData = UserPratilipiDataUtil.getUserPratilipi( pratilipiId );
		
		DataListCursorTuple<UserPratilipiData> reviewListCursorTuple =
				UserPratilipiDataUtil.getPratilipiReviewList( pratilipiId, null, 20 );
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		if( basicMode ) {
			dataModel.put( "title", createPratilipiPageTitle( pratilipiData ) );
			dataModel.put( "pratilipi", pratilipiData );
			dataModel.put( "userpratilipi", userPratilipiData );
			dataModel.put( "reviewList", reviewListCursorTuple.getDataList() );
		} else {
			Gson gson = new Gson();
			dataModel.put( "title", createPratilipiPageTitle( pratilipiData ) );
			dataModel.put( "pratilipi", pratilipiData );
			dataModel.put( "pratilipiJson", gson.toJson( pratilipiData ).toString() );
			dataModel.put( "userpratilipiJson", gson.toJson( userPratilipiData ).toString() );
//			dataModel.put( "recommendedJsonList", gson.toJson( pratilipiDataList ).toString() );
			dataModel.put( "reviewListJson", gson.toJson( reviewListCursorTuple.getDataList() ).toString() );
			dataModel.put( "reviewListCursor", reviewListCursorTuple.getCursor() );
		}
		return dataModel;
		
	}
	
	public Map<String, Object> createDataModelForAuthorPage( Long authorId, boolean basicMode ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = dataAccessor.getAuthor( authorId );
		AuthorData authorData = AuthorDataUtil.createAuthorData( author, true, false );

		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setAuthorId( authorId );
		DataListCursorTuple<PratilipiData> pratilipiDataListCursorTuple =
				PratilipiDataUtil.getPratilipiDataList( pratilipiFilter, null, 20 );
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		if( basicMode ) {
			dataModel.put( "title", createAuthorPageTitle( authorData ) );
			dataModel.put( "author", authorData );
			dataModel.put( "pratilipiList", pratilipiDataListCursorTuple.getDataList() );
			if( pratilipiDataListCursorTuple.getDataList().size() == 20
					&& pratilipiDataListCursorTuple.getCursor() != null )
				dataModel.put( "searchQuery", pratilipiFilter.toUrlEncodedString() );
		} else {
			Gson gson = new Gson();
			dataModel.put( "title", createAuthorPageTitle( authorData ) );
			dataModel.put( "author", authorData );
			dataModel.put( "authorJson", gson.toJson( authorData ).toString() );
			dataModel.put( "publishedPratilipiListJson", gson.toJson( pratilipiDataListCursorTuple.getDataList() ).toString() );
			dataModel.put( "publishedPratilipiListFilterJson", gson.toJson( pratilipiFilter ).toString() );
			dataModel.put( "publishedPratilipiListCursor", pratilipiDataListCursorTuple.getCursor() );
		}
		return dataModel;
		
	}

	public Map<String, Object> createDataModelForSearchPage( Language lang, HttpServletRequest request ) {
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setLanguage( lang );
		
		DataListCursorTuple<PratilipiData> pratilipiDataListCursorTuple =
				PratilipiDataUtil.getPratilipiDataList( request.getParameter( "q" ), pratilipiFilter, null, 20 );

		Gson gson = new Gson();

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "pratilipiListJson", gson.toJson( pratilipiDataListCursorTuple.getDataList() ).toString() );
		dataModel.put( "pratilipiListSearchQuery", request.getParameter( "q" ) );
		dataModel.put( "pratilipiListFilterJson", gson.toJson( pratilipiFilter ).toString() );
		dataModel.put( "pratilipiListCursor", pratilipiDataListCursorTuple.getCursor() );
		return dataModel;
	}

	public Map<String, Object> createDataModelForListPage( PratilipiType type, Language lang ) {
		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setType( type );
		pratilipiFilter.setLanguage( lang );
		
		DataListCursorTuple<PratilipiData> pratilipiDataListCursorTuple =
				PratilipiDataUtil.getPratilipiDataList( pratilipiFilter, null, 20 );

		Gson gson = new Gson();

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", type.getNamePlural() );
		dataModel.put( "pratilipiListJson", gson.toJson( pratilipiDataListCursorTuple.getDataList() ).toString() );
		dataModel.put( "pratilipiListFilterJson", gson.toJson( pratilipiFilter ).toString() );
		dataModel.put( "pratilipiListCursor", pratilipiDataListCursorTuple.getCursor() );
		return dataModel;
	}

	public Map<String, Object> createDataModelForListPage( String listName, Language lang )
			throws UnexpectedServerException {

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		String fileName = "list." + lang.getCode() + "." + listName;
		
		// Fetching Pratilipi List from data file
		String listTitle = null;
		List<Long> pratilipiIdList = new LinkedList<Long>();
		try {
			File file = new File( getClass().getResource( dataFilePrefix + fileName ).toURI() );
			LineIterator it = FileUtils.lineIterator( file, "UTF-8" );
			if( it.hasNext() )
				listTitle = it.nextLine().trim();
			while( it.hasNext() ) {
				String pageUrl = it.nextLine();
				if( ! pageUrl.trim().isEmpty() ) {
					Page page = dataAccessor.getPage( pageUrl );
					if( page != null && page.getPrimaryContentId() != null )
						pratilipiIdList.add( page.getPrimaryContentId() );
				}
			}
			LineIterator.closeQuietly( it );
		} catch( NullPointerException e ) {
			return null;
		} catch( URISyntaxException | IOException e ) {
			logger.log( Level.SEVERE, "Exception while reading from data file.", e );
			throw new UnexpectedServerException();
		}

		List<PratilipiData> pratilipiDataList =
				PratilipiDataUtil.createPratilipiDataList( pratilipiIdList, true );

		Gson gson = new Gson();

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", listTitle );
		dataModel.put( "pratilipiListJson", gson.toJson( pratilipiDataList ).toString() );
		return dataModel;
	}
	
	public Map<String, Object> createDataModelForReadPage( Long pratilipiId ) {
		Map<String, Object> dataModel = new HashMap<String, Object>();
		// TODO:
		return dataModel;
	}
	
	public Map<String, Object> createDataModelForStaticPage( String pageName, Language lang )
			throws UnexpectedServerException {

		String title = null;
		StringBuilder content = new StringBuilder();
		try {
			String fileName = "static." + ( lang == null ? "" : lang.getCode() + "." ) + pageName;
			File file = new File( getClass().getResource( dataFilePrefix + fileName ).toURI() );
			LineIterator it = FileUtils.lineIterator( file, "UTF-8" );
			if( it.hasNext() )
				title = it.nextLine().trim();
			while( it.hasNext() )
				content.append( it.nextLine() + "<br/>" );
			LineIterator.closeQuietly( it );
		} catch( NullPointerException e ) {
			return null;
		} catch( URISyntaxException | IOException e ) {
			logger.log( Level.SEVERE, "Exception while reading from data file.", e );
			throw new UnexpectedServerException();
		}

		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put( "title", title );
		dataModel.put( "content", content.toString() );
		return dataModel;
	}

	public <T> T getData( String fileName, Class<T> clazz )
			throws UnexpectedServerException {
		
		// Fetching content (json) from data file
		String jsonStr = "";
		try {
			File file = new File( getClass().getResource( dataFilePrefix + fileName ).toURI() );
			LineIterator it = FileUtils.lineIterator( file, "UTF-8" );
			while( it.hasNext() )
				jsonStr += it.nextLine() + '\n';
			LineIterator.closeQuietly( it );
		} catch( URISyntaxException | IOException e ) {
			logger.log( Level.SEVERE, "Exception while reading from data file.", e );
			throw new UnexpectedServerException();
		}

		// The magic
		return new Gson().fromJson( jsonStr, clazz );
	}
	
}