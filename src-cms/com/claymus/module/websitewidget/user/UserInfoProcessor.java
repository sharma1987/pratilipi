package com.claymus.module.websitewidget.user;

import com.claymus.ClaymusHelper;
import com.claymus.module.websitewidget.WebsiteWidgetProcessor;
import com.google.appengine.api.users.User;

public class UserInfoProcessor implements WebsiteWidgetProcessor<UserInfo> {

	@Override
	public String getHtml( UserInfo userInfo ) {

		User user = ClaymusHelper.getCurrentUser();
		
		if( user == null )
			return "<div> Hello, Guest ! (<a href='" + ClaymusHelper.createLoginURL() + "'>login</a>)</div>";

		else
			return "<div> Hello, " + user.getNickname() + " ! (<a href='" + ClaymusHelper.createLogoutURL() + "'>logout</a>)</div>";
	}
	
}
