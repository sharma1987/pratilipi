package com.pratilipi.commons.shared;


public enum PageType {
	
	GENERIC( "/page/" ),
	BLOG( "/blog/" ),

	PRATILIPI( "/pratilipi/" ),
	READ( "/read?id=" ),
	WRITE( "/write?id=" ),

	AUTHOR( "/author/" ),
	AUTHOR_DASHBOARD( null ),
	
	PUBLISHER( "/publisher/" ),
	EVENT( "/event/" ),
	;
	
	
	private String urlPrefix;
	
	
	private PageType( String urlPrefix ) {
		this.urlPrefix = urlPrefix;
	}
	
	public String getUrlPrefix() {
		return urlPrefix;
	}
	
}
