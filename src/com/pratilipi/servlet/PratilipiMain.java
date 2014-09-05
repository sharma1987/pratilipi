package com.pratilipi.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.data.transfer.PageContent;
import com.claymus.data.transfer.WebsiteWidget;
import com.claymus.module.pagecontent.html.HtmlContent;
import com.claymus.module.pagecontent.html.HtmlContentFactory;
import com.claymus.module.websitewidget.footer.FooterWidget;
import com.claymus.module.websitewidget.footer.FooterWidgetFactory;
import com.claymus.module.websitewidget.header.HeaderWidget;
import com.claymus.module.websitewidget.header.HeaderWidgetFactory;
import com.claymus.servlet.ClaymusMain;
import com.pratilipi.commons.shared.PratilipiHelper;
import com.pratilipi.commons.shared.PratilipiType;
import com.pratilipi.module.pagecontent.booklist.BookListFactory;
import com.pratilipi.pagecontent.authors.AuthorsContentFactory;
import com.pratilipi.pagecontent.book.BookContentFactory;
import com.pratilipi.pagecontent.books.BooksContentFactory;
import com.pratilipi.pagecontent.genres.GenresContentFactory;
import com.pratilipi.pagecontent.languages.LanguagesContentFactory;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentFactory;
import com.pratilipi.pagecontent.pratilipis.PratilipisContentFactory;

@SuppressWarnings("serial")
public class PratilipiMain extends ClaymusMain {
	
	@SuppressWarnings("unused")
	private static final Logger logger = 
			Logger.getLogger( PratilipiMain.class.getName() );

	static {
		PAGE_CONTENT_REGISTRY.register( BookListFactory.class );
		PAGE_CONTENT_REGISTRY.register( BookContentFactory.class );
		PAGE_CONTENT_REGISTRY.register( BooksContentFactory.class );
		
		PAGE_CONTENT_REGISTRY.register( PratilipiContentFactory.class );
		PAGE_CONTENT_REGISTRY.register( PratilipisContentFactory.class );
		PAGE_CONTENT_REGISTRY.register( LanguagesContentFactory.class );
		PAGE_CONTENT_REGISTRY.register( AuthorsContentFactory.class );
		PAGE_CONTENT_REGISTRY.register( GenresContentFactory.class );
	}


	@Override
	protected String getTemplateName() {
		return "com/pratilipi/servlet/PratilipiTemplate.ftl";
	}

