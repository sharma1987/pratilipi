package com.claymus.pagecontent.blogpost;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.claymus.commons.client.InsufficientAccessException;
import com.claymus.commons.client.UnexpectedServerException;
import com.claymus.pagecontent.PageContentProcessor;
import com.claymus.pagecontent.PageContentRegistry;
import com.claymus.pagecontent.blogpost.shared.BlogPostContentData;

public class BlogPostContentProcessor extends PageContentProcessor<BlogPostContent> {

	@Override
	protected String generateHtml(
			BlogPostContent blogPostContent, HttpServletRequest request )
			throws InsufficientAccessException, UnexpectedServerException {
		
		BlogPostContentHelper blogPostContentHelper =
				(BlogPostContentHelper) PageContentRegistry.getPageContentHelper( BlogPostContentData.class );
		
		boolean showEditOptions =
				blogPostContentHelper.hasRequestAccessToAddContent( request );
		
		// Creating data model required for template processing
		Map<String, Object> dataModel = new HashMap<>();
		dataModel.put( "blogPostContent", blogPostContent );
		dataModel.put( "showEditOptions", showEditOptions );
		
		return super.processTemplate( dataModel, getTemplateName() );
	}

}
