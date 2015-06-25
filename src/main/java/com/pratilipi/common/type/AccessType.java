package com.pratilipi.common.type;


public enum AccessType {

	PRATILIPI_LIST		  ( "List Pratilipi" ),
	PRATILIPI_ADD		  ( "Add Pratilipi" ),
	PRATILIPI_UPDATE	  ( "Update Pratilipi" ),
	PRATILIPI_READ_META	  ( "View Pratilipi Meta Data" ),
	PRATILIPI_UPDATE_META ( "Update Pratilipi Meta Data" ),
	PRATILIPI_ADD_REVIEW  ( "Add Pratilipi Review" ),
	PRATILIPI_READ_CONTENT( "View Pratilipi Content" ),

	AUTHOR_LIST	 ( "List Author" ),
	AUTHOR_ADD	 ( "Add Author" ),
	AUTHOR_UPDATE( "Update Author" ),
	;

	
	private String description;
	
	
	private AccessType( String description ) {
		this.description = description;
	}


	public String getDescription() {
		return description;
	}

}
