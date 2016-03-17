package com.pratilipi.pagecontent.author.api.shared;

import com.claymus.api.annotation.Validate;
import com.claymus.api.shared.GenericRequest;

@SuppressWarnings("serial")
public class PutAuthorFollowerRequest extends GenericRequest {

	@Validate( required=true )
	private Long authorId;
	
	@Validate( required=true )
	private Long userId;
	
	@Validate( required=true )
	private Boolean following;
	
	public Long getAuthorId(){
		return authorId;
	}
	
	public Long getUserId(){
		return userId;
	}
	
	public Boolean isFollowing(){
		return following;
	}
	
}
