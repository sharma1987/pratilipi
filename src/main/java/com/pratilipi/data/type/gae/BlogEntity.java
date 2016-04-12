package com.pratilipi.data.type.gae;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.pratilipi.common.type.Language;
import com.pratilipi.data.type.Blog;

@Cache
@Entity( name = "BLOG" )
public class BlogEntity extends GenericOfyEntity implements Blog {

	@Id
	private Long BLOG_ID;

	private String TITLE;
	private String TITLE_EN;

	@Index
	private Language LANGUAGE;

	@Index
	private Date CREATION_DATE;

	@Index
	private Date LAST_UPDATED;


	public BlogEntity() {}

	public BlogEntity( Long id ) {
		this.BLOG_ID = id;
	}

	
	@Override
	public Long getId() {
		return BLOG_ID;
	}
	
	public void setId( Long id ) {
		this.BLOG_ID = id;
	}
	
	@Override
	public <T extends GenericOfyEntity> void setKey( Key<T> key ) {
		this.BLOG_ID = key.getId();
	}
	
	
	@Override
	public String getTitle() {
		return TITLE;
	}

	@Override
	public void setTitle( String title ) {
		this.TITLE = title;
	}

	@Override
	public String getTitleEn() {
		return TITLE_EN;
	}

	@Override
	public void setTitleEn( String titleEn ) {
		this.TITLE_EN = titleEn;
	}

	@Override
	public Language getLanguage() {
		return LANGUAGE;
	}

	@Override
	public void setLanguage( Language language ) {
		this.LANGUAGE = language;
	}

	
	@Override
	public Date getCreationDate() {
		return CREATION_DATE;
	}
	
	@Override
	public void setCreationDate( Date creationDate ) {
		this.CREATION_DATE = creationDate;
	}

	@Override
	public Date getLastUpdated() {
		return LAST_UPDATED == null ? CREATION_DATE : LAST_UPDATED;
	}

	@Override
	public void setLastUpdated( Date lastUpdated ) {
		this.LAST_UPDATED = lastUpdated;
	}

}
