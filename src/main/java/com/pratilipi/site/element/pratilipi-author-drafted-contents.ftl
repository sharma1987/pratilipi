<#if author.hasAccessToUpdate()==true >
	<div class="pratilipi-shadow secondary-500 box">	
		<div class="pull-left">
			<h5 class="pratilipi-red pratilipi-bold pratilipi-no-margin">
				Drafts
					<#-- <span><button class="pratilipi-grey-button"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add new</button></span> -->
			</h5>
			<p class="works-number"> ${ author.getContentDrafted() }</p>
		</div>
		<div class="pull-right">
			<a class="pull-right pratilipi-red pratilipi-view-more-link" href="#">View More</a>
		</div>                   
		<div class="clearfix"></div>
		<hr>	
		<#if draftedPratilipiList?has_content>
			<#list draftedPratilipiList as pratilipi>
				<#include "pratilipi-draft-card-mini.ftl">
			</#list>
		</#if>			
	</div>
</#if>
