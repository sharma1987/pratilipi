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
	
	public static Long TOP_READS_CATEGORY_ID = 5943744289308672L;
	public static String TOP_READS_CATEGORY_NAME = "Top Reads";
	public static Long FEATURED_CATEGORY_ID = 6444076840779776L;
	public static String FEATURED_CATEGORY_NAME = "Featured";
	public static Long NEW_RELEASES_CATEGORY_ID = 4984594474467328L;
	public static String NEW_RELEASES_CATEGORY_NAME = "New Releases";
	public static Long ROMANCE_CATEGORY_ID = 5992734565335040L;
	public static String ROMANCE_CATEGORY_NAME = "Romance";
	public static Long CLASSIC_CATEGORY_ID = 5763472432300032L;
	public static String CLASSIC_CATEGORY_NAME = "Classic";
	public static Long FICTION_CATEGORY_ID = 5667960169431040L;
	public static String FICTION_CATEGORY_NAME = "Fiction";

	@Get
	public GetMobileInitResponse getMobileInit( GetMobileInitRequest request ){
		
		List<Long> topReadsPratilipiIdList = new ArrayList<Long>();
		List<Long> featuredPratilipiIdList = new ArrayList<Long>();
		List<Long> newReleasesPratilipiIdList = new ArrayList<Long>();
		List<Long> romancePratilipiIdList = new ArrayList<Long>();
		List<Long> classicPratilipiIdList = new ArrayList<Long>();
		List<Long> fictionPratilipiIdList = new ArrayList<Long>();
		
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
			
			romancePratilipiIdList.add( 5637094319849472L );
			romancePratilipiIdList.add( 5659569942429696L );
			romancePratilipiIdList.add( 5686626164408320L );
			
			classicPratilipiIdList.add( 5700019675987968L );
			classicPratilipiIdList.add( 5704980631650304L );
			classicPratilipiIdList.add( 6450508839518208L );
			classicPratilipiIdList.add( 6518793719250944L );
			classicPratilipiIdList.add( 6582036038942720L );
			classicPratilipiIdList.add( 6435940042014720L );
			
			fictionPratilipiIdList.add( 5727614605983744L );
			fictionPratilipiIdList.add( 5979786463674368L );
			fictionPratilipiIdList.add( 6450508839518208L );
			fictionPratilipiIdList.add( 6518793719250944L );
			fictionPratilipiIdList.add( 6582036038942720L );
			fictionPratilipiIdList.add( 6435940042014720L );
			
		} else if( request.getLanguageId() == 5965057007550464L ){
			topReadsPratilipiIdList.add( 4818859890573312L );
			topReadsPratilipiIdList.add( 4834680419385344L );
			topReadsPratilipiIdList.add( 4849784863064064L );
			topReadsPratilipiIdList.add( 4869201873338368L );
			
			featuredPratilipiIdList.add( 5054455087104000L );
			featuredPratilipiIdList.add( 4815090553454592L );
			featuredPratilipiIdList.add( 5110699831328768L );
			featuredPratilipiIdList.add( 5677651805077504L );
			featuredPratilipiIdList.add( 5144374119759872L );
			
			newReleasesPratilipiIdList.add( 5677651805077504L );
			newReleasesPratilipiIdList.add( 5685330787172352L );
			newReleasesPratilipiIdList.add( 5704013609697280L );
			newReleasesPratilipiIdList.add( 5706316634914816L );
			newReleasesPratilipiIdList.add( 5733614071316480L );
			
			classicPratilipiIdList.add( 5726348362383360L );
			classicPratilipiIdList.add( 5744421383438336L );
			classicPratilipiIdList.add( 6330477187170304L );
			classicPratilipiIdList.add( 5711865531334656L );
			classicPratilipiIdList.add( 5739895830085632L );
			classicPratilipiIdList.add( 6260573373202432L );
			
			fictionPratilipiIdList.add( 6330477187170304L );
			fictionPratilipiIdList.add( 6260573373202432L );
			fictionPratilipiIdList.add( 5191192820056064L );
			fictionPratilipiIdList.add( 5151430885244928L );
			fictionPratilipiIdList.add( 5706721502691328L );
			fictionPratilipiIdList.add( 6332351135088640L );
			
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

			romancePratilipiIdList.add( 6270529652654080L );
		}
		
		GetMobileInitResponse response = new GetMobileInitResponse();
		
		List<PratilipiData> topReadsPratilipiDataList = 
					PratilipiContentHelper.createPratilipiDataList( 
								topReadsPratilipiIdList,
								true,
								true,
								true,
								this.getThreadLocalRequest() );
		response.attachToResponse( TOP_READS_CATEGORY_NAME, TOP_READS_CATEGORY_ID, topReadsPratilipiDataList );
		
		
		List<PratilipiData> featuredPratilipiDataList = 
				PratilipiContentHelper.createPratilipiDataList( 
						featuredPratilipiIdList,
							true,
							true,
							true,
							this.getThreadLocalRequest() );
		response.attachToResponse( FEATURED_CATEGORY_NAME, FEATURED_CATEGORY_ID, featuredPratilipiDataList );
		
		List<PratilipiData> newReleasesPratilipiDataList = 
				PratilipiContentHelper.createPratilipiDataList( 
						newReleasesPratilipiIdList,
							true,
							true,
							true,
							this.getThreadLocalRequest() );
		response.attachToResponse( NEW_RELEASES_CATEGORY_NAME, NEW_RELEASES_CATEGORY_ID, newReleasesPratilipiDataList );
		
		if( romancePratilipiIdList.size() > 0 ){
			List<PratilipiData> romancePratilipiDataList = 
					PratilipiContentHelper.createPratilipiDataList( 
							romancePratilipiIdList,
								true,
								true,
								true,
								this.getThreadLocalRequest() );
			response.attachToResponse( ROMANCE_CATEGORY_NAME, ROMANCE_CATEGORY_ID, romancePratilipiDataList );
		}
		
		if( classicPratilipiIdList.size() > 0 ){
			List<PratilipiData> classicPratilipiDataList = 
					PratilipiContentHelper.createPratilipiDataList( 
							classicPratilipiIdList,
								true,
								true,
								true,
								this.getThreadLocalRequest() );
			response.attachToResponse( CLASSIC_CATEGORY_NAME, CLASSIC_CATEGORY_ID, classicPratilipiDataList );
		}
		
		if( fictionPratilipiIdList.size() > 0 ){
			List<PratilipiData> fictionPratilipiDataList = 
					PratilipiContentHelper.createPratilipiDataList( 
							fictionPratilipiIdList,
								true,
								true,
								true,
								this.getThreadLocalRequest() );
			response.attachToResponse( FICTION_CATEGORY_NAME, FICTION_CATEGORY_ID, fictionPratilipiDataList );
		}
		return response;
	}
}
