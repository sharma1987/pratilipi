package com.pratilipi.api.impl.debug;

import java.util.List;

import com.googlecode.objectify.ObjectifyService;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.AuthorState;
import com.pratilipi.common.type.UserState;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.User;
import com.pratilipi.data.type.gae.AccessTokenEntity;
import com.pratilipi.data.type.gae.AuthorEntity;
import com.pratilipi.data.type.gae.PageEntity;
import com.pratilipi.data.type.gae.PratilipiEntity;
import com.pratilipi.data.type.gae.UserAuthorEntity;
import com.pratilipi.data.type.gae.UserEntity;
import com.pratilipi.data.type.gae.UserPratilipiEntity;

@SuppressWarnings("serial")
@Bind( uri = "/debug" )
public class DebugApi extends GenericApi {
	
	public static class PostRequest extends GenericRequest {
		Long deleteUserId;
		String emailId;
		String facebookId;
	}
	
	public static class Response extends GenericResponse {
		
		String msg;
		
		Response( String msg ) {
			this.msg = msg;
		}
		
	}
	
	
	@Post
	public GenericResponse post( PostRequest request ) throws InsufficientAccessException, InvalidArgumentException, UnexpectedServerException {
		
		if( request.emailId != null || request.facebookId != null ) {
			
			List<UserEntity> userList = null;
			
			if( request.emailId != null ) {
				userList = ObjectifyService.ofy().load()
						.type( UserEntity.class )
						.filter( "EMAIL", request.emailId )
						.filter( "STATE !=", UserState.DELETED )
						.order( "STATE" )
						.order( "SIGN_UP_DATE" )
						.list();
			
			} else if( request.facebookId != null ) {
				userList = ObjectifyService.ofy().load()
						.type( UserEntity.class )
						.filter( "FACEBOOK_ID", request.facebookId )
						.filter( "STATE !=", UserState.DELETED )
						.order( "STATE" )
						.order( "SIGN_UP_DATE" )
						.list();
			}
		
			if( userList.size() == 0 )
				return new Response( "Could not find user for given email/facebook id !" );
			
			if( userList.size() == 1 )
				return new Response( "Everythig looks well !" );
			
			User xUser = null;
			for( User user : userList )
				if( _canDelete( user ) )
					xUser = user;
			
			if( xUser == null )
				return new Response( "Its complicated !" );
			
			return new Response( "User (" + xUser.getId() + ") of " + userList.size() + " users can be deleted !" );
			
		} else if( request.deleteUserId != null ) {
			
			List<AuthorEntity> authorList = ObjectifyService.ofy().load()
					.type( AuthorEntity.class )
					.filter( "USER_ID", request.deleteUserId )
					.filter( "STATE !=", AuthorState.DELETED )
					.order( "STATE" )
					.order( "REGISTRATION_DATE" )
					.list();
			
			for( Author author : authorList ) {
				List<PageEntity> pageList = ObjectifyService.ofy().load()
						.type( PageEntity.class )
						.filter( "PAGE_TYPE", "AUTHOR" )
						.filter( "PRIMARY_CONTENT_ID", author.getId() )
						.list();
				ObjectifyService.ofy().delete().entities( pageList );
				author.setState( AuthorState.DELETED );
				ObjectifyService.ofy().save().entity( author );
			}
			
			User user = ObjectifyService.ofy().load()
					.type( UserEntity.class )
					.id( request.deleteUserId )
					.now();
			user.setState( UserState.DELETED );
			ObjectifyService.ofy().save().entity( user );
			
			return new Response( "User (" + request.deleteUserId + ") deleted !" );

		} else {
			
			return new GenericResponse();
			
		}
		
	}
	
	private boolean _canDelete( User user ) {
		
		List<AuthorEntity> authorList = ObjectifyService.ofy().load()
				.type( AuthorEntity.class )
				.filter( "USER_ID", user.getId() )
				.filter( "STATE !=", AuthorState.DELETED )
				.order( "STATE" )
				.order( "REGISTRATION_DATE" )
				.list();
		
		List<UserPratilipiEntity> userPratilipiList = ObjectifyService.ofy().load()
				.type( UserPratilipiEntity.class )
				.filter( "USER_ID", user.getId() )
				.list();
		
		List<UserAuthorEntity> userAuthorList = ObjectifyService.ofy().load()
				.type( UserAuthorEntity.class )
				.filter( "USER_ID", user.getId() )
				.list();
		
		List<AccessTokenEntity> accessTokenList = ObjectifyService.ofy().load()
				.type( AccessTokenEntity.class )
				.filter( "USER_ID", user.getId() )
				.list();
		
		return ( authorList.size() == 0 || ( authorList.size() == 1 && _canDelete( authorList.get( 0 ) ) ) )
				&& userPratilipiList.size() == 0
				&& userAuthorList.size() == 0
				&& accessTokenList.size() == 0;
		
	}
	
	private boolean _canDelete( Author author ) {
		
		List<PratilipiEntity> pratilipiList = ObjectifyService.ofy().load()
				.type( PratilipiEntity.class )
				.filter( "AUTHOR_ID", author.getId() )
				.list();
		
		List<PageEntity> pageList = ObjectifyService.ofy().load()
				.type( PageEntity.class )
				.filter( "PAGE_TYPE", "AUTHOR" )
				.filter( "PRIMARY_CONTENT_ID", author.getId() )
				.list();
		
		return pratilipiList.size() == 0 && pageList.size() <= 1;
		
	}
	
}
