package com.pratilipi.pagecontent.userpratilipi.api;

import java.util.List;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.api.annotation.Put;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.AccessToken;
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
	
	@Get
	public GetReviewListResponse getReviewList( GetReviewListRequest request ){
		
		UserPratilipiFilter userPratilipiFilter = new UserPratilipiFilter();
		
		if( request.hasPratilipiId() )
			userPratilipiFilter.setPratilipiId( request.getPratilipiId() );
		
		if( request.hasUserId() ){
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
		
		AccessToken accessToken = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() )
										.getAccessToken( request.getAccessToken() );
		if( accessToken == null )
			throw new InvalidArgumentException( "Invalid Access Token or Access Token is expired" );
		if( accessToken.getUserId() == 0L )
			throw new InsufficientAccessException( "User is not logged in" );
		this.getThreadLocalRequest().setAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN, accessToken );
		
		UserPratilipiData userPratilipiData = new UserPratilipiData();
		userPratilipiData.setReview( request.getReview() );
		userPratilipiData.setPratilipiId( request.getPratilipiId() );
		
		userPratilipiData = UserPratilipiContentHelper.saveUserPratilipi( userPratilipiData, this.getThreadLocalRequest() );
		
		return new PutReviewResponse( userPratilipiData );
	}
}
