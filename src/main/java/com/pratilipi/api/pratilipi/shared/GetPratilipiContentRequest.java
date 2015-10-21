package com.pratilipi.api.pratilipi.shared;

import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;

public class GetPratilipiContentRequest extends GenericRequest {

	@Validate( required = true )
	private Long pratilipiId;

	@Validate( required = true )
	private Integer chapterNo;
	
	@Validate( required = true )
	private Integer pageNo;
	
	@Validate
	private String pageletId;

	
	public Long getPratilipiId() {
		return pratilipiId;
	}

	public Integer getChapterNo() {
		return chapterNo;
	}
	
	public Integer getPageNo() {
		return pageNo;
	}
	
	public Integer getPageletId() {
		return pageNo;
	}
	
}
