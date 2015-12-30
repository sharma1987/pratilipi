package com.pratilipi.api.impl.pratilipi.shared;

import java.util.List;

import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.data.client.PratilipiData;

@SuppressWarnings("unused")
public class GetPratilipiListResponse extends GenericResponse { 
	
	private List<GenericPratilipiResponse> pratilipiList;
	private String cursor;

	
	private GetPratilipiListResponse() {}
	
	public GetPratilipiListResponse( List<GenericPratilipiResponse> pratilipiList, String cursor ) {
		this.pratilipiList = pratilipiList;
		this.cursor = cursor;
	}
	
}
