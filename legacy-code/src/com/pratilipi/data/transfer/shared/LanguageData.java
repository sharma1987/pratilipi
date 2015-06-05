package com.pratilipi.data.transfer.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LanguageData implements IsSerializable {

	private Long id;
	
	private String name;
	
	private String nameEn;
	
	private Date creationDate;
	
	private Boolean hidden;
	
	
	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}
	
	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn( String nameEn ) {
		this.nameEn = nameEn;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate( Date creationDate ) {
		this.creationDate = creationDate;
	}
	
	public Boolean getHidden(){
		return hidden;
	}
	
	public void setHidden( Boolean hidden ){
		this.hidden = hidden;
	}
	
}
