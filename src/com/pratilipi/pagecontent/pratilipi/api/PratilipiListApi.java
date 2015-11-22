package com.pratilipi.pagecontent.pratilipi.api;

import java.util.ArrayList;
import java.util.List;

import com.claymus.api.GenericApi;
import com.claymus.api.annotation.Bind;
import com.claymus.api.annotation.Get;
import com.claymus.commons.shared.exception.InsufficientAccessException;
import com.claymus.commons.shared.exception.InvalidArgumentException;
import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.claymus.data.access.DataAccessorFactory;
import com.claymus.data.access.DataListCursorTuple;
import com.pratilipi.api.MobileInitApi;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.commons.shared.CategoryType;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.data.transfer.Category;
import com.pratilipi.data.transfer.shared.PratilipiData;
import com.pratilipi.data.type.Author;
import com.pratilipi.data.type.Page;
import com.pratilipi.data.type.Pratilipi;
import com.pratilipi.pagecontent.pratilipi.PratilipiContentHelper;
import com.pratilipi.pagecontent.pratilipi.api.shared.GetPratilipiListRequest;
import com.pratilipi.pagecontent.pratilipi.api.shared.GetPratilipiListResponse;
import com.pratilipi.pagecontent.pratilipicategory.PratilipiCategoryContentHelper;

@SuppressWarnings("serial")
@Bind( uri = "/pratilipi/list" )
public class PratilipiListApi extends GenericApi {

	@Get
	public GetPratilipiListResponse getPratilipiList( GetPratilipiListRequest request )
			throws InvalidArgumentException, InsufficientAccessException, UnexpectedServerException {

		PratilipiFilter pratilipiFilter = new PratilipiFilter();
		pratilipiFilter.setType( request.getType() );
		pratilipiFilter.setLanguageId( request.getLanguageId() );
		pratilipiFilter.setAuthorId( request.getAuthorId() );
		if( request.getState() == null )
			pratilipiFilter.setState( PratilipiState.PUBLISHED );
		else
			pratilipiFilter.setState( request.getState() );
		
		DataListCursorTuple<PratilipiData> pratilipiListCursorTuple;
		
		if( request.getCategory() != null ){
			Category category = com.pratilipi.data.access.DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() )
					.getCategory( request.getCategory() );
			if( category != null && category.getType() != CategoryType.GENERAL ){
				DataListCursorTuple<Long> pratilipiIdList = 
						PratilipiCategoryContentHelper.getCategoryPratilipiList( 
								request.getLanguageId(),
								category.getId(), 
								request.getResultCount(),
								request.getCursor(), 
								this.getThreadLocalRequest() );
				List<PratilipiData> pratilipiDataList = 
						PratilipiContentHelper.createPratilipiDataList( pratilipiIdList.getDataList(),
								true, true, true, this.getThreadLocalRequest() );
				
				return new GetPratilipiListResponse( pratilipiDataList, pratilipiIdList.getCursor() );
			} else
				pratilipiListCursorTuple = getCategoryPratilipiList( 
											request.getLanguageId(),
											request.getCategory(), 
											request.getResultCount()  == null ? 20 : request.getResultCount(), 
											request.getCursor() );
		} else {
			pratilipiListCursorTuple = PratilipiContentHelper.getPratilipiList(
					pratilipiFilter,
					request.getCursor(),
					request.getResultCount() == null ? 20 : request.getResultCount(),
							this.getThreadLocalRequest() );
		}

		if( pratilipiListCursorTuple == null )
			return null;
		
