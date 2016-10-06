var MainWriterPanel = function() {
	this.$save_button = $( "[data-behaviour='save_button']" );
	this.$preview_button = $( "[data-behaviour='preview_button']" );
	this.$publish_button = $( "[data-behaviour='publish_button']" );
	this.$back_button = $( "[data-behaviour='back_button']" );
};


MainWriterPanel.prototype.init = function() {
    this.addAffixClasses();
    this.setWrappersHeight();
    this.checkBookLanguage();
    this.initializeGlobalVariables();
    this.$panel_container = $(".panel");
    this.preventFormSubmission();
    this.preventBackspaceDefaultAction();
    
    var pagination_container = $("#pagination");
    this.pagination_object = new Pagination( pagination_container, this );
    this.pagination_object.init();

    var toc_container = $( "#toc_container" );
    this.table_of_contents_object = new TableOfContents( toc_container, this.pagination_object, this );
    this.table_of_contents_object.init();

    var content_container = $( "#chapter-content" );
    this.content_object = new Content( content_container, this );
    this.content_object.init();
    
    var chapter_name_container = $( "#subtitle" );
    this.chapter_name_object = new ChapterName( chapter_name_container, this );
    this.chapter_name_object.init();

    var editor_container = $( "#editor");
    this.editor_object = new Editor( editor_container, this.content_object );
    this.editor_object.init();
    
    var publish_modal_container = $("#publishModal");
    this.publish_modal_object = new PublishModal( publish_modal_container );
    this.publish_modal_object.init();
    
    this.hideProgressBarOnMobileFocus();
    this.initializeData();
    
    //add button listeners
    this.attachActionButtonListeners();
    this.initializeAutosave();
    
};

MainWriterPanel.prototype.addAffixClasses = function() {
    $('#editor').affix({
        offset: {
            top: 50,
            // bottom: 0
        }
    });
    $('#header1').affix({
        offset: {
            top: 50,
            // bottom: 0
        }
    }); 

    $('#header1').on('affixed.bs.affix', function(e) {
        $("#chapter-content").css("margin-top","50px");
    });
    $('#header1').on('affixed-top.bs.affix', function(e) {
        $("#chapter-content").css("margin-top","10px");
    });
    //make textarea empty to show placeholder.
    $("#chapter-content").empty();
                    
};

MainWriterPanel.prototype.setWrappersHeight = function() {
    $(".editor-wrapper").height($("#editor").height());
    $(".header-wrapper").height($("#header1").height());
     $('[data-toggle="popover"]').popover();
};

MainWriterPanel.prototype.checkBookLanguage = function() {
	<#if pratilipi.getLanguage() != language >
		window.location = ( "http://${pratilipi.getLanguage()?lower_case}"  + ".pratilipi.com" + window.location.pathname + window.location.search );
	</#if>
};

MainWriterPanel.prototype.hideProgressBarOnMobileFocus = function() {
     if(navigator.userAgent.indexOf('Android') > -1 ){
	     this.lastWindowHeight = $(window).height();
	     this.lastWindowWidth = $(window).width();
	     var _this = this;
	     
	     $(window).resize(function () {
		    if ($("#subtitle,#chapter-content").is(":focus")) {
		        var keyboardIsOn =
		           ((_this.lastWindowWidth == $(window).width()) && (_this.lastWindowHeight > $(window).height()));
		    }   
		    if(keyboardIsOn){
				$("#pagination").hide();
		    } else{
				$("#pagination").show();
		    }
		 }); 
     }
};

MainWriterPanel.prototype.initializeGlobalVariables = function() {
	this.pratilipiJson = ${ pratilipiJson };
	var indexJson = ${ indexJson };
	if ( indexJson ) {
		this.index = ${ indexJson };
		this.currChapter = 1;
	}
	else {
		this.currChapter = 0;	
	}	
};

MainWriterPanel.prototype.initializeData = function() {
	//if indexJson is not null, book exists, get first chapter and populate the index too.
	var indexJson = ${ indexJson };
	//var indexJson = [{"chapterNo":1,"chapterTitle":"Radhika"},{"chapterNo":2}];
	if ( indexJson ) {
		//get first chapter and populate it in the writer
		this.getChapter( 1 );
		this.table_of_contents_object.populateIndex( indexJson );
	}
	else {
		//make a new chapter call asychrolously and populate the index
		this.addNewChapter();
		console.log("indexjson doesnt exists");
	}
};

MainWriterPanel.prototype.attachActionButtonListeners = function() {
	var _this = this;
	this.$save_button.on('click', function() {
		$("#header1").addClass("small-spinner");
		_this.$save_button.attr('disabled', 'disabled');
		_this.saveChapter();
	} );
	
	this.$publish_button.on('click', function() {
		_this.saveChapter( true );
	} );
	
	this.$preview_button.on('click', function() {
		_this.saveChapter( true );

	} );
	
	this.$back_button.on('click', function() {
		_this.saveChapter( true );
	} );
	
};

