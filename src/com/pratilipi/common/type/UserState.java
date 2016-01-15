package com.pratilipi.common.type;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum UserState implements IsSerializable {
	
	GUEST,
	REFERRAL,
	REGISTERED,	// User account NOT active - email verification required
	ACTIVE,		// User account active with verified email
	BLOCKED,	// User account blocked by Moderator/System
	DELETED,	// User account deleted by User
	
}
