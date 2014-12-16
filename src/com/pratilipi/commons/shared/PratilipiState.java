package com.pratilipi.commons.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum PratilipiState implements IsSerializable {

	DRAFTED,				// Work in progress
	PUBLISHED,				// Published and free
	PUBLISHED_PAID,			// Published and paid
	PUBLISHED_DISCONTINUED,	// Published and discontinued
	DELETED,
	
}