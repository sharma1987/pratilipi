package com.pratilipi.api.impl.init;

import java.util.logging.Logger;

import com.google.gson.Gson;
import com.pratilipi.api.GenericApi;
import com.pratilipi.api.annotation.Bind;
import com.pratilipi.api.annotation.Get;
import com.pratilipi.api.impl.init.shared.GetInitApiRequest;
import com.pratilipi.api.shared.GenericResponse;
import com.pratilipi.common.type.AccessType;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;
import com.pratilipi.data.type.AccessToken;
import com.pratilipi.data.type.AuditLog;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.filter.AccessTokenFilter;

@SuppressWarnings("serial")
@Bind( uri = "/init" )
public class InitApi extends GenericApi {
	
	private static final Logger logger =
			Logger.getLogger( InitApi.class.getName() );

	@Get
	public GenericResponse get( GetInitApiRequest request ) {
		
/*		
		List<Long> authorIdList = DataAccessorFactory.getDataAccessor()
				.getAuthorIdList( new AuthorFilter(), null, null )
				.getDataList();

		List<Task> taskList = new ArrayList<Task>( authorIdList.size() );
		for( Long authorId : authorIdList ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/author/process" )
					.addParam( "authorId", authorId.toString() )
					.addParam( "processData", "true" );
			taskList.add( task );
		}
		TaskQueueFactory.getAuthorTaskQueue().addAll( taskList );
		logger.log( Level.INFO, "Added " + taskList.size() + " tasks in the queue." );
*/
		
/*		
		List<Long> pratilipiIdList = DataAccessorFactory.getDataAccessor()
				.getPratilipiIdList( new PratilipiFilter(), null, null )
				.getDataList();

		List<Task> taskList = new ArrayList<Task>( pratilipiIdList.size() );
		for( Long pratilipiId : pratilipiIdList ) {
			Task task = TaskQueueFactory.newTask()
					.setUrl( "/pratilipi/process" )
					.addParam( "pratilipiId", pratilipiId.toString() )
					.addParam( "processData", "true" );
			taskList.add( task );
		}
		TaskQueueFactory.getPratilipiTaskQueue().addAll( taskList );
		logger.log( Level.INFO, "Added " + taskList.size() + " tasks in the queue." );
*/
		
		long[][] offset = {
//				new long[] {5135361533542400L,2663L},
				new long[] {5196411878506496L,183L},
				new long[] {5741295515467776L,1483L},
				new long[] {5709949130768384L,78L},
//				new long[] {5155035268775936L,474L},
				new long[] {5645455495004160L,6L},
				new long[] {5725637813731328L,52L},
//				new long[] {4946718139351040L,0L},
//				new long[] {5822385531912192L,10L},
//				new long[] {5669071953592320L,1609L},
				new long[] {5693765442338816L,193L},
//				new long[] {5693908432453632L,0L},
//				new long[] {5670554354843648L,2119L},
//				new long[] {5691188921237504L,3040L},
//				new long[] {5096266023305216L,1009L},
		};
		
		
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		
		Gson gson = new Gson();

		for( long[] offsetCount : offset ) {
		
			Pratilipi pratilipi = dataAccessor.getPratilipi( offsetCount[0] );
	
			AccessToken accessToken = AccessTokenFilter.getAccessToken();
			AuditLog auditLog = dataAccessor.newAuditLogOfy();
			auditLog.setAccessId( accessToken.getId() );
			auditLog.setAccessType( AccessType.PRATILIPI_UPDATE );
			auditLog.setEventDataOld( gson.toJson( pratilipi ) );
			
	//		pratilipi.setFbLikeShareCountOffset( 92L );
	//		pratilipi.setFbLikeShareCount( 2L );
			pratilipi.setReadCountOffset( offsetCount[1] );
			
			auditLog.setEventDataNew( gson.toJson( pratilipi ) );
	
			pratilipi = dataAccessor.createOrUpdatePratilipi( pratilipi, auditLog );
			
		}
		
		return new GenericResponse();
		
	}
	
}
