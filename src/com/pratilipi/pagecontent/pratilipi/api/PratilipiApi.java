package com.pratilipi.pagecontent.pratilipi.api;

import java.util.List;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.api.annotation.Put;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.taskqueue.Task;
import com.pratilipi.data.access.DataAccessor;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.transfer.Category;
import com.pratilipi.data.transfer.Language;
import com.pratilipi.data.transfer.shared.PratilipiData;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipi.api.shared.GetPratilipiRequest;
import com.pratilipi.pagecontent.pratilipi.api.shared.GetPratilipiResponse;
import com.pratilipi.pagecontent.pratilipi.api.shared.PutPratilipiRequest;
import com.pratilipi.pagecontent.pratilipi.api.shared.PutPratilipiResponse;
import com.pratilipi.pagecontent.pratilipicategory.PratilipiCategoryContentHelper;
import com.pratilipi.taskqueue.TaskQueueFactory;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi" )
public class PratilipiApi extends GenericApi {
	
	@Get
	public GetPratilipiResponse getPratilipi( GetPratilipiRequest request )
			throws InvalidArgumentException, UnexpectedServerException {
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() );
		Pratilipi pratilipi = dataAccessor.getPratilipi( request.getPratilipiId() );
		Author author = dataAccessor.getAuthor( pratilipi.getAuthorId() );
		Language language = dataAccessor.getLanguage( pratilipi.getLanguageId() );
		List<Category> category = PratilipiCategoryContentHelper.getPratilipiCategoryList( pratilipi.getId(), this.getThreadLocalRequest() );
		
		PratilipiData pratilipiData = PratilipiContentHelper.createPratilipiData(
											pratilipi,
											language,
											author,
											category,
											this.getThreadLocalRequest() 
											);
		
		return new GetPratilipiResponse( pratilipiData );
	}

	@Put
	public PutPratilipiResponse putPratilipi( PutPratilipiRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		PratilipiData pratilipiData = new PratilipiData();
		pratilipiData.setId( request.getPratilipiId() );
		if( request.hasSummary() )
			pratilipiData.setSummary( request.getSummary() );
		if( request.hasIndex() )
			pratilipiData.setIndex( request.getIndex() );
		
		pratilipiData = PratilipiContentHelper.savePratilipi(
				pratilipiData,
				this.getThreadLocalRequest() );
		
		
		Task task = TaskQueueFactory.newTask()
				.addParam( "pratilipiId", pratilipiData.getId().toString() )
				.addParam( "processData", "true" )
				.setUrl( "/pratilipi/process" );
		TaskQueueFactory.getPratilipiTaskQueue().add( task );
		
		
		return new PutPratilipiResponse();
	}		

}
