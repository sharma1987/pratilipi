package com.pratilipi.data;

import static com.pratilipi.data.mock.AuthorMock.AUTHOR_TABLE;
import static com.pratilipi.data.mock.PageMock.PAGE_TABLE;
import static com.pratilipi.data.mock.PratilipiMock.PRATILIPI_TABLE;
import static com.pratilipi.data.mock.UserPratilipiMock.USER_PRATILIPI_TABLE;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PageType;
import com.pratilipi.common.util.AuthorFilter;
import com.pratilipi.common.util.PratilipiFilter;
import com.pratilipi.data.mock.AccessTokenMock;
import com.pratilipi.data.mock.AppPropertyMock;
import com.pratilipi.data.mock.AuthorMock;
import com.pratilipi.data.mock.PratilipiMock;
import com.pratilipi.data.mock.UserMock;
import com.pratilipi.data.mock.UserPratilipiMock;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AppProperty;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Category;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.PratilipiCategory;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.UserPratilipi;
import com.pratilipi.data.type.gae.AccessTokenEntity;
import com.pratilipi.data.type.gae.AppPropertyEntity;
import com.pratilipi.data.type.gae.AuditLogEntity;
import com.pratilipi.data.type.gae.AuthorEntity;
import com.pratilipi.data.type.gae.PageEntity;
import com.pratilipi.data.type.gae.PratilipiEntity;
import com.pratilipi.data.type.gae.UserEntity;
import com.pratilipi.data.type.gae.UserPratilipiEntity;

public class DataAccessorMockImpl implements DataAccessor {

	// Transaction Helpers

	public void beginTx() {
		// Do nothing
	}

	public void commitTx() {
		// Do nothing
	}
	
	public void rollbackTx() {
		// Do nothing
	}

	public boolean isTxActive() {
		return false;
	}
	
	
	// APP_PROPERTY Table
	
	@Override
	public AppProperty newAppProperty( String id ) {
		return new AppPropertyEntity();
	}

	@Override
	public AppProperty getAppProperty( String id ) {
		if( id == null )
			return null;
				
		for( AppProperty appProperty : AppPropertyMock.APP_PROPERTY_TABLE )
			if( appProperty.getId().equals( id ) )
				return appProperty;
		return null;
	}

	@Override
	public AppProperty createOrUpdateAppProperty( AppProperty appProperty ) {
		// TODO: Implementation
		return null;
	}


	// USER Table
	
	@Override
	public User newUser() {
		return new UserEntity();
	}
	
	@Override
	public User getUser( Long id ) {
		if( id == null )
			return null;
		
		for( User user : UserMock.USER_TABLE )
			if( user.getId().equals( id ) )
				return user;
				
		return null;
	}
	
	@Override
	public User getUserByEmail( String email ) {
		if( email == null )
			return null;
		
		for( User user : UserMock.USER_TABLE )
			if( user.getEmail() != null && user.getEmail().equals( email ) )
				return user;
				
		return null;
	}
	
	@Override
	public User getUserByFacebookId( String facebookId ) {
		if( facebookId == null )
			return null;
		
		for( User user : UserMock.USER_TABLE )
			if( user.getFacebookId() != null && user.getFacebookId().equals( facebookId ) )
				return user;
				
		return null;
	}
	
	@Override
	public DataListCursorTuple<User> getUserList( String cursor, Integer resultCount ) {
		List<User> dataList = new ArrayList<>( UserMock.USER_TABLE.size() );
		for( User user : UserMock.USER_TABLE )
			dataList.add( user );
		return new DataListCursorTuple<User>( dataList, null );
	}

	@Override
	public User createOrUpdateUser( User user ) {
		Long id = 0L;
		for( User aUser : UserMock.USER_TABLE )
			if( aUser.getId() >= id )
				id = aUser.getId() + 1;
		
		( (UserEntity) user ).setId( id );
		UserMock.USER_TABLE.add( user );
		return user;
	}
	

	// ACCESS_TOKEN Table
	
	@Override
	public AccessToken newAccessToken() {
		return new AccessTokenEntity();
	}

	@Override
	public AccessToken getAccessToken( String accessTokenId ) {
		for( AccessToken accessToken : AccessTokenMock.ACCESS_TOKEN_TABLE )
			if( accessToken.getId().equals( accessTokenId ) )
				return accessToken;
		
		return null;
	}
	
	@Override
	public DataListCursorTuple<AccessToken> getAccessTokenList( String cursorStr, Integer resultCount ) {
		// TODO: Implementation
		return null;
	}
	
	@Override
	public AccessToken createOrUpdateAccessToken( AccessToken accessToken ) {
		AccessTokenMock.ACCESS_TOKEN_TABLE.add( accessToken );
		return accessToken;
	}

	@Override
	public AccessToken[] createOrUpdateAccessTokens( AccessToken... accessTokens ) {
		for( AccessToken accessToken : accessTokens )
			AccessTokenMock.ACCESS_TOKEN_TABLE.add( accessToken );
		return accessTokens;
	}

	@Override
	public void deleteAccessToken( AccessToken accessToken ) {
		// TODO: Implementation
	}

	
	// AUDIT_LOG Table

	@Override
	public AuditLog newAuditLog() {
		return new AuditLogEntity();
	}

	@Override
	public AuditLog createAuditLog( AuditLog auditLog ) {
		// TODO: Implementation
		return null;
	}
	

	@Override
	public DataListCursorTuple<AuditLog> getAuditLogList( String cursorStr, Integer resultCount) {
		// TODO: Implementation
		return null;
	}

	@Override
	public DataListCursorTuple<AuditLog> getAuditLogList( String accessId, String cursorStr, Integer resultCount) {
		// TODO: Implementation
		return null;
	}

	
	// PAGE Table
	
	@Override
	public Page newPage() {
		return new PageEntity();
	}
	
	@Override
	public Page getPage( Long id ) {
		for( Page page : PAGE_TABLE )
			if( page.getId().equals( id ) )
				return page;

		return null;
	}
	
	@Override
	public Page getPage( String uri ) {
		for( Page page : PAGE_TABLE )
			if( uri.equals( page.getUri() ) )
				return page;

		for( Page page : PAGE_TABLE )
			if( uri.equals( page.getUriAlias() ) )
				return page;

		return null;
	}
	
	@Override
	public Page getPage( PageType pageType, Long primaryContentId ) {
		for( Page page : PAGE_TABLE )
			if( page.getType().equals( pageType ) && page.getPrimaryContentId().equals( primaryContentId ) )
				return page;

		return null;
	}
	
	@Override
	public Page createOrUpdatePage( Page page ) {
		PAGE_TABLE.add( page );
		return page;
	}
	
	
	// PRATILIPI Table

	@Override
	public Pratilipi newPratilipi() {
		return new PratilipiEntity();
	}

	@Override
	public Pratilipi getPratilipi( Long id ) {
		for( Pratilipi pratilipi : PRATILIPI_TABLE )
			if( pratilipi.getId().equals( id ) )
				return pratilipi;
		
		return null;
	}

	@Override
	public List<Pratilipi> getPratilipiList( List<Long> idList ) {
		List<Pratilipi> pratilipiList = new ArrayList<>( idList.size() );
		for( Long id : idList )
			pratilipiList.add( getPratilipi( id ) );
		return pratilipiList;
	}
	
	@Override
	public DataListCursorTuple<Long> getPratilipiIdList(
			PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount ) {
		
		// TODO: Implementation
		return null;
	}
	
	@Override
	public DataListCursorTuple<Pratilipi> getPratilipiList(
			PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount ) {
		
		List<Pratilipi> dataList = new ArrayList<>(); 
		for( Pratilipi pratilipi : PratilipiMock.PRATILIPI_TABLE )
			dataList.add( pratilipi );
		return new DataListCursorTuple<Pratilipi>( dataList, null );

	}
	
	@Override
	public Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi ) {
		PRATILIPI_TABLE.add( pratilipi );
		return pratilipi;
	}
	
	@Override
	public Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi, AuditLog auditLog ) {
		PRATILIPI_TABLE.add( pratilipi );
		return pratilipi;
	}

	
	// AUTHOR Table
	
	@Override
	public Author newAuthor() {
		return new AuthorEntity();
	}

	@Override
	public Author getAuthor( Long id ) {
		for( Author author : AUTHOR_TABLE )
			if( author.getId().equals( id ) )
				return author;
		
		return null;
	}
	
	@Override
	public Author getAuthorByEmailId( String email ) {
		for( Author author : AUTHOR_TABLE )
			if( author.getEmail().equals( email ) )
				return author;
		
		return null;
	}

	@Override
	public Author getAuthorByUserId( Long userId ) {
		for( Author author : AUTHOR_TABLE )
			if( author.getUserId().equals( userId ) )
				return author;
		
		return null;
	}
	
	@Override
	public DataListCursorTuple<Long> getAuthorIdList( AuthorFilter authorFilter,
			String cursor, Integer resultCount ) {
		
		// TODO: Implementation
		return null;
	}

	@Override
	public DataListCursorTuple<Author> getAuthorList(
			AuthorFilter authorFilter, String cursor, Integer resultCount ) {
		
		List<Author> dataList = new ArrayList<>(); 
		for( Author author : AuthorMock.AUTHOR_TABLE )
			dataList.add( author );
		return new DataListCursorTuple<Author>( dataList, null );

	}

	@Override
	public List<Author> getAuthorList( List<Long> idList ) {
		List<Author> authorList = new ArrayList<>( idList.size() );
		for( Long id : idList )
			authorList.add( getAuthor( id ) );
		return authorList;
	}
	
	@Override
	public Author createOrUpdateAuthor( Author author ) {
		AUTHOR_TABLE.add( author );
		return author;
	}

	@Override
	public Author createOrUpdateAuthor( Author author, AuditLog auditLog ) {
		AUTHOR_TABLE.add( author );
		return author;
	}

	
	// USER_PRATILIPI Table
	
	@Override
	public UserPratilipi newUserPratilipi() {
		return new UserPratilipiEntity();
	}
	
	@Override
	public UserPratilipi getUserPratilipi( Long userId, Long pratilipiId ) {
		for( UserPratilipi userPratilipi : UserPratilipiMock.USER_PRATILIPI_TABLE )
			if( userPratilipi.getUserId().equals( userId ) && userPratilipi.getPratilipiId().equals( pratilipiId ) )
				return userPratilipi;
		return null;
	}

	@Override
	public DataListCursorTuple<UserPratilipi> getPratilipiReviewList(
			Long pratilipiId, String cursor, Integer resultCount ) {

		List<UserPratilipi> userPratilipiList = new LinkedList<>();

		for( UserPratilipi userPratilipi : USER_PRATILIPI_TABLE )
			if( userPratilipi.getPratilipiId().equals( pratilipiId ) && userPratilipi.getReviewDate() != null )
				userPratilipiList.add( userPratilipi );

		return new DataListCursorTuple<UserPratilipi>( userPratilipiList, userPratilipiList.size() > 0 ? "cursor" : null );
	}

	@Override
	public DataListCursorTuple<UserPratilipi> getUserPratilipiList( Long userId,
			Long pratilipiId, String cursorStr, Integer resultCount ) {
		
		// TODO: Implementation
		return null;
	}

	@Override
	public UserPratilipi createOrUpdateUserPratilipi( UserPratilipi userPratilipi ) {
		for( int i = 0; i < UserPratilipiMock.USER_PRATILIPI_TABLE.size(); i++ ) {
			if( UserPratilipiMock.USER_PRATILIPI_TABLE.get( i ).getUserId().equals( userPratilipi.getUserId() )
					&& UserPratilipiMock.USER_PRATILIPI_TABLE.get( i ).getPratilipiId().equals( userPratilipi.getPratilipiId() ) ) {
				UserPratilipiMock.USER_PRATILIPI_TABLE.remove( i );
				break;
			}
		}

		( (UserPratilipiEntity) userPratilipi ).setId( userPratilipi.getUserId() + "-" + userPratilipi.getPratilipiId() );
		UserPratilipiMock.USER_PRATILIPI_TABLE.add( userPratilipi );
		return userPratilipi;
	}
	
	
	//CATEGORY Table
	
	@Override
	public Category getCategory(Long categoryId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Category> getCategoryList( Language language ) {
		// TODO Auto-generated method stub
		return new ArrayList<Category>( 0 );
	}

	
	//PRATILIPI_CATEGORY Table
	
	@Override
	public List<PratilipiCategory> getPratilipiCategoryList(Long pratilipiId) {
		// TODO Auto-generated method stub
		return new ArrayList<PratilipiCategory>( 0 );
	}
	
	
	// Destroy

	@Override
	public void destroy() {}

}
