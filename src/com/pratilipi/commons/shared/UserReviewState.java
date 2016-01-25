package com.pratilipi.commons.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum UserReviewState implements IsSerializable {

	@Deprecated
	PENDING_APPROVAL,
	DRAFTED,	// Review saved but not SUBMITTED or PUBLISHED
	SUBMITTED,	// Review waiting for Moderator/System approval
	PUBLISHED,	// Review approved by Moderator/System
	BLOCKED,	// Review blocked by Moderator/System
	DELETED,	// Review deleted by User OR by System when User is DELETED
	NOT_SUBMITTED
}
