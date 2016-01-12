package com.pratilipi.commons.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum UserReviewState implements IsSerializable {
	
	NOT_SUBMITTED,
	SUBMITTED,
	APPROVED,
	AUTO_APPROVED,
	PUBLISHED
		
}
