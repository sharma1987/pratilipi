package com.pratilipi.api.impl.userpratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.client.UserPratilipiData;
import com.pratilipi.data.util.UserPratilipiDataUtil;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind( uri = "/userpratilipi/library" )
public class UserPratilipiLibraryApi extends GenericApi {
	
	public static class PostRequest extends GenericRequest {

		@Validate( required = true )
		private Long pratilipiId;
		
		@Validate( required = true )
		private Boolean addedToLib;

	}
	
	
	@Post
	public UserPratilipiApi.Response post( PostRequest request )
			throws InsufficientAccessException, UnexpectedServerException {

		UserPratilipiData userPratilipiData = UserPratilipiDataUtil.saveUserPratilipiAddToLibrary(
				AccessTokenFilter.getAccessToken().getUserId(),
				request.pratilipiId,
				request.addedToLib );

		return new UserPratilipiApi.Response( userPratilipiData, UserPratilipiLibraryApi.class );
		
	}

}
