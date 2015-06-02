package com.pratilipi.pagecontent.userpratilipi.api;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.api.annotation.Put;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.pratilipi.commons.shared.UserPratilipiFilter;
import com.pratilipi.data.transfer.shared.UserPratilipiData;
import com.pratilipi.pagecontent.userpratilipi.UserPratilipiContentHelper;
import com.pratilipi.pagecontent.userpratilipi.api.shared.GetReviewListRequest;
import com.pratilipi.pagecontent.userpratilipi.api.shared.GetReviewListResponse;
import com.pratilipi.pagecontent.userpratilipi.api.shared.PutReviewRequest;
import com.pratilipi.pagecontent.userpratilipi.api.shared.PutReviewResponse;

@SuppressWarnings("serial")
@Bind( uri = "/userpratilipi/review" )
public class ReviewApi extends GenericApi{
	
	private Logger logger = Logger.getLogger( ReviewApi.class.getName() );

	@Get
	public GetReviewListResponse getReviewList( GetReviewListRequest request ){
		
		UserPratilipiFilter userPratilipiFilter = new UserPratilipiFilter();
		
		logger.log( Level.INFO, "hasPratilipiId : " + String.valueOf( request.hasPratilipiId() ) );
		if( request.hasPratilipiId() != null && request.hasPratilipiId() )
			userPratilipiFilter.setPratilipiId( request.getPratilipiId() );
		
		logger.log( Level.INFO, "hasUserId : " + String.valueOf( request.hasUserId() ) );
		if( request.hasUserId() != null && request.hasUserId() ){
			logger.log( Level.INFO, "User id : " + request.getUserId() );
			userPratilipiFilter.setUserId( request.getUserId() );
		}
		
		userPratilipiFilter.setOrderByReviewDate( false );	//false for reverse chronological order
		
		List<UserPratilipiData> userPratilipiDataList = 
				UserPratilipiContentHelper.getReviewDataList( userPratilipiFilter, this.getThreadLocalRequest() );
		
		return new GetReviewListResponse( userPratilipiDataList );
	}
	
	@Put
	public PutReviewResponse putReview( PutReviewRequest request )
			throws InsufficientAccessException, InvalidArgumentException{
		
		if( request.getReview() == null || request.getReview().isEmpty() )
			return new PutReviewResponse();
		
		UserPratilipiData userPratilipiData = new UserPratilipiData();
		userPratilipiData.setReview( request.getReview() );
		userPratilipiData.setPratilipiId( request.getPratilipiId() );
		
		userPratilipiData = UserPratilipiContentHelper.saveUserPratilipi( userPratilipiData, this.getThreadLocalRequest() );
		
		return new PutReviewResponse( userPratilipiData );
	}
}