	@Override
	protected List<PageContent> getPageContentList(
			HttpServletRequest request ) throws IOException {
	
		List<PageContent> pageContentList
				= super.getPageContentList( request );
		
		// Home pages
		String requestUri = request.getRequestURI();
		if( requestUri.equals( "/" ) )
			pageContentList.add( BookListFactory.newBookList() );

		else if( requestUri.equals( "/books" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.BOOK ) );
		
		else if( requestUri.equals( "/poems" ) )
			pageContentList.add( PratilipisContentFactory.newPratilipisContent( PratilipiType.POEM ) );
		
		else if( requestUri.equals( "/languages" ) )
			pageContentList.add( LanguagesContentFactory.newLanguagesContent() );

		else if( requestUri.equals( "/authors" ) )
			pageContentList.add( AuthorsContentFactory.newAuthorsContent() );

		else if( requestUri.equals( "/genres" ) )
			pageContentList.add( GenresContentFactory.newGenresContent() );


		// Individual item's pages
		else if( requestUri.startsWith( PratilipiHelper.URL_BOOK_PAGE ) )
			pageContentList.add( PratilipiContentFactory.newPratilipiContent( Long.parseLong( requestUri.substring( PratilipiHelper.URL_BOOK_PAGE.length() ) ), PratilipiType.BOOK ) );

		else if( requestUri.startsWith( PratilipiHelper.URL_POEM_PAGE ) )
			pageContentList.add( PratilipiContentFactory.newPratilipiContent( Long.parseLong( requestUri.substring( PratilipiHelper.URL_POEM_PAGE.length() ) ), PratilipiType.POEM ) );

		
		// Static pages
		// Migrate these PageContents to DataStore
		else if( requestUri.equals( "/give-away" ) )
			pageContentList.add( generateHtmlContentFromFile( "WEB-INF/classes/com/pratilipi/servlet/content/GiveAwayPageContent.ftl" ) );

		else if( requestUri.equals( "/contact" ) )
			pageContentList.add( generateHtmlContentFromFile( "WEB-INF/classes/com/pratilipi/servlet/content/ContactPageContent.ftl" ) );
			
		else if( requestUri.equals( "/faq" ) )
			pageContentList.add( generateHtmlContentFromFile( "WEB-INF/classes/com/pratilipi/servlet/content/FaqPageContent.ftl" ) );
			
		else if( requestUri.equals( "/about/pratilipi" ) )
			pageContentList.add( generateHtmlContentFromFile( "WEB-INF/classes/com/pratilipi/servlet/content/AboutPratilipiPageContent.ftl" ) );

		else if( requestUri.equals( "/about/team" ) )
			pageContentList.add( generateHtmlContentFromFile( "WEB-INF/classes/com/pratilipi/servlet/content/AboutTeamPageContent.ftl" ) );
		
		else if( requestUri.equals( "/about/the-founding-readers" ) )
			pageContentList.add( generateHtmlContentFromFile( "WEB-INF/classes/com/pratilipi/servlet/content/AboutFoundingReadersPageContent.ftl" ) );
			
		
		return pageContentList;
	}
	
	@Override
	protected List<WebsiteWidget> getWebsiteWidgetList(
			HttpServletRequest request ) {
		
		String requestUri = request.getRequestURI();
		List<WebsiteWidget> websiteWidgetList
				= super.getWebsiteWidgetList( request );

		ClaymusHelper claymusHelper = new ClaymusHelper( request );
		
		if( ! requestUri.equals( "/home" ) ) {
			HeaderWidget headerWidget = HeaderWidgetFactory.newHeaderWidget();
			headerWidget.setBrand( "Pratilipi" );
			headerWidget.setTagLine( "you become what you read ..." );

			headerWidget.setRightNavItems( new Object[][] {
					{ "Give Away", "/give-away", null },
					{ "Books", "/books", null },
					{ "Poems", "/poems", null },
					{ "Languages", "/languages", null },
					{ "Authors", "/authors" },
					{ "Genres", "/genres" },
					{ "About", null, new String[][] {
							{ "Pratilipi", "/about/pratilipi" },
							{ "Team", "/about/team" },
							{ "The Founding Readers", "/about/the-founding-readers" }}},
					{ claymusHelper.isUserLoggedIn() ? "Sign Out" : "Sign In", claymusHelper.isUserLoggedIn() ? "#signout" : "#signin", null },
					{ "Sign up", "#signup", null },
			});
			headerWidget.setPosition( "HEADER" );
			websiteWidgetList.add( headerWidget );
		}
		
		FooterWidget footerWidget = FooterWidgetFactory.newFooterWidget();
		footerWidget.setLinks( new String[][] {
				{ "About", "/about" },
				{ "Contact", "/contact" },
				{ "FAQs", "/faq" }
		} );
		footerWidget.setCopyrightNote( "&copy; 2014 pratilipi.com" );
		footerWidget.setPosition( "FOOTER" );
		websiteWidgetList.add( footerWidget );
		
		return websiteWidgetList;
	}

	private HtmlContent generateHtmlContentFromFile(
			String fileName ) throws IOException {
		
		File file = new File( fileName );
		List<String> lines = FileUtils.readLines( file, "UTF-8" );
		String html = "";
		for( String line : lines )
			html = html + line;
		HtmlContent htmlContent = HtmlContentFactory.newHtmlContent();
		htmlContent.setHtml( html );
		return htmlContent;
	}
	
}
