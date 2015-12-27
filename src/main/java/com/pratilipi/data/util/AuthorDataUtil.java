package com.pratilipi.data.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.UserCampaign;
import com.pratilipi.common.type.UserState;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.common.util.ImageUtil;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.common.util.SystemProperty;
import com.pratilipi.common.util.UriAliasUtil;
import com.pratilipi.common.util.UserAccessUtil;
import com.pratilipi.data.BlobAccessor;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.DataListCursorTuple;
import com.pratilipi.data.SearchAccessor;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.User;
import com.pratilipi.filter.AccessTokenFilter;

public class AuthorDataUtil {
	
	private static final Logger logger =
			Logger.getLogger( AuthorDataUtil.class.getName() );
	
	private static final String IMAGE_FOLDER = "author-image/original";

	
	public static boolean hasAccessToListAuthorData( Language language ) {
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		return UserAccessUtil.hasUserAccess( accessToken.getUserId(), language, AccessType.AUTHOR_LIST );
	}

	public static boolean hasAccessToAddAuthorData( AuthorData authorData ) {
		if( authorData.getState() != AuthorState.ACTIVE )
			return false;
		
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		return UserAccessUtil.hasUserAccess( accessToken.getUserId(), authorData.getLanguage(), AccessType.AUTHOR_ADD );
	}

	public static boolean hasAccessToUpdateAuthorData( Author author, AuthorData authorData ) {
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		if( UserAccessUtil.hasUserAccess( accessToken.getUserId(), author.getLanguage(), AccessType.AUTHOR_UPDATE ) ) {
			if( authorData == null || ! authorData.hasLanguage() || authorData.getLanguage() == author.getLanguage() )
				return true;
			else if( UserAccessUtil.hasUserAccess( accessToken.getUserId(), authorData.getLanguage(), AccessType.AUTHOR_UPDATE ) )
				return true;
		}
		
		if( author.getState() != AuthorState.BLOCKED && author.getState() != AuthorState.DELETED )
			return accessToken.getUserId().equals( author.getUserId() );
		
		return false;
	}
	
	
	public static String createAuthorImageUrl( Author author ) {
		return createAuthorImageUrl( author, null );
	}
	
	public static String createAuthorImageUrl( Author author, Integer width ) {
		String url = "/author/image";
		if( author.hasCustomImage() ) {
			url = url + "?authorId=" + author.getId() + "&version=" + author.getLastUpdated().getTime();
			if( width != null )
				url = url + "&width=" + width;
			if( SystemProperty.CDN != null )
				url = SystemProperty.CDN.replace( "*", author.getId() % 10 + "" ) + url;
		} else {
			if( width != null )
				url = url + "?width=" + width;
			if( SystemProperty.CDN != null )
				url = SystemProperty.CDN.replace( "*", "10" ) + url;
		}
		return url;
	}
	
	
	public static AuthorData createAuthorData( Author author ) {
		return createAuthorData(author, false, false );
	}
	
	public static AuthorData createAuthorData( Author author, boolean includeAll, boolean includeMetaData ) {
		if( author == null )
			return null;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Page authorPage = dataAccessor.getPage( PageType.AUTHOR, author.getId() );
		
		
		AuthorData authorData = new AuthorData();
		
		authorData.setId( author.getId() );

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

		if( includeMetaData )
			authorData.setEmail( author.getEmail() );
		authorData.setLanguage( author.getLanguage() );
		if( includeAll )
			authorData.setSummary( author.getSummary() );
		
		authorData.setPageUrl( authorPage.getUri() );
		authorData.setPageUrlAlias( authorPage.getUriAlias() );
		authorData.setImageUrl( createAuthorImageUrl( author ) );

		authorData.setRegistrationDate( author.getRegistrationDate() );
		authorData.setContentPublished( author.getContentPublished() );
		authorData.setTotalReadCount( author.getTotalReadCount() );
		authorData.setTotalFbLikeShareCount( author.getTotalFbLikeShareCount() );
		
		return authorData;
	}
	
