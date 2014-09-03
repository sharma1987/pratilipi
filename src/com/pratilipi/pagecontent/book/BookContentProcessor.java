package com.pratilipi.pagecontent.book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.claymus.commons.server.ClaymusHelper;
import com.claymus.module.pagecontent.PageContentProcessor;
import com.pratilipi.commons.shared.PratilipiHelper;
import com.pratilipi.commons.shared.UserReviewState;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Author;
import com.pratilipi.data.transfer.Book;
import com.pratilipi.data.transfer.UserBook;

public class BookContentProcessor extends PageContentProcessor<BookContent> {

	public static String ACCESS_ID_BOOK_VIEW = "book_view";
	public static String ACCESS_ID_BOOK_ADD = "book_add";
	public static String ACCESS_ID_BOOK_UPDATE = "book_update";
	
	public static String ACCESS_ID_BOOK_REVIEW_VIEW = "book_review_view";
	public static String ACCESS_ID_BOOK_REVIEW_ADD = "book_review_add";

	
	@Override
	public String getHtml( BookContent bookContent,
			HttpServletRequest request, HttpServletResponse response ) {

		ClaymusHelper claymusHelper = new ClaymusHelper( request );
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();

		if( bookContent.getBookId() != null ) {
			Book book = dataAccessor.getBook( bookContent.getBookId() );
			Author author = dataAccessor.getAuthor( book.getAuthorId() );
			UserBook userBook = dataAccessor.getUserBook(
					claymusHelper.getCurrentUserId(), book.getId() );
			List<UserBook> reviewList = dataAccessor.getUserBookList( book.getId() );
	
			dataAccessor.destroy();
			
			Map<String, Object> dataModel = new HashMap<>();
			dataModel.put( "book", book );
			dataModel.put( "author", author );
			dataModel.put( "reviewList", reviewList );
			
			dataModel.put( "bookCoverUrl", PratilipiHelper.URL_BOOK_COVER + book.getId() );
			dataModel.put( "bookHomeUrl", PratilipiHelper.URL_BOOK_PAGE + book.getId() );
			dataModel.put( "authorHomeUrl", PratilipiHelper.URL_AUTHOR_PAGE + book.getId() );
			
			dataModel.put( "showEditOptions",
					( claymusHelper.getCurrentUserId() == book.getAuthorId() && claymusHelper.hasUserAccess( ACCESS_ID_BOOK_ADD, false ) )
					|| claymusHelper.hasUserAccess( ACCESS_ID_BOOK_UPDATE, false ) );
			
			dataModel.put( "showAddReviewOption",
					claymusHelper.getCurrentUserId() != book.getAuthorId()
					&& ( userBook == null || userBook.getReviewState() == UserReviewState.NOT_SUBMITTED )
					&& claymusHelper.hasUserAccess( ACCESS_ID_BOOK_REVIEW_ADD, false ) );
			
			return super.processTemplate(
					dataModel,
					"com/pratilipi/pagecontent/book/BookContent.ftl" );
		
		} else if( claymusHelper.hasUserAccess( ACCESS_ID_BOOK_ADD, false ) ) {
			return super.processTemplate(
					null,
					"com/pratilipi/pagecontent/book/BookContentNew.ftl" );
			
		} else {
			return "";
		}
		
	}
	
}
