package com.pratilipi.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.pagecontent.blogpost.BlogPostContent;
import com.pratilipi.common.type.Language;
import com.pratilipi.common.type.PageType;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;

public class PratilipiFilter implements Filter {
	
	private final Map<String, String> redirections = new HashMap<>();
	private final List<String> nonExistents = new LinkedList<>();
	
	private final Pattern oldPratilipiCoverUrlRegEx = Pattern.compile( "/resource\\.(book|poem|story|article|pratilipi)-cover/.*" );
	private final Pattern oldPratilipiReaderUrlRegEx = Pattern.compile( "/read/(book|poem|story|article|pratilipi)/.*" );
	private final Pattern validHostRegEx = Pattern.compile(
			"(www|hindi|gujarati|tamil|marathi|malayalam|bengali|telugu|kannada|m|hi|gu|ta|mr|bn|te|kn)(\\.gamma)?\\.pratilipi\\.com"
			+ "|"
			+ "((mark-4p14\\d\\.www|www)\\.prod-pratilipi|.+\\.(dev|devo)-pratilipi)\\.appspot\\.com"
			+ "|"
			+ "localhost|127.0.0.1" );
	
	{
		redirections.put( "/favicon.ico", "/theme.pratilipi/favicon.png" );
		redirections.put( "/apple-touch-icon.png", "/theme.pratilipi/favicon.png" );
		redirections.put( "/apple-touch-icon-precomposed.png", "/theme.pratilipi/favicon.png" );

		redirections.put( "/robots.txt", "/service.robots" );

		redirections.put( "/give-away", "/" );
		redirections.put( "/give-away/Gora.pdf", "/" );
		redirections.put( "/give-away/Kukurmutta.pdf", "/" );
		redirections.put( "/give-away/Ram_Ki_Shakti_Pooja.pdf", "/" );
		redirections.put( "/give-away/Utkrasht_Sahitya_1.pdf", "/" );
		redirections.put( "/give-away/Chandrakanta.pdf", "/book/5673309542809600" );

		redirections.put( "/about", "/about/pratilipi" );
		redirections.put( "/career", "/JoinTheGang" );
		
		redirections.put( "/event/gpv", "/event/gnayam-pada-varai" );
		redirections.put( "/event/gnayam&pada&varai", "/event/gnayam-pada-varai" );
		redirections.put( "/event/gnayam&pada&varai>", "/event/gnayam-pada-varai" );
		redirections.put( "/event/gnayam-pada-varai", "http://tamil.pratilipi.com/event/gnayam-pada-varai" );
		redirections.put( "/event/kk", "/event/kondaadapadaadha-kaadhalgal" );
		redirections.put( "/event/kondaadapadaadha-kaadhalgal", "http://tamil.pratilipi.com/event/kondaadapadaadha-kaadhalgal" );
		redirections.put( "/event/story-writing-contest", "http://tamil.pratilipi.com/event/story-writing-contest" );

		redirections.put( "/event/katha-kadi", "http://gujarati.pratilipi.com/event/katha-kadi" );
		redirections.put( "/event/my-daddy-strongest", "http://gujarati.pratilipi.com/event/my-daddy-strongest" );
		redirections.put( "/event/varta-re-varta", "http://gujarati.pratilipi.com/event/varta-re-varta" );
		redirections.put( "/event/tasvir-bole-che", "http://gujarati.pratilipi.com/event/tasvir-bole-che" );
		redirections.put( "/event/samarthini", "http://gujarati.pratilipi.com/event/samarthini" );
		redirections.put( "/event/kavya-spardha-2015", "http://gujarati.pratilipi.com/event/kavya-spardha-2015" );
		redirections.put( "/event/april-tasavir-bole-chhe", "http://gujarati.pratilipi.com/event/april-tasavir-bole-chhe" );
		redirections.put( "/event/mansi-lekhspardha", "http://gujarati.pratilipi.com/event/mansi-lekhspardha" );
		redirections.put( "/event/direct-encounter", "http://gujarati.pratilipi.com/event/direct-encounter" );
		redirections.put( "/event/vahali-ma-lekhan-spardha", "http://gujarati.pratilipi.com/event/vahali-ma-lekhan-spardha" );
		redirections.put( "/event/march-tasvir-bole-chhe", "http://gujarati.pratilipi.com/event/march-tasvir-bole-chhe" );
		redirections.put( "/event/upado-tamari-kalam-varta-spardha", "http://gujarati.pratilipi.com/event/upado-tamari-kalam-varta-spardha" );
		redirections.put( "/event/varta-mahotsav-2015", "http://gujarati.pratilipi.com/event/varta-mahotsav-2015" );

		redirections.put( "/books/gujarati",    "http://gujarati.pratilipi.com/search?q=books" );
		redirections.put( "/poems/gujarati",    "http://gujarati.pratilipi.com/poetry" );
		redirections.put( "/stories/gujarati",  "http://gujarati.pratilipi.com/short-stories" );
		redirections.put( "/articles/gujarati", "http://gujarati.pratilipi.com/non-fiction" );

		redirections.put( "/books/tamil",    "http://tamil.pratilipi.com/books" );
		redirections.put( "/poems/tamil",    "http://tamil.pratilipi.com/poems" );
		redirections.put( "/stories/tamil",  "http://tamil.pratilipi.com/stories" );
		redirections.put( "/articles/tamil", "http://tamil.pratilipi.com/articles" );

		nonExistents.add( "/pagecontent.userforms/undefined.cache.js" );
		nonExistents.add( "/pagecontent.pratilipi/undefined.cache.js" );
		nonExistents.add( "/pagecontent.reader/undefined.cache.js" );
		nonExistents.add( "/pagecontent.author/undefined.cache.js" );
	
	}
	