	public static List<AuthorData> createAuthorDataList( List<Author> authorList ) {
		List<AuthorData> authorDataList = new ArrayList<>( authorList.size() );
		for( Author author : authorList )
			authorDataList.add( createAuthorData( author ) );
		return authorDataList;
	}
	

	public static DataListCursorTuple<AuthorData> getAuthorDataList( AuthorFilter authorFilter,
			String cursor, Integer resultCount ) throws InsufficientAccessException {
		
		if( ! hasAccessToListAuthorData( authorFilter.getLanguage() ) )
			throw new InsufficientAccessException();
		
		DataListCursorTuple<Author> authorListCursorTuple = DataAccessorFactory
				.getDataAccessor()
				.getAuthorList( authorFilter, cursor, resultCount );
		
		List<AuthorData> authorDataList = createAuthorDataList( authorListCursorTuple.getDataList() );
		
		return new DataListCursorTuple<AuthorData>( authorDataList, authorListCursorTuple.getCursor() );
	}
	
	public static AuthorData saveAuthorData( AuthorData authorData )
			throws InvalidArgumentException, InsufficientAccessException {
		
		_validateAuthorDataforSave( authorData );

		boolean isNew = authorData.getId() == null;
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = isNew ? dataAccessor.newAuthor() : dataAccessor.getAuthor( authorData.getId() );
		
		if( isNew && ! hasAccessToAddAuthorData( authorData ) )
			throw new InsufficientAccessException();
		if( ! isNew && ! hasAccessToUpdateAuthorData( author, authorData ) )
			throw new InsufficientAccessException();
		
		
		Gson gson = new Gson();

		
		AuditLog auditLog = dataAccessor.newAuditLog();
		auditLog.setAccessId( AccessTokenFilter.getAccessToken().getId() );
		auditLog.setAccessType( isNew ? AccessType.AUTHOR_ADD : AccessType.AUTHOR_UPDATE );
		auditLog.setEventDataOld( gson.toJson( author ) );
		
		
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
		
		if( authorData.hasEmail() )
			author.setEmail( authorData.getEmail() == null ? null : authorData.getEmail().toLowerCase() );
		if( authorData.hasLanguage() )
			author.setLanguage( authorData.getLanguage() );
		if( authorData.hasSummary() )
			author.setSummary( authorData.getSummary() );

		if( authorData.hasState() )
			author.setState( authorData.getState() );
		if( isNew )
			author.setRegistrationDate( new Date() );
		author.setLastUpdated( new Date() );

		
		auditLog.setEventDataNew( gson.toJson( author ) );

		
		if( isNew && authorData.hasEmail() && authorData.getEmail() != null ) {

			User user = dataAccessor.newUser();
			user.setEmail( authorData.getEmail().toLowerCase() );
			user.setState( UserState.REGISTERED );
			user.setCampaign( UserCampaign.AEE_TEAM );
			user.setReferrer( AccessTokenFilter.getAccessToken().getUserId().toString() );
			user.setSignUpDate( new Date() );
			user.setSignUpSource( UserDataUtil.getUserSignUpSource( false, false ) );

			user = dataAccessor.createOrUpdateUser( user );
			
			author.setUserId( user.getId() );
			
		} else if( ! isNew && authorData.hasEmail() && authorData.getEmail() != null ) {

			User user = dataAccessor.getUser( author.getUserId() );
			if( ! user.getEmail().equals( authorData.getEmail().toLowerCase() ) ) {

				user.setEmail( authorData.getEmail().toLowerCase() );
				if( user.getState() == UserState.ACTIVE )
					user.setState( UserState.REGISTERED );
				
				user = dataAccessor.createOrUpdateUser( user );
				
			}
			
		}
		
		
		author = dataAccessor.createOrUpdateAuthor( author, auditLog );

		if( isNew )
			createOrUpdateAuthorPageUrl( author.getId() );

		return createAuthorData( author );
		
	}
	
	private static void _validateAuthorDataforSave( AuthorData authorData ) throws InvalidArgumentException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		boolean isNew = authorData.getId() == null;
		JsonObject errorMessages = new JsonObject();

		
		// Language is mandatory.
		if( isNew && ( ! authorData.hasLanguage() || authorData.getLanguage() == null ) )
			errorMessages.addProperty( "langauge", GenericRequest.ERR_LANGUAGE_REQUIRED );
		// Language can not be un-set or set to null.
		if( ! isNew && authorData.hasLanguage() && authorData.getLanguage() == null )
			errorMessages.addProperty( "langauge", GenericRequest.ERR_LANGUAGE_REQUIRED );

		
		// Email is optional.
		if( isNew && authorData.hasEmail() && authorData.getEmail() != null ) {
			Author author = dataAccessor.getAuthorByEmailId( authorData.getEmail().toLowerCase() );
			if( author != null )
				errorMessages.addProperty( "email", GenericRequest.ERR_EMAIL_REGISTERED_ALREADY );
		}
		// Email, once set, can not be un-set or changed to null.
		if( ! isNew && authorData.hasEmail() && authorData.getEmail() == null ) {
			Author author = dataAccessor.getAuthor( authorData.getId() );
			if( author.getEmail() != null )
				errorMessages.addProperty( "email", GenericRequest.ERR_EMAIL_REQUIRED );
		}
		// Email should not be registered already.
		if( ! isNew && authorData.hasEmail() && authorData.getEmail() != null ) {
			Author author = dataAccessor.getAuthorByEmailId( authorData.getEmail().toLowerCase() );
			if( author != null && ! author.getId().equals( authorData.getId() ) )
				errorMessages.addProperty( "email", GenericRequest.ERR_EMAIL_REGISTERED_ALREADY );				
		}
		

		// State must be ACTIVE for new profile.
		if( isNew && ( ! authorData.hasState() || authorData.getState() != AuthorState.ACTIVE ) )
			errorMessages.addProperty( "state", GenericRequest.ERR_AUTHOR_STATE_INVALID );
		// State can not be un-set or set to null.
		if( ! isNew && authorData.hasState() && authorData.getState() == null )
			errorMessages.addProperty( "state", GenericRequest.ERR_AUTHOR_STATE_REQUIRED );


