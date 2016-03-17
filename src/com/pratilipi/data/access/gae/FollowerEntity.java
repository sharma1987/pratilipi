package com.pratilipi.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.data.transfer.Follower;

@SuppressWarnings("serial")
@PersistenceCapable( table = "CATEGORY" )
public class FollowerEntity implements Follower {

	@PrimaryKey
	@Persistent( column = "FOLLOWER_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private String id;
	
	@Persistent( column = "AUTHOR_ID")
	private Long authorId;
	
	@Persistent( column = "USER_ID" )
	private Long userId;

	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;
	
	@Override
	public String getId() {
		return this.id;
	}
	
	@Override
	public void setId( String id ){
		this.id = id;
	}

	@Override
	public Long getAuthorId() {
		return this.authorId;
	}

	@Override
	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	@Override
	public Long getUserId() {
		return this.userId;
	}

	@Override
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public Date getCreationDate() {
		return this.creationDate;
	}

	@Override
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
		
	

}
