<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta name="description" content="A platform to discover, read and share your favorite stories, poems and books in a language, device and format of your choice.">
	<title>Read Hindi, Gujarati and Tamil Stories, Poems and Books</title>
	<link rel="shortcut icon" type="image/png" href="/favicon.png">
	<link rel="stylesheet" type="text/css" href="/resources/style-home.css?5">
</head>

<body>
	<div class="container">
		<div class="landing-page">
			<div class="pratilipi-banner clearfix">

				<div class="pratilipi-background"></div>

				<div class="content-wrapper">

					<div class="heading-wrapper">
						<h2>2,000,000 Readers.&nbsp;</h2><h2>4,000 Writers.&nbsp;</h2><h2>1 Platform.</h2>
					</div>

					<div class="logo">
						<div class="pratilipi"></div>
						<h1>pratilipi</h1>
					</div>

					<div class="description description-mobile">
						<div class="bg-white">Read great stories and write your own on world's largest platform for Indian languages</div>
					</div>

					<div class="description description-widescreen">
						<h5 class="bg-white">Read great stories and write your own on</h5>
						<br>
						<h5 class="bg-white">world's largest platform for Indian languages</h5>
					</div>

					<button type="button" class="start-reading-btn" onClick="startReading()">START READING</button>

				</div>

			</div>

			<div class="wrapper" id="wrapper">
				<ul class="tiles-container" id="tiles-container">
					<li class="image">
						<a href="http://hindi.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://0.ptlp.co/resource-all/home-page/pratilipi-hindi-compressed-s.jpg');">
								<span class="language">हिंदी</span>
							</div>
						</a>
					</li>
					<li class="image">
						<a href="http://tamil.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://1.ptlp.co/resource-all/home-page/pratilipi-tamil-compressed-s.jpg');">
								<span class="language">தமிழ்</span>
							</div>
						</a>
					</li>
					<li class="image">
						<a href="http://malayalam.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://2.ptlp.co/resource-all/home-page/pratilipi-malayalam-compressed-s.jpg');">
								<span class="language">മലയാളം</span>
							</div>
						</a>
					</li>
					<li class="image">
						<a href="http://bengali.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://3.ptlp.co/resource-all/home-page/pratilipi-bengali-compressed-s.jpg'); background-size: cover; background-position: top;">
								<span class="language">বাংলা</span>
							</div>
						</a>
					</li>
					<li class="image">
						<a href="http://telugu.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://0.ptlp.co/resource-all/home-page/pratilipi-telugu-compressed-s.jpg');">
								<span class="language">తెలుగు</span>
							</div>
						</a>
					</li>
					<li class="image">
						<a href="http://gujarati.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://1.ptlp.co/resource-all/home-page/pratilipi-gujarati-compressed-s.jpg'); background-size: cover; background-position: top;">
								<span class="language">ગુજરાતી</span>
							</div>
						</a>
					</li>
					<li class="image">
						<a href="http://marathi.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://2.ptlp.co/resource-all/home-page/pratilipi-marathi-compressed-s.jpg');">
								<span class="language">मराठी</span>
							</div>
						</a>
					</li>
					<li class="image">
						<a href="http://kannada.pratilipi.com/">
							<div class="tiles" style="background-image:url('http://3.ptlp.co/resource-all/home-page/pratilipi-kannada-compressed-s.jpg');">
								<span class="language">ಕನ್ನಡ</span>
							</div>
						</a>
					</li> 
				</ul>

				<div class="tiles-container-mobile" id="tiles-container-mobile">
					<a class="language-button" href="http://hindi.pratilipi.com/">
						<span>हिंदी</span>
					</a>
					<a class="language-button" href="http://tamil.pratilipi.com/">
						<span>தமிழ்</span>
					</a>
					<a class="language-button" href="http://malayalam.pratilipi.com/">
						<span class="language">മലയാളം</span>
					</a>
					<a class="language-button" href="http://bengali.pratilipi.com/">
						<span class="language">বাংলা</span>
					</a>
					<a class="language-button" href="http://telugu.pratilipi.com/">
						<span class="language">తెలుగు</span>
					</a>
					<a class="language-button" href="http://gujarati.pratilipi.com/">
						<span class="language">ગુજરાતી</span>
					</a>
					<a class="language-button" href="http://marathi.pratilipi.com/">
						<span class="language">मराठी</span>
					</a>
					<a class="language-button" href="http://kannada.pratilipi.com/">
						<span class="language">ಕನ್ನಡ</span>
					</a>
				</div>

			</div>

			<div class="notify-me-wrapper" id="notify-me-wrapper">

				<div class="notify-elements" id="notify-elements">
					<h3>Be the first to know when your language gets added</h3>

					<select id="mailingListLanguage" name="Language" class="language-selection">
						<option value="none" selected disabled>Language</option>
						<option value="LAUNCH_ANNOUNCEMENT_URDU">		Urdu		</option>
						<option value="LAUNCH_ANNOUNCEMENT_ODIA">		Odia		</option>
						<option value="LAUNCH_ANNOUNCEMENT_PUNJABI">	Punjabi		</option>
						<option value="LAUNCH_ANNOUNCEMENT_ASSAMESE">	Assamese	</option>
						<option value="LAUNCH_ANNOUNCEMENT_MAITHILI">	Maithili	</option>
						<option value="LAUNCH_ANNOUNCEMENT_BHOJPURI">	Bhojpuri	</option>
						<option value="LAUNCH_ANNOUNCEMENT_OTHER">		Any Other	</option>
					</select>
					<input class="input-field" type="text" name="mailingListLanguageInput" id="mailingListLanguageInput" placeholder="Language">
					<input class="input-field" type="email" name="mailingListEmail" id="mailingListEmail" placeholder="Email" onKeyPress="mailingList( event, this )">
					<button class="notify-me-btn" onClick="mailingList()">NOTIFY ME!</button>
				</div>
				
				<div id="notify-loader" class="notify-loader"></div>

				<div class="notify-message" id="notify-message">
					<h2 id="notify-message-text"></h2>
					<button class="notify-me-btn" onClick="addSubscription()">ADD ANOTHER SUBSCRIPTION</button>
				</div>
			</div>
			<div class="pratilipi-footer">
				<div class="about-pratilipi">
					<h5>Pratilipi</h5>
					<span>Jai&nbsp;Plaza&nbsp;Elite, #1064, 1st&nbsp;Floor, 7th&nbsp;A&nbsp;Main, 3rd&nbsp;Block&nbsp;Koramangala, Bengaluru, Karnataka&nbsp;-&nbsp;560095</span>
					<span>CIN:&nbsp;U72200KA2015PTC079230</span>
					<br/>
					<a href="mailto:contact@pratilipi.com"><span>contact@pratilipi.com</span></a>
					<br/>
					<a href="tel:+918041710149"><span>080-41710149</span></a>
				</div>
				<div class="about-part-3">
					<span>Follow us on Social Media :</span>
					<div class="social-media-links">
						<a href="https://www.facebook.com/Pratilipidotcom" target="_blank"><img src="http://0.ptlp.co/resource-all/icon/svg/facebook-grey.svg" /></a>
						<a href="https://twitter.com/TeamPratilipi" target="_blank"><img src="http://0.ptlp.co/resource-all/icon/svg/twitter-grey.svg" /></a>
						<a href="https://plus.google.com/+PratilipiTeam" target="_blank"><img src="http://0.ptlp.co/resource-all/icon/svg/google-plus-grey.svg" /></a>
						<a href="https://www.linkedin.com/company/pratilipi" target="_blank"><img src="http://0.ptlp.co/resource-all/icon/svg/linkedin-grey.svg" /></a>
						<a href="https://www.quora.com/topic/Pratilipi-company" target="_blank"><img src="http://0.ptlp.co/resource-all/icon/svg/quora.svg" /></a>
					</div>
				</div>
				<p class="copyrights">&copy;&nbsp;2015-2016 Nasadiya Tech. Pvt. Ltd.</p>
			</div>
		</div>
	</div>

	<script src='http://0.ptlp.co/third-party/jquery-2.1.4/jquery-2.1.4.min.js'></script>
	<script>
		$( document ).ready(function() {
			<#-- Setting tiles min-height -->
			jQuery( '.wrapper' ).css( "min-height", jQuery( window ).height() + "px" );
			<#-- Setting banner height -->
			if( $( window ).width() < 768 ) {
				jQuery( '.pratilipi-banner' ).height( jQuery( window ).height() );
			} else {
				var diff = ( jQuery( window ).height() - jQuery( '#tiles-container' ).height() ) / 2;
				jQuery( '.pratilipi-banner' ).height( jQuery( window ).height() - 20 - diff - 108 );
				jQuery( '.pratilipi-banner' ).css( "max-height", Math.max( jQuery( window ).height(), jQuery( '.content-wrapper' ).height() + 108 ) + "px" );
			}
			<#-- Hiding the language input on ready -->
			document.getElementById( 'mailingListLanguageInput' ).style.display = "none";
			document.getElementById( "mailingListLanguage" ).addEventListener( "change", onMailingListLanguageChange, false );
		});
		function onMailingListLanguageChange() {
			var val = document.getElementById( 'mailingListLanguage' ).value;
			if( val == "LAUNCH_ANNOUNCEMENT_OTHER" ) {
				document.getElementById( 'mailingListLanguageInput' ).style.display = "inline-block";
			} else {
				document.getElementById( 'mailingListLanguageInput' ).style.display = "none";
			}
		}
		function validateEmail( email ) {
			var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
			return re.test(email);
		}
		function startReading() {
			jQuery('html, body').animate( { scrollTop: jQuery( '#wrapper' ).offset().top }, 500 );
		}
		function addSubscription() {
			document.getElementById( "notify-elements" ).style.display = "block";
			document.getElementById( "notify-message" ).style.display = "none";
		}
		function mailingList( e, input ) {
			if( e != null ) {
				var code = ( e.keyCode ? e.keyCode : e.which );
				if( code != 13 )
					return;
			}
			var mailingList = document.getElementById( 'mailingListLanguage' ).value;
			var comment = mailingList == "LAUNCH_ANNOUNCEMENT_OTHER" ? 
					document.getElementById( 'mailingListLanguageInput' ).value.trim() : "";

			var email = document.getElementById( 'mailingListEmail' ).value;
			var passed = true;
			
			if( mailingList == "none" ) {
				jQuery( '#mailingListLanguage' ).css( { "border": '#FF0000 1px solid' } );
				passed = false;
			} else {
				jQuery( '#mailingListLanguage' ).css( { "border": 'none' } );
			}

			if( mailingList == "LAUNCH_ANNOUNCEMENT_OTHER" && comment == "" ) {
				jQuery( '#mailingListLanguageInput' ).css( { "border": '#FF0000 1px solid' } );
				passed = false;
			} else {
				jQuery( '#mailingListLanguage' ).css( { "border": 'none' } );
			}

			if( email.trim() == "" || !validateEmail( email ) ) {
				jQuery( '#mailingListEmail' ).css( { "border": '#FF0000 1px solid' } );
				passed = false;
			} else {
				jQuery( '#mailingListEmail' ).css( { "border": 'none' } );
			}
			
			if( !passed )
				return;

			<#-- Spinner active -->
			jQuery( '#notify-me-wrapper' ).height( jQuery( '#notify-me-wrapper' ).height() );
			document.getElementById( "notify-elements" ).style.display = "none";
			document.getElementById( 'notify-loader' ).style.display = "block";

			var body = { 
					'email': email, 
					'mailingList': mailingList,
			};
			if( comment != "" )
					body[ 'comment' ] = comment;

			$.ajax({
				type: 'post',
				url: '/api/mailinglist/subscribe',
				data: body,
				success: function( response ) {
					document.getElementById( 'notify-loader' ).style.display = "none";
					document.getElementById( 'mailingListLanguage' ).value = "none";
					document.getElementById( 'mailingListLanguageInput' ).style.display = "none";
					document.getElementById( 'mailingListEmail' ).value = null;
					document.getElementById( "notify-message-text" ).innerText = "Thank You! You will be notified when we launch the language!";
					document.getElementById( "notify-message" ).style.display = "block";
				},
				error: function( response ) {
					var messageJson = jQuery.parseJSON( response.responseText );
					var message = "";
					if( messageJson["message"] != null )
						message = messageJson["message"];
					else
						message = "Failed due to some reason! Please try again!";

					document.getElementById( 'notify-loader' ).style.display = "none";
					document.getElementById( "notify-message-text" ).innerText = message;
					document.getElementById( "notify-message" ).style.display = "block";
				}
			});
		}
	</script>
	<#include "meta/GoogleAnalytics.ftl">
</body>

</html>