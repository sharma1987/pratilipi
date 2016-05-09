package com.pratilipi.data.type.gae;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfNotNull;
import com.pratilipi.common.type.MailingList;
import com.pratilipi.data.type.MailingListSubscription;

@Cache
@Entity( name = "MAILING_LIST_SUBSCRIPTION" )
public class MailingListSubscriptionEntity implements MailingListSubscription {

	@Id
	private Long MAILING_LIST_SUBSCRIPTION_ID;

	@Index
	private MailingList MAILING_LIST;

	@Index( IfNotNull.class )
	private Long USER_ID;

	@Index( IfNotNull.class )
	private String EMAIL;
	
	@Index
	private Date SUBSCRIPTION_DATE;


	public MailingListSubscriptionEntity() {}

	public MailingListSubscriptionEntity( Long id ) {
		this.MAILING_LIST_SUBSCRIPTION_ID = id;
	}

	
	public Long getId() {
		return MAILING_LIST_SUBSCRIPTION_ID;
	}
	
	public void setId( Long id ) {
		this.MAILING_LIST_SUBSCRIPTION_ID = id;
	}
	
	@Override
	public <T> void setKey( Key<T> key ) {
		this.MAILING_LIST_SUBSCRIPTION_ID = key.getId();
	}
	

	@Override
	public MailingList getMailingList() {
		return MAILING_LIST;
	}

	@Override
	public void setMailingList( MailingList mailingList ) {
		this.MAILING_LIST = mailingList;
	}

	@Override
	public Long getUserId() {
		return USER_ID;
	}
	
	@Override
	public void setUserId( Long userId ) {
		this.USER_ID = userId;
	}
	
	@Override
	public String getEmail() {
		return EMAIL;
	}

	@Override
	public void setEmail( String email ) {
		this.EMAIL = email;
	}
	
	@Override
	public Date getSubscriptionDate() {
		return SUBSCRIPTION_DATE;
	}
	
	@Override
	public void setSubscriptionDate( Date date ) {
		this.SUBSCRIPTION_DATE = date;
	}

}
