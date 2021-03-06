<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#include "meta/HeadBasic.ftl">
	</head>

	<body>
		<#include "../element/basic/pratilipi-header.ftl">
		<#include "../element/basic/pratilipi-facebook-login.ftl">
		<div class="parent-container">
			<div class="container">
				<div class="pratilipi-shadow secondary-500 box">
					<#if user.isGuest()>
						<div style="padding: 50px 10px;" class="secondary-500 pratilipi-shadow box">
							<img style="width: 48px; height: 48px; margin: 0px auto 20px auto; display: block;" 
									src="https://storage.googleapis.com/devo-pratilipi.appspot.com/icomoon_24_icons/SVG/info.svg" alt="${ _strings.author_no_contents_published }" />
							<div class="text-center"><a class="login-link" href="/login?ret=/notification">${ _strings.user_login_to_view_notifications }</a></div>
						</div>							
					<#else>
						<div class="pull-left">
							<a style="cursor: pointer;">
								<img style="width: 20px;height: 20px;" onclick="goBack()" src="http://0.ptlp.co/resource-all/icon/svg/arrow-left.svg">
						  	</a>	
						</div>
						<div class="center-heading">
							<h5 class="pratilipi-red pratilipi-bold pratilipi-no-margin">
								${ _strings.notification_notifications }
							</h5>
						</div>					
						<div class="clearfix"></div>
						<hr>				
						<#if notificationList?has_content >
							<div class="list-group">
							<#list notificationList as notification>
								<#if ( notification.getMessage()?? && notification.getSourceUrl()?? ) >
									<a href="${ notification.getSourceUrl() }" style="margin-bottom:4px;" class="list-group-item <#if ( notification.getState() == "UNREAD")>notification-unread-state</#if>">${ notification.getMessage() }</a>
								</#if>	
							</#list>
							</div>
						<#else>
							<div style="padding: 50px 10px;" class="secondary-500 pratilipi-shadow box">
								<img style="width: 48px; height: 48px; margin: 0px auto 20px auto; display: block;" 
										src="https://storage.googleapis.com/devo-pratilipi.appspot.com/icomoon_24_icons/SVG/info.svg" alt="${ _strings.author_no_contents_published }" />
								<div class="text-center">${ _strings.notifications_no_notifications }</div>
							</div>							
						</#if>
					</#if>	
				</div>				
			</div>
		</div>
		<#include "../element/basic/pratilipi-footer.ftl">
	</body>
	<script>
		function goBack() {
			if( getUrlParameter( "ret" ) != null )
				window.location.href =  getUrlParameter( "ret" );
			else
				window.location.href = "/";
		}
	</script>	
</html>