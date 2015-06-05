package com.pratilipi.api;

import java.util.ArrayList;
import java.util.List;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.pratilipi.api.shared.GetMobileInitRequest;
import com.pratilipi.api.shared.GetMobileInitResponse;
import com.pratilipi.data.transfer.shared.PratilipiData;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;

@SuppressWarnings( "serial" )
@Bind( uri= "/mobileinit" )
public class MobileInitApi extends GenericApi {

	@Get
	public GetMobileInitResponse getMobileInit( GetMobileInitRequest request ){
		
		List<Long> topReadsPratilipiIdList = new ArrayList<Long>();
		List<Long> featuredPratilipiIdList = new ArrayList<Long>();
		List<Long> newReleasesPratilipiIdList = new ArrayList<Long>();
		
		if( request.getLanguageId() == 5130467284090880L ){
			topReadsPratilipiIdList.add( 4530418997002240L );
			topReadsPratilipiIdList.add( 4843865324388352L );
			topReadsPratilipiIdList.add( 4629135213199360L );
			topReadsPratilipiIdList.add( 4538710716579840L );
			topReadsPratilipiIdList.add( 4571168321306624L );
			
			featuredPratilipiIdList.add( 4653241656672256L );
			featuredPratilipiIdList.add( 4571168321306624L );
			featuredPratilipiIdList.add( 4747639635574784L );
			featuredPratilipiIdList.add( 4843865324388352L );
			featuredPratilipiIdList.add( 4910999823974400L );
			
			newReleasesPratilipiIdList.add( 5636953047302144L );
			newReleasesPratilipiIdList.add( 5653702044024832L );
			newReleasesPratilipiIdList.add( 5688643247144960L );
			newReleasesPratilipiIdList.add( 5752256343310336L );
			newReleasesPratilipiIdList.add( 5756836925931520L );
			
		} else if( request.getLanguageId() == 5965057007550464L ){
			topReadsPratilipiIdList.add( 4818859890573312L );
			topReadsPratilipiIdList.add( 4834680419385344L );
			topReadsPratilipiIdList.add( 4849784863064064L );
			topReadsPratilipiIdList.add( 4869201873338368L );
			topReadsPratilipiIdList.add( 4901223018790912L );
			
			featuredPratilipiIdList.add( 5054455087104000L );
			featuredPratilipiIdList.add( 4815090553454592L );
			featuredPratilipiIdList.add( 5110699831328768L );
			featuredPratilipiIdList.add( 5142641637326848L );
			featuredPratilipiIdList.add( 5144374119759872L );
			
			newReleasesPratilipiIdList.add( 5677651805077504L );
			newReleasesPratilipiIdList.add( 5685330787172352L );
			newReleasesPratilipiIdList.add( 5704013609697280L );
			newReleasesPratilipiIdList.add( 5706316634914816L );
			newReleasesPratilipiIdList.add( 5733614071316480L );
			
		} else if( request.getLanguageId() == 6319546696728576L ){
			topReadsPratilipiIdList.add( 4664696015683584L );
			topReadsPratilipiIdList.add( 4801061093113856L );
			topReadsPratilipiIdList.add( 4685596450619392L );
			topReadsPratilipiIdList.add( 4803319927144448L );
			topReadsPratilipiIdList.add( 4837289477799936L );
			
			featuredPratilipiIdList.add( 4803319927144448L );
			featuredPratilipiIdList.add( 4831463656652800L );
			featuredPratilipiIdList.add( 4912458217029632L );
			featuredPratilipiIdList.add( 4957982857101312L );
			featuredPratilipiIdList.add( 5107756889538560L );
			
			newReleasesPratilipiIdList.add( 5149579519459328L );
			newReleasesPratilipiIdList.add( 5151970692169728L );
			newReleasesPratilipiIdList.add( 5169145746292736L );
			newReleasesPratilipiIdList.add( 5203681645428736L );
			newReleasesPratilipiIdList.add( 5246387360890880L );
			
		}
		
		GetMobileInitResponse response = new GetMobileInitResponse();
		
		List<PratilipiData> topReadsPratilipiDataList = 
					PratilipiContentHelper.createPratilipiDataList( 
								topReadsPratilipiIdList,
								true,
								true,
								this.getThreadLocalRequest() );
		response.attachToResponse( "Top Reads", "topReadsPratilipiDataList", topReadsPratilipiDataList );
		
		
		List<PratilipiData> featuredPratilipiDataList = 
				PratilipiContentHelper.createPratilipiDataList( 
						featuredPratilipiIdList,
							true,
							true,
							this.getThreadLocalRequest() );
		response.attachToResponse( "Featured", "featuredPratilipiDataList", featuredPratilipiDataList );
		
		List<PratilipiData> newReleasesPratilipiDataList = 
				PratilipiContentHelper.createPratilipiDataList( 
						newReleasesPratilipiIdList,
							true,
							true,
							this.getThreadLocalRequest() );
		response.attachToResponse( "New Releases", "newReleasesPratilipiDataList", newReleasesPratilipiDataList );
		
		return response;
	}
}
