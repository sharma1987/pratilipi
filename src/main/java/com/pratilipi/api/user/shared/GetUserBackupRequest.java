package com.pratilipi.api.user.shared;

import com.pratilipi.api.shared.GenericRequest;

public class GetUserBackupRequest extends GenericRequest {
	
	private Boolean generateCsv;
		
	public boolean generateCsv() {
		return generateCsv == null ? false : generateCsv;
	}
	
}
