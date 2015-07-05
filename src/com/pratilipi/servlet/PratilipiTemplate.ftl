<!DOCTYPE html>
<html itemscope="" itemtype="http://schema.org/WebPage" lang="en-IN">
	<head>
		<meta content="/theme.pratilipi/logo-200x200.png" itemprop="image">
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		
		<#if request.getRequestURI() == "/">
			<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
		</#if>

		<link rel="shortcut icon" type="image/png" href="/theme.pratilipi/favicon.png">

		<#if basicMode>
			<script src="//j.lib.cdssolutions.in/jquery-1.11.1/jquery-1.11.1.min.js"></script>
			<script src="//b.lib.cdssolutions.in/bootstrap-3.3.4/js/bootstrap.min.js" defer></script>
			<link rel="stylesheet" href="//b.lib.cdssolutions.in/bootstrap-3.3.4/css/bootstrap.min.css">
			<script src="//j.lib.cdssolutions.in/jquery-file-upload-9.7.0/js/vendor/jquery.ui.widget.js" defer></script>
			<script src="//j.lib.cdssolutions.in/jquery-file-upload-9.7.0/js/jquery.iframe-transport.js" defer></script>
			<script src="//j.lib.cdssolutions.in/jquery-file-upload-9.7.0/js/jquery.fileupload.js" defer></script>
			<script src="//c.lib.cdssolutions.in/ckeditor-4.4.5-full/ckeditor.js" charset="utf-8" defer></script>
			<script language="javascript" defer>
				window.onload = function() {
					CKEDITOR.config.toolbar = [
							['Source','Format','Bold','Italic','Underline','Strike','-','Subscript','Superscript','-','RemoveFormat'],
							['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','Outdent','Indent'],
							['NumberedList','BulletedList'],
							['Blockquote','Smiley','HorizontalRule','PageBreak'],
							['Link','Unlink'],
							['Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo'],
							['ShowBlocks','Maximize']];
					CKEDITOR.config.toolbar_BASIC = [
							['Bold','Italic','Underline','Strike','-','Subscript','Superscript','-','RemoveFormat'],
							['NumberedList','BulletedList'],
							['Blockquote','Smiley','HorizontalRule'],
							['Link','Unlink']];	
				 	CKEDITOR.env.isCompatible = true;
					
				}				
			</script>
		</#if>

		<meta property="og:site_name" content="Pratilipi"/>
		<meta property="fb:admins" content="526808250"/>
		
		<#list resourceTagList as resourceTag>
			${ resourceTag }
		</#list>
		
		<meta property="og:image" content="http://www.pratilipi.com/theme.pratilipi/logo-200x200.png">

		<#if basicMode>
			<link type="text/css" rel="stylesheet" href="/theme.pratilipi/style.basicmode.min.css?21062015">
			<script type="text/javascript" language="javascript" src="/theme.pratilipi/script.basicmode.min.js" defer></script>
		<#else>
			<link type="text/css" rel="stylesheet" href="/theme.pratilipi/style.min.css?21062015">
		</#if>
		<script type="text/javascript" language="javascript" src="/theme.pratilipi/script.min.js?24052015" defer></script>

		<#if page.getType()! != "READ" && page.getType()! != "WRITE">
			<script type="text/javascript" language="javascript" src="/pagecontent.userforms/pagecontent.userforms.nocache.js?20150421" async></script>
		</#if>

		<title>${ (page.getTitle() + " &#0187 Pratilipi") ! "Pratilipi" }</title>		
		
		<!-- For Google Analytics tracking -->
		<script language="javascript">
			(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
			(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
			m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
			})(window,document,'script','//www.google-analytics.com/analytics.js','ga');
			
			<#if userId == 0>
				ga('create', 'UA-53742841-2', 'auto' );
				ga('require', 'displayfeatures');
				ga('send', 'pageview');
			<#else>
				ga('create', 'UA-53742841-2', { 'userId': ${ userId?c } });
				ga('require', 'displayfeatures');
				ga('set', 'dimension1', ${ userId?c });
				ga('send', 'pageview');
			</#if>
		</script>	
		
		<!-- Facebook Conversion-Tracking Pixel Code -->
		<script>
			(function() {
				var _fbq = window._fbq || (window._fbq = []);
				if (!_fbq.loaded) {
					var fbds = document.createElement('script');
					fbds.async = true;
					fbds.src = '//connect.facebook.net/en_US/fbds.js';
					var s = document.getElementsByTagName('script')[0];
					s.parentNode.insertBefore(fbds, s);
					_fbq.loaded = true;
				}
			})();
			window._fbq = window._fbq || [];
			window._fbq.push(['track', '6017683737248', {'value':'0.01','currency':'INR'}]);
		</script>
		<noscript>
			<img height="1" width="1" alt="" style="display:none" src="https://www.facebook.com/tr?ev=6017683737248&amp;cd[value]=0.01&amp;cd[currency]=INR&amp;noscript=1" />
		</noscript>
		
		<!-- Facebook Custom Audience Pixel -->
		<script>
			(function() {
				var _fbq = window._fbq || (window._fbq = []);
				if (!_fbq.loaded) {
					var fbds = document.createElement('script');
					fbds.async = true;
					fbds.src = '//connect.facebook.net/en_US/fbds.js';
					var s = document.getElementsByTagName('script')[0];
					s.parentNode.insertBefore(fbds, s);
					_fbq.loaded = true;
				}
				_fbq.push(['addPixelId', '1569748966613739']);
			})();
			window._fbq = window._fbq || [];
			window._fbq.push(['track', 'PixelInitialized', {}]);
		</script>
		<noscript>
			<img height="1" width="1" alt="" style="display:none" src="https://www.facebook.com/tr?id=1569748966613739&amp;ev=PixelInitialized" />
		</noscript>

	</head>
	<#if basicMode>
		<body>
			<!-- Google Tag Manager -->
			<noscript><iframe src="//www.googletagmanager.com/ns.html?id=GTM-THCCG3"
			height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
			<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
			new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
			j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
			'//www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
			})(window,document,'script','dataLayer','GTM-THCCG3');</script>
			<!-- End Google Tag Manager -->
			
			<span id="Claymus-Loading">Loading...</span>
			<#if websiteWidgetHtmlListMap["HEADER"] ??>
				<#list websiteWidgetHtmlListMap["HEADER"] as websiteWidgetHtml>
					${ websiteWidgetHtml }
				</#list>
			</#if>
	<#else>
		<body fullbleed layout vertical>
			<!-- Google Tag Manager -->
			<noscript><iframe src="//www.googletagmanager.com/ns.html?id=GTM-THCCG3"
			height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
			<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
			new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
			j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
			'//www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
			})(window,document,'script','dataLayer','GTM-THCCG3');</script>
			<!-- End Google Tag Manager -->
			
			<span id="Claymus-Loading">Loading...</span>
			<template unresolved is="auto-binding" id="Polymer">
				<#if websiteWidgetHtmlListMap["HEADER"] ??>
					<core-scroll-header-panel flex id="Polymer-Window">
						<core-toolbar>
							<div flex>
								Header
							</div>
						</core-toolbar>
				</#if>
	</#if>
		

		<#list pageContentHtmlList as pageContentHtml>
			${ pageContentHtml }
		</#list>
	
		<#if websiteWidgetHtmlListMap["FOOTER"] ??>
			<#list websiteWidgetHtmlListMap["FOOTER"] as websiteWidgetHtml>
				${ websiteWidgetHtml }
			</#list>
		</#if>
			
		<#if !basicMode>
				<#if websiteWidgetHtmlListMap["HEADER"] ??>
					</core-scroll-header-panel>
				</#if>
			</template>
		</#if>
		
		<script type="application/ld+json">
			{
			  "@context": "http://schema.org",
			  "@type": "Organization",
			  "url": "https://www.pratilipi.com/",
			  "logo": "http://www.pratilipi.com/theme.pratilipi/logo-200x200.png",
			  "sameAs" : [ "http://www.facebook.com/Pratilipidotcom",
						    "http://www.twitter.com/TeamPratilipi",
						    "https://plus.google.com/u/0/+PratilipiTeam" ],
			  "potentialAction": {
			    "@type": "SearchAction",
			    "target": "https://www.pratilipi.com/search?q={search_term_string}",
			    "query-input": "required name=search_term_string"
              }
			}
		</script>
		
		<!-- pop-up for marketing -->
		<div  id ="backdrop">
			<div id="popup" onclick="event.stopPropagation();">
				<div id="popup_heading" style="text-align: left;">
					<span>Sign up for Pratilipi</span><span id="popupCloseButton" onclick="closePopup()">x</span>
				</div>
				<div id="popup_body">
					<h2 style="margin-bottom: 40px;">Enjoy Reading?</h2>
					<p>Sign up to <strong>rate and review</strong> your favorite stories, books, poems and more!</p>
					<div id="popup_action" onclick="signUpButtonClick()">Sign up</div>
					<p>Already a member?</p>
					<a href="#" onclick="loginLinkClick()" style="color: #0000FF; text-decoration: underline;">Sign In</a>
				</div>
			</div> 
		</div>
		<script>
			var clickEventSend = Boolean( 0 );
			var userId = ${ userId?c };
			function showPopup () {
				jQuery( "#backdrop" ).addClass( "backDrop" );
				var popup = jQuery( "#popup" );
				popup.show();
				setCookie( "rate_review_notification", Boolean(1), 365, "/" );
				ga( 'send', 'event',
					'Encourage Users to Rate/Review',	// Event Category
					'Users Prompted',				// Event Action
					'Number of Users Prompted', // Event Label
					1 );									// Event Value
			}
			function closePopup() {
				jQuery( "#popup" ).hide()
				jQuery( "#backdrop" ).removeClass( "backDrop" );
			}
			function signUpButtonClick(){
				closePopup();
				jQuery( "#signupModal" ).modal('show');
				if( !clickEventSend ){
					ga( 'send', 'event',
						'Encourage Users to Rate/Review',	// Event Category
						'User Action',				// Event Action
						'User Signed Up', // Event Label
						1 );									// Event Value
				}
				clickEventSend = Boolean( 1 );
			}
			function loginLinkClick(){
				closePopup();
				jQuery( "#loginModal" ).modal('show');
				if( !clickEventSend ){
					ga( 'send', 'event',
						'Encourage Users to Rate/Review',	// Event Category
						'User Action',				// Event Action
						'User Signed In', // Event Label
						1 );									// Event Value
				}
				clickEventSend = Boolean( 1 );	
			}
			function showNotification(){
				var visitNumber = getVisitCount();
				var hasReceivedNotification = getCookie( "rate_review_notification" );
				if( parseInt( userId ) == 0 && !hasReceivedNotification &&  parseInt( visitNumber ) >= 2 )
					showPopup();
			}
			
			if( window.attachEvent) {//for IE8 and below
				window.attachEvent( 'onload', function( event ){
					showNotification();
				});
				
				window.attachEvent( 'onclick', function( event ){
					closePopup( event );
				});
				
				window.attachEvent( 'onkeyup', function( event ){
					if (event.keyCode == 27) {
						closePopup( event );
					}
				});
			}
			else {
				window.addEventListener( 'load', function( event ){
					showNotification();
				});
				
				window.addEventListener( 'click',function( event ){
					closePopup( event );
						
				});
				window.addEventListener( 'keyup', function ( event ) {
					if (event.keyCode == 27) {
						closePopup( event );
					}
				});
			}
		</script>

	</body>
</html>
