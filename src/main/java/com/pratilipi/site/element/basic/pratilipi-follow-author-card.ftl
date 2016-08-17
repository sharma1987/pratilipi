<script>
	function FollowAuthorPostRequest(authorId, follow){
		$.ajax({type: "POST",
		        url: "/api/userauthor/follow",
		        data: { authorId: authorId, following: follow },
		        success:function(response){
		        	console.log(response);
		        	console.log(typeof response);
		        	
		        	var parsed_data = jQuery.parseJSON( response );
		  			if ( parsed_data.following == follow ) {
		  				window.location.reload();
		  			}
		  			else {
		  				
		  			}
				},
		        fail:function(response){
					alert("Sorry, we could not process your request.");
				}			    		
				
		});
	}
</script>
<#macro follow_author_card isGuest can_follow retUrl authorId followCount following name pageUrl imageUrl >
	<div class="media">
		<a class="media-left" href="${ pageUrl }">
		    <img class="media-object img-circle pratilipi-without-margin" style="width:70px;height:70px;" src="${ imageUrl }">
		</a>
		<div class="media-body pratilipi-padding-10">
		    <#if isGuest == "true" >
		    	<a class="pratilipi-red-button pull-right" href="/login?ret=${ retUrl }"><img class="width-16" src="http://0.ptlp.co/resource-all/icon/svg/user-plus-red.svg"></img></a>
		    <#else>
		    	<#if can_follow == "true">
			    	<#if following == true >
					    <button class="pratilipi-red-button pull-right" style="background: #d0021b;" onclick="FollowAuthorPostRequest(${ authorId?c }, false)">
							<img class="width-16" src="http://0.ptlp.co/resource-all/icon/svg/user-check-white-red.svg"></img>
						</button>
					<#else>
						<button class="pratilipi-red-button pull-right" onclick="FollowAuthorPostRequest(${ authorId?c }, true)">
							<img class="width-16" src="http://0.ptlp.co/resource-all/icon/svg/user-plus-red.svg"></img>
						</button>			
					</#if>		    		
		    	</#if>
		    </#if>	
		    <a href="${ pageUrl }"><h4 style="display:inline;" class="media-heading clip-content-2-lines bigger-line-height">${ name }</h4></a>
		    <div style="white-space: nowrap;">${ followCount } ${ _strings.author_followers_count }</div>  			
		</div>
	</div>
</#macro>