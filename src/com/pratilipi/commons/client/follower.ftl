<#macro follower authorId userId following followerCount followingCount>
	<div style="text-align:center;">
		
		<div id="Author-content-unfollow-button" class="btn-group" style="display:none;">
			<button type="button" class="btn btn-info dropdown-toggle" style="border-radius:20px;" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				Following <span class="caret"></span>
			</button>
			<ul class="dropdown-menu">
				<li onClick="unFollow(this)"><a href="#">Unfollow</a></li>
			</ul>
		</div>
	
		<button id="Author-content-follow-button" type="button" class="btn btn-default" onClick="follow(this)">Follow</button>
		
	</div>
	<div id="Author-content-follow-text-div" style="text-align:center;font-size:70%; padding:5px;display:none">
		<span id="Author-content-follow-text-span">${ followerCount } followers</span>
		<span style="margin-left:20px;">${ followingCount } following</span>
	</div>


	<style>
		#Author-content-follow-button {
			width:100px;
			color:#46b8da;
			border:solid 1px #46b8da;
			border-radius:20px;
			display:none;
		}
	
	</style>

	<script>
		var isFollowing = ${ following?c };
		var followButton = jQuery("#Author-content-follow-button");
		var unFollowButton = jQuery("#Author-content-unfollow-button");
		
		function follow(object){
			console.log( "follow button is click" );
			ajaxPost( object, 1 )
		}
		
		function unFollow(object){
			console.log( "un follow button is click" );
			ajaxPost( object, 0 )
		}
		
		function showFollowButton(){
			unFollowButton.hide();
			followButton.show();
		}
		
		function showUnFollowButton(){
			followButton.hide();
			unFollowButton.show();
		}
		
		function ajaxPost( object, following ){
			var requestData = JSON.stringify({
										authorId: ${ authorId?c }, 
										userId: ${ userId?c },
										following: Boolean(following)
									});

			jQuery.ajax({
				url: "/api.pratilipi/author/followers",
				type: "PUT",
				contentType: "application/json",
				dataType: "json",
				handleAs: "json",
				data: requestData,
				beforeSend: function(){
					if(Boolean(following)){
						showUnFollowButton();
					} else{
						showFollowButton()
					}
				},
				success: function( response, status, xhr ) {
					handleAjaxPostResponse( response );
				}, 
				error: function( xhr, status, error) {
					if(Boolean(following)){
						showFollowButton();
					} else{
						showUnFollowButton()
					}
					alert( status + " : " + error );
					
				},
				complete: function( event, response ){
					console.log( response );
				}
			});	
		}
		
		function handleAjaxPostResponse( response ){
			followerCount = response[ 'followerCount' ];
			jQuery("#Author-content-follow-text-span").text(followerCount+" Followers"); 
		}
		
		if(Boolean(isFollowing)){
			showUnFollowButton();
		} else{
			showFollowButton()
		}
		
		jQuery("#Author-content-follow-text-div").show();
	</script>		

</#macro>