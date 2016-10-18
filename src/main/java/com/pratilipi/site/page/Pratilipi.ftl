<!DOCTYPE html>
<html lang="${lang}">

	<head>
		<#assign mainPage="pratilipi-pratilipi-page">
		<#include "meta/Head.ftl">
	</head>
	
	<body>
		<#include "meta/PolymerDependencies.ftl">
		<pratilipi-pratilipi-page 
				user-data='${ userJson }'
				pratilipi-id='${ pratilipi.getId()?c }'
				pratilipi-data='${ pratilipiJson }'
				userpratilipi-data='${ userpratilipiJson! }'
				
				review-list='${ reviewListJson }'
				cursor='${ reviewListCursor! }'
				pratilipi-types='${ pratilipiTypesJson }'
				navigation-list='${ navigationList }'
				language-map='${ languageMap }'></pratilipi-pratilipi-page>

		<#include "meta/Foot.ftl">
	</body>
	
</html>