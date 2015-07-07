package com.pratilipi.data.type;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.common.type.Language;
import com.pratilipi.commons.shared.PratilipiContentType;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.commons.shared.PratilipiType;

public interface Pratilipi extends Serializable {

	Long getId();
	
	String getTitle();
	
	void setTitle( String title );
	
	public String getTitleEn();
	
	public void setTitleEn( String titleEn );
	
	Language getLanguage();
	
	void setLanguage( Language language );
	
	Long getLanguageId();

	void setLanguageId( Long languageId );

	Long getAuthorId();
	
	void setAuthorId( Long authorId );
	
	Long getPublisherId();

	void setPublisherId( Long publisherId );
	
	String getSummary();
	
	void setSummary( String summary );

	Long getPublicationYear();
	
	void setPublicationYear( Long publicationYear );


	boolean isPublicDomain();
	
	void setPublicDomain( boolean isPublicDomain );
	
	PratilipiType getType();
	
	void setType( PratilipiType pratilipiType );
	
	PratilipiContentType getContentType();
	
	void setContentType( PratilipiContentType contentType );

	PratilipiState getState();
	
	void setState( PratilipiState state );
	
	Boolean hasCustomCover();

	void setCustomCover( Boolean customCover );

	Date getListingDate();
	
	void setListingDate( Date listingDate );

	Date getLastUpdated();
	
	void setLastUpdated( Date lastUpdated );


	String getIndex();
	
	void setIndex( String index );
	
	Long getWordCount();
	
	void setWordCount( Long wordCount );

	Integer getPageCount();
	
	void setPageCount( Integer pageCount );
	
	
	Long getReviewCount();
	
	void setReviewCount( Long reviewCount );
	
	Long getRatingCount();
	
	void setRatingCount( Long ratingCount );
	
	Long getStarCount();

	void setStarCount( Long starCount );
	
	Long getTotalRating();
	
	void setTotalRating( Long totalRating );

	Long getRelevanceOffset();

	void setRelevanceOffset( Long relevanceOffset );
	
	
	Long getReadCount();
	
	void setReadCount( Long readCount );
	
	Long getFbLikeShareCount();

	void setFbLikeShareCount( Long fbLikeShareCount );

	
	Date getLastProcessDate();

	void setLastProcessDate( Date lastProcessDate );

	Date getNextProcessDate();

	void setNextProcessDate( Date nextProcessDate );

}