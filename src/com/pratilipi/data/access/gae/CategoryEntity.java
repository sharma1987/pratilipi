package com.pratilipi.data.access.gae;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.pratilipi.commons.shared.CategoryType;
import com.pratilipi.data.transfer.Category;

@SuppressWarnings("serial")
@PersistenceCapable( table = "CATEGORY" )
public class CategoryEntity implements Category {

	@PrimaryKey
	@Persistent( column = "CATEGORY_ID", valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;
	
	@Persistent( column = "CATEGORY_NAME")
	private String name;
	
	@Persistent( column = "LANGUAGE_ID" )
	private Long languageId;
	
	@Persistent( column = "CATEGORY_TYPE" )
	private CategoryType type;
	
	@Persistent( column = "CREATION_DATE" )
	private Date creationDate;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String categoryName) {
		this.name = categoryName;
	}

	@Override
	public Long getLanguageId() {
		return languageId;
	}

	@Override
	public void setLangugeId(Long languageId) {
		this.languageId = languageId;
	}

	@Override
	public CategoryType getType() {
		return type;
	}

	@Override
	public void setType(CategoryType type) {
		this.type = type;
	}

	@Override
	public Date getCreationData() {
		return creationDate;
	}

	@Override
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
