package com.pratilipi.pagecontent.userpratilipi.api.shared;

import com.claymus.api.shared.GenericResponse;
import com.pratilipi.data.transfer.shared.UserPratilipiData;

@SuppressWarnings("serial")
public class PutReviewResponse extends GenericResponse {
	
	@SuppressWarnings( "unused" )
	private UserPratilipiData userPratilipiData;
	
	public PutReviewResponse(){}
	
	public PutReviewResponse( UserPratilipiData userPratilipiData ){
		this.userPratilipiData = userPratilipiData;
	}
}
