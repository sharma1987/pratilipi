package com.pratilipi.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.data.transfer.Publisher;

@SuppressWarnings("serial")
@PersistenceCapable( table = "PUBLISHER" )
public class PublisherEntity implements Publisher {

	@PrimaryKey
	@Persistent( column = "PUBLISHER_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "USER_ID" )
	private Long userId;
	
	@Persistent( column = "LANGUAGE_ID" )
	private Long languageId;

	@Persistent( column = "NAME" )
	private String name;
	
	@Persistent( column = "NAME_EN" )
	private String nameEn;

	@Persistent( column = "EMAIL" )
	private String email;
	
	@Persistent( column = "SECRET" )
	private String secret;

	@Persistent( column = "REGISTRATION_DATE" )
	private Date registrationDate;

	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public Long getUserId() {
		return userId;
	}

	@Override
	public void setUserId( Long userId ) {
		this.userId = userId;
	}

	@Override
	public Long getLanguageId() {
		return languageId;
	}

	@Override
	public void setLanguageId( Long languageId ) {
		this.languageId = languageId;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName( String name ) {
		this.name = name;
	}

	@Override
	public String getNameEn() {
		return nameEn;
	}

	@Override
	public void setNameEn( String nameEn ) {
		this.nameEn = nameEn;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail( String email ) {
		this.email = email;
	}

	@Override
	public Date getRegistrationDate() {
		return registrationDate;
	}

	@Override
	public void setRegistrationDate( Date registrationDate ) {
		this.registrationDate = registrationDate;
	}

	@Override
	public String getSecret() {
		return this.secret;
	}

	@Override
	public void setSecret( String secret ) {
		this.secret = secret;
		
	}
	
}