MainWriterPanel.prototype.getChapter = function( chapterNum ) {
	var _this = this;
    $.ajax({type: "GET",
        url: "/api/pratilipi/content",
        data: {
        	pratilipiId: ${ pratilipiId?c },
        	chapterNo: chapterNum,
        	asHtml: true,
        	pageNo:1
        },
        success:function(response){
        	
        	var parsed_data = jQuery.parseJSON( response );
			_this.populateContent( parsed_data );
			_this.pagination_object.setProgressPage();
			_this.editor_object.resetExecCommandIcons();
		},
        fail:function(response){
        	var message = jQuery.parseJSON( response.responseText );
			alert(message);
		}			    		
		
	});	
};

MainWriterPanel.prototype.populateContent = function( parsed_data ) {
	this.content_object.populateContent( parsed_data.content );
	this.chapter_name_object.change_name( parsed_data.chapterTitle );
};

MainWriterPanel.prototype.addNewChapter = function( chapterNum ) {
	var _this = this;
	var ajaxData = { pratilipiId: ${ pratilipiId?c } };
	if ( chapterNum !== undefined ){
    	ajaxData.chapterNo = chapterNum;   
  	}
    $.ajax({type: "POST",
        url: "/api/pratilipi/content/chapter/add",
        data: ajaxData,
        success:function(response){
        	
        	var index = jQuery.parseJSON( response ).index;
        	_this.index = index;
        	//increase current chapter and reset 
			_this.currChapter++;
			_this.resetContent();
			
		},
        fail:function(response){
        	var message = jQuery.parseJSON( response.responseText );
			alert(message);
		}			    		
		
	});	
};

MainWriterPanel.prototype.removeChapter = function( chapterNum ) {
	var _this = this;
	//first and only chapter, dont remove, just reset content and save.
	if( this.index.length == 1 ) {
		this.resetContent();
		this.saveChapter( true );
		//update index too in save chapter for all cases - remember
	}
	else {
		var ajaxData = { pratilipiId: ${ pratilipiId?c } };
	    	ajaxData.chapterNo = chapterNum;
	    $.ajax({type: "POST",
	        url: " /api/pratilipi/content/chapter/delete",
	        data: ajaxData,
	        success:function(response){
	        
	        	var index = jQuery.parseJSON( response ).index;
	        	_this.index = index;
	        	if( _this.currChapter >= chapterNum ) {
	        		if( _this.currChapter == 1 ) {
	        			_this.setCurrentPage( 1 );	
	        		}
	        		else {
	        			_this.setCurrentPage( _this.currChapter - 1 );
	        		}
	        	}	        	
	        	_this.table_of_contents_object.populateIndex( _this.index );
	        	// check if we need to change the page number
	        	
				
			},
	        fail:function(response){
	        	var message = jQuery.parseJSON( response.responseText );
				alert(message);
			}			    		
			
		});	
	}
};

MainWriterPanel.prototype.resetContent = function() {
	this.table_of_contents_object.populateIndex( this.index );
	this.content_object.reset();
	this.chapter_name_object.reset();
};

MainWriterPanel.prototype.saveChapter = function( autosaveFlag ) {
	var _this = this;
	if( this.content_object.hasEmptyText() ) {
		this.content_object.wrapInParagraph();
	}
	var ajaxData = { pratilipiId: ${ pratilipiId?c },
					chapterNo: this.currChapter,
					chapterTitle: this.chapter_name_object.getTitle(),
					content: this.content_object.getContent(),
					pageNo: 1
				   };
    $.ajax({type: "POST",
        url: " /api/pratilipi/content",
        data: ajaxData,
        success:function(response){
			toastr.options = {
			positionClass: 'toast-top-center',
			"timeOut": "1100"
			};
			if( !autosaveFlag ) {
				$("#header1").removeClass("small-spinner");
				_this.$save_button.removeAttr("disabled");
				toastr.success('${ _strings.writer_changes_saved }');
			}	
			var title = jQuery.parseJSON( response ).chapterTitle;
			_this.table_of_contents_object.changeCurrentChapterName( _this.currChapter, title );

		},
        fail:function(response){
        	var message = jQuery.parseJSON( response.responseText );
        	_this.$panel_container.find(".spinner").remove();
        	$("#header1").removeClass("small-spinner");
        	_this.$save_button.removeAttr("disabled");
			alert(message);
		}			    		
		
	});	
};

MainWriterPanel.prototype.setCurrentPage = function( chapterNum ) {
	this.currChapter = chapterNum;
	this.getChapter( chapterNum );
};

MainWriterPanel.prototype.initializeAutosave = function() {
	var _this = this;
	this.chapter_name_object.$chapter_name_container.keyup( $.debounce( 300, _this.saveChapter.bind(this, true) ) );
	this.content_object.$content_container.keyup( $.debounce( 1500, _this.saveChapter.bind(this, true) ) );
	setInterval(function () {
     	_this.saveChapter( true );
 	}, 60000);
};

MainWriterPanel.prototype.preventFormSubmission = function() {
	this.$panel_container.find("form").on("submit", function(e) {
		e.preventDefault();
	});
};

MainWriterPanel.prototype.changeName = function( name ) {
	this.publish_modal_object.setBookName( name );
	this.table_of_contents_object.changeName( name );
};

MainWriterPanel.prototype.preventBackspaceDefaultAction = function() {
	$(document).on("keydown", function (e) {
	    if (e.which === 8 && !$(e.target).is("input:not([readonly]):not([type=radio]):not([type=checkbox]), textarea, [contentEditable], [contentEditable=true]")) {
	        e.preventDefault();
	    }
	});
};