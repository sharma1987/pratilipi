package com.pratilipi.data.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonObject;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.type.ContactTeam;
import com.pratilipi.common.type.UserState;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Conversation;
import com.pratilipi.data.type.ConversationMessage;
import com.pratilipi.data.type.ConversationUser;
import com.pratilipi.data.type.User;

public class ConversationDataUtil {
	
	public static void saveMessage( ContactTeam team, Long userId,
			String name, String email, String phone,
			String message, JsonObject data ) throws InvalidArgumentException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		User user = dataAccessor.getUser( userId );
		Conversation conversation = dataAccessor.getConversation( team, userId );
		if( conversation == null )
			dataAccessor.getConversation( team, email );
		
		
		if( conversation != null ) {
			// Do Nothing !
		} else if( user != null && ( user.getState() == UserState.ACTIVE || user.getState() == UserState.REGISTERED ) ) { // && conversation == null
			conversation = dataAccessor.newConversation( team, userId );
			conversation.setCreator( userId );
			conversation.setCreatorName( name );
			conversation.setCreatorEmail( email );
			conversation.setCreatorPhone( phone );
			conversation.setCreationDate( new Date() );
			List<ConversationUser> conversationUserList = new ArrayList<>( team.getUserIds().length + 1 );
			conversationUserList.add( dataAccessor.newConversationUser( conversation.getId(), userId ) );
			for( Long recipientUserId : team.getUserIds() )
				conversationUserList.add( dataAccessor.newConversationUser( conversation.getId(), recipientUserId ) );
			conversation = dataAccessor.createOrUpdateConversation( conversation, conversationUserList );
		} else if( email != null ) { // && conversation == null
			conversation = dataAccessor.newConversation( team, email );
			conversation.setCreatorName( name );
			conversation.setCreatorEmail( email );
			conversation.setCreatorPhone( phone );
			conversation.setCreationDate( new Date() );
			List<ConversationUser> conversationUserList = new ArrayList<>( team.getUserIds().length + 1 );
			for( Long recipientUserId : team.getUserIds() )
				conversationUserList.add( dataAccessor.newConversationUser( conversation.getId(), recipientUserId ) );
			conversation = dataAccessor.createOrUpdateConversation( conversation, conversationUserList );
		} else {
			throw new InvalidArgumentException( "Valid 'email' is required." );
		}
		
		conversation.setLastUpdated( new Date() );
		
		ConversationMessage conversationMessage = dataAccessor.newConversationMessage();
		conversationMessage.setConversationId( conversation.getId() );
		conversationMessage.setCreatorId( userId );
		conversationMessage.setMessage( message );
		conversationMessage.setData( data );
		conversationMessage.setCreationDate( new Date() );
		
		conversation = dataAccessor.createOrUpdateConversation( conversation, conversationMessage );
		
	}

}
