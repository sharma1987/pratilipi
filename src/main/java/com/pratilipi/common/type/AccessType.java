package com.pratilipi.common.type;

public enum AccessType {

	USER_ADD   ( "Add User" ),
	USER_UPDATE( "Update User" ),

	PRATILIPI_LIST		  ( "List Pratilipi" ),
	PRATILIPI_ADD		  ( "Add Pratilipi" ),
	PRATILIPI_UPDATE	  ( "Update Pratilipi" ),
	PRATILIPI_READ_META	  ( "View Pratilipi Meta Data" ),
	PRATILIPI_UPDATE_META ( "Update Pratilipi Meta Data" ),
	@Deprecated
	PRATILIPI_ADD_REVIEW  ( "Add Pratilipi Review" ),
	PRATILIPI_READ_CONTENT( "View Pratilipi Content" ),

	AUTHOR_LIST	 ( "List Author" ),
	AUTHOR_ADD	 ( "Add Author" ),
	AUTHOR_UPDATE( "Update Author" ),
	
	EVENT_ADD	( "Add Event" ),
	EVENT_UPDATE( "Update Event" ),

	BLOG_POST_LIST	( "List BlogPost" ),
	BLOG_POST_ADD	( "Add BlogPost" ),
	BLOG_POST_UPDATE( "Update BlogPost" ),

	USER_PRATILIPI_REVIEW		( "Updating REVIEW field in USER_PRATILIPI." ),
	USER_PRATILIPI_ADDED_TO_LIB	( "Updating ADDED_TO_LIB field in USER_PRATILIPI." ),
	
	COMMENT_ADD( "Add Comment" ),
	COMMENT_UPDATE( "Update Comment" ),
	
	VOTE( "Vote" ),
	
	MAILING_LIST_SUBSCRIPTION_ADD( "Add Mailing List Subscription" ),
	;

	
	private String description;
	
	
	private AccessType( String description ) {
		this.description = description;
	}


	public String getDescription() {
		return description;
	}

}
