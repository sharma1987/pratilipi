package com.pratilipi.data;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.util.GsonLongDateAdapter;
import com.pratilipi.data.type.BatchProcessDoc;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.CommentDoc;
import com.pratilipi.data.type.InitBannerDoc;
import com.pratilipi.data.type.InitDoc;
import com.pratilipi.data.type.PratilipiContentDoc;
import com.pratilipi.data.type.PratilipiGoogleAnalyticsDoc;
import com.pratilipi.data.type.PratilipiMetaDoc;
import com.pratilipi.data.type.PratilipiReviewsDoc;
import com.pratilipi.data.type.UserPratilipiDoc;
import com.pratilipi.data.type.doc.BatchProcessDocImpl;
import com.pratilipi.data.type.doc.CommentDocImpl;
import com.pratilipi.data.type.doc.InitBannerDocImpl;
import com.pratilipi.data.type.doc.InitDocImpl;
import com.pratilipi.data.type.doc.PratilipiContentDocImpl;
import com.pratilipi.data.type.doc.PratilipiGoogleAnalyticsDocImpl;
import com.pratilipi.data.type.doc.PratilipiMetaDocImpl;
import com.pratilipi.data.type.doc.PratilipiReviewsDocImpl;
import com.pratilipi.data.type.doc.UserPratilipiDocImpl;

public class DocAccessorImpl implements DocAccessor {

	private final BlobAccessor blobAccessor;
	
	
	public DocAccessorImpl( BlobAccessor blobAccessor ) {
		this.blobAccessor = blobAccessor;
	}
	
	
	private static final Logger logger =
			Logger.getLogger( DocAccessorImpl.class.getName() );

	
	// BatchProcess Doc
	
	public BatchProcessDoc newBatchProcessDoc() {
		return new BatchProcessDocImpl();
	}
	
	public BatchProcessDoc getBatchProcessDoc( Long processId ) throws UnexpectedServerException {
		if( processId != null )
			return _get( "batch-process/" + processId, BatchProcessDocImpl.class );
		return null;
	}
	
	public void save( Long processId, BatchProcessDoc processDoc ) throws UnexpectedServerException {
		if( processId != null )
			_save( "batch-process/" + processId, processDoc );
	}
	
	
	// UserPratilipi Doc
	
	public UserPratilipiDoc newUserPratilipiDoc() {
		return new UserPratilipiDocImpl();
	}
	
	
	// Comment Doc
	
	public CommentDoc newCommentDoc() {
		return new CommentDocImpl();
	}
	


	@Override
	public InitDoc newInitDoc() {
		return new InitDocImpl();
	}


	@Override
	public InitBannerDoc newInitBannerDoc( String bannerId, String title, String actionUrl ) {
		return new InitBannerDocImpl( bannerId, title, actionUrl );
	}
	

	// Pratilipi Content Doc
	public PratilipiContentDoc newPratilipiContentDoc() {
		return new PratilipiContentDocImpl();
	}
	
	public PratilipiContentDoc getPratilipiContentDoc( Long pratilipiId ) throws UnexpectedServerException {
		if( pratilipiId != null )
			return _get( "pratilipi/" + pratilipiId + "/content", PratilipiContentDocImpl.class );
		return null;
	}
	
	@Override
	public void save( Long pratilipiId, PratilipiContentDoc contentDoc ) throws UnexpectedServerException {
		if( pratilipiId != null )
			_save( "pratilipi/" + pratilipiId + "/content", contentDoc );
	}

	
	// Pratilipi Meta Doc
	public PratilipiMetaDoc newPratilipiMetaDoc() {
		return new PratilipiMetaDocImpl();
	}
	
	public PratilipiMetaDoc getPratilipiMetaDoc( Long pratilipiId ) throws UnexpectedServerException {
		if( pratilipiId != null )
			return _get( "pratilipi/" + pratilipiId + "/meta", PratilipiMetaDocImpl.class );
		return null;
	}
	
	@Override
	public void save( Long pratilipiId, PratilipiMetaDoc metaDoc ) throws UnexpectedServerException {
		if( pratilipiId != null )
			_save( "pratilipi/" + pratilipiId + "/meta", metaDoc );
	}

	
	// Pratilipi Reviews Doc
	
	@Override
	public PratilipiReviewsDoc newPratilipiReviewsDoc() {
		return new PratilipiReviewsDocImpl();
	}
	
	@Override
	public PratilipiReviewsDoc getPratilipiReviewsDoc( Long pratilipiId ) throws UnexpectedServerException {
		if( pratilipiId != null )
			return _get( "pratilipi/" + pratilipiId + "/reviews", PratilipiReviewsDocImpl.class );
		return null;
	}
	
	@Override
	public void save( Long pratilipiId, PratilipiReviewsDoc reviewsDoc ) throws UnexpectedServerException {
		if( pratilipiId != null )
			_save( "pratilipi/" + pratilipiId + "/reviews", reviewsDoc );
	}
	
	
	// Pratilipi GoogleAnalytics Doc
	
	@Override
	public PratilipiGoogleAnalyticsDoc getPratilipiGoogleAnalyticsDoc( Long pratilipiId ) throws UnexpectedServerException {
		if( pratilipiId != null )
			return _get( "pratilipi/" + pratilipiId + "/googleAnalytics", PratilipiGoogleAnalyticsDocImpl.class );
		return null;
	}
	
	@Override
	public void save( Long pratilipiId, PratilipiGoogleAnalyticsDoc gaDoc ) throws UnexpectedServerException {
		if( pratilipiId != null )
			_save( "pratilipi/" + pratilipiId + "/googleAnalytics", gaDoc );
	}
	
	
	private <T> T _get( String docPath, Class<T> clazz ) throws UnexpectedServerException {
		BlobEntry blobEntry = blobAccessor.getBlob( docPath );
		try {
			if( blobEntry == null )
				return clazz.newInstance();
			else
				return new GsonBuilder()
						.registerTypeAdapter( Date.class, new GsonLongDateAdapter() ).create()
						.fromJson( new String( blobEntry.getData(), "UTF-8" ), clazz );
		} catch( InstantiationException | IllegalAccessException | JsonSyntaxException | UnsupportedEncodingException e) {
			logger.log( Level.SEVERE, e.getMessage() );
			throw new UnexpectedServerException();
		}
	}
	
	private <T> void _save( String docPath, T doc ) throws UnexpectedServerException {
		try {
			byte[] blobData = new GsonBuilder()
					.registerTypeAdapter( Date.class, new GsonLongDateAdapter() ).create()
					.toJson( doc )
					.getBytes( "UTF-8" );
			BlobEntry blobEntry = blobAccessor.newBlob( docPath, blobData, "application/json" );
			blobAccessor.createOrUpdateBlob( blobEntry );
		} catch( UnsupportedEncodingException e ) {
			logger.log( Level.SEVERE, e.getMessage() );
			throw new UnexpectedServerException();
		}
	}


}
