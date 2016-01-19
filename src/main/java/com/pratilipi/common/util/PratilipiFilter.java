package com.pratilipi.common.util;

import java.io.Serializable;
import java.util.Date;

import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;

public class PratilipiFilter implements Serializable {
	
	private static final long serialVersionUID = 6769002082197329230L;

	
	private Long authorId;
	private Language language;
	private PratilipiType type;
	private String listName;
	private PratilipiState state;
	
	private Date nextProcessDateEnd;

	private Boolean orderByReadCount;
	private Boolean orderByLastUpdated;
	

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId( Long authorId ) {
		this.authorId = authorId;
	}
	
	public Language getLanguage() {
		return language;
	}

	public void setLanguage( Language language ) {
		this.language = language;
	}

	public PratilipiType getType() {
		return type;
	}

	public void setType( PratilipiType pratilipiType ) {
		this.type = pratilipiType;
	}

	public String getListName() {
		return listName;
	}
	
	public void setListName( String listName ) {
		this.listName = listName;
	}
	
	public PratilipiState getState() {
		return state;
	}

	public void setState( PratilipiState state ) {
		this.state = state;
	}

	
	public Date getNextProcessDateEnd() {
		return nextProcessDateEnd;
	}

	public void setNextProcessDateEnd( Date nextProcessDateEnd ) {
		this.nextProcessDateEnd = nextProcessDateEnd;
	}
	
	public Boolean getOrderByReadCount() {
		return orderByReadCount;
	}

	public void setOrderByReadCount( Boolean orderByReadCount ) {
		this.orderByReadCount = orderByReadCount;
	}
	
	public Boolean getOrderByLastUpdate() {
		return orderByLastUpdated;
	}

	public void setOrderByLastUpdate( Boolean orderByLastUpdated ) {
		this.orderByLastUpdated = orderByLastUpdated;
	}

	
	public String toUrlEncodedString() {
		
		String urlEncodedString = "";
		
		if( authorId != null )
			urlEncodedString += "&authorId=" + authorId;
		if( language != null )
			urlEncodedString += "&language=" + language;
		if( type != null )
			urlEncodedString += "&type=" + type;
		if( listName != null )
			urlEncodedString += "&listName=" + listName;
		if( state != null )
			urlEncodedString += "&state=" + state;
		if( nextProcessDateEnd != null )
			urlEncodedString += "&nextProcessDateEnd=" + nextProcessDateEnd.getTime();
		if( orderByReadCount != null )
			urlEncodedString += "&orderByReadCount=" + orderByReadCount;
		if( orderByLastUpdated != null )
			urlEncodedString += "&orderByLastUpdated=" + orderByLastUpdated;
		
		return urlEncodedString.length() == 0 ? "" : urlEncodedString.substring( 1 );
		
	}
	
}
