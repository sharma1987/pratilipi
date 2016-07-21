<div class="pratilipi-shadow secondary-500 box">	
	<div class="pull-left">
		<h5 class="pratilipi-red pratilipi-bold pratilipi-no-margin">
			<!-- ${ _strings.author_drafts } -->Following
				<!-- <span><button class="pratilipi-grey-button"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add new</button></span> -->
		</h5>
		<p class="works-number">${ followingList.getNumberFound() } Members</p>
	</div>
	<div class="pull-right">
		<br>	
		<a class="pull-right pratilipi-red" href="#">View More</a>
	</div>
	<div class="clearfix"></div>
	<hr style="margin-top:0px;margin-bottom:0px;">	
	
	<#if followingList.getAuthorList()?has_content>
		<#include "pratilipi-follow-author-card.ftl">
		<#list followingList.getAuthorList() as author>
			<#assign
				isGuestBoolean = user.isGuest();
				can_follow_boolean = ( user.getId()?? && ( user.getId() == author.getUser().getId() ) )?then( false, true )
			>
			<@follow_author_card isGuest=isGuestBoolean can_follow=can_follow_boolean retUrl=author.getPageUrl() userId=author.getUser().getId()?c followCount=author.getFollowCount() following=author.isFollowing() name=author.getName()!author.getNameEn() pageUrl=author.getPageUrl() imageUrl=author.getImageUrl()/>
		</#list>
	</#if>
					
</div>	