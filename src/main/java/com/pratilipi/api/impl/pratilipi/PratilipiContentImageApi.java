package com.pratilipi.api.impl.pratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Post;
import com.pratilipi.api.annotation.Validate;
import com.pratilipi.api.shared.GenericFileDownloadResponse;
import com.pratilipi.api.shared.GenericFileUploadRequest;
import com.pratilipi.api.shared.GenericRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.PratilipiContentType;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.BlobEntry;
import com.pratilipi.data.util.PratilipiDataUtil;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/content/image" )
public class PratilipiContentImageApi extends GenericApi {

	public static class GetRequest extends GenericRequest {

		@Validate( required = true, requiredErrMsg = ERR_PRATILIPI_ID_REQUIRED, minLong = 1L )
		private Long pratilipiId;

		@Validate( minInt = 1 )
		private Integer pageNo;

		private String name;

		private Integer width;

		private PratilipiContentType contentType;

	}

	public static class PostRequest extends GenericFileUploadRequest {

		@Validate( required = true, minLong = 1L, requiredErrMsg = ERR_PRATILIPI_ID_REQUIRED )
		private Long pratilipiId;

		@Validate( minInt = 1 )
		private Integer pageNo;

		private PratilipiContentType contentType;

	}

	@SuppressWarnings("unused")
	public class Response extends GenericResponse {

		private Integer pageNo;
		private Integer pageCount;
		private String imageName;


		private Response() {}

		public Response( Integer pageNo, Integer pageCount, String imageName ) {
			this.pageNo = pageNo;
			this.pageCount = pageCount;
			this.imageName = imageName;
		}

	}


	@Get
	public GenericFileDownloadResponse getPratilipiContent( GetRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		PratilipiContentType contentType = request.contentType;
		if( contentType == null )
			contentType = DataAccessorFactory.getDataAccessor()
					.getPratilipi( request.pratilipiId )
					.getContentType();

		BlobEntry blobEntry = null;

		if( contentType == PratilipiContentType.IMAGE ) {
			blobEntry = (BlobEntry) PratilipiDataUtil.getPratilipiContent(
					request.pratilipiId,
					0,
					request.pageNo,
					contentType );

		} else if( contentType == PratilipiContentType.PRATILIPI ) {
			blobEntry = (BlobEntry) PratilipiDataUtil.getPratilipiContentImage(
					request.pratilipiId,
					request.name,
					request.width );

		}

		return new GenericFileDownloadResponse(
				blobEntry.getData(),
				blobEntry.getMimeType(),
				blobEntry.getETag() );

	}

	@Post
	public Response postPratilipiContent( PostRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		PratilipiContentType contentType = request.contentType;
		if( contentType == null )
			contentType = DataAccessorFactory.getDataAccessor()
					.getPratilipi( request.pratilipiId )
					.getContentType();


		if( contentType == PratilipiContentType.PRATILIPI ) {
			BlobEntry blobEntry = DataAccessorFactory.getBlobAccessor().newBlob( request.getName() );
			blobEntry.setData( request.getData() );
			blobEntry.setMimeType( request.getMimeType() );
			blobEntry.setMetaName( request.getName() );

			String imageName = PratilipiDataUtil.createNewImage( 
									request.pratilipiId, 
									blobEntry );

			return new Response( request.pageNo, null, imageName );

		} else if( contentType == PratilipiContentType.IMAGE ) {
			BlobEntry blobEntry = DataAccessorFactory.getBlobAccessor().newBlob( request.getName() );
			blobEntry.setData( request.getData() );
			blobEntry.setMimeType( request.getMimeType() );

			int pageCount = PratilipiDataUtil.updatePratilipiContent(
					request.pratilipiId,
					request.pageNo,
					PratilipiContentType.IMAGE,
					blobEntry,
					false );

			return new Response( request.pageNo, pageCount, null );

		}

		return null;

	}		

}
