package com.pratilipi.pagecontent.pratilipicategory.api;

import java.util.List;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.data.access.DataListCursorTuple;
import com.pratilipi.data.access.DataAccessorFactory;
import com.pratilipi.data.access.SearchAccessor;
import com.pratilipi.data.transfer.shared.PratilipiData;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipicategory.api.shared.GetCategoryPratilipiRequest;
import com.pratilipi.pagecontent.pratilipicategory.api.shared.GetCategoryPratilipiResponse;

@SuppressWarnings( "serial" )
@Bind( uri = "/category/pratilipilist" )
public class CategoryPratilipiApi extends GenericApi {

	@Get
	public GetCategoryPratilipiResponse getCategoryPratilipiList( GetCategoryPratilipiRequest request ){
		
		String query = request.getLanguageId() + " AND " + request.getCategoryId();
		SearchAccessor searchAccessor = DataAccessorFactory.getSearchAccessor();
		DataListCursorTuple<Long> pratilipiIdListCursorTuple = 
							searchAccessor.searchQuery( query, 
									request.getCursor(), 
									request.getResultCount() == null ? 20 : request.getResultCount() );
		List<PratilipiData> pratilipiDataList = PratilipiContentHelper.createPratilipiDataList( 
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