		if( errorMessages.entrySet().size() > 0 )
			throw new InvalidArgumentException( errorMessages );
	}
	
	
	public static BlobEntry getAuthorImage( Long authorId, Integer width )
			throws UnexpectedServerException {

		Author author = authorId == null ? null : DataAccessorFactory.getDataAccessor().getAuthor( authorId );
		
		String fileName = "";
		if( author != null && author.hasCustomImage() )
			fileName = IMAGE_FOLDER + "/" + authorId;
		else
			fileName = IMAGE_FOLDER + "/" + "author";

		BlobEntry blobEntry = DataAccessorFactory.getBlobAccessor().getBlob( fileName );
		if( width != null )
			blobEntry.setData( ImageUtil.resize( blobEntry.getData(), width, width ) );
		return blobEntry;
	}
	
	public static void saveAuthorImage( Long authorId, BlobEntry blobEntry )
			throws InsufficientAccessException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = dataAccessor.getAuthor( authorId );

		if( ! hasAccessToUpdateAuthorData( author, null ) )
			throw new InsufficientAccessException();

		
		BlobAccessor blobAccessor = DataAccessorFactory.getBlobAccessor();
		blobEntry.setName( IMAGE_FOLDER + "/" + authorId );
		blobAccessor.createOrUpdateBlob( blobEntry );
		

		Gson gson = new Gson();
		
		AccessToken accessToken = AccessTokenFilter.getAccessToken();
		AuditLog auditLog = dataAccessor.newAuditLog();
		auditLog.setAccessId( accessToken.getId() );
		auditLog.setAccessType( AccessType.AUTHOR_UPDATE );
		auditLog.setEventDataOld( gson.toJson( author ) );

		author.setCustomImage( true );
		author.setLastUpdated( new Date() );

		auditLog.setEventDataNew( gson.toJson( author ) );
		auditLog.setEventComment( "Uploaded profile image." );
		
		author = dataAccessor.createOrUpdateAuthor( author, auditLog );
	}

	
	public static boolean createOrUpdateAuthorPageUrl( Long authorId ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Author author = dataAccessor.getAuthor( authorId );
		Page page = dataAccessor.getPage( PageType.AUTHOR, authorId );
		
		if( page == null ) {
			page = dataAccessor.newPage();
			page.setType( PageType.AUTHOR );
			page.setUri( PageType.AUTHOR.getUrlPrefix() + authorId );
			page.setPrimaryContentId( authorId );
			page.setCreationDate( new Date() );
			page = dataAccessor.createOrUpdatePage( page );
		}
		
		String uriAlias = UriAliasUtil.generateUriAlias(
				page.getUriAlias(),
				"/", author.getFirstNameEn(), author.getLastNameEn(), author.getPenNameEn() );

		if( uriAlias == page.getUriAlias()
				|| ( uriAlias != null && uriAlias.equals( page.getUriAlias() ) )
				|| ( page.getUriAlias() != null && page.getUriAlias().equals( uriAlias ) ) )
			return false;
		
		logger.log( Level.INFO, "Updating Author Page Url: '" + page.getUriAlias() + "' -> '" + uriAlias + "'" );
		
		page.setUriAlias( uriAlias );
		page = dataAccessor.createOrUpdatePage( page );
		return true;
	}
	
	public static boolean createOrUpdateAuthorDashboardPageUrl( Long authorId ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Page page = dataAccessor.getPage( PageType.AUTHOR, authorId );
		
		Page dashboardPage = dataAccessor.getPage( PageType.AUTHOR_DASHBOARD, authorId );
		if( dashboardPage == null ) {
			dashboardPage = dataAccessor.newPage();
			dashboardPage.setType( PageType.AUTHOR_DASHBOARD );
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
	
	public static boolean updateAuthorStats( Long authorId ) {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setAuthorId( authorId );
		List<Pratilipi> pratilipiList = dataAccessor.getPratilipiList( pratilipiFilter, null, null ).getDataList();
		
		if( pratilipiList.size() == 0 )
			return false;
		
		int contentPublished = 0;
		long totalReadCount = 0;
		long totalFbLikeShareCount = 0;
		for( Pratilipi pratilipi : pratilipiList ) {
			if( pratilipi.getState() == PratilipiState.PUBLISHED || pratilipi.getState() == PratilipiState.PUBLISHED_PAID )
				contentPublished++;
			totalReadCount = totalReadCount + pratilipi.getReadCount();
			totalFbLikeShareCount = totalFbLikeShareCount + pratilipi.getFbLikeShareCount();
		}
		
		Author author = dataAccessor.getAuthor( authorId );
		if( (int) author.getContentPublished() == contentPublished
				&& (long) author.getTotalReadCount() == totalReadCount
				&& (long) author.getTotalFbLikeShareCount() == totalFbLikeShareCount )
			return false;
		
		author.setContentPublished( contentPublished );
		author.setTotalReadCount( totalReadCount );
		author.setTotalFbLikeShareCount( totalFbLikeShareCount );
		author = dataAccessor.createOrUpdateAuthor( author );
		return true;

	}
	
	public static void updateAuthorSearchIndex( Long authorId )
			throws UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		SearchAccessor searchAccessor = DataAccessorFactory.getSearchAccessor();

		Author author = dataAccessor.getAuthor( authorId );
		searchAccessor.indexAuthorData( createAuthorData( author, true, true ) );
	}
	
}
