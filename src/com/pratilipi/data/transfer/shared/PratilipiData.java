package com.pratilipi.data.transfer.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.pratilipi.common.type.PratilipiContentType;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;

@SuppressWarnings("serial")
public class PratilipiData implements Serializable {

	private Long id;
	
	private PratilipiType type;
	private boolean hasType;

	private String pageUrl;
	private String pageUrlAlias;
	private String coverImageUrl;
	private String readerPageUrl;
	private String writerPageUrl;
	
	private String title;
	private boolean hasTitle;
	
	private String titleEn;
	private boolean hasTitleEn;
	
	private Long languageId;
	private boolean hasLanguageId;

	private LanguageData language;

	
	private Long authorId;
	private boolean hasAuthorId;
	
	private AuthorData author;

	
	private Long publicationYear;
	private boolean hasPublicationYear;
	
	private Date listingDate;
	private Date lastUpdated;

	
	private String summary;
	private boolean hasSummary;
	
	private String index;
	private boolean hasIndex;

	private Long wordCount;
	private boolean hasWordCount;
	
	private Integer pageCount;
	private boolean hasPageCount;

	private PratilipiContentType contentType;
	private boolean hasContentType;
	
	private PratilipiState state;
	private boolean hasState;
	
	private List<Long> genreIdList;
	private List<String> genreNameList;
	
	private Long readCount;
	
	private Long reviewCount;
	
	private Long ratingCount;
	
	private Long starCount;
	
	private Double relevance;

	
	public PratilipiData() {}
	
	public PratilipiData( Long id ) {
		this.id = id;
	}
	

	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public PratilipiType getType(){
		return this.type;
	}
	
	public void setType( PratilipiType type ) {
		this.type = type;
		this.hasType = true;
	}
	
	public boolean hasType() {
		return hasType;
	}
	
	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl( String pageUrl ) {
		this.pageUrl = pageUrl;
	}

	public String getPageUrlAlias() {
		return pageUrlAlias;
	}

	public void setPageUrlAlias( String pageUrlAlias ) {
		this.pageUrlAlias = pageUrlAlias;
	}

	public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public void setCoverImageUrl( String coverImageUrl ) {
		this.coverImageUrl = coverImageUrl;
	}

	public String getReaderPageUrl() {
		return readerPageUrl;
	}

	public void setReaderPageUrl( String readerPageUrl ) {
		this.readerPageUrl = readerPageUrl;
	}
	
	public String getWriterPageUrl(){
		return this.writerPageUrl;
	}
	
	public void setWriterPageUrl( String writerPageUrl ){
		this.writerPageUrl = writerPageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle( String title ) {
		this.title = title;
		this.hasTitle = true;
	}
	
	public boolean hasTitle() {
		return hasTitle;
	}

	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn( String titleEn ) {
		this.titleEn = titleEn;
		this.hasTitleEn = true;
	}
	
	public boolean hasTitleEn() {
		return hasTitleEn;
	}

	
	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId( Long languageId ) {
		this.languageId = languageId;
		this.hasLanguageId = true;
	}

	public boolean hasLanguageId() {
		return hasLanguageId;
	}
	
	public LanguageData getLanguage() {
		return language;
	}

	public void setLanguage( LanguageData language ) {
		this.language = language;
	}
	

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId( Long authorId ) {
		this.authorId = authorId;
		this.hasAuthorId = true;
	}

	public boolean hasAuthorId() {
		return hasAuthorId;
	}
	
	public AuthorData getAuthor() {
		return author;
	}

	public void setAuthor( AuthorData author ) {
		this.author = author;
	}
	
	
	public Long getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear( Long publicationYear ) {
		this.publicationYear = publicationYear;
		this.hasPublicationYear = true;
	}

	public boolean hasPublicationYear() {
		return hasPublicationYear;
	}
	
	public Date getListingDate() {
		return listingDate;
	}

	public void setListingDate( Date listingDate ) {
		this.listingDate = listingDate;
	}

	public Date getLastUpdated(){
		return this.lastUpdated;
	}
	
	public void setLastUpdated( Date lastUpdated ){
		this.lastUpdated = lastUpdated;
	}

	public String getSummary() {
		return summary;
	}
	
	public void setSummary( String summary ) {
		this.summary = summary;
		this.hasSummary = true;
	}

	public boolean hasSummary() {
		return hasSummary;
	}
	
	public String getIndex() {
		return index;
	}
	
	public void setIndex( String index ) {
		this.index = index;
		this.hasIndex = true;
	}

	public boolean hasIndex() {
		return hasIndex;
	}

	public Long getWordCount() {
		return wordCount;
	}

	public void setWordCount( Long wordCount ) {
		this.wordCount = wordCount;
		this.hasWordCount = true;
	}
	
	public boolean hasWordCount() {
		return hasWordCount;
	}
	
	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount( Integer pageCount ) {
		this.pageCount = pageCount;
		this.hasPageCount = true;
	}
	
	public boolean hasPageCount() {
		return hasPageCount;
	}
	
	public PratilipiContentType getContentType() {
		return contentType;
	}
	
	public void setContentType( PratilipiContentType contentType ){
		this.contentType = contentType;
		this.hasContentType = true;
	}
	
	public boolean hasContentType() {
		return hasContentType;
	}

	public PratilipiState getState() {
		return state;
	}
	
	public void setState( PratilipiState state ) {
		this.state = state;
		this.hasState = true;
	}
	
	public boolean hasState() {
		return hasState;
	}
	
	
	public List<Long> getGenreIdList() {
		return genreIdList;
	}

	public void setGenreIdList( List<Long> genreIdList ) {
		this.genreIdList = genreIdList;
	}
	
	public List<String> getGenreNameList() {
		return genreNameList;
	}

	public void setGenreNameList( List<String> genreNameList ) {
		this.genreNameList = genreNameList;
	}
	
	
	public Long getReadCount() {
		return readCount;
	}

	public void setReadCount( Long readCount ) {
		this.readCount = readCount;
	}
	
	public Long getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(Long reviewCount) {
		this.reviewCount = reviewCount;
	}

	public Long getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(Long ratingCount) {
		this.ratingCount = ratingCount;
	}

	public Long getStarCount() {
		return starCount;
	}

	public void setStarCount(Long starCount) {
		this.starCount = starCount;
	}
	
	public Double getRelevance() {
		return relevance;
	}
	
	public void setRelevance( Double relevance ) {
		this.relevance = relevance;
	}
}
