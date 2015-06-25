package com.pratilipi.api.pratilipi;

import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.annotation.Put;
import com.pratilipi.api.author.shared.GetAuthorResponse;
import com.pratilipi.api.pratilipi.shared.GetPratilipiRequest;
import com.pratilipi.api.pratilipi.shared.GetPratilipiResponse;
import com.pratilipi.api.pratilipi.shared.PutPratilipiRequest;
import com.pratilipi.api.pratilipi.shared.PutPratilipiResponse;
import com.pratilipi.common.exception.InsufficientAccessException;
import com.pratilipi.common.exception.InvalidArgumentException;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.client.AuthorData;
import com.pratilipi.data.client.PratilipiData;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.data.util.AuthorDataUtil;
import com.pratilipi.data.util.PratilipiDataUtil;
import com.pratilipi.taskqueue.Task;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi" )
public class PratilipiApi extends GenericApi {
	
	@Get
	public GetPratilipiResponse getPratilipi( GetPratilipiRequest request )
			throws InvalidArgumentException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Pratilipi pratilipi = dataAccessor.getPratilipi( request.getPratilipiId() );
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		
		PratilipiData pratilipiData = PratilipiDataUtil.createPratilipiData( pratilipi, null );
		AuthorData authorData = AuthorDataUtil.createAuthorData( author );

		GetPratilipiResponse response = gson.fromJson( gson.toJson( pratilipiData ), GetPratilipiResponse.class );
		response.setAuthor( gson.fromJson( gson.toJson( authorData ), GetAuthorResponse.class ) );

		return response;
	}

	@Put
	public PutPratilipiResponse putPratilipi( PutPratilipiRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		PratilipiData pratilipiData = gson.fromJson( gson.toJson( request ), PratilipiData.class );
		PratilipiDataUtil.savePratilipiData( pratilipiData);
		
		Task task = TaskQueueFactory.newTask()
				.setUrl( "/pratilipi/process" )
				.addParam( "pratilipiId", pratilipiData.getId().toString() )
				.addParam( "processData", "true" );
		TaskQueueFactory.getPratilipiTaskQueue().add( task );
		
		
		return new PutPratilipiResponse();
	}		

}
