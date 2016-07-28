package com.pratilipi.service;

import com.pratilipi.api.ApiRegistry;
import com.pratilipi.api.GenericService;
import com.pratilipi.api.impl.author.AuthorApi;
import com.pratilipi.api.impl.author.AuthorImageApi;
import com.pratilipi.api.impl.author.AuthorListApi;
import com.pratilipi.api.impl.blogpost.BlogPostApi;
import com.pratilipi.api.impl.blogpost.BlogPostListApi;
import com.pratilipi.api.impl.comment.CommentApi;
import com.pratilipi.api.impl.comment.CommentListApi;
import com.pratilipi.api.impl.event.EventApi;
import com.pratilipi.api.impl.event.EventBannerApi;
import com.pratilipi.api.impl.event.EventListApi;
import com.pratilipi.api.impl.mailinglist.MailingListSubscribeApi;
import com.pratilipi.api.impl.notification.NotificationApi;
import com.pratilipi.api.impl.pratilipi.PratilipiApi;
import com.pratilipi.api.impl.pratilipi.PratilipiContentApi;
import com.pratilipi.api.impl.pratilipi.PratilipiCoverApi;
import com.pratilipi.api.impl.pratilipi.PratilipiListApi;
import com.pratilipi.api.impl.user.UserApi;
import com.pratilipi.api.impl.user.UserEmailApi;
import com.pratilipi.api.impl.user.UserLoginApi;
import com.pratilipi.api.impl.user.UserLoginFacebookApi;
import com.pratilipi.api.impl.user.UserLogoutApi;
import com.pratilipi.api.impl.user.UserPasswordUpdateApi;
import com.pratilipi.api.impl.user.UserRegisterApi;
import com.pratilipi.api.impl.user.UserVerificationApi;
import com.pratilipi.api.impl.userauthor.UserAuthorFollowApi;
import com.pratilipi.api.impl.userauthor.UserAuthorFollowListApi;
import com.pratilipi.api.impl.userpratilipi.UserPratilipiApi;
import com.pratilipi.api.impl.userpratilipi.UserPratilipiLibraryApi;
import com.pratilipi.api.impl.userpratilipi.UserPratilipiLibraryListApi;
import com.pratilipi.api.impl.userpratilipi.UserPratilipiReviewApi;
import com.pratilipi.api.impl.userpratilipi.UserPratilipiReviewListApi;
import com.pratilipi.api.impl.vote.VoteApi;

@SuppressWarnings("serial")
public class ApiService extends GenericService {
	
	static {
		
		ApiRegistry.register( UserLoginApi.class );					// *.pratilipi.com
		ApiRegistry.register( UserLoginFacebookApi.class );			// *.pratilipi.com
		ApiRegistry.register( UserEmailApi.class );					// *.pratilipi.com
		ApiRegistry.register( UserLogoutApi.class );				// *.pratilipi.com
		ApiRegistry.register( UserRegisterApi.class );				// *.pratilipi.com
		ApiRegistry.register( UserVerificationApi.class );			// *.pratilipi.com
		ApiRegistry.register( UserPasswordUpdateApi.class );		// *.pratilipi.com
		
		ApiRegistry.register( PratilipiApi.class );					// *.pratilipi.com
		ApiRegistry.register( PratilipiListApi.class );				// *.pratilipi.com
		ApiRegistry.register( PratilipiContentApi.class );			// *.pratilipi.com
		ApiRegistry.register( PratilipiCoverApi.class );			// *.pratilipi.com & AWS CloudFront
		
		ApiRegistry.register( AuthorApi.class );					// *.pratilipi.com
		ApiRegistry.register( AuthorListApi.class );				// *.pratilipi.com
		ApiRegistry.register( AuthorImageApi.class );				// *.pratilipi.com & AWS CloudFront
		
		ApiRegistry.register( EventApi.class );						// *.pratilipi.com
		ApiRegistry.register( EventListApi.class );					// *.pratilipi.com
		ApiRegistry.register( EventBannerApi.class );				// *.pratilipi.com
		
		ApiRegistry.register( BlogPostApi.class );					// *.pratilipi.com
		ApiRegistry.register( BlogPostListApi.class );				// *.pratilipi.com
		
		ApiRegistry.register( UserApi.class );						// *.pratilipi.com
		
		ApiRegistry.register( UserPratilipiApi.class );				// *.pratilipi.com
		ApiRegistry.register( UserPratilipiLibraryApi.class );		// *.pratilipi.com
		ApiRegistry.register( UserPratilipiLibraryListApi.class );	// *.pratilipi.com
		
		ApiRegistry.register( UserPratilipiReviewApi.class );		// *.pratilipi.com
		ApiRegistry.register( UserPratilipiReviewListApi.class );	// *.pratilipi.com
		
		ApiRegistry.register( UserAuthorFollowApi.class );			// *.pratilipi.com
		ApiRegistry.register( UserAuthorFollowListApi.class );		// *.pratilipi.com

		ApiRegistry.register( CommentApi.class );					// *.pratilipi.com
		ApiRegistry.register( CommentListApi.class );				// *.pratilipi.com
		
		ApiRegistry.register( VoteApi.class );						// *.pratilipi.com
		
		ApiRegistry.register( MailingListSubscribeApi.class );		// *.pratilipi.com
		
		ApiRegistry.register( NotificationApi.class );		// *.pratilipi.com
		
	}
	
}
