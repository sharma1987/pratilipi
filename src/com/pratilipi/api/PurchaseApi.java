package com.pratilipi.api;

import java.util.Date;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Put;
import com.claymus.commons.server.ClaymusHelper;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.transfer.User;
import com.pratilipi.api.shared.PutPurchaseRequest;
import com.pratilipi.api.shared.PutPurchaseResponse;
import com.pratilipi.commons.shared.PratilipiAccessTokenType;
import com.pratilipi.commons.shared.SellerType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.UserPratilipi;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.Pratilipi;
@SuppressWarnings("serial")
@Bind( uri = "/purchase" )
public class PurchaseApi extends GenericApi {

	@Put
	public PutPurchaseResponse purchase( PutPurchaseRequest apiRequest )
			throws InvalidArgumentException, InsufficientAccessException,
			UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		AccessToken accessToken = (AccessToken) this.getThreadLocalRequest().getAttribute( ClaymusHelper.REQUEST_ATTRIB_ACCESS_TOKEN );
		
		if( !accessToken.getType().equals( PratilipiAccessTokenType.PUBLISHER.toString() ) )
			throw new InsufficientAccessException();
		
		
		Pratilipi pratilipi = dataAccessor.getPratilipi( apiRequest.getPratilipiId() );
		if( pratilipi == null )
			throw new InvalidArgumentException( "Invalid Pratilipi id." );

		
		if( pratilipi.getPublisherId() == null || (long) pratilipi.getPublisherId() != (long) accessToken.getPublisherId() )
			throw new InsufficientAccessException( "Insufficient privilege to take this action on Pratilipi Id " + apiRequest.getPratilipiId() );

		
		User user = dataAccessor.getUserByEmail( apiRequest.getUserId() );
		if( user == null ) {
			user = dataAccessor.newUser();
			user.setEmail( apiRequest.getUserId() );
			user.setSignUpDate( new Date() );
			user.setCampaign( "Publisher:" + accessToken.getPublisherId() );
			dataAccessor.createOrUpdateUser( user );
		}
		

		UserPratilipi userPratilipi = dataAccessor.getUserPratilipi( user.getId(), pratilipi.getId() );
		if( userPratilipi == null ) {
			userPratilipi = dataAccessor.newUserPratilipi();
			userPratilipi.setUserId( user.getId() );
			userPratilipi.setPratilipiId( pratilipi.getId() );
		} else if( userPratilipi.getPurchasedFrom() != null ) {
			throw new InvalidArgumentException( "Pratilipi already purchased by the user." );
		}
		userPratilipi.setPurchasedFrom( SellerType.PUBLISHER );
		userPratilipi.setPurchaseDate( new Date() );
		dataAccessor.createOrUpdateUserPratilipi( userPratilipi );

		
		return new PutPurchaseResponse( userPratilipi.getId() );
	}
	
}
