<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

		<#-- Page Title & Favicon -->
		<title>${ _strings.search } &#0171 ${ _strings.pratilipi }</title>
		<link rel="shortcut icon" type="image/png" href="/theme.pratilipi/favicon.png">

		<#-- Third-Party Library -->
		<#list resourceList as resource>
			${ resource }
		</#list>

		<#-- Polymer 1.0 Custom Elements -->
		<link rel='import' href='/elements.${lang}/pratilipi-header.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-navigation.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-card-grid.html'>
		<link rel='import' href='/elements.${lang}/pratilipi-footer.html'>

		<#-- Custom Stylesheets -->
		<link type="text/css" rel="stylesheet" href="/stylesheets/main.css">
		<link type="text/css" rel="stylesheet" href="/stylesheets/palettes.css">
		<link type="text/css" rel="stylesheet" href="/stylesheets/pratilipi.css">


		<script defer>
			jQuery( window ).scroll( function() {
				document.querySelector( 'pratilipi-card-grid' ).loadMore();
			} );
		</script>
	</head>

	<body>
		<template is="dom-bind">

			<pratilipi-user user={{ user }} user-data='${ userJson }'></pratilipi-user>
			<pratilipi-header user={{ user }}></pratilipi-header>
		
			<div class="container" style="margin-top:10px">
				<pratilipi-navigation
						class='pull-left hidden-xs hidden-sm'
						></pratilipi-navigation>
				<pratilipi-card-grid
						id='SearchResult'
						style='overflow:hidden'
						pratilipi-list='${ pratilipiListJson }'
						search-query='${ pratilipiListSearchQuery! }'
						filter='${ pratilipiListFilterJson }'
						cursor='${ pratilipiListCursor! }'
						></pratilipi-card-grid>
			</div>
		
			<pratilipi-footer></pratilipi-footer>
		
		</template>
	</body>
</html>
