package com.pratilipi.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.NotificationState;
import com.pratilipi.common.type.RequestParameter;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.Notification;

public class NotificationFilter implements Filter {

	private static final Logger logger =
			Logger.getLogger( NotificationFilter.class.getName() );

	@Override
	public void init( FilterConfig filterConfig ) throws ServletException {	}

	@Override
	public void destroy() {	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = ( HttpServletRequest ) req;
		HttpServletResponse response = ( HttpServletResponse ) resp;

		Long notificationId = null;
		/* Updating notification state as READ*/
		if( request.getParameter( RequestParameter.NOTIFICATION_ID.getName() ) != null )
			notificationId = Long.parseLong( req.getParameter( RequestParameter.NOTIFICATION_ID.getName() ) );

		if( notificationId != null ) {
			DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
			Notification notification = dataAccessor.getNotification( notificationId );

			Long userId = AccessTokenFilter.getAccessToken().getUserId();
			logger.log( Level.INFO, "UserId from AccessToken = " + userId );
			logger.log( Level.INFO, "UserId from Notification Entity = " + notification.getUserId() );

			/* Guest user */
			if( userId == null || userId == 0L )  {
				dispatchResposne( response, new InvalidArgumentException( "User is not logged in to access notification." ) );
				return;
			}

			/* user doesn't have access */
			if( ! userId.equals( notification.getUserId() ) ) {
				dispatchResposne( response, new InvalidArgumentException( "User does not have access to notification." ) );
				return;
			}

			notification.setState( NotificationState.READ );
			dataAccessor.createOrUpdateNotification( notification );
		}

		chain.doFilter( request, response );

	}

	private void dispatchResposne( HttpServletResponse response, Throwable ex )
			throws IOException {
		
		response.setCharacterEncoding( "UTF-8" );
		PrintWriter writer = response.getWriter();
		if( ex instanceof InvalidArgumentException )
			response.setStatus( HttpServletResponse.SC_BAD_REQUEST );
		else if( ex instanceof InsufficientAccessException )
			response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
		else if( ex instanceof UnexpectedServerException )
			response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		else
			response.setStatus( HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
		writer.println( ex.getMessage() );
		writer.close();
	}

}