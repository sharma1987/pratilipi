package com.pratilipi.data.access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.Query;

import com.claymus.data.access.DataListCursorTuple;
import com.claymus.data.access.GaeQueryBuilder;
import com.claymus.data.access.GaeQueryBuilder.Operator;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.commons.shared.AuthorFilter;
import com.pratilipi.commons.shared.CategoryFilter;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.commons.shared.UserPratilipiFilter;
import com.pratilipi.data.access.gae.CategoryEntity;
import com.pratilipi.data.access.gae.EventEntity;
import com.pratilipi.data.access.gae.EventPratilipiEntity;
import com.pratilipi.data.access.gae.GenreEntity;
import com.pratilipi.data.access.gae.LanguageEntity;
import com.pratilipi.data.access.gae.PratilipiAuthorEntity;
import com.pratilipi.data.access.gae.PratilipiCategoryEntity;
import com.pratilipi.data.access.gae.PratilipiGenreEntity;
import com.pratilipi.data.access.gae.PratilipiMetaEntity;
import com.pratilipi.data.access.gae.PratilipiTagEntity;
import com.pratilipi.data.access.gae.PublisherEntity;
import com.pratilipi.data.access.gae.TagEntity;
import com.pratilipi.data.access.gae.UserPratilipiEntity;
import com.pratilipi.data.transfer.Category;
import com.pratilipi.data.transfer.Event;
import com.pratilipi.data.transfer.EventPratilipi;
import com.pratilipi.data.transfer.Genre;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.PratilipiAuthor;
import com.pratilipi.data.transfer.PratilipiCategory;
import com.pratilipi.data.transfer.PratilipiGenre;
import com.pratilipi.data.transfer.PratilipiMeta;
import com.pratilipi.data.transfer.PratilipiTag;
import com.pratilipi.data.transfer.Publisher;
import com.pratilipi.data.transfer.Tag;
import com.pratilipi.data.transfer.UserPratilipi;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.type.gae.AuthorEntity;
import com.pratilipi.data.type.gae.PratilipiEntity;

@SuppressWarnings("serial")
public class DataAccessorGaeImpl
		extends com.claymus.data.access.DataAccessorGaeImpl
		implements DataAccessor {

	private static final Logger logger = 
			Logger.getLogger( DataAccessorGaeImpl.class.getName() );

	
	@Override
	public Pratilipi newPratilipi() {
		return new PratilipiEntity();
	}

	@Override
	public Pratilipi getPratilipi( Long id ) {
		try {
			Pratilipi pratilipi = getEntity( PratilipiEntity.class, id );
			return pratilipi.getState() == PratilipiState.DELETED ? null : pratilipi;
		} catch( JDOObjectNotFoundException e ) {
			return null;
		}
	}

	@Override
	public DataListCursorTuple<Long> getPratilipiIdList(
			PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount ) {
		
		return getPratilipiList( pratilipiFilter, cursorStr, resultCount, true );
	}
	
	@Override
	public DataListCursorTuple<Pratilipi> getPratilipiList(
			PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount ) {
		
		return getPratilipiList( pratilipiFilter, cursorStr, resultCount, false );
	}

	@Override
	public List<Pratilipi> getPratilipiList( List<Long> idList ) {
		List<Pratilipi> pratilipiList = new ArrayList<>( idList.size() );
		for( Long id : idList )
			pratilipiList.add( getPratilipi( id ) );
		return pratilipiList;
	}
	
	@SuppressWarnings("unchecked")
	private <T> DataListCursorTuple<T> getPratilipiList(
			PratilipiFilter pratilipiFilter, String cursorStr,
			Integer resultCount, boolean idOnly ) {
		
		GaeQueryBuilder gaeQueryBuilder =
				new GaeQueryBuilder( pm.newQuery( PratilipiEntity.class ) );

		if( pratilipiFilter.getType() != null )
			gaeQueryBuilder.addFilter( "type", pratilipiFilter.getType() );
		if( pratilipiFilter.getLanguageId() != null )
			gaeQueryBuilder.addFilter( "languageId", pratilipiFilter.getLanguageId() );
		if( pratilipiFilter.getAuthorId() != null )
			gaeQueryBuilder.addFilter( "authorId", pratilipiFilter.getAuthorId() );
		if( pratilipiFilter.getPublisherId() != null )
			gaeQueryBuilder.addFilter( "publisherId", pratilipiFilter.getPublisherId() );
		if( pratilipiFilter.getState() != null )
			gaeQueryBuilder.addFilter( "state", pratilipiFilter.getState() );
		if( pratilipiFilter.getNextProcessDateEnd() != null ) {
			gaeQueryBuilder.addFilter( "nextProcessDate", pratilipiFilter.getNextProcessDateEnd(), Operator.LESS_THAN_OR_EQUAL );
			gaeQueryBuilder.addOrdering( "nextProcessDate", true );
		}
		
		if( pratilipiFilter.getOrderByReadCount() != null )
			gaeQueryBuilder.addOrdering( "readCount", pratilipiFilter.getOrderByReadCount() );

		if( resultCount != null )
			gaeQueryBuilder.setRange( 0, resultCount );
		
		Query query = gaeQueryBuilder.build();
		if( cursorStr != null ) {
			Cursor cursor = Cursor.fromWebSafeString( cursorStr );
			Map<String, Object> extensionMap = new HashMap<String, Object>();
			extensionMap.put( JDOCursorHelper.CURSOR_EXTENSION, cursor );
			query.setExtensions( extensionMap );
		}

		if( idOnly )
			query.setResult( "id" );
		
		List<T> pratilipiEntityList =
				(List<T>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
		Cursor cursor = JDOCursorHelper.getCursor( pratilipiEntityList );
		
		return new DataListCursorTuple<T>(
				idOnly ? pratilipiEntityList : (List<T>) pm.detachCopyAll( pratilipiEntityList ),
				cursor == null ? null : cursor.toWebSafeString() );
	}

	@Override
	public Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi ) {
		return createOrUpdateEntity( pratilipi );
	}

	@Override
	public Boolean deletePratilipi( Long id ){
		try {
//			deleteEntity( PratilipiEntity.class, id );
			return false;
		} catch( JDOObjectNotFoundException e ) {
			e.printStackTrace();
			return false;
		}
	}

	
	@Override
	public PratilipiMeta newPrice(){
		return new PratilipiMetaEntity();
	}
	
	@Override
	public PratilipiMeta getPrice( Long id ){
		try{
			return getEntity( PratilipiMetaEntity.class, id );
		} catch( JDOObjectNotFoundException e ){
			logger.log( Level.SEVERE, "Price Record of id " + id + " is not present" );
			return null;
		}
	}
	
	@Override
	public PratilipiMeta getPriceByPratilipiId( Long pratilipiId ){
		if( pratilipiId == null )
			return null;
		
		Query query = new GaeQueryBuilder( pm.newQuery( PratilipiMetaEntity.class ) )
						.addFilter( "pratilipiId", pratilipiId )
						.addOrdering( "creationDate", false )
						.build();
		
		@SuppressWarnings( "unchecked" )
		List<PratilipiMeta> priceList = ( List<PratilipiMeta> ) query.execute( pratilipiId );
		
		return priceList.size() == 0 ? null : pm.detachCopy( priceList.get( 0 ) );
	}
	
	@Override
	public PratilipiMeta createOrUpdatePrice( PratilipiMeta price ){
		return createOrUpdateEntity( price );
	}
	
	
	@Override
	public Language newLanguage() {
		return new LanguageEntity();
	}

	@Override
	public Language getLanguage( Long id ) {
		return id == null ? null : getEntity( LanguageEntity.class, id );
	}

	@Override
	public List<Language> getLanguageList() {
		Query query =
				new GaeQueryBuilder( pm.newQuery( LanguageEntity.class ) )
						.addOrdering( "nameEn", true )
						.build();
		
		@SuppressWarnings("unchecked")
		List<Language> languageEntityList = (List<Language>) query.execute();
		return (List<Language>) pm.detachCopyAll( languageEntityList );
	}
	
	@Override
	public List<Language> getLanguageList( List<Long> idList ) {
		List<Language> languageList = new ArrayList<>( idList.size() );
		for( Long id : idList )
			languageList.add( getLanguage( id ) );
		return languageList;
	}
	
	@Override
	public Language createOrUpdateLanguage( Language language ) {
		return createOrUpdateEntity( language );
	}

	
	@Override
	public Author newAuthor() {
		return new AuthorEntity();
	}

	@Override
	public Author getAuthor( Long id ) {
		if( id == null )
			return null;
		Author author = getEntity( AuthorEntity.class, id );
		return author.getState() == AuthorState.DELETED ? null : author;
	}
	
	@Override
	public Author getAuthorByEmailId( String email ) {
		Query query = new GaeQueryBuilder( pm.newQuery( AuthorEntity.class ) )
				.addFilter( "email", email )
				.addFilter( "state", AuthorState.DELETED, Operator.NOT_EQUALS )
				.addOrdering( "state", true )
				.addOrdering( "registrationDate", true )
				.build();
		
		@SuppressWarnings("unchecked")
		List<Author> authorList = (List<Author>) query.execute( email, AuthorState.DELETED );

		if( authorList.size() > 1 )
			logger.log( Level.SEVERE, authorList.size() + " Authors found with Email Id " + email   + " ." );

		return authorList.size() == 0 ? null : pm.detachCopy( authorList.get( 0 ) );
	}

	@Override
	public Author getAuthorByUserId( Long userId ) {
		Query query = new GaeQueryBuilder( pm.newQuery( AuthorEntity.class ) )
				.addFilter( "userId", userId )
				.addFilter( "state", AuthorState.DELETED, Operator.NOT_EQUALS )
				.addOrdering( "state", true )
				.addOrdering( "registrationDate", true )
				.build();
		
		@SuppressWarnings("unchecked")
		List<Author> authorList = (List<Author>) query.execute( userId, AuthorState.DELETED );
		
		if( authorList.size() > 1 )
			logger.log( Level.SEVERE, authorList.size() + " Authors found with User Id " + userId + " ." );
		
		return authorList.size() == 0 ? null : pm.detachCopy( authorList.get( 0 ) );
	}
	
	@Override
	public Author getAuthorByName( String nameEn ){
		String firstNameEn = null;
		String lastNameEn = null;
		if( nameEn.lastIndexOf( " " ) != -1 ){
			firstNameEn = nameEn.substring( 0, nameEn.lastIndexOf( " " ));
			lastNameEn = nameEn.substring( nameEn.lastIndexOf( " " ) + 1 );
		} else
			firstNameEn = nameEn;
		
		Query query = new GaeQueryBuilder( pm.newQuery( AuthorEntity.class ) )
				.addFilter( "firstNameEn", firstNameEn )
				.addFilter( "lastNameEn", lastNameEn )
				.build();
		
		@SuppressWarnings("unchecked")
		List<Author> authorList = (List<Author>) query.execute( firstNameEn, lastNameEn );
		
		if( authorList.size() > 1 )
			logger.log( Level.SEVERE, authorList.size() + " Authors found with name " + nameEn + " ." );
		
		return authorList.size() == 0 ? null : pm.detachCopy( authorList.get( 0 ) );
	}
	
	@Override
	public DataListCursorTuple<Author> getAuthorList( String cursorStr, int resultCount ) {

		Query query =
				new GaeQueryBuilder( pm.newQuery( AuthorEntity.class ) )
						.addOrdering( "firstNameEn", true )
						.addOrdering( "lastNameEn", true )
						.addOrdering( "penNameEn", true )
						.setRange( 0, resultCount )
						.build();

		if( cursorStr != null ) {
			Cursor cursor = Cursor.fromWebSafeString( cursorStr );
			Map<String, Object> extensionMap = new HashMap<String, Object>();
			extensionMap.put( JDOCursorHelper.CURSOR_EXTENSION, cursor );
			query.setExtensions(extensionMap);
		}
		
		@SuppressWarnings("unchecked")
		List<Author> authorEntityList = (List<Author>) query.execute();
		Cursor cursor = JDOCursorHelper.getCursor( authorEntityList );
		
		return new DataListCursorTuple<>(
				(List<Author>) pm.detachCopyAll( authorEntityList ),
				cursor.toWebSafeString() );
	
	}
	
	@Override
	public DataListCursorTuple<Long> getAuthorIdList(
			AuthorFilter authorFilter, String cursorStr, Integer resultCount ) {
		
		return getAuthorList( authorFilter, cursorStr, resultCount, true );
	}
	
	@Override
	public DataListCursorTuple<Author> getAuthorList(
			AuthorFilter authorFilter, String cursorStr, Integer resultCount ) {
		
		return getAuthorList( authorFilter, cursorStr, resultCount, false );
	}

	@Override
	public List<Author> getAuthorList( List<Long> idList ) {
		List<Author> authorList = new ArrayList<>( idList.size() );
		for( Long id : idList )
			authorList.add( getAuthor( id ) );
		return authorList;
	}
	
	@SuppressWarnings("unchecked")
	private <T> DataListCursorTuple<T> getAuthorList( AuthorFilter authorFilter, String cursorStr, Integer resultCount, boolean idOnly ) {

		GaeQueryBuilder gaeQueryBuilder =
				new GaeQueryBuilder( pm.newQuery( AuthorEntity.class ) );
		
		if( authorFilter.getNextProcessDateEnd() != null ) {
			gaeQueryBuilder.addFilter( "nextProcessDate", authorFilter.getNextProcessDateEnd(), Operator.LESS_THAN_OR_EQUAL );
			gaeQueryBuilder.addOrdering( "nextProcessDate", true );
		}
	
		if( authorFilter.getOrderByContentPublished() != null )
			gaeQueryBuilder.addOrdering( "contentPublished", authorFilter.getOrderByContentPublished() );
	
		if( resultCount != null )
			gaeQueryBuilder.setRange( 0, resultCount );
	
		Query query = gaeQueryBuilder.build();
		if( cursorStr != null ) {
			Cursor cursor = Cursor.fromWebSafeString( cursorStr );
			Map<String, Object> extensionMap = new HashMap<String, Object>();
			extensionMap.put( JDOCursorHelper.CURSOR_EXTENSION, cursor );
			query.setExtensions( extensionMap );
		}

		if( idOnly )
			query.setResult( "id" );
		
		List<T> authorEntityList = (List<T>) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
		Cursor cursor = JDOCursorHelper.getCursor( authorEntityList );
		
		return new DataListCursorTuple<T>(
				idOnly ? authorEntityList : (List<T>) pm.detachCopyAll( authorEntityList ),
				cursor == null ? null : cursor.toWebSafeString() );
	}
	
	@Override
	public Author createOrUpdateAuthor( Author author ) {
		return createOrUpdateEntity( author );
	}

	@Override
	public Boolean deleteAuthor( Long id ){
		try {
//			deleteEntity( AuthorEntity.class, id );
			return false;
		} catch( JDOObjectNotFoundException e ) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public Publisher newPublisher() {
		return new PublisherEntity();
	}

	@Override
	public Publisher getPublisher( Long id ) {
		try {
			return  id == null ? null : getEntity( PublisherEntity.class, id );
		} catch( JDOObjectNotFoundException e ) {
			return null;
		}
	}
	
	@Override
	public List<Publisher> getPublisherList() {
		Query query =
				new GaeQueryBuilder( pm.newQuery( PublisherEntity.class ) )
						.build();
		
		@SuppressWarnings("unchecked")
		List<Publisher> publisherEntityList = (List<Publisher>) query.execute();
		return (List<Publisher>) pm.detachCopyAll( publisherEntityList );
	}

	@Override
	public Publisher createOrUpdatePublisher( Publisher publisher ) {
		return createOrUpdateEntity( publisher );
	}

	
	@Override
	public Event newEvent() {
		return new EventEntity();
	}

	@Override
	public Event getEvent( Long id ) {
		try {
			return id == null ? null : getEntity( EventEntity.class, id );
		} catch( JDOObjectNotFoundException e ) {
			return null;
		}
	}
	
	@Override
	public Event createOrUpdateEvent( Event event ) {
		return createOrUpdateEntity( event );
	}
	
	
	@Override
	public EventPratilipi newEventPratilipi() {
		return new EventPratilipiEntity();
	}

	@Override
	public EventPratilipi createOrUpdateEventPratilipi( EventPratilipi eventPratilipi ) {
		return createOrUpdateEntity( eventPratilipi );
	}

	@Override
	public List<EventPratilipi> getEventPratilipiListByEventId( Long eventId ) {
		Query query = new GaeQueryBuilder( pm.newQuery( EventPratilipiEntity.class ) )
				.addFilter( "eventId", eventId )
				.addOrdering( "praticipationDate", false )
				.build();
		
		@SuppressWarnings("unchecked")
		List<EventPratilipi> eventPratilipiList = (List<EventPratilipi>) query.execute( eventId );
		
		return ( List<EventPratilipi> ) pm.detachCopy( eventPratilipiList );
	}
	
	@Override
	public EventPratilipi getEventPratilipiByPratilipiId( Long pratilipiId ){
		Query query = new GaeQueryBuilder( pm.newQuery( EventPratilipiEntity.class ) )
				.addFilter( "pratilipiId", pratilipiId )
				.build();
		
		@SuppressWarnings("unchecked")
		List<EventPratilipi> eventPratilipiList = ( List<EventPratilipi> ) query.execute( pratilipiId );
		
		return eventPratilipiList.size() == 0 ? null : pm.detachCopy( eventPratilipiList.get(0));
	}

	
	@Override
	public Genre newGenre() {
		return new GenreEntity();
	}

	@Override
	public Genre getGenre( Long id ) {
		return getEntity( GenreEntity.class, id );
	}

	@Override
	public List<Genre> getGenreList() {
		Query query =
				new GaeQueryBuilder( pm.newQuery( GenreEntity.class ) )
						.addOrdering( "name", false )
						.build();
		
		@SuppressWarnings("unchecked")
		List<Genre> genreEntityList = (List<Genre>) query.execute();
		return (List<Genre>) pm.detachCopyAll( genreEntityList );
	}
	
	@Override
	public Genre createOrUpdateGenre( Genre genre ) {
		return createOrUpdateEntity( genre );
	}


	@Override
	public Category newCategory() {
		return new CategoryEntity();
	}

	@Override
	public Category getCategory(Long id) {
		if( id == null )
			return null;
		else
			return getEntity( CategoryEntity.class, id );
	}

	@Override
	public List<Category> getCategoryList() {
		Query query = 
				new GaeQueryBuilder( pm.newQuery( CategoryEntity.class ))
						.addOrdering( "serial_number", true )
						.addOrdering( "name", true )
						.build();
		
		@SuppressWarnings( "unchecked" )
		List<Category> categoryEntityList = ( List<Category> ) query.execute();
		return ( List<Category> ) pm.detachCopyAll( categoryEntityList );
	}
	
	@Override
	public List<Category> getCategoryList( CategoryFilter categoryFilter ) {
		
		GaeQueryBuilder gaeQueryBuilder = new GaeQueryBuilder( pm.newQuery( CategoryEntity.class ) );
		
		if( categoryFilter.getType() != null )
			gaeQueryBuilder.addFilter( "type", categoryFilter.getType() );
		
		if( categoryFilter.getLanguageId() != null )
			gaeQueryBuilder.addFilter( "languageId", categoryFilter.getLanguageId() );
		
		if( categoryFilter.getHidden() != null )
			gaeQueryBuilder.addFilter( "hidden", categoryFilter.getHidden() );
		
		gaeQueryBuilder.addOrdering( "name", true );
		
		Query query = gaeQueryBuilder.build();
		
		@SuppressWarnings( "unchecked" )
		List<Category> categoryEntityList = ( List<Category> ) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
		return ( List<Category> ) pm.detachCopyAll( categoryEntityList );
	}

	@Override
	public Category createOrUpdateCategory(Category category) {
		return createOrUpdateEntity( category );
	}


	@Override
	public Tag newTag() {
		return new TagEntity();
	}

	@Override
	public Tag getTag( Long id ) {
		return getEntity( TagEntity.class, id );
	}

	@Override
	public Tag createOrUpdateTag( Tag tag ) {
		return createOrUpdateEntity( tag );
	}

	
	@Override
	public PratilipiAuthor newPratilipiAuthor() {
		return new PratilipiAuthorEntity();
	}

	@Override
	public PratilipiAuthor createOrUpdatePratilipiAuthor( PratilipiAuthor pratilipiAuthor ) {
		return createOrUpdateEntity( pratilipiAuthor );
	}

	
	@Override
	public PratilipiGenre newPratilipiGenre() {
		return new PratilipiGenreEntity();
	}

	@Override
	public PratilipiGenre getPratilipiGenre( Long pratilipiId, Long genreId ) {
		try {
			return getEntity( PratilipiGenreEntity.class, pratilipiId + "-" + genreId );
		} catch( JDOObjectNotFoundException e ) {
			return null;
		}
	}
	
	@Override
	public List<PratilipiGenre> getPratilipiGenreList( Long pratilipiId ) {
		Query query =
				new GaeQueryBuilder( pm.newQuery( PratilipiGenreEntity.class ) )
						.addFilter( "genreId", pratilipiId )
						.build();
		
		@SuppressWarnings("unchecked")
		List<PratilipiGenre> pratilipiGenreEntityList = (List<PratilipiGenre>) query.execute( pratilipiId );
		return (List<PratilipiGenre>) pm.detachCopyAll( pratilipiGenreEntityList );
	}
	
	@Override
	public PratilipiGenre createPratilipiGenre( PratilipiGenre pratilipiGenre ) {
		( (PratilipiGenreEntity) pratilipiGenre ).setId( pratilipiGenre.getPratilipiId() + "-" + pratilipiGenre.getGenreId() );
		return createOrUpdateEntity( pratilipiGenre );
	}

	@Override
	public void deletePratilipiGenre( Long pratilipiId, Long genreId ) {
		try {
			deleteEntity( PratilipiGenreEntity.class, pratilipiId + "-" + genreId );
		} catch( JDOObjectNotFoundException e ) {
			// Do nothing
		}
	}

	
	@Override
	public PratilipiCategory newPratilipiCategory() {
		return new PratilipiCategoryEntity();
	}

	@Override
	public PratilipiCategory getPratilipiCategory(Long pratilipiId, Long categoryId) {
		try{
			return getEntity( PratilipiCategoryEntity.class, pratilipiId + "-" + categoryId );
		} catch ( JDOObjectNotFoundException e ){
			return null;
		}
	}

	@Override
	public List<PratilipiCategory> getPratilipiCategoryList(Long pratilipiId) {
		if( pratilipiId == null )
			return null;
		
		Query query = 
				new GaeQueryBuilder( pm.newQuery( PratilipiCategoryEntity.class ))
					.addFilter( "pratilipiId", pratilipiId )
					.build();
		
		@SuppressWarnings( "unchecked" )
		List<PratilipiCategory> pratilipiCategoryEntityList = ( List<PratilipiCategory> ) query.execute( pratilipiId );
		return (List<PratilipiCategory>) pm.detachCopyAll( pratilipiCategoryEntityList );
	}
	
	@Override
	public DataListCursorTuple<PratilipiCategory> getCategoryPratilipiList( Long categoryId, Integer resultCount, String cursorStr ) {
		if( categoryId == null )
			return null;
		
		GaeQueryBuilder gaeQueryBuilder = 
				new GaeQueryBuilder( pm.newQuery( PratilipiCategoryEntity.class ))
					.addFilter( "categoryId", categoryId );
		
		if( resultCount != null )
					gaeQueryBuilder.setRange( 0, resultCount );
		
		Query query = gaeQueryBuilder.build();
		
		if( cursorStr != null ) {
			Cursor cursor = Cursor.fromWebSafeString( cursorStr );
			Map<String, Object> extensionMap = new HashMap<String, Object>();
			extensionMap.put( JDOCursorHelper.CURSOR_EXTENSION, cursor );
			query.setExtensions(extensionMap);
		}
		
		@SuppressWarnings("unchecked")
		List<PratilipiCategory> pratilipiCategoryEntityList = (List<PratilipiCategory>) query.execute( categoryId );
		Cursor cursor = JDOCursorHelper.getCursor( pratilipiCategoryEntityList );
		
		return new DataListCursorTuple<>(
				(List<PratilipiCategory>) pm.detachCopyAll( pratilipiCategoryEntityList ),
				cursor == null ? null : cursor.toWebSafeString() );
	}

	@Override
	public PratilipiCategory createPratilipiCategory( PratilipiCategory pratilipiCategory) {
		( (PratilipiCategoryEntity) pratilipiCategory ).setId( pratilipiCategory.getPratilipiId() + "-" + pratilipiCategory.getCategoryId() );
		return createOrUpdateEntity( pratilipiCategory );
	}

	@Override
	public void deletePratilipiCategory(Long pratilipiId, Long categoryId) {
		try {
			deleteEntity( PratilipiCategoryEntity.class, pratilipiId + "-" + categoryId );
		} catch( JDOObjectNotFoundException e ) {
			// Do nothing
		}
	}

	
	@Override
	public PratilipiTag newPratilipiTag() {
		return new PratilipiTagEntity();
	}

	@Override
	public PratilipiTag createOrUpdatePratilipiTag( PratilipiTag pratilipiTag ) {
		return createOrUpdateEntity( pratilipiTag );
	}

	
	@Override
	public UserPratilipi newUserPratilipi() {
		return new UserPratilipiEntity();
	}
	
	@Override
	public UserPratilipi getUserPratilipiById( String userPratilipiId ){
		if( userPratilipiId == null )
			return null;
		
		try {
			return getEntity( UserPratilipiEntity.class, userPratilipiId );
		} catch( JDOObjectNotFoundException e ) {
			return null;
		}
	}
	
	@Override
	public UserPratilipi getUserPratilipi( Long userId, Long pratilipiId ) {
		if( userId == null || pratilipiId == null )
			return null;
		
		try {
			return getEntity( UserPratilipiEntity.class, userId + "-" + pratilipiId );
		} catch( JDOObjectNotFoundException e ) {
			return null;
		}
	}

	@Override
	public List<UserPratilipi> getUserPratilipiList( Long pratilipiId ) {
		
		Query query =
				new GaeQueryBuilder( pm.newQuery( UserPratilipiEntity.class ) )
						.addFilter( "pratilipiId", pratilipiId )
						.addOrdering( "reviewDate", false )
						.build();
		
		@SuppressWarnings("unchecked")
		List<UserPratilipi> userPratilipiList = (List<UserPratilipi>) query.execute( pratilipiId );
		return (List<UserPratilipi>) pm.detachCopyAll( userPratilipiList );
		
	}
	
	@Override
	public List<UserPratilipi> getUserPratilipiList( UserPratilipiFilter userPratilipiFilter) {
		
		GaeQueryBuilder gaeQueryBuilder =
				new GaeQueryBuilder( pm.newQuery( UserPratilipiEntity.class ) );
		
		if( userPratilipiFilter.getPratilipiId() != null )
			gaeQueryBuilder.addFilter( "pratilipiId", userPratilipiFilter.getPratilipiId() );
		if( userPratilipiFilter.getUserId() != null )
			gaeQueryBuilder.addFilter( "userId", userPratilipiFilter.getUserId() );
		if( userPratilipiFilter.getOrderByReviewDate() != null )
			gaeQueryBuilder.addOrdering( "reviewDate", userPratilipiFilter.getOrderByReviewDate() );
		if( userPratilipiFilter.getOrderByLastOpenedDate() != null )
			gaeQueryBuilder.addOrdering( "lastOpenedDate", userPratilipiFilter.getOrderByLastOpenedDate() );
		
		Query query = gaeQueryBuilder.build();
		
		@SuppressWarnings("unchecked")
		List<UserPratilipi> userPratilipiList = 
							( List<UserPratilipi> ) query.executeWithMap( gaeQueryBuilder.getParamNameValueMap() );
		
		return ( List<UserPratilipi> ) pm.detachCopyAll( userPratilipiList );
	}

	@SuppressWarnings("unchecked")
	public List<Long> getPurchaseList( Long userId ) {
		Query query =
				new GaeQueryBuilder( pm.newQuery( UserPratilipiEntity.class ) )
						.addFilter( "userId", userId )
						.addFilter( "purchasedFrom", null, GaeQueryBuilder.Operator.NOT_NULL )
						.setResult( "pratilipiId" )
						.build();
		
		return (List<Long>) query.execute( userId );
	}
	
	@Override
	public UserPratilipi createOrUpdateUserPratilipi( UserPratilipi userPratilipi ) {
		( (UserPratilipiEntity) userPratilipi ).setId( userPratilipi.getUserId() + "-" + userPratilipi.getPratilipiId() );
		return createOrUpdateEntity( userPratilipi );
	}

	@Override
	public Boolean deleteUserPratilipi( UserPratilipi userPratilipi ){
		try {
//			deleteEntity( UserPratilipiEntity.class, userPratilipi.getId() );
			return false;
		} catch( JDOObjectNotFoundException e ) {
			e.printStackTrace();
			return false;
		}
	}
}
