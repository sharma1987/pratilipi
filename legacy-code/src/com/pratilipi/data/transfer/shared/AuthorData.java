package com.pratilipi.data.transfer.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AuthorData implements Serializable {

	private Long id;

	private String pageUrl;
	private String pageUrlAlias;
	private String authorImageUrl;
	
	private Long languageId;
	private boolean hasLanguageId;

	private LanguageData language;

	
	private String firstName;
	private boolean hasFirstName;
	
	private String lastName;
	private boolean hasLastName;
	
	private String penName;
	private boolean hasPenName;

	private String name;

	private String fullName;

	private String firstNameEn;
	private boolean hasFirstNameEn;
	
	private String lastNameEn;
	private boolean hasLastNameEn;
	
	private String penNameEn;
	private boolean hasPenNameEn;
	
	private String nameEn;

	private String fullNameEn;


	private String summary;
	private boolean hasSummary;
	
	private String email;
	private boolean hasEmail;

	private Long registrationDate;
	
	private Long contentPublished;
	
	
	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
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

	public String getAuthorImageUrl() {
		return authorImageUrl;
	}

	public void setAuthorImageUrl( String authorImageUrl ) {
		this.authorImageUrl = authorImageUrl;
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


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName( String firstName ) {
		this.firstName = firstName;
		this.hasFirstName = true;
	}

	public boolean hasFirstName() {
		return hasFirstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName( String lastName ) {
		this.lastName = lastName;
		this.hasLastName = true;
	}
	
	public boolean hasLastName() {
		return hasLastName;
	}

	public String getPenName() {
		return penName;
	}

	public void setPenName( String penName ) {
		this.penName = penName;
		this.hasPenName = true;
	}
	
	public boolean hasPenName() {
		return hasPenName;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName( String fullName ) {
		this.fullName = fullName;
	}

	public String getFirstNameEn() {
		return firstNameEn;
	}

	public void setFirstNameEn( String firstNameEn ) {
		this.firstNameEn = firstNameEn;
		this.hasFirstNameEn = true;
	}
	
	public boolean hasFirstNameEn() {
		return hasFirstNameEn;
	}

	public String getLastNameEn() {
		return lastNameEn;
	}

	public void setLastNameEn( String lastNameEn ) {
		this.lastNameEn = lastNameEn;
		this.hasLastNameEn = true;
	}
	
	public boolean hasLastNameEn() {
		return hasLastNameEn;
	}

	public String getPenNameEn() {
		return penNameEn;
	}

	public void setPenNameEn( String penNameEn ) {
		this.penNameEn = penNameEn;
		this.hasPenNameEn = true;
	}
	
	public boolean hasPenNameEn() {
		return hasPenNameEn;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn( String nameEn ) {
		this.nameEn = nameEn;
	}

	public String getFullNameEn() {
		return fullNameEn;
	}

	public void setFullNameEn( String fullNameEn ) {
		this.fullNameEn = fullNameEn;
	}


	public String getSummary() {
		return summary;
	}

	public void setSummary( String summary ) {
		this.summary = summary;
		this.hasSummary = true;
	}
	
	public boolean hasSummary() {
		return this.hasSummary;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail( String email ) {
		this.email = email;
		this.hasEmail = true;
	}
	
	public boolean hasEmail() {
		return hasEmail;
	}

	public Long getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate( Long registrationDate ) {
		this.registrationDate = registrationDate;
	}
	
	public Long getContentPublished() {
		return contentPublished;
	}

	public void setContentPublished(Long contentPublished) {
		this.contentPublished = contentPublished;
	}

}