		return new GetPratilipiListResponse(
				pratilipiListCursorTuple.getDataList(),
				pratilipiListCursorTuple.getCursor() );
	}
	
	private DataListCursorTuple<PratilipiData> getCategoryPratilipiList( Long languageId, Long categoryId, Integer resultCount, String cursor ){
		
		int listSize = 0;
		List<String> topReadsPratilipiDataList = new ArrayList<>();
		List<String> featuredPratilipiDataList = new ArrayList<>();
		List<String> newReleasesPratilipiDataList = new ArrayList<>();
		
		if( languageId == 5130467284090880L ){
			//LANGUAGE : HINDI
			topReadsPratilipiDataList.add( "/sharatchandra-chattopadhyay/devdas" );
			topReadsPratilipiDataList.add( "/goswami-tulsidas/ramcharitmanas-sundarkand" );
			topReadsPratilipiDataList.add( "/munshi-premchand/nirmala" );
			topReadsPratilipiDataList.add( "/unknown/baital-pachisi" );
			topReadsPratilipiDataList.add( "/vijay-kumar-sappatti/kavitaye-aur-nazme" );
			topReadsPratilipiDataList.add( "/mahendra-bhatnagar/hans-hans-gane-gae-ham" );
			topReadsPratilipiDataList.add( "/goswami-tulsidas/dohavali" );
			topReadsPratilipiDataList.add( "/vimal-gandhi/maa-1" );
			topReadsPratilipiDataList.add( "/unknown/baital-pachisi" );
			topReadsPratilipiDataList.add( "/vivek-mishra/haniya" );
			topReadsPratilipiDataList.add( "/mahavir-uttranchali/man-mein-naache-mor-hai" );
			topReadsPratilipiDataList.add( "/rashmi-prabha/amrita-imroz" );
			
			featuredPratilipiDataList.add( "/munshi-premchand/nirmala" );
			featuredPratilipiDataList.add( "/goswami-tulsidas/dohavali" );
			featuredPratilipiDataList.add( "/goswami-tulsidas/kavitavali" );
			featuredPratilipiDataList.add( "/unknown/alif-laila" );
			featuredPratilipiDataList.add( "/unknown/baital-pachisi" );
			featuredPratilipiDataList.add( "/ayodhya-singh-upadhyay-hariaudh/adhkhila-phool" );
			featuredPratilipiDataList.add( "/sharatchandra-chattopadhyay/devdas" );
			featuredPratilipiDataList.add( "/sharatchandra-chattopadhyay/devdas" );
			featuredPratilipiDataList.add( "/tripurari-kumar-sharma/tripurari-kumar-sharma-ki-najmein" );
			featuredPratilipiDataList.add( "/ayodhya-singh-upadhyay-hariaudh/itivratt" );
			featuredPratilipiDataList.add( "/mahendra-bhatnagar/hans-hans-gane-gae-ham" );
			featuredPratilipiDataList.add( "/sharatchandra-chattopadhyay/srikanta" );
			featuredPratilipiDataList.add( "/unknown/baital-pachisi" );
			featuredPratilipiDataList.add( "/mahendra-bhatnagar/dadi-ki-kahaniya" );
			featuredPratilipiDataList.add( "/rashmi-prabha/amrita-imroz" );
			featuredPratilipiDataList.add( "/munshi-premchand/sohag-ka-shav" );
			
			newReleasesPratilipiDataList.add( "/ayodhya-singh-upadhyay-hariaudh/itivratt" );
			newReleasesPratilipiDataList.add( "/sharatchandra-chattopadhyay/devdas" );
			newReleasesPratilipiDataList.add( "/vijay-kumar-sappatti/kavitaye-aur-nazme" );
			newReleasesPratilipiDataList.add( "/anwar-suhail/gyarah-sitambar-ke-baad" );
			newReleasesPratilipiDataList.add( "/ayodhya-singh-upadhyay-hariaudh/adhkhila-phool" );
			newReleasesPratilipiDataList.add( "/goswami-tulsidas/ramcharitmanas-sundarkand" );
			newReleasesPratilipiDataList.add( "/mahendra-bhatnagar/hans-hans-gane-gae-ham" );
			newReleasesPratilipiDataList.add( "/mahendra-bhatnagar/jay-yatra" );
			newReleasesPratilipiDataList.add( "/unknown/baital-pachisi" );
			newReleasesPratilipiDataList.add( "/suresh-chaudhary/shree-shiv-mahimn-strot" );
			newReleasesPratilipiDataList.add( "/jayshankar-prasad/kankaal" );
			newReleasesPratilipiDataList.add( "/tripurari-kumar-sharma/tripurari-kumar-sharma-ki-najmein" );
			newReleasesPratilipiDataList.add( "/munshi-premchand/mansarovar-part-6" );
			newReleasesPratilipiDataList.add( "/munshi-premchand/mansarovar-part-2" );
			newReleasesPratilipiDataList.add( "/mahendra-bhatnagar/mrityu-bodh-jivan-bodh" );
			newReleasesPratilipiDataList.add( "/munshi-premchand/mansarovar-part-8" );
			newReleasesPratilipiDataList.add( "/vijay-kumar-sappatti/ek-thi-maya" );
			
		} else if( languageId == 6319546696728576L ){
			//LANGUAGE : TAMIL
			topReadsPratilipiDataList.add( "/p-raghavan/rendu" );
			topReadsPratilipiDataList.add( "/subashini-tremmel/payanangal-thodargindrana-then-korea-2003-1" );
			topReadsPratilipiDataList.add( "/dubukku/namadevarum-kaipidi-sundalum" );
			topReadsPratilipiDataList.add( "/kalki-r-krishnamoorthy/ponniyin-selvan-condensed" );
			topReadsPratilipiDataList.add( "/p-raghavan/booku" );
			topReadsPratilipiDataList.add( "/chithran-ragunath/tharunam" );
			topReadsPratilipiDataList.add( "/shammi-muthuvel/kanavugal-virpanaikku-alla" );
			topReadsPratilipiDataList.add( "/aravindh-sachidanandam/thatpam-thavir" );
			topReadsPratilipiDataList.add( "/geetha-sambasivam/kadhai-kadhaiyam-karanamam-ramayanam" );
			topReadsPratilipiDataList.add( "/jothiji-tiruppur/konjam-soru-konjam-varalaru" );
			topReadsPratilipiDataList.add( "/nirmala-raghavan/pengalo-pengal" );
			
			featuredPratilipiDataList.add( "/subashini-tremmel/en-sarithiram-u-ve-sa-vudan-oru-ula-pagudhi-i" );
			featuredPratilipiDataList.add( "/jothiji-tiruppur/oru-thozhirchaalayin-kurippugal" );
			featuredPratilipiDataList.add( "/subramanian-sivam/saadhika-pirandhavar-neengal" );
			featuredPratilipiDataList.add( "/tanjore-v-gopalan/iniyavai-naarpadhu" );
			featuredPratilipiDataList.add( "/s-kothandaraman/oru-vaasagam" );
			featuredPratilipiDataList.add( "/tamil-keechargal-twitamils-group/twitter-kaiyedu" );
			featuredPratilipiDataList.add( "/yercaud-ilango/dhaaniyangal" );
			featuredPratilipiDataList.add( "/yercaud-ilango/indhiya-desiya-chinnangal" );
			featuredPratilipiDataList.add( "/tanjore-v-gopalan/urainadaiyil-kambaramayanam" );
			featuredPratilipiDataList.add( "/bengar/varalaaru-puviyiyal-kudiyiyal" );
			featuredPratilipiDataList.add( "/aravindh-sachidanandam/thatpam-thavir" );
			
			newReleasesPratilipiDataList.add( "/s-kothandaraman/oru-vaasagam" );
			newReleasesPratilipiDataList.add( "/p-raghavan/booku" );
			newReleasesPratilipiDataList.add( "/p-raghavan/rendu" );
			newReleasesPratilipiDataList.add( "/tamil-keechargal-twitamils-group/twitter-kaiyedu" );
			newReleasesPratilipiDataList.add( "/subramanian-sivam/saadhika-pirandhavar-neengal" );
			newReleasesPratilipiDataList.add( "/chithran-ragunath/tharunam" );
			newReleasesPratilipiDataList.add( "/kalki-r-krishnamoorthy/ponniyin-selvan-condense" );
			newReleasesPratilipiDataList.add( "/t-s-varadhan/moondram-puram" );
			newReleasesPratilipiDataList.add( "/vaa-mu-komu/pudhu-kavidhai-thogudhi" );
			newReleasesPratilipiDataList.add( "/n-chokkan/a-r-rahman" );
			newReleasesPratilipiDataList.add( "/ra-krishnaiyaa/arasum-puratchiyum" );
			newReleasesPratilipiDataList.add( "/aravindh-sachidanandam/bothitharmar-muthal-jamesbond-varai" );
			
		} else if( languageId == 5965057007550464L ){
			//LANGUAGE : GUJARATI
			topReadsPratilipiDataList.add( "/zaverchand-meghani/meghani-ni-navlikao-khand-2" );
			topReadsPratilipiDataList.add( "/p-p-kuntanpuri-yogi/akbar-ane-birbalni-ramuji-vartao" );
			topReadsPratilipiDataList.add( "/dalpatraam/mithybhiman" );
			topReadsPratilipiDataList.add( "/nimisha-dalal/kone-kahu-1" );
			topReadsPratilipiDataList.add( "/lalit-parikh/bhed-abhed" );
			topReadsPratilipiDataList.add( "/zaverchand-meghani/meghani-ni-navlikao-khand-2" );
			topReadsPratilipiDataList.add( "/lalit-parikh/rummy-master" );
			topReadsPratilipiDataList.add( "/gunavant-upadhyay/gazal-graph" );
			topReadsPratilipiDataList.add( "/hema-patel/virangna-saroj-shrof" );
			topReadsPratilipiDataList.add( "/pratima-maniyar/laal-swetar" );
			
			featuredPratilipiDataList.add( "/dalpatraam/mithybhiman" );
			featuredPratilipiDataList.add( "/suresh-jani/antarni-vani" );
			featuredPratilipiDataList.add( "/suresh-jani/200-avlokano" );
			featuredPratilipiDataList.add( "/nanhalaal-dalpatraam-kavi/pankhadio" );
			featuredPratilipiDataList.add( "/lalit-parikh/varta-re-varta-3" );
			featuredPratilipiDataList.add( "/zaverchand-meghani/tulsi-kyaro" );
			featuredPratilipiDataList.add( "/lalit-parikh/bhed-abhed" );
			featuredPratilipiDataList.add( "/zaverchand-meghani/tulsi-kyaro" );
			featuredPratilipiDataList.add( "/mohandas-karamchand-gandhi-gandhiji/satya-na-prayogo-athva-atmakatha-bhag-1" );
			featuredPratilipiDataList.add( "/sneha-patel/paglu-varta-sangrah" );
			featuredPratilipiDataList.add( "/zaverchand-meghani/meghani-ni-navlikao-khand-2" );
			
			newReleasesPratilipiDataList.add( "/zaverchand-meghani/meghani-ni-navlikao-khand-2" );
			newReleasesPratilipiDataList.add( "/lalit-parikh/bhed-abhed" );
			newReleasesPratilipiDataList.add( "/suresh-jani/200-avlokano" );
			newReleasesPratilipiDataList.add( "/suresh-jani/antarni-vani" );
			newReleasesPratilipiDataList.add( "/lalit-parikh/varta-re-varta-3" );
			newReleasesPratilipiDataList.add( "/zaverchand-meghani/tulsi-kyaro" );
			newReleasesPratilipiDataList.add( "/nimisha-dalal/kone-kahu-1" );
			newReleasesPratilipiDataList.add( "/nanhalaal-dalpatraam-kavi/pankhadio" );
			newReleasesPratilipiDataList.add( "/lata-bhatt/prem-ras-piyalo-pidho" );
			newReleasesPratilipiDataList.add( "/ankit-gadhiya-nirbhay/anek-harifo-ni-hod-par" );
			newReleasesPratilipiDataList.add( "/lalit-parikh/bhavebhav" );
			newReleasesPratilipiDataList.add( "/anaami/panchtantra-ni-vartao" );
			newReleasesPratilipiDataList.add( "/mukul-jani/seven-days-six-night" );
		}
		List<String> responseDataList = new ArrayList<>();
		if( categoryId.equals( MobileInitApi.TOP_READS_CATEGORY_ID )){
			responseDataList = topReadsPratilipiDataList;
		} else if( categoryId.equals( MobileInitApi.FEATURED_CATEGORY_ID )){
			responseDataList = featuredPratilipiDataList;
		} else if( categoryId.equals( MobileInitApi.NEW_RELEASES_CATEGORY_ID )){
			responseDataList = newReleasesPratilipiDataList;
		}
		
		if( responseDataList.size() == 0 )
			return null;
		else
			listSize = responseDataList.size();
		
		int startIndex;
		if( cursor != null )
			startIndex = Integer.parseInt( cursor );
		else 
			startIndex = 0;
		
		if( startIndex >= listSize )
			return null;
		
		int endIndex = startIndex + resultCount;
		if( endIndex > listSize )
			endIndex = listSize;
		
		List<PratilipiData> tempPratilipiDataList = new ArrayList<>();
		for( int i = startIndex; i < endIndex; ++i ){
			Page page = DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() )
								.getPage( responseDataList.get( i ));
			Pratilipi pratilipi = com.pratilipi.data.access.DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() )
										.getPratilipi( page.getPrimaryContentId() );
			Author author = com.pratilipi.data.access.DataAccessorFactory.getDataAccessor( this.getThreadLocalRequest() )
										.getAuthor( pratilipi.getAuthorId() );
			List<Category> categoryList = PratilipiCategoryContentHelper.getPratilipiCategoryList( pratilipi.getId(), this.getThreadLocalRequest() );
			PratilipiData pratilipiData = PratilipiContentHelper.createPratilipiData( pratilipi, null, author, categoryList, this.getThreadLocalRequest() );
			
			tempPratilipiDataList.add( pratilipiData );
		}
		
		if( endIndex == listSize )
			cursor = null;
		else
			cursor = String.valueOf( endIndex );
		
		return new DataListCursorTuple<>( tempPratilipiDataList, cursor );
	}

}
