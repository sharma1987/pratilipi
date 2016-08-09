<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#-- Page Description -->
		<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		
		<#assign mainPage="pratilipi-home-page">
		<#include "meta/Head.ftl">

		<link rel='import' href='/elements.${lang}/pratilipi-home-page.html?201608'>
		<link rel='import' href='/elements.${lang}/pratilipi-${ language?lower_case }-carousel.html?201608'>
	</head>

	<body>
		<pratilipi-home-page 
			user-data='${ userJson }'
			sections-list='${ sectionsJson }'
			pratilipi-types='${ pratilipiTypesJson }'
			navigation-list='${ navigationList }'
			language-map='${ languageMap }'></pratilipi-home-page>
	</body>

</html>
