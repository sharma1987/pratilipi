package com.pratilipi.pagecontent.author;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.server.Access;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.server.ImageUtil;
import com.claymus.commons.server.UserAccessHelper;
import com.claymus.commons.shared.ClaymusAccessTokenType;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.BlobAccessor;
import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.transfer.AuditLog;
import com.claymus.data.transfer.BlobEntry;
import com.claymus.data.transfer.User;
import com.claymus.pagecontent.PageContentHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.commons.server.PratilipiHelper;
import com.pratilipi.commons.server.SystemProperty;
import com.pratilipi.commons.shared.AuthorFilter;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.access.SearchAccessor;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.shared.AuthorData;
import com.pratilipi.data.transfer.shared.LanguageData;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.pagecontent.author.gae.AuthorContentEntity;
import com.pratilipi.pagecontent.author.shared.AuthorContentData;
import com.pratilipi.pagecontent.language.LanguageContentHelper;

public class AuthorContentHelper extends PageContentHelper<
		AuthorContent,
		AuthorContentData,
		AuthorContentProcessor> {
	
	private static final Logger logger =
			Logger.getLogger( AuthorContentHelper.class.getName() );
	
	private static final Gson gson = new GsonBuilder().create();

	
	private static final String IMAGE_FOLDER = "author-image";

	
	private static final Access ACCESS_TO_LIST_AUTHOR_DATA =
			new Access( "author_data_list", false, "View Author Data" );
	private static final Access ACCESS_TO_ADD_AUTHOR_DATA =
			new Access( "author_data_add", false, "Add Author Data" );
	private static final Access ACCESS_TO_UPDATE_AUTHOR_DATA =
			new Access( "author_data_update", false, "Update Author Data" );
	
	
	@Override
	public String getModuleName() {
		return "Author";
	}

	@Override
	public Double getModuleVersion() {
		return 5.3;
	}

	@Override
	public Access[] getAccessList() {
		return new Access[] {
				ACCESS_TO_LIST_AUTHOR_DATA,
				ACCESS_TO_ADD_AUTHOR_DATA,
				ACCESS_TO_UPDATE_AUTHOR_DATA
		};
	}
	
	
	public static AuthorContent newAuthorContent( Long authorId ) {
		return new AuthorContentEntity( authorId );
	}

	
	public static boolean hasRequestAccessToListAuthorData( HttpServletRequest request ) {
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN ); 
		return accessToken.getType().equals( ClaymusAccessTokenType.USER.toString() )
				&& UserAccessHelper.hasUserAccess( accessToken.getUserId(), ACCESS_TO_LIST_AUTHOR_DATA, request );
	}
	
	public static boolean hasRequestAccessToAddAuthorData( HttpServletRequest request ) {
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN ); 
		return accessToken.getType().equals( ClaymusAccessTokenType.USER.toString() )
				&& UserAccessHelper.hasUserAccess( accessToken.getUserId(), ACCESS_TO_ADD_AUTHOR_DATA, request );
	}
	
	public static boolean hasRequestAccessToUpdateAuthorData( HttpServletRequest request, Author author ) {
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN ); 

		if( accessToken.getType().equals( ClaymusAccessTokenType.USER.toString() ) ) {
			return UserAccessHelper.hasUserAccess( accessToken.getUserId(), ACCESS_TO_UPDATE_AUTHOR_DATA, request )
					|| ( author.getUserId() != null && author.getUserId().equals( accessToken.getUserId() ) );
	
		} else {
			return false;
		}
	}
	
	
	@Deprecated
	public static boolean hasAuthorProfile( HttpServletRequest request, Long userId ){
		
		return DataAccessorFactory.getDataAccessor( request ).getAuthorByUserId( userId ) == null ? false : true;
	}
	
	@Deprecated
	public static Author createAuthorProfile( HttpServletRequest request, User user ){
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Author author = dataAccessor.getAuthorByEmailId( user.getEmail() );
		
		if( author == null ){
			author = dataAccessor.newAuthor();
			author.setEmail( user.getEmail() );
			author.setFirstNameEn( user.getFirstName() );
			author.setLastNameEn( user.getLastName() );
			author.setUserId( user.getId() );
			author.setRegistrationDate( new Date() );
			author.setLanguageId( 5130467284090880L );
			
			logger.log( Level.INFO, "Created Author Id : " + author.getId() );
			author = dataAccessor.createOrUpdateAuthor( author );
			
			Page page = dataAccessor.newPage();
			page.setType( PageType.AUTHOR.toString() );
			page.setUri( "/author/" + author.getId().toString() );
			page.setPrimaryContentId( author.getId() );
			page.setCreationDate( new Date() );
			page = dataAccessor.createOrUpdatePage( page );
			
		} else if( author.getUserId() == null ){
			author.setUserId( user.getId() );
			logger.log( Level.INFO, "Updated Author Id : " + author.getId() );
			dataAccessor.createOrUpdateAuthor( author );
		}
		
		return author;
	}
	
	
	public static String creatAuthorImageUrl( Author author ) {
		if( author.hasCustomCover() ) {
//			String domain = "//" + author.getId() % 10 + "." + ClaymusHelper.getSystemProperty( "domain.cdn" );
//			String uri = "/author-image/150/" + author.getId() + "?" + author.getLastUpdated().getTime();
//			return domain + uri;
			String domain = "//" + author.getId() % 10 + "." + SystemProperty.get( "cdn" );
			String uri = "/author/image?authorId=" + author.getId() + "&width=150&version=" + author.getLastUpdated().getTime();
			return domain + uri;
		} else {
//			String domain = "//10." + ClaymusHelper.getSystemProperty( "domain.cdn" );
//			String uri = "/author-image/150/author";
//			return domain + uri;
			String domain = "//10." + SystemProperty.get( "cdn" );
			String uri = "/author/image?width=150";
			return domain + uri;
		}
	}
	
	public static AuthorData createAuthorData( Author author, Language language, HttpServletRequest request ) {
		if( author == null )
			return null;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Page authorPage = dataAccessor.getPage( PageType.AUTHOR.toString(), author.getId() );
		
		AuthorData authorData = new AuthorData();
		
		authorData.setId( author.getId() );
		authorData.setPageUrl( authorPage.getUri() );
		authorData.setPageUrlAlias( authorPage.getUriAlias() );
		authorData.setAuthorImageUrl( creatAuthorImageUrl( author ) );

		authorData.setLanguageId( author.getLanguageId() );
		authorData.setLanguage( LanguageContentHelper.createLanguageData( language ) );

		authorData.setFirstName( author.getFirstName() );
		authorData.setLastName( author.getLastName() );
		authorData.setPenName( author.getPenName() );

		if( author.getFirstName() != null && author.getLastName() == null )
			authorData.setName( author.getFirstName() );
		else if( author.getFirstName() == null && author.getLastName() != null )
			authorData.setName( author.getLastName() );
		else if( author.getFirstName() != null && author.getLastName() != null )
			authorData.setName( author.getFirstName() + " " + author.getLastName() );
		
		if( authorData.getName() != null && author.getPenName() == null )
			authorData.setFullName( authorData.getName() );
		else if( authorData.getName() == null && author.getPenName() != null )
			authorData.setFullName( author.getPenName() );
		else if( authorData.getName() != null && author.getPenName() != null )
			authorData.setFullName( authorData.getName() + " '" + author.getPenName() + "'" );
		
		authorData.setFirstNameEn( author.getFirstNameEn() );
		authorData.setLastNameEn( author.getLastNameEn() );
		authorData.setPenNameEn( author.getPenNameEn() );
		
		if( author.getFirstNameEn() != null && author.getLastNameEn() == null )
			authorData.setNameEn( author.getFirstNameEn() );
		else if( author.getFirstNameEn() == null && author.getLastNameEn() != null )
			authorData.setNameEn( author.getLastNameEn() );
		else if( author.getFirstNameEn() != null && author.getLastNameEn() != null )
			authorData.setNameEn( author.getFirstNameEn() + " " + author.getLastNameEn() );
		
		if( authorData.getNameEn() != null && author.getPenNameEn() == null )
			authorData.setFullNameEn( authorData.getNameEn() );
		else if( authorData.getNameEn() == null && author.getPenNameEn() != null )
			authorData.setFullNameEn( author.getPenNameEn() );
		else if( authorData.getNameEn() != null && author.getPenNameEn() != null )
			authorData.setFullNameEn( authorData.getNameEn() + " '" + author.getPenNameEn() + "'" );
		
		authorData.setSummary( author.getSummary() );
		authorData.setEmail( author.getEmail() );
		authorData.setRegistrationDate( author.getRegistrationDate().getTime() );
		authorData.setContentPublished( author.getContentPublished() );
		
		return authorData;
	}
	
	public static List<AuthorData> createAuthorDataList( List<Author> authorList, boolean includeLanguageData, HttpServletRequest request ) {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		
		Map<Long, LanguageData> languageIdToDataMap = null;
		if( includeLanguageData ) {
			List<Long> languageIdList = new LinkedList<>();
			for( Author author : authorList )
				if( ! languageIdList.contains( author.getLanguageId() ) )
					languageIdList.add( author.getLanguageId() );
			List<Language> languageList = dataAccessor.getLanguageList( languageIdList );
		
			languageIdToDataMap = new HashMap<>( languageList.size() );
			for( Language language : languageList )
				languageIdToDataMap.put( language.getId(), LanguageContentHelper.createLanguageData( language ) );
		}
		
		
		List<AuthorData> authorDataList = new ArrayList<>( authorList.size() );

		for( Author author : authorList ) {
			AuthorData authorData = createAuthorData( author, null, request );
			if( includeLanguageData )
				authorData.setLanguage( languageIdToDataMap.get( author.getLanguageId() ) );
			authorDataList.add( authorData );
		}
		
		return authorDataList;
		
	}
	

	public static DataListCursorTuple<AuthorData> getAuthorList( AuthorFilter authorFilter, String cursor, Integer resultCount, HttpServletRequest request )
			throws InsufficientAccessException {
		
		if( ! hasRequestAccessToListAuthorData( request ) )
			throw new InsufficientAccessException();
		
		DataListCursorTuple<Author> authorListCursorTuple = DataAccessorFactory
				.getDataAccessor( request )
				.getAuthorList( authorFilter, cursor, resultCount );
		
		List<AuthorData> authorDataList = createAuthorDataList(
				authorListCursorTuple.getDataList(),
				authorFilter.getLanguageId() == null,
				request );
		
		return new DataListCursorTuple<AuthorData>( authorDataList, authorListCursorTuple.getCursor() );
	}
	
	public static AuthorData saveAuthor( AuthorData authorData, HttpServletRequest request )
			throws InvalidArgumentException, InsufficientAccessException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Author author = null;
		
		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN ); 
		AuditLog auditLog = dataAccessor.newAuditLog();
		auditLog.setAccessId( accessToken.getId() );
		
		if( authorData.getId() == null ) { // Add Author usecase
			
			if( ! hasRequestAccessToAddAuthorData( request ) )
				throw new InsufficientAccessException();
			
			if( ! authorData.hasLanguageId() )
				throw new InvalidArgumentException( "'languageId' is missing !" );

			if( authorData.hasEmail() ){
				Author existingAuthor = dataAccessor.getAuthorByEmailId( authorData.getEmail().toLowerCase() );
				if( existingAuthor != null )
					return createAuthorData( existingAuthor, dataAccessor.getLanguage( existingAuthor.getLanguageId() ), request );
			}
			
			author = dataAccessor.newAuthor();
			auditLog.setEventId( ACCESS_TO_ADD_AUTHOR_DATA.getId() );
			auditLog.setEventDataOld( gson.toJson( author ) );
			
			author.setRegistrationDate( new Date() );
			author.setLastUpdated( new Date() );

		} else { // Update Author usecase

			author = dataAccessor.getAuthor( authorData.getId() );

			if( ! hasRequestAccessToUpdateAuthorData( request, author ) )
				throw new InsufficientAccessException();
			
			auditLog.setEventId( ACCESS_TO_UPDATE_AUTHOR_DATA.getId());
			auditLog.setEventDataOld( gson.toJson( author ) );

			author.setLastUpdated( new Date() );
		}
		
		if( authorData.hasLanguageId() )
			author.setLanguageId( authorData.getLanguageId() );
		if( authorData.hasFirstName() )
			author.setFirstName( authorData.getFirstName() );
		if( authorData.hasLastName() )
			author.setLastName( authorData.getLastName() );
		if( authorData.hasPenName() )
			author.setPenName( authorData.getPenName() );
		if( authorData.hasFirstNameEn() )
			author.setFirstNameEn( authorData.getFirstNameEn() );
		if( authorData.hasLastNameEn() )
			author.setLastNameEn( authorData.getLastNameEn() );
		if( authorData.hasPenNameEn() )
			author.setPenNameEn( authorData.getPenNameEn() );
		if( authorData.hasSummary() )
			author.setSummary( authorData.getSummary() );
		if( authorData.hasEmail() )
			author.setEmail( authorData.getEmail() == null ? null : authorData.getEmail().toLowerCase() );
		
		
		author = dataAccessor.createOrUpdateAuthor( author );

		if( authorData.getId() == null )
			AuthorContentHelper.createOrUpdateAuthorPageUrl( author.getId(), request );

		auditLog.setEventDataNew( gson.toJson( author ) );
		auditLog = dataAccessor.createAuditLog( auditLog );
		
		return createAuthorData( author, dataAccessor.getLanguage( author.getLanguageId() ), request );
	}
	
	public static void saveAuthorImage( Long authorId, BlobEntry blobEntry, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Author author = dataAccessor.getAuthor( authorId );

		if( ! hasRequestAccessToUpdateAuthorData( request, author ) )
			throw new InsufficientAccessException();

		
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		BlobAccessor blobAccessorPublic = DataAccessorFactory.getBlobAccessorPublic();
		try {
			blobEntry.setName( IMAGE_FOLDER + "/original/" + authorId );
			blobAccessor.createOrUpdateBlob( blobEntry );
			
			blobEntry.setName( IMAGE_FOLDER + "/150/" + authorId );
			blobEntry.setData( ImageUtil.resize( blobEntry.getData(), 150, 1500 ) );
			blobEntry.setCacheControl( "public, max-age=31536000" );
			blobAccessorPublic.createOrUpdateBlob( blobEntry );
		} catch( IOException e ) {
			logger.log( Level.SEVERE, "Failed to create/update author image.", e );
			throw new UnexpectedServerException();
		}
		

		AccessToken accessToken = (AccessToken) request.getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
		AuditLog auditLog = dataAccessor.newAuditLog();
		auditLog.setAccessId( accessToken.getId() );
		auditLog.setEventId( ACCESS_TO_UPDATE_AUTHOR_DATA.getId() );
		auditLog.setEventDataOld( gson.toJson( author ) );

		author.setCustomCover( true );
		author.setLastUpdated( new Date() );
		author = dataAccessor.createOrUpdateAuthor( author );

		auditLog.setEventDataNew( gson.toJson( author ) );
		auditLog.setEventComment( "Uploaded profile image." );
		auditLog = dataAccessor.createAuditLog( auditLog );
	}
	
	public static boolean createOrUpdateAuthorPageUrl( Long authorId, HttpServletRequest request ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Author author = dataAccessor.getAuthor( authorId );
		Page page = dataAccessor.getPage( PageType.AUTHOR.toString(), authorId );
		
		if( page == null ) {
			page = dataAccessor.newPage();
			page.setType( PageType.AUTHOR.toString() );
			page.setUri( PageType.AUTHOR.getUrlPrefix() + authorId );
			page.setPrimaryContentId( authorId );
			page.setCreationDate( new Date() );
		}
		
		String uriAlias = PratilipiHelper.get( request ).generateUriAlias(
				page.getUriAlias(),
				"/", author.getFirstNameEn(), author.getLastNameEn(), author.getPenNameEn() );

		if( uriAlias.equals( page.getUriAlias() ) )
			return false;
		
		page.setUriAlias( uriAlias );
		page = dataAccessor.createOrUpdatePage( page );
		return true;
	}
	
	public static boolean createOrUpdateAuthorDashboardPageUrl( Long authorId, HttpServletRequest request ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Page page = dataAccessor.getPage( PageType.AUTHOR.toString(), authorId );
		
		Page dashboardPage = dataAccessor.getPage( PageType.AUTHOR_DASHBOARD.toString(), authorId );
		if( dashboardPage == null ) {
			dashboardPage = dataAccessor.newPage();
			dashboardPage.setType( PageType.AUTHOR_DASHBOARD.toString() );
			dashboardPage.setUri( page.getUri() + "/dashboard" );
			dashboardPage.setPrimaryContentId( authorId );
			dashboardPage.setCreationDate( new Date() );
			if( page.getUriAlias() == null )
				dashboardPage = dataAccessor.createOrUpdatePage( dashboardPage );
		}
		
		if( page.getUriAlias() == null ) {
		
			if( dashboardPage.getUriAlias() == null )
				return false;
			
			dashboardPage.setUriAlias( null );
		
		} else {
			
			String uriAlias = page.getUriAlias() + "/dashboard";
			if( uriAlias.equals( dashboardPage.getUriAlias() ) )
				return false;
			
			dashboardPage.setUriAlias( uriAlias );
			
		}

		dashboardPage = dataAccessor.createOrUpdatePage( dashboardPage );

		return true;
	}
	
	public static boolean updateAuthorStats( Long authorId, HttpServletRequest request ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );

		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setAuthorId( authorId );
		List<Pratilipi> pratilipiList = dataAccessor.getPratilipiList( pratilipiFilter, null, null ).getDataList();
		
		if( pratilipiList.size() == 0 )
			return false;
		
		long contentPublished = 0;
		long totalReadCount = 0;
		for( Pratilipi pratilipi : pratilipiList ) {
			if( pratilipi.getState() == PratilipiState.PUBLISHED || pratilipi.getState() == PratilipiState.PUBLISHED_PAID )
				contentPublished++;
			totalReadCount = totalReadCount + pratilipi.getReadCount();
		}
		
		Author author = dataAccessor.getAuthor( authorId );
		if( (long) author.getContentPublished() != contentPublished || (long) author.getTotalReadCount() != totalReadCount ) {
			author.setContentPublished( contentPublished );
			author.setTotalReadCount( totalReadCount );
			author = dataAccessor.createOrUpdateAuthor( author );
			return true;
		} else {
			return false;
		}

	}
	
	public static void updateAuthorSearchIndex( Long authorId, HttpServletRequest request )
			throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		SearchAccessor searchAccessor = DataAccessorFactory.getSearchAccessor();

		Author author = dataAccessor.getAuthor( authorId );
		Language language = dataAccessor.getLanguage( author.getLanguageId() );
		searchAccessor.indexAuthorData( createAuthorData( author, language, request ) );
	}
	
}