	@Override
	public void init( FilterConfig config ) throws ServletException { }

	@Override
	public void destroy() { }

	@Override
	public void doFilter( ServletRequest req, ServletResponse resp, FilterChain chain )
				throws IOException, ServletException {

		HttpServletRequest request = ( HttpServletRequest ) req;
		HttpServletResponse response = ( HttpServletResponse ) resp;
		
		String host = request.getServerName();
		String requestUri = request.getRequestURI();
		String userAgent = request.getHeader( "user-agent" );

		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( request );
		Page page = dataAccessor.getPage(requestUri);
		
		if( userAgent != null && userAgent.startsWith( "libwww-perl" ) ) {
			response.setStatus( HttpServletResponse.SC_NO_CONTENT );
			
			
		} else if( nonExistents.contains( requestUri ) ) {
			response.setStatus( HttpServletResponse.SC_NOT_FOUND );

		
		} else if( redirections.get( requestUri ) != null ) {
			response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
			response.setHeader( "Location", redirections.get( requestUri ) );

			
		} else if( !validHostRegEx.matcher( host ).matches() ) { // Redirecting to www.pratilipi.com
			response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
			String queryString = request.getQueryString();
			if( queryString == null || queryString.isEmpty() )
				response.setHeader( "Location", ( request.isSecure() ? "https:" : "http:" ) + "//www.pratilipi.com" + requestUri );
			else
				response.setHeader( "Location", ( request.isSecure() ? "https:" : "http:" ) + "//www.pratilipi.com" + requestUri + "?" + request.getQueryString() );

			
		} else if( page != null && page.getType().equals( PageType.PRATILIPI.toString() ) ){ // Redirecting to new Pratilipi website
			Long pratilipiId = page.getPrimaryContentId();
			Pratilipi pratilipi = dataAccessor.getPratilipi( pratilipiId );
			String uri = page.getUriAlias() == null ? page.getUri() : page.getUriAlias();
			if( pratilipi.getLanguage() == Language.TAMIL ){
				response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
				response.setHeader( "Location", "http://tamil.pratilipi.com" + uri);
			} else if( pratilipi.getLanguage() == Language.GUJARATI ){
				response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
				response.setHeader( "Location", "http://gujarati.pratilipi.com" + uri);
			} else {
				chain.doFilter( request, response );
			}
		} else if( oldPratilipiCoverUrlRegEx.matcher( requestUri ).matches() ) { // Redirecting to new Pratilipi cover url
			response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
			response.setHeader( "Location", requestUri
					.replaceFirst( "/resource.", ( request.isSecure() ? "https:" : "http:" ) + "//10." + ClaymusHelper.getSystemProperty( "domain.cdn" ) + "/" )
					.replaceFirst( "book|poem|story|article", "pratilipi" )
					.replaceFirst( "original|300", "150" ) );


		} else if( requestUri.startsWith( "/resource.author-image/original/" ) ) { // Redirecting to new Author image url
			response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
			response.setHeader( "Location", requestUri
					.replaceFirst( "/resource.", ( request.isSecure() ? "https:" : "http:" ) + "//10." + ClaymusHelper.getSystemProperty( "domain.cdn" ) + "/" )
					.replaceFirst( "original", "150" ) );
		
			
		} else if( oldPratilipiReaderUrlRegEx.matcher( requestUri ).matches() ) { // Redirecting to new Pratilipi reader url
			response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
			response.setHeader( "Location", requestUri.replaceFirst( "/(book|poem|story|article|pratilipi)/", "?id=" ) );

			
		} else if( requestUri.equals( "/read" ) ) {
			String pratilipiId = request.getParameter( "id" );
			if( pratilipiId == null || pratilipiId.isEmpty() ) {
				response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
				response.setHeader( "Location", ( request.isSecure() ? "https:" : "http:" ) + "//www.pratilipi.com" );
			} else {
				chain.doFilter( request, response );
			}
		
			
		} else if( requestUri.equals( "/_ah/start" ) || requestUri.equals( "/_ah/stop" ) ) {
			response.setStatus( HttpServletResponse.SC_NO_CONTENT );
			
			
		} else if( requestUri.startsWith( "/blog/" ) ) { // Redirecting author interviews to /author-interview/*
			String blogIdStr = requestUri.substring( 6 );
			if( ! blogIdStr.equals( "new" ) ) {
				BlogPostContent blogPostContent = (BlogPostContent) dataAccessor.getPageContent( Long.parseLong( blogIdStr ) );
				if( blogPostContent.getBlogId() == 5197509039226880L ) {
					response.setStatus( HttpServletResponse.SC_MOVED_PERMANENTLY );
					response.setHeader( "Location", "/author-interview/" + blogIdStr );
				} else {
					chain.doFilter( request, response );
				}
			} else {
				chain.doFilter( request, response );
			}
			
			
		} else {
			chain.doFilter( request, response );
		}

		
		dataAccessor.destroy();
	}

}
