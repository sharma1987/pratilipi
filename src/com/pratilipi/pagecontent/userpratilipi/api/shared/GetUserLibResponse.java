package com.pratilipi.pagecontent.userpratilipi.api.shared;

import java.util.List;

import com.claymus.api.shared.GenericResponse;
import com.pratilipi.data.transfer.shared.PratilipiData;

@SuppressWarnings( "serial" )
public class GetUserLibResponse extends GenericResponse {

	@SuppressWarnings( "unused" )
	private List<PratilipiData> pratilipiDataList;
	
	public GetUserLibResponse( List<PratilipiData> pratilipiDataList ){
		this.pratilipiDataList = pratilipiDataList;
	}
}
