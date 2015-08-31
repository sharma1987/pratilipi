package com.pratilipi.service;

import com.pratilipi.api.ApiRegistry;
import com.pratilipi.api.GenericService;
import com.pratilipi.api.author.AuthorImageApi;
import com.pratilipi.api.pratilipi.PratilipiApi;
import com.pratilipi.api.pratilipi.PratilipiCoverApi;
import com.pratilipi.api.pratilipi.PratilipiListApi;
import com.pratilipi.api.user.UserAccessTokenApi;
import com.pratilipi.api.user.UserLoginApi;
import com.pratilipi.api.user.UserLogoutApi;
import com.pratilipi.api.user.UserRegisterApi;
import com.pratilipi.api.userpratilipi.UserPratilipiApi;
import com.pratilipi.api.userpratilipi.UserPratilipiReviewListApi;

@SuppressWarnings("serial")
public class AndroidService extends GenericService {
	
	static {
		ApiRegistry.register( UserAccessTokenApi.class );
		ApiRegistry.register( UserLoginApi.class );
		ApiRegistry.register( UserLogoutApi.class );
		ApiRegistry.register( UserRegisterApi.class );

		ApiRegistry.register( PratilipiApi.class );
		ApiRegistry.register( PratilipiListApi.class );
		ApiRegistry.register( PratilipiCoverApi.class );
		
		ApiRegistry.register( AuthorImageApi.class );
		
		ApiRegistry.register( UserPratilipiApi.class );
		ApiRegistry.register( UserPratilipiReviewListApi.class );
	}
	
}
