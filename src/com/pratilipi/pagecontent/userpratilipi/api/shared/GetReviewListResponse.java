package com.pratilipi.pagecontent.userpratilipi.api.shared;

import java.util.List;

import com.claymus.api.shared.GenericResponse;
import com.pratilipi.data.transfer.shared.UserPratilipiData;


@SuppressWarnings("serial")
public class GetReviewListResponse extends GenericResponse {

	@SuppressWarnings( "unused" )
	private List<UserPratilipiData> reviewDataList;
	
	public GetReviewListResponse( List<UserPratilipiData> reviewDataList ){
		this.reviewDataList = reviewDataList;
	}
}
