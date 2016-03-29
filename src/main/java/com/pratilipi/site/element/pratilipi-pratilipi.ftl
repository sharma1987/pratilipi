<#if userpratilipi.isAddedtoLib() ??>
	<script>
		function addToOrRemoveFromLibrary() {
			$.ajax({
					
				type: 'post',
				url: '/api/userpratilipi/library',
	
				data: { 
					'pratilipiId': ${ pratilipi.getId() }, 
					'addedToLib': <#if userpratilipi.isAddedtoLib()>false<#else>true</#if>
				},
				
				success: function( response ) {
					alert( "Success" );
					location.reload(); 
				},
				
				error: function( response ) {
					var message = jQuery.parseJSON( response.responseText );
					var status = response.status;
	
					if( message["message"] != null )
						alert( "Error " + status + " : " + message["message"] );
					else if( message["error"] != null )
						alert( "Error " + status + " : " + message["error"] ); 
					else
						alert( "Some exception occured at the server! Please try again." );
				}
			});
		}
	</script>
</#if>

<div class="secondary-500 pratilipi-shadow box text-center">
	<h2 class="pratilipi-red">${ pratilipi.title!pratilipi.titleEn }</h2>
	
	<#if pratilipi.author?? >
		<a href="${ pratilipi.author.pageUrlAlias!pratilipi.author.pageUrl }"><h4>${ pratilipi.author.name }</h4></a>
	</#if>
	
	<div style="width: 150px; height: 225px; margin: 15px auto;" class="pratilipi-shadow">
		<img src="${ pratilipi.getCoverImageUrl( 150 ) }" alt="${ pratilipi.title!pratilipi.titleEn }" title="${ pratilipi.titleEn!pratilipi.title }" />
	</div>
	
	<#assign rating=pratilipi.averageRating >
	<#include "pratilipi-rating.ftl" ><small>(${ pratilipi.ratingCount })</small>
	
	<h6 style="margin-top: 10px;">${ pratilipi.type }</h6>
	
	<div style="margin:25px 0px 5px 0px">
		<#if pratilipi.listingDateMillis?? ><h5>${ _strings.pratilipi_listing_date }&nbsp;&minus;&nbsp;${ pratilipi.listingDateMillis }</h5></#if>
		<h5>${ _strings.pratilipi_count_reads }&nbsp;&minus;&nbsp;${ pratilipi.readCount }</h5>
	</div>
	
	<div style="padding-top: 20px; padding-bottom: 20px;">
		<a class="pratilipi-light-blue-button" href="${ pratilipi.readPageUrl }&ret=${ requestUrl }">${ _strings.read }</a>
		<#if userpratilipi.isAddedtoLib()??>
			<br />
			<button type="button" class="pratilipi-grey-button" onclick="addToOrRemoveFromLibrary()">
				<#if !userpratilipi.isAddedtoLib()>${ _strings.add_to_library }<#else>${ _strings.remove_from_library }</#if>
			</button>
		</#if>
	</div>
			
</div>

<#if pratilipi.summary?? >
	<div class="secondary-500 pratilipi-shadow box">
		<h2 style="margin-top: 10px; margin-bottom: 15px;" class="pratilipi-red text-center">${ _strings.pratilipi_summary }</h2>
		<div style="text-align: justify;">${ pratilipi.summary }</div>
	</div>
</#if>

<div style="min-height: 7px;"></div>