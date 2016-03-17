package com.pratilipi.data.access;

import java.util.List;

import com.claymus.data.access.DataListCursorTuple;
import com.pratilipi.commons.shared.AuthorFilter;
import com.pratilipi.commons.shared.CategoryFilter;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.commons.shared.UserPratilipiFilter;
import com.pratilipi.data.transfer.Category;
import com.pratilipi.data.transfer.Event;
import com.pratilipi.data.transfer.EventPratilipi;
import com.pratilipi.data.transfer.Follower;
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

public interface DataAccessor extends com.claymus.data.access.DataAccessor {
	
	Pratilipi newPratilipi();

	Pratilipi getPratilipi( Long id );

	DataListCursorTuple<Long> getPratilipiIdList( PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount );

	DataListCursorTuple<Pratilipi> getPratilipiList( PratilipiFilter pratilipiFilter, String cursorStr, Integer resultCount );
	
	List<Pratilipi> getPratilipiList( List<Long> idList );
	
	Pratilipi createOrUpdatePratilipi( Pratilipi pratilipi );
	
	Boolean deletePratilipi( Long id );
	
	
	PratilipiMeta newPrice();
	
	PratilipiMeta getPrice( Long id );
	
	PratilipiMeta getPriceByPratilipiId( Long pratilipiId );
	
	PratilipiMeta createOrUpdatePrice( PratilipiMeta price );
	

	Language newLanguage();

	Language getLanguage( Long id );

	List<Language> getLanguageList();

	List<Language> getLanguageList( List<Long> idList );
	
	Language createOrUpdateLanguage( Language language );

	
	Author newAuthor();

	Author getAuthor( Long id );
	
	Author getAuthorByEmailId( String email );

	Author getAuthorByUserId( Long userId );
	
	Author getAuthorByName( String nameEn );
	
	@Deprecated
	DataListCursorTuple<Author> getAuthorList( String cursor, int resultCount );

	DataListCursorTuple<Long> getAuthorIdList( AuthorFilter authorFilter, String cursor, Integer resultCount );

	DataListCursorTuple<Author> getAuthorList( AuthorFilter authorFilter, String cursor, Integer resultCount );

	List<Author> getAuthorList( List<Long> idList );
	
	Author createOrUpdateAuthor( Author author );
	
	Boolean deleteAuthor( Long id );

	
	Publisher newPublisher();

	Publisher getPublisher( Long id );
	
	List<Publisher> getPublisherList();

	Publisher createOrUpdatePublisher( Publisher publisher );

	
	Event newEvent();

	Event getEvent( Long id );
	
	Event createOrUpdateEvent( Event event );
	
	
	EventPratilipi newEventPratilipi();
	
	EventPratilipi createOrUpdateEventPratilipi( EventPratilipi eventPratilipi );
	
	List<EventPratilipi> getEventPratilipiListByEventId( Long eventId );
	
	EventPratilipi getEventPratilipiByPratilipiId( Long pratilipiId );

	
	Genre newGenre();

	Genre getGenre( Long id );

	List<Genre> getGenreList();

	Genre createOrUpdateGenre( Genre genre );


	Category newCategory();
	
	Category getCategory( Long id );
	
	List<Category> getCategoryList();
	
	List<Category> getCategoryList( CategoryFilter categoryFilter );
	
	Category createOrUpdateCategory( Category category );


	Tag newTag();

	Tag getTag( Long id );

	Tag createOrUpdateTag( Tag tag );
	

	PratilipiAuthor newPratilipiAuthor();

	PratilipiAuthor createOrUpdatePratilipiAuthor( PratilipiAuthor pratilipiAuthor );
	

	PratilipiGenre newPratilipiGenre();

	PratilipiGenre getPratilipiGenre( Long pratilipiId, Long genreId );

	List<PratilipiGenre> getPratilipiGenreList( Long pratilipiId );
	
	PratilipiGenre createPratilipiGenre( PratilipiGenre pratilipiGenre );

	void deletePratilipiGenre( Long pratilipiId, Long genreId );
	
	
	PratilipiCategory newPratilipiCategory();
	
	PratilipiCategory getPratilipiCategory( Long pratilipiId, Long categoryId );
	
	List<PratilipiCategory> getPratilipiCategoryList( Long pratilipiId );
	
	DataListCursorTuple<PratilipiCategory> getCategoryPratilipiList( Long categoryId, Integer resultCount, String cursorStr );
	
	PratilipiCategory createPratilipiCategory( PratilipiCategory pratilipiCategory );
	
	void deletePratilipiCategory( Long pratilipiId, Long categoryId );


	PratilipiTag newPratilipiTag();

	PratilipiTag createOrUpdatePratilipiTag( PratilipiTag pratilipiTag );
	
	
	UserPratilipi newUserPratilipi();
	
	UserPratilipi getUserPratilipiById( String userPratilipiId );
	
	UserPratilipi getUserPratilipi( Long userId, Long pratilipiId );

	List<UserPratilipi> getUserPratilipiList( Long pratilipiId );
	
	List<UserPratilipi> getUserPratilipiList( UserPratilipiFilter userPratilipiFilter );
	
	List<Long> getPurchaseList( Long userId );

	UserPratilipi createOrUpdateUserPratilipi( UserPratilipi userPratilipi );
	
	Boolean deleteUserPratilipi( UserPratilipi userPratilipi );
	
	Follower newFollower();
	
	Follower addFollower( Follower follower );
	
	Follower getFollowerById( Long authorId, Long userId );
	
	void deleteFollower( Long authorId, Long userId );
	
	List<Follower> getFollowersByAuthorId( Long authorId );
	
	List<Follower> getFollowersByUserId( Long userId );
	
}
