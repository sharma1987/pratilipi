package com.pratilipi.api.pratilipi.shared;

import com.pratilipi.api.shared.GenericRequest;

public class GetPratilipiCoverRequest extends GenericRequest {

	private Long pratilipiId;

	private Integer width;
	

	public Long getPratilipiId() {
		return pratilipiId;
	}

	public Integer getWidth() {
		return width;
	}
	
}
