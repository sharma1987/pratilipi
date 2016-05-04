<div class="secondary-500 pratilipi-shadow" style="display: block; padding: 5px; height: 64px;">
	<table style="min-width: 100%;">
		<tr>
			<td align="left">
				<#-- Back Button -->
				<a onClick="exitReader()" style="cursor: pointer;">back</a>
				<#-- Index Button if content has Index -->
				<a onClick="gotoNavigation()" style="cursor: pointer">nav</a>
			</td>
			<td align="right">
				<#-- Increase and Decrease font-size buttons -->
				<a style="cursor: pointer;" onCLick="increaseFontSize()">inc</a>
				<a style="cursor: pointer;" onCLick="decreaseFontSize()">dec</a>

				<#-- Library Section -->
				<#if user.isGuest == true>
					<a href="/login?ret=/read?id=${ pratilipi.getId()?c }&addToLib=true">addToLib</a>
				<#else>
					<#if !userpratilipi?? || !userpratilipi.isAddedtoLib()?? || userpratilipi.isAddedToLib == false>
						<a style="cursor: pointer;" onClick="addToLibrary()">addToLib</a>
					<#else>
						<a style="cursor: pointer;" onCLick="removeFromLibrary()">remFromLib</a>
					</#if>
				</#if>

				<a href="/library">goToLib</a>

				<#-- Menu button -->
				<a href="${ pratilipi.getPageUrl() }">${ pratilipi.getTitle()!pratilipi.getTitleEn() }</a>

				<#-- User Logged in -->
				<a style="cursor: pointer;" href="${ pratilipi.getPageUrl() }?review=write">writeReview</a>
				<#-- User Not Logged in -->

				<a href="${ pratilipi.getAuthor().getPageUrl() }">${ _strings.reader_goto_author_profile }</a>
				<a href="/">${ _strings.reader_goto_home_page }</a>
			</td>
		</tr>
	</table>
</div>