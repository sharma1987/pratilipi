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
import com.pratilipi.data.transfer.shared.PratilipiData;
import com.pratilipi.data.transfer.shared.UserPratilipiData;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.userpratilipi.UserPratilipiContentHelper;
import com.pratilipi.pagecontent.userpratilipi.api.shared.GetUserLibRequest;
import com.pratilipi.pagecontent.userpratilipi.api.shared.GetUserLibResponse;
import com.pratilipi.pagecontent.userpratilipi.api.shared.PutAddToLibRequest;
import com.pratilipi.pagecontent.userpratilipi.api.shared.PutAddToLibResposne;

@SuppressWarnings( "serial" )
@Bind( uri = "/userpratilipi/library" )
public class UserLibraryApi extends GenericApi {

	@Get
	public GetUserLibResponse getLibList( GetUserLibRequest request ) 
			throws InvalidArgumentException, InsufficientAccessException{
		
		if( request.getAccessToken() == null )
			throw new InvalidArgumentException( "Access Token Is missing" );
		
		AccessToken accessToken = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() )
										.getAccessToken( request.getAccessToken() );
		
		if( accessToken == null )
			throw new InvalidArgumentException( "Access Token is invalid or expired" );
		
		UserPratilipiFilter userPratilipiFilter = new UserPratilipiFilter();
		userPratilipiFilter.setUserId( accessToken.getUserId() );
		userPratilipiFilter.setOrderByAddedToLibDate( false );
		
		List<Long> pratilipiIdList = 
								UserPratilipiContentHelper.getUserLibrary( userPratilipiFilter, this.getThreadLocalRequest() );
		
		List<PratilipiData> pratilipiDataList = 
								PratilipiContentHelper.createPratilipiDataList( pratilipiIdList, true, true, true, this.getThreadLocalRequest() );
		
		return new GetUserLibResponse( pratilipiDataList, pratilipiDataList.size() );
	}
	
	@Put
	public PutAddToLibResposne addToLib( PutAddToLibRequest request ) 
			throws InvalidArgumentException, InsufficientAccessException{
		
		if( request.getAccessToken() == null )
			throw new InvalidArgumentException( "accessToken is missing" );
		if( request.getPratilipiId() == null )
			throw new InvalidArgumentException( "pratilipiId is missing" );
		
		AccessToken accessToken = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() )
										.getAccessToken( request.getAccessToken() );
		if( accessToken == null )
			throw new InvalidArgumentException( "Invalid Access Token!" );
		
		this.getThreadLocalRequest().setAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN, accessToken ); 
		
		UserPratilipiData userPratilipiData = new UserPratilipiData();
		userPratilipiData.setPratilipiId( request.getPratilipiId() );
		userPratilipiData.setAddedToLib( request.getAddToLib() );
		
		userPratilipiData = UserPratilipiContentHelper.saveUserPratilipi( userPratilipiData, this.getThreadLocalRequest() );
		
		return new PutAddToLibResposne( userPratilipiData.isAddedToLib() );
	}
}
