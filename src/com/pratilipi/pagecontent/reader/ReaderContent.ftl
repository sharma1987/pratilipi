<!-- PageContent :: Reader :: Start -->


<template is="auto-binding" id="PageContent-Reader">

	<core-scroll-header-panel flex on-scroll={{performScrollActions}}>
		
		<core-toolbar class="bg-green">
			<paper-icon-button icon="arrow-back" title="Exit Reader" on-tap="{{performExit}}"></paper-icon-button>
			<div flex>
				${ pratilipiData.getTitle() }
			</div>
			<paper-icon-button icon="reorder" title="Display Options" on-tap="{{displayOptions}}"></paper-icon-button>
		</core-toolbar>
		
		<div horizontal center-justified layout class="bg-gray">
			<#if pratilipiData.getContentType() == "PRATILIPI" >
	
				<div class="paper">
					<div style="position:relative">
						<#if contentSize??>
							<div id="PageContent-Reader-Content" style="font-size:${ contentSize }"></div>
						<#else>
							<div id="PageContent-Reader-Content" ></div>
						</#if>
						<div id="PageContent-Reader-Overlay"></div>
					</div>
				</div>

			<#elseif pratilipiData.getContentType() == "IMAGE" >			

				<div class="paper" style="width:inherit; max-width:none; min-height:inherit; overflow-x:auto;">
					<div style="position:relative">
						<#if contentSize??>
							<div id="PageContent-Reader-Content" style="width:${ contentSize }"></div>
							<div id="PageContent-Reader-Overlay" style="width:${ contentSize }"></div>
						<#else>
							<div id="PageContent-Reader-Content"></div>
							<div id="PageContent-Reader-Overlay"></div>
						</#if>
					</div>
				</div>

			</#if>
		</div>
		
		<div class="bg-gray green" style="text-align:center;padding-bottom:16px;margin-bottom:65px;">
			<b>{{ pageNo }} / ${ pageCount }</b>
		</div>
				
	</core-scroll-header-panel>
	
	
	<div center horizontal layout id="PageContent-Reader-Navigation" style="position:fixed; bottom:10px; width:100%;">
		<paper-slider flex pin="true" snaps="false" min="1" max="{{ pageCount > 1 ? pageCount : 2 }}" value="{{ pageNo }}" class="bg-green" style="width:100%" disabled="{{ pageCount == 1 }}" on-change="{{displayPage}}"></paper-slider>
		<#if showEditOption>
			<paper-fab mini icon="create" title="Edit" class="bg-green" style="margin-right:10px;" on-tap="{{goToWriter}}"></paper-fab>
		</#if>
		<paper-fab mini icon="chevron-left" title="Previous Page" class="bg-green" style="margin-right:10px;" disabled="{{ pageNo == 1 }}" on-tap="{{displayPrevious}}"></paper-fab>
		<paper-fab mini icon="chevron-right" title="Next Page" class="bg-green" style="margin-right:25px;" disabled="{{ pageNo == pageCount }}" on-tap="{{displayNext}}"></paper-fab>
	</div>

	<paper-dialog id="PageContent-Reader-Options">
		<div><b>Text Size</b></div>
		<core-icon-button icon="remove" title="Decrease Text Size" on-tap="{{decTextSize}}"></core-icon-button>
		<core-icon-button icon="add" title="Increase Text Size" on-tap="{{incTextSize}}"></core-icon-button>
	</paper-dialog>


	<#if pratilipiData.getContentType() == "PRATILIPI" >
		<core-ajax
				id="PageContent-Reader-Ajax"
				url="/api.pratilipi/pratilipi/content"
				contentType="application/json"
				method="GET"
				handleAs="json"
				on-core-response="{{handleAjaxResponse}}"
				on-core-error="{{handleAjaxError}}" ></core-ajax>
	</#if>

</template>


<script>

	var scope = document.querySelector( '#PageContent-Reader' );
	
	scope.pageCount = ${ pageCount };
	scope.pageNo = ${ pageNo };
	var pageNoDisplayed = 0;
	
	var contentArray = [];
	var isCtrl = false;
	
	
	jQuery( 'body' ).bind( 'contextmenu', function( event ) {
		event.preventDefault();
	});
	
	jQuery( 'body' ).keyup( function( event) {
		if( event.which == 17 )
			isCtrl = false;
	});
	
	jQuery( 'body' ).keydown( function( event ) {
		if( event.which == 37 ) {
			scope.displayPrevious();
		} else if( event.which == 39 ) {
			scope.displayNext();
		} else if( event.which == 17 ) {
			isCtrl = true;
		} else if( event.which == 67 || event.which == 80 ) {
			if( isCtrl)
				return false; // Disabled Ctrl + C/P
		}
	});
	
	
	scope.performScrollActions = function( e ) {
		var bottom = jQuery( '.paper' ).position().top
				+ jQuery( '.paper' ).outerHeight( true )
				+ 66;
		if( e.target.y > 60 && bottom > e.target.scrollHeight && jQuery( '#PageContent-Reader-Navigation' ).is( ':visible' ) )
			jQuery( '#PageContent-Reader-Navigation' ).fadeOut( 'fast' );
		else if( ( e.target.y <= 60 || bottom <= e.target.scrollHeight ) && !jQuery( '#PageContent-Reader-Navigation' ).is( ':visible' ) )
			jQuery( '#PageContent-Reader-Navigation' ).fadeIn( 'fast' );
	}
	
	scope.displayOptions = function( e ) {
		var dialog = document.querySelector( '#PageContent-Reader-Options' );
		if( dialog ) {
			dialog.open();
		}
	};

	scope.goToWriter = function( e ) {
		window.location.href="${ pratilipiData.getWriterPageUrl() }";
	};

	scope.performExit = function( e ) {
		window.location.href="${ exitUrl ! pratilipiData.getPageUrl() }";
	};


	scope.displayPage = function( e ) {
		updateAndPrefetchContent();
		recordPageChangeEvent( 'SetPage' );
	};
	
	scope.displayPrevious = function( e ) {
		if( scope.pageNo > 1 ) {
			scope.pageNo--;
			updateAndPrefetchContent();
			recordPageChangeEvent( 'PreviousPage' );
		}
	};

	scope.displayNext = function( e ) {
		if( scope.pageNo < scope.pageCount ) {
			scope.pageNo++;
			updateAndPrefetchContent();
			recordPageChangeEvent( 'NextPage' );
		}
	};
    
	function updateAndPrefetchContent() {
		updateContent();
		document.querySelector( 'core-scroll-header-panel' ).scroller.scrollTop = 0;
		prefetchContent();
		setCookie( '${ pageNoCookieName }', scope.pageNo, 365, '${ pratilipiData.getReaderPageUrl() }' );
		setCookie( '${ pageNoCookieName }', scope.pageNo, 365, '${ pratilipiData.getWriterPageUrl() }' );
    }
    
    
	<#if pratilipiData.getContentType() == "PRATILIPI" >
    
		contentArray[scope.pageNo] = ${ pageContent }
		
		scope.handleAjaxResponse = function( event, response ) {
			contentArray[response.response['pageNo']] = response.response['pageContent'];
			updateContent();
	    };
	    
	    scope.handleAjaxError = function( event, response ) {
			updateContent();
		};
	    
	    
		function updateContent() {
			if( pageNoDisplayed == scope.pageNo ) {
				return;
			
			} else if( contentArray[scope.pageNo] == null ) {
				loading( true );
				var ajax = document.querySelector( '#PageContent-Reader-Ajax' );
				ajax.params = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo, contentType:'PRATILIPI' } );
				ajax.go();
			
			} else {
				jQuery( '#PageContent-Reader-Content' ).fadeOut( 'fast', function() {
						jQuery( '#PageContent-Reader-Content' ).html( contentArray[scope.pageNo] );
						jQuery( '#PageContent-Reader-Content' ).fadeIn( 'fast', function() {
								loading( false );
						});
				});
				pageNoDisplayed = scope.pageNo;
			}
		}
		
		function prefetchContent() {
			var ajax = document.querySelector( '#PageContent-Reader-Ajax' );
			if( scope.pageNo > 1 && contentArray[scope.pageNo - 1] == null ) {
				ajax.params = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo - 1, contentType:'PRATILIPI' } );
				ajax.go();
			}
			if( scope.pageNo < scope.pageCount && contentArray[scope.pageNo + 1] == null ) {
				ajax.params = JSON.stringify( { pratilipiId:${ pratilipiData.getId()?c }, pageNo:scope.pageNo + 1, contentType:'PRATILIPI' } );
				ajax.go();
			}
		}
		
		
		scope.decTextSize = function( e ) {
			var fontSize = parseInt( jQuery( '#PageContent-Reader-Content' ).css( 'font-size' ).replace( 'px', '' ) );
			var newFontSize = fontSize - 2;
			if( newFontSize < 10 )
				newFontSize = 10;
			jQuery( '#PageContent-Reader-Content' ).css( 'font-size', newFontSize + 'px' );
			setCookie( '${ contentSizeCookieName }', newFontSize + 'px', 365, '${ pratilipiData.getReaderPageUrl() }' );
			setCookie( '${ contentSizeCookieName }', newFontSize + 'px', 365, '${ pratilipiData.getWriterPageUrl() }' );
		};

		scope.incTextSize = function( e ) {
			var fontSize = parseInt( jQuery( '#PageContent-Reader-Content' ).css( 'font-size' ).replace( 'px', '' ) );
			var newFontSize = fontSize + 2;
			if( newFontSize > 30 )
				newFontSize = 30;
			jQuery( '#PageContent-Reader-Content' ).css( 'font-size', newFontSize + 'px' );
			setCookie( '${ contentSizeCookieName }', newFontSize + 'px', 365, '${ pratilipiData.getReaderPageUrl() }' );
			setCookie( '${ contentSizeCookieName }', newFontSize + 'px', 365, '${ pratilipiData.getWriterPageUrl() }' );
		};
		
	<#elseif pratilipiData.getContentType() == "IMAGE" >
		
		function loadImage( pageNo ) {
			var img = "<img src='/api.pratilipi/pratilipi/content?pratilipiId=${ pratilipiData.getId()?c }&pageNo=" + pageNo + "&contentType=IMAGE' />";
			$( img ).on( 'load', function() {
				contentArray[pageNo] = img;
				updateContent();
			});
			$( img ).on( 'error', function() {
				updateContent();
			});
		}
		
		function updateContent() {
			if( pageNoDisplayed == scope.pageNo ) {
				return;
			
			} else if( contentArray[scope.pageNo] == null ){
				loading( true );
				loadImage( scope.pageNo );
			
			} else {
				jQuery( '#PageContent-Reader-Content' ).fadeOut( 'fast', function() {
						jQuery( '#PageContent-Reader-Content' ).html( contentArray[scope.pageNo] );
						jQuery( '#PageContent-Reader-Content' ).fadeIn( 'fast', function() {
								loading( false );
						});
				});
				pageNoDisplayed = scope.pageNo;
			}
		}

		function prefetchContent() {
			if( scope.pageNo > 1 && contentArray[scope.pageNo - 1] == null ) {
				loadImage( scope.pageNo - 1 );
			}
			if( scope.pageNo < scope.pageCount && contentArray[scope.pageNo + 1] == null ) {
				loadImage( scope.pageNo + 1 );
			}
		}
		
		
		scope.decTextSize = function( e ) {
			var width = jQuery( '#PageContent-Reader-Content' ).width();
			var newWidth = width - 50;
			if( newWidth < 300 )
				newWidth = 300;
			jQuery( '#PageContent-Reader-Content' ).css( 'width', newWidth + 'px' );
			jQuery( '#PageContent-Reader-Overlay' ).css( 'width', newWidth + 'px' );
			setCookie( '${ contentSizeCookieName }', newWidth + 'px' );
	    };

		scope.incTextSize = function( e ) {
			var width = jQuery( '#PageContent-Reader-Content' ).width();
			var newWidth = width + 50;
			jQuery( '#PageContent-Reader-Content' ).css( 'width', newWidth + 'px' );
			jQuery( '#PageContent-Reader-Overlay' ).css( 'width', newWidth + 'px' );
			setCookie( '${ contentSizeCookieName }', newWidth + 'px' );
	    };
		
	</#if>
	
	
	recordPageChangeEvent = function( eventAction ){
		ga( 'send', 'event', ${ pratilipiData.getId()?c }, eventAction, 'Page ' + scope.pageNo );
	};


	addEventListener( 'template-bound', function( e ) {
		if( e.target != scope )
			return;
		updateContent();
		prefetchContent();
		recordPageChangeEvent( 'PageLoad' );
	});
	
</script>


<style>

	#PageContent-Reader-Content img {
		width:100%;
	}

	#PageContent-Reader-Overlay {
		position: absolute;
		top: 0px;
		left: 0px;
		height: 100%;
		width: 100%;
	}

</style>


<!-- PageContent :: Reader :: End -->
