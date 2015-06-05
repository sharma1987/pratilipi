package com.pratilipi.data.transfer;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.commons.shared.PratilipiContentType;
import com.pratilipi.commons.shared.PratilipiState;
import com.pratilipi.commons.shared.PratilipiType;

public interface Pratilipi extends Serializable {

	Long getId();
	
	PratilipiType getType();
	
	void setType( PratilipiType pratilipiType );
	
	boolean isPublicDomain();
	
	void setPublicDomain( boolean isPublicDomain );

	String getTitle();
	
	void setTitle( String title );
	
	public String getTitleEn();
	
	public void setTitleEn(String titleEn);
	
	Long getLanguageId();
	
	void setLanguageId( Long languageId );
	
	Long getAuthorId();
	
	void setAuthorId( Long authorId );
	
	Long getPublisherId();
	
	void setPublisherId( Long publisherId );
	
	Long getPublicationYear();
	
	void setPublicationYear( Long publicationYear );
	
	Date getListingDate();
	
	void setListingDate( Date listingDate );
	
	
	Boolean hasCustomCover();

	void setCustomCover( Boolean customCover );

	String getSummary();
	
	void setSummary( String summary );
	
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
	
	Long getRelevanceOffset();

	void setRelevanceOffset( Long relevanceOffset );
	
	PratilipiContentType getContentType();
	
	void setContentType( PratilipiContentType contentType );
	
	PratilipiState getState();
	
	void setState( PratilipiState state );
	
	Date getLastUpdated();
	
	void setLastUpdated( Date lastUpdated );


	Long getReadCount();
	
	void setReadCount( Long readCount );
	
	Long getFbLikeShareCount();

	void setFbLikeShareCount( Long fbLikeShareCount );
	
	Date getLastProcessDate();

	void setLastProcessDate( Date lastProcessDate );

	Date getNextProcessDate();

	void setNextProcessDate( Date nextProcessDate );

}
