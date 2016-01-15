package com.pratilipi.common.type;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum PratilipiState implements IsSerializable {

	DRAFTED,		// Work in progress
	SUBMITTED,		// Final work waiting for approval from Moderator
	PUBLISHED,		// Published work (free or paid)
	@Deprecated
	PUBLISHED_PAID,
	DISCONTINUED,	// Work discontinued by Author
	BLOCKED,		// Work blocked by System when Author's profile is BLOCKED
	DELETED,		// Work deleted by User OR by System when Author's profile is DELETED
	
}