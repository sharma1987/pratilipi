package com.pratilipi.service;

import com.pratilipi.api.ApiRegistry;
import com.pratilipi.api.GenericService;
import com.pratilipi.api.impl.author.AuthorApi;
import com.pratilipi.api.impl.author.AuthorCoverApi;
import com.pratilipi.api.impl.author.AuthorCoverRemoveApi;
import com.pratilipi.api.impl.author.AuthorImageApi;
import com.pratilipi.api.impl.author.AuthorImageRemoveApi;
import com.pratilipi.api.impl.category.CategoryListApi;
import com.pratilipi.api.impl.comment.CommentApi;
import com.pratilipi.api.impl.comment.CommentListApi;
import com.pratilipi.api.impl.contact.ContactApi;
import com.pratilipi.api.impl.init.InitApi;
import com.pratilipi.api.impl.init.InitBannerListApi;
import com.pratilipi.api.impl.navigation.NavigationListApi;
import com.pratilipi.api.impl.notification.NotificationApi;
import com.pratilipi.api.impl.notification.NotificationListApi;
import com.pratilipi.api.impl.pratilipi.PratilipiApi;
import com.pratilipi.api.impl.pratilipi.PratilipiContentApi;
import com.pratilipi.api.impl.pratilipi.PratilipiContentImageApi;
import com.pratilipi.api.impl.pratilipi.PratilipiCoverApi;
import com.pratilipi.api.impl.pratilipi.PratilipiListApi;
import com.pratilipi.api.impl.user.UserAccessTokenApi;
import com.pratilipi.api.impl.user.UserAccessTokenFcmTokenApi;
import com.pratilipi.api.impl.user.UserLoginApi;
import com.pratilipi.api.impl.user.UserLoginFacebookApi;
import com.pratilipi.api.impl.user.UserLogoutApi;
import com.pratilipi.api.impl.user.UserPasswordUpdateApi;
import com.pratilipi.api.impl.user.UserRegisterApi;
import com.pratilipi.api.impl.userauthor.UserAuthorApi;
import com.pratilipi.api.impl.userauthor.UserAuthorFollowApi;
import com.pratilipi.api.impl.userauthor.UserAuthorFollowListApi;
import com.pratilipi.api.impl.userpratilipi.UserPratilipiApi;
import com.pratilipi.api.impl.userpratilipi.UserPratilipiLibraryApi;
import com.pratilipi.api.impl.userpratilipi.UserPratilipiLibraryListApi;
import com.pratilipi.api.impl.userpratilipi.UserPratilipiReviewApi;
import com.pratilipi.api.impl.userpratilipi.UserPratilipiReviewListApi;
import com.pratilipi.api.impl.vote.VoteApi;

@SuppressWarnings("serial")
public class AndroidService extends GenericService {
	
	static {
		
		ApiRegistry.register( InitApi.class );
		ApiRegistry.register( InitBannerListApi.class );
		
		ApiRegistry.register( UserAccessTokenApi.class );
		ApiRegistry.register( UserAccessTokenFcmTokenApi.class );
		ApiRegistry.register( UserLoginApi.class );
		ApiRegistry.register( UserLoginFacebookApi.class );
		ApiRegistry.register( UserLogoutApi.class );
		ApiRegistry.register( UserRegisterApi.class );
		ApiRegistry.register( UserPasswordUpdateApi.class );

		ApiRegistry.register( PratilipiApi.class );
		ApiRegistry.register( PratilipiListApi.class );
		ApiRegistry.register( PratilipiContentApi.class );
		ApiRegistry.register( PratilipiCoverApi.class );
		ApiRegistry.register( PratilipiContentImageApi.class );
		
		ApiRegistry.register( AuthorApi.class );
		ApiRegistry.register( AuthorImageApi.class );
		ApiRegistry.register( AuthorImageRemoveApi.class );
		ApiRegistry.register( AuthorCoverApi.class );
		ApiRegistry.register( AuthorCoverRemoveApi.class );
		
		ApiRegistry.register( UserPratilipiApi.class );
		ApiRegistry.register( UserPratilipiLibraryApi.class );
		ApiRegistry.register( UserPratilipiLibraryListApi.class );
		
		ApiRegistry.register( UserPratilipiReviewApi.class );
		ApiRegistry.register( UserPratilipiReviewListApi.class );

		ApiRegistry.register( UserAuthorApi.class );
		ApiRegistry.register( UserAuthorFollowApi.class );
		ApiRegistry.register( UserAuthorFollowListApi.class );

		ApiRegistry.register( CommentApi.class );
		ApiRegistry.register( CommentListApi.class );
		
		ApiRegistry.register( VoteApi.class );
		
		ApiRegistry.register( ContactApi.class );
		
		ApiRegistry.register( NavigationListApi.class );
		ApiRegistry.register( CategoryListApi.class );

		ApiRegistry.register( NotificationApi.class );
		ApiRegistry.register( NotificationListApi.class );
		
	}
	
}
