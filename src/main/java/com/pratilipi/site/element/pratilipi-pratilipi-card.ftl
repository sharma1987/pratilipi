<div class="box" style="padding:5px 15px 0px 15px">
	<table>
		<tr>
			<td rowspan="2" style="height:120px;width:80px;padding-top:15px">
				<a href="${ pratilipi.pageUrl }">
					<img src="${ pratilipi.getCoverImageUrl( 80 ) }" alt="${ pratilipi.title }" title="${ pratilipi.title }" />
				</a>
			</td>
			<td>
				<h3><a style="text-decoration: none;" href="${ pratilipi.pageUrl }">${ pratilipi.title }</a></h3>
				<#if pratilipi.author?? >
					<h5><a style="text-decoration: none;" href="${ pratilipi.author.pageUrl }">
						${ pratilipi.author.name }
					</a></h5>
				</#if>
				<div style="margin:10px">
					<#assign rating=pratilipi.averageRating >
					<#include "pratilipi-rating.ftl" > (${rating})
				</div>
			</td>
		</tr>
		<tr>
			<td style="vertical-align:bottom">
				<div style="margin:3px 10px;text-transform:uppercase">
					<button type="button">${ _strings.read }</button>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<hr/>
				<p class="three-cols" style="text-align:center">
					<span>${ pratilipi.readCount } ${ _strings.pratilipi_count_reads }</span> <br/>
					<span>${ pratilipi.fbLikeShareCount } ${ _strings.pratilipi_count_likes }</span> <br/>
					<span>${ pratilipi.reviewCount } ${ _strings.pratilipi_count_reviews }</span> <br/>
				</p>
			</td>
		</tr>
	</table>
</div>