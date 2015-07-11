package com.pratilipi.pagecontent.pratilipicategory.api;

import java.util.List;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.data.access.DataListCursorTuple;
import com.pratilipi.data.transfer.shared.PratilipiData;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipicategory.PratilipiCategoryContentHelper;
import com.pratilipi.pagecontent.pratilipicategory.api.shared.GetCategoryPratilipiRequest;
import com.pratilipi.pagecontent.pratilipicategory.api.shared.GetCategoryPratilipiResponse;

@SuppressWarnings( "serial" )
@Bind( uri = "/category/pratilipilist" )
public class CategoryPratilipiApi extends GenericApi {

	@Get
	public GetCategoryPratilipiResponse getCategoryPratilipiList( GetCategoryPratilipiRequest request ) 
			throws InvalidArgumentException{
		
		DataListCursorTuple<Long> pratilipiIdListCursorTuple = 
							PratilipiCategoryContentHelper.getCategoryPratilipiList( 
									request.getLanguageId(), 
									request.getCategoryId(), 
									request.getResultCount(), 
									request.getCursor(), 
									this.getThreadLocalRequest() );
		
		List<PratilipiData> pratilipiDataList = 
							PratilipiContentHelper.createPratilipiDataList( 
									pratilipiIdListCursorTuple.getDataList(),
									false,
									true,
									true,
									this.getThreadLocalRequest() );
		
		return new GetCategoryPratilipiResponse( 
				pratilipiDataList,
				pratilipiIdListCursorTuple.getCursor() );
	}
}
