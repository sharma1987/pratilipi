package com.pratilipi.data.type.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.commons.shared.PageType;
import com.pratilipi.data.type.Page;

@PersistenceCapable( table = "PAGE" )
public class PageEntity implements Page {

	private static final long serialVersionUID = 6798007451769465807L;

	@PrimaryKey
	@Persistent( column = "PAGE_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "PAGE_TYPE" )
	private PageType type;

	@Persistent( column = "URI" )
	private String uri;

	@Persistent( column = "URI_ALIAS" )
	private String uriAlias;

	@Persistent( column = "TITLE" )
	private String title;
	
	@Persistent( column = "PRIMARY_CONTENT_ID" )
	private Long primaryContentId;
	
	@Persistent( column = "LAYOUT_ID" )
	private Long layoutId;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;
	
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getType() {
		return type == null ? null : type.toString();
	}

	@Override
	public void setType( String type ) {
		this.type = PageType.valueOf( type );
	}

	@Override
	public String getUri() {
		return uri;
	}

	@Override
	public void setUri( String uri ) {
		this.uri = uri;
	}

	@Override
	public String getUriAlias() {
		return uriAlias;
	}

	@Override
	public void setUriAlias( String uri ) {
		this.uriAlias = uri;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public Long getPrimaryContentId() {
		return primaryContentId;
	}

	@Override
	public void setPrimaryContentId( Long pageContentId ) {
		this.primaryContentId = pageContentId;
	}

	@Override
	public Long getLayoutId() {
		return layoutId;
	}

	@Override
	public void setLayout( Long layoutId ) {
		this.layoutId = layoutId;
	}

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate;
	}

}
