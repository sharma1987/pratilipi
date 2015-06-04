package com.pratilipi.api.shared;

import java.util.List;

import com.claymus.api.shared.GenericResponse;
import com.pratilipi.data.transfer.shared.PratilipiData;

@SuppressWarnings( "serial" )
public class GetMobileInitResponse extends GenericResponse {

	// TODO : PROPER NAMING BASED ON USE OF THE LIST
	List<PratilipiData> topReadsPratilipiDataList;
	List<PratilipiData> featuredPratilipiDataList;
	List<PratilipiData> newReleasesPratilipiDataList;
	
	public GetMobileInitResponse( 
			List<PratilipiData> topReadPratilipiDataList,
			List<PratilipiData> featuredPratilipiDataList,
			List<PratilipiData> newReleasePratilipiDataList ){
		
		this.topReadsPratilipiDataList = topReadPratilipiDataList;
		this.featuredPratilipiDataList = featuredPratilipiDataList;
		this.newReleasesPratilipiDataList = newReleasePratilipiDataList;
	}

}
