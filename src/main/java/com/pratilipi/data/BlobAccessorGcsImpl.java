package com.pratilipi.data;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.tools.cloudstorage.GcsFileMetadata;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFileOptions.Builder;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.ListOptions;
import com.google.appengine.tools.cloudstorage.ListResult;
import com.google.appengine.tools.cloudstorage.RetryParams;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.type.gcs.BlobEntryGcsImpl;

public class BlobAccessorGcsImpl implements BlobAccessor {

	private static final Logger logger =
			Logger.getLogger( BlobAccessorGcsImpl.class.getName() );

	private static final GcsService gcsService =
			GcsServiceFactory.createGcsService( RetryParams.getDefaultInstance() );
	
	private final String bucketName;
	
	
	public BlobAccessorGcsImpl( String bucketName ) {
		this.bucketName = bucketName;
	}
	
	
	@Override
	public BlobEntry newBlob( String fileName ) {
		return new BlobEntryGcsImpl( fileName );
	}

	@Override
	public BlobEntry newBlob( String fileName, byte[] data, String mimeType ) {
		return new BlobEntryGcsImpl( fileName, data, mimeType );
	}

	@Override
	public List<String> getNameList( String prefix ) throws IOException {
		ListOptions.Builder options = new ListOptions.Builder();
		options.setPrefix( prefix );
		
		ListResult result = gcsService.list( bucketName, options.build() );
		
		List<String> fileNameList = new LinkedList<>();
		while( result.hasNext() ) {
			String fileName = result.next().getName();
			if( ! fileName.equals( prefix ) )
				fileNameList.add( fileName.substring( prefix.length() ) );
		}
		return fileNameList;
	}

	@Override
	public BlobEntry getBlob( String fileName ) throws UnexpectedServerException {
		GcsFilename gcsFileName
				= new GcsFilename( bucketName, fileName );
		
		try {

			GcsFileMetadata gcsFileMetadata
					= gcsService.getMetadata( gcsFileName );
			
			if( gcsFileMetadata == null )
				return null;
			
			if( gcsFileMetadata.getLength() == 0 )
				return null;
			
			GcsInputChannel gcsInputChannel
					= gcsService.openReadChannel( gcsFileName, 0 );
			
			ByteBuffer byteBuffer
					= ByteBuffer.allocate( (int) gcsFileMetadata.getLength() );
			gcsInputChannel.read( byteBuffer );
		
			return new BlobEntryGcsImpl( byteBuffer, gcsFileMetadata );
			
		} catch( IOException ex ) {
			logger.log( Level.INFO, "Failed to fetch blob with name '" + fileName + "'", ex );
			throw new UnexpectedServerException();
		}
		
	}

	@Override
	public void createOrUpdateBlob( BlobEntry blobEntry ) throws UnexpectedServerException {
		GcsFilename gcsFileName
				= new GcsFilename( bucketName, blobEntry.getName() );

		Builder builder = new GcsFileOptions.Builder();
		if( blobEntry.getMimeType() != null )
			builder.mimeType( blobEntry.getMimeType() );
		if( blobEntry.getCacheControl() != null )
			builder.cacheControl( blobEntry.getCacheControl() );
		if( blobEntry.getMetaName() != null )
			builder.addUserMetadata( BlobEntry.META_NAME, blobEntry.getMetaName() );
		GcsFileOptions gcsFileOptions = builder.build();

		try {
			GcsOutputChannel gcsOutputChannel = gcsService
					.createOrReplace( gcsFileName , gcsFileOptions );
			gcsOutputChannel.write( ByteBuffer.wrap( blobEntry.getData() ) );
			gcsOutputChannel.close();
		} catch( IOException ex ) {
			logger.log( Level.INFO, "Failed to create/update blob with name '" + blobEntry.getName() + "'", ex );
			throw new UnexpectedServerException();
		}
	}

	@Override
	public void deleteBlob( BlobEntry blobEntry ) throws UnexpectedServerException {
		try {
			gcsService.delete( new GcsFilename( bucketName, blobEntry.getName() ) );
		} catch( IOException ex ) {
			logger.log( Level.INFO, "Failed to delete blob with name '" + blobEntry.getName() + "'", ex );
			throw new UnexpectedServerException();
		}
	}

}
