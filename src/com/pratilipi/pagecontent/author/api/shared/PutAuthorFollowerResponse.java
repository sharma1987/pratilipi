package com.pratilipi.pagecontent.author.api.shared;

import com.claymus.api.shared.GenericResponse;

@SuppressWarnings("serial")
public class PutAuthorFollowerResponse extends GenericResponse {

	private int followerCount;
	
	@SuppressWarnings("unused")
	private PutAuthorFollowerResponse() {}
	
	public PutAuthorFollowerResponse( int followerCount ) {
		this.followerCount = followerCount;
	}
	
	public int getFollowerCount(){
		return this.followerCount;
	}
}
