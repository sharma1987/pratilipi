package com.pratilipi.pagecontent.pratilipicategory.api.shared;

import java.util.List;

import com.claymus.api.shared.GenericResponse;
import com.pratilipi.data.transfer.shared.PratilipiData;

@SuppressWarnings( "serial" )
public class GetCategoryPratilipiResponse extends GenericResponse {
	
	@SuppressWarnings( "unused" )
	private List<PratilipiData> pratilipiDataList;
	
	@SuppressWarnings( "unused" )
	private String cursor;
	
	public GetCategoryPratilipiResponse( List<PratilipiData> pratilipiDataList, String cursor ){
		this.pratilipiDataList = pratilipiDataList;
		this.cursor = cursor;
	}
}
