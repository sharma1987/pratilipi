package com.pratilipi.api;

import java.util.ArrayList;
import java.util.List;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.transfer.Page;
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
		
		List<String> topReadsPratilipiPageUrlList = new ArrayList<String>();
		List<String> featuredPratilipiPageUrlList = new ArrayList<String>();
		List<String> newReleasesPratilipiPageUrlList = new ArrayList<String>();
		List<String> romancePratilipiPageUrlList = new ArrayList<String>();
		List<String> classicPratilipiPageUrlList = new ArrayList<String>();
		List<String> fictionPratilipiPageUrlList = new ArrayList<String>();
		
		if( request.getLanguageId() == 5130467284090880L ){
			topReadsPratilipiPageUrlList.add( "/sharatchandra-chattopadhyay/devdas" );
			topReadsPratilipiPageUrlList.add( "/goswami-tulsidas/ramcharitmanas-sundarkand" );
			topReadsPratilipiPageUrlList.add( "/munshi-premchand/nirmala" );
			topReadsPratilipiPageUrlList.add( "/unknown/baital-pachisi" );
			topReadsPratilipiPageUrlList.add( "/vijay-kumar-sappatti/kavitaye-aur-nazme" );
			
			featuredPratilipiPageUrlList.add( "/munshi-premchand/nirmala" );
			featuredPratilipiPageUrlList.add( "/goswami-tulsidas/dohavali" );
			featuredPratilipiPageUrlList.add( "/goswami-tulsidas/kavitavali" );
			featuredPratilipiPageUrlList.add( "/unknown/alif-laila" );
			featuredPratilipiPageUrlList.add( "/unknown/baital-pachisi" );
			featuredPratilipiPageUrlList.add( "/ayodhya-singh-upadhyay-hariaudh/adhkhila-phool" );
			
			newReleasesPratilipiPageUrlList.add( "/ayodhya-singh-upadhyay-hariaudh/itivratt" );
			newReleasesPratilipiPageUrlList.add( "/sharatchandra-chattopadhyay/devdas" );
			newReleasesPratilipiPageUrlList.add( "/vijay-kumar-sappatti/kavitaye-aur-nazme" );
			newReleasesPratilipiPageUrlList.add( "/anwar-suhail/gyarah-sitambar-ke-baad" );
			newReleasesPratilipiPageUrlList.add( "/ayodhya-singh-upadhyay-hariaudh/adhkhila-phool" );
			
			romancePratilipiPageUrlList.add( "/preeti-tailor/page-18" );
			romancePratilipiPageUrlList.add( "/ranjeet-pratap-singh-rohit-1/jeevan-kya" );
			romancePratilipiPageUrlList.add( "/preeti-tailor/page-16" );
			
			classicPratilipiPageUrlList.add( "/mohandas-karamchand-gandhi-gandhiji/satyke-prayog-athva-atmkatha-bhag-4" );
			classicPratilipiPageUrlList.add( "/mohandas-karamchand-gandhi-gandhiji/saty-ke-prayog-athva-atmkatha-bhag-3" );
			classicPratilipiPageUrlList.add( "/munshi-premchand/juloos" );
			classicPratilipiPageUrlList.add( "/munshi-premchand/baudam" );
			classicPratilipiPageUrlList.add( "/munshi-premchand/grih-neeti" );
			classicPratilipiPageUrlList.add( "/munshi-premchand/brahma-ka-svaang" );
			
			fictionPratilipiPageUrlList.add( "/ummedsingh-baid-sadhak/ganpati-shatak-1" );
			fictionPratilipiPageUrlList.add( "/munshi-premchand/visham-samasya" );
			fictionPratilipiPageUrlList.add( "/munshi-premchand/juloos" );
			fictionPratilipiPageUrlList.add( "/munshi-premchand/baudam" );
			fictionPratilipiPageUrlList.add( "/munshi-premchand/grih-neeti" );
			fictionPratilipiPageUrlList.add( "/munshi-premchand/brahma-ka-svaang" );
			
		} else if( request.getLanguageId() == 5965057007550464L ){
			topReadsPratilipiPageUrlList.add( "/zaverchand-meghani/meghani-ni-navlikao-khand-2" );
			topReadsPratilipiPageUrlList.add( "/p-p-kuntanpuri-yogi/akbar-ane-birbalni-ramuji-vartao" );
			topReadsPratilipiPageUrlList.add( "/dalpatraam/mithybhiman" );
			topReadsPratilipiPageUrlList.add( "/nimisha-dalal/kone-kahu-1" );
			
			featuredPratilipiPageUrlList.add( "/dalpatraam/mithybhiman" );
			featuredPratilipiPageUrlList.add( "/suresh-jani/antarni-vani" );
			featuredPratilipiPageUrlList.add( "/suresh-jani/200-avlokano" );
			featuredPratilipiPageUrlList.add( "/nanhalaal-dalpatraam-kavi/pankhadio" );
			featuredPratilipiPageUrlList.add( "/lalit-parikh/varta-re-varta-3" );
			
			newReleasesPratilipiPageUrlList.add( "/zaverchand-meghani/meghani-ni-navlikao-khand-2" );
			newReleasesPratilipiPageUrlList.add( "/lalit-parikh/bhed-abhed" );
			newReleasesPratilipiPageUrlList.add( "/suresh-jani/200-avlokano" );
			newReleasesPratilipiPageUrlList.add( "/suresh-jani/antarni-vani" );
			newReleasesPratilipiPageUrlList.add( "/lalit-parikh/varta-re-varta-3" );
			
			classicPratilipiPageUrlList.add( "/kishorlaal-ghanshyamlaal-mashruvaala/yesu-khrist" );
			classicPratilipiPageUrlList.add( "/agnat/bakru-ke-kutru" );
			classicPratilipiPageUrlList.add( "/agnat/bapa-kagado" );
			classicPratilipiPageUrlList.add( "/agnat/pemlo-pemli" );
			classicPratilipiPageUrlList.add( "/agnat/lobhiyo-bhai-latki-gaya" );
			classicPratilipiPageUrlList.add( "/zaverchand-meghani/aai-kaambai" );
			
			fictionPratilipiPageUrlList.add( "/agnat/bapa-kagado" );
			fictionPratilipiPageUrlList.add( "/zaverchand-meghani/aai-kaambai" );
			fictionPratilipiPageUrlList.add( "/ravindra-parekh/lat-hukam-prakran-3" );
			fictionPratilipiPageUrlList.add( "/ravindra-parekh/lathukam-prakran-2" );
			fictionPratilipiPageUrlList.add( "/agnat/popat-bhukhyo-nathi-popat-tarasyo-nathi" );
			fictionPratilipiPageUrlList.add( "/zaverchand-meghani/bholo-katyay" );
			
		} else if( request.getLanguageId() == 6319546696728576L ){
			topReadsPratilipiPageUrlList.add( "/p-raghavan/rendu" );
			topReadsPratilipiPageUrlList.add( "/subashini-tremmel/payanangal-thodargindrana-then-korea-2003-1" );
			topReadsPratilipiPageUrlList.add( "/dubukku/namadevarum-kaipidi-sundalum" );
			topReadsPratilipiPageUrlList.add( "/kalki-r-krishnamoorthy/ponniyin-selvan-condensed" );
			topReadsPratilipiPageUrlList.add( "/p-raghavan/booku" );
			
			featuredPratilipiPageUrlList.add( "/subashini-tremmel/en-sarithiram-u-ve-sa-vudan-oru-ula-pagudhi-i" );
			featuredPratilipiPageUrlList.add( "/jothiji-tiruppur/oru-thozhirchaalayin-kurippugal" );
			featuredPratilipiPageUrlList.add( "/subramanian-sivam/saadhika-pirandhavar-neengal" );
			featuredPratilipiPageUrlList.add( "/tanjore-v-gopalan/iniyavai-naarpadhu" );
			featuredPratilipiPageUrlList.add( "/s-kothandaraman/oru-vaasagam" );
			
			newReleasesPratilipiPageUrlList.add( "/s-kothandaraman/oru-vaasagam" );
			newReleasesPratilipiPageUrlList.add( "/p-raghavan/booku" );
			newReleasesPratilipiPageUrlList.add( "/p-raghavan/rendu" );
			newReleasesPratilipiPageUrlList.add( "/tamil-keechargal-twitamils-group/twitter-kaiyedu" );
			newReleasesPratilipiPageUrlList.add( "/subramanian-sivam/saadhika-pirandhavar-neengal" );

			romancePratilipiPageUrlList.add( "/manobharathi-ezhuthupizhai/ezhuthupizhai-1" );
		}
		
		GetMobileInitResponse response = new GetMobileInitResponse();
		
		
		List<PratilipiData> topReadsPratilipiDataList = 
					PratilipiContentHelper.createPratilipiDataList( 
								getPratilipiIdList( topReadsPratilipiPageUrlList ),
								true,
								true,
								true,
								this.getThreadLocalRequest() );
		response.attachToResponse( TOP_READS_CATEGORY_NAME, TOP_READS_CATEGORY_ID, topReadsPratilipiDataList );
		
		
		List<PratilipiData> featuredPratilipiDataList = 
				PratilipiContentHelper.createPratilipiDataList( 
							getPratilipiIdList( featuredPratilipiPageUrlList ),
							true,
							true,
							true,
							this.getThreadLocalRequest() );
		response.attachToResponse( FEATURED_CATEGORY_NAME, FEATURED_CATEGORY_ID, featuredPratilipiDataList );
		
		List<PratilipiData> newReleasesPratilipiDataList = 
				PratilipiContentHelper.createPratilipiDataList( 
							getPratilipiIdList( newReleasesPratilipiPageUrlList ),
							true,
							true,
							true,
							this.getThreadLocalRequest() );
		response.attachToResponse( NEW_RELEASES_CATEGORY_NAME, NEW_RELEASES_CATEGORY_ID, newReleasesPratilipiDataList );
		
		if( romancePratilipiPageUrlList.size() > 0 ){
			List<PratilipiData> romancePratilipiDataList = 
					PratilipiContentHelper.createPratilipiDataList( 
								getPratilipiIdList( romancePratilipiPageUrlList ),
								true,
								true,
								true,
								this.getThreadLocalRequest() );
			response.attachToResponse( ROMANCE_CATEGORY_NAME, ROMANCE_CATEGORY_ID, romancePratilipiDataList );
		}
		
		if( classicPratilipiPageUrlList.size() > 0 ){
			List<PratilipiData> classicPratilipiDataList = 
					PratilipiContentHelper.createPratilipiDataList( 
								getPratilipiIdList( classicPratilipiPageUrlList ),
								true,
								true,
								true,
								this.getThreadLocalRequest() );
			response.attachToResponse( CLASSIC_CATEGORY_NAME, CLASSIC_CATEGORY_ID, classicPratilipiDataList );
		}
		
		if( fictionPratilipiPageUrlList.size() > 0 ){
			List<PratilipiData> fictionPratilipiDataList = 
					PratilipiContentHelper.createPratilipiDataList( 
								getPratilipiIdList( fictionPratilipiPageUrlList ),
								true,
								true,
								true,
								this.getThreadLocalRequest() );
			response.attachToResponse( FICTION_CATEGORY_NAME, FICTION_CATEGORY_ID, fictionPratilipiDataList );
		}
		return response;
	}
	
	private List<Long> getPratilipiIdList( List<String> pageUrlList ){
		
		List<Long> pratilipiIdList = new ArrayList<Long>( pageUrlList.size() );
		for( String pageUrl : pageUrlList ){
			Page page = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() )
					.getPage( pageUrl );
			if( page != null )
				pratilipiIdList.add( page.getPrimaryContentId() );
		}
		
		return pratilipiIdList;
	}
}
