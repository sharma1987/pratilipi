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
import com.claymus.data.transfer.Page;
import com.pratilipi.api.MobileInitApi;
import com.pratilipi.commons.shared.PratilipiFilter;
import com.pratilipi.data.transfer.Category;
import com.pratilipi.data.transfer.shared.PratilipiData;
import com.pratilipi.data.type.Author;
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
		pratilipiFilter.setState( request.getState() );
		
		DataListCursorTuple<PratilipiData> pratilipiListCursorTuple;
		
		if( request.getCategory() != null ){
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
	
	private DataListCursorTuple<PratilipiData> getCategoryPratilipiList( Long languageId, String category, Integer resultCount, String cursor ){
		
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
			
		} else if( languageId == 6319546696728576L ){
			//LANGUAGE : TAMIL
			topReadsPratilipiDataList.add( "/p-raghavan/rendu" );
			topReadsPratilipiDataList.add( "/subashini-tremmel/payanangal-thodargindrana-then-korea-2003-1" );
			topReadsPratilipiDataList.add( "/dubukku/namadevarum-kaipidi-sundalum" );
			topReadsPratilipiDataList.add( "/kalki-r-krishnamoorthy/ponniyin-selvan-condensed" );
			topReadsPratilipiDataList.add( "/p-raghavan/booku" );
			topReadsPratilipiDataList.add( "/chithran-ragunath/tharunam" );
			
			featuredPratilipiDataList.add( "/subashini-tremmel/en-sarithiram-u-ve-sa-vudan-oru-ula-pagudhi-i" );
			featuredPratilipiDataList.add( "/jothiji-tiruppur/oru-thozhirchaalayin-kurippugal" );
			featuredPratilipiDataList.add( "/subramanian-sivam/saadhika-pirandhavar-neengal" );
			featuredPratilipiDataList.add( "/tanjore-v-gopalan/iniyavai-naarpadhu" );
			featuredPratilipiDataList.add( "/s-kothandaraman/oru-vaasagam" );
			featuredPratilipiDataList.add( "/tamil-keechargal-twitamils-group/twitter-kaiyedu" );
			
			newReleasesPratilipiDataList.add( "/s-kothandaraman/oru-vaasagam" );
			newReleasesPratilipiDataList.add( "/p-raghavan/booku" );
			newReleasesPratilipiDataList.add( "/p-raghavan/rendu" );
			newReleasesPratilipiDataList.add( "/tamil-keechargal-twitamils-group/twitter-kaiyedu" );
			newReleasesPratilipiDataList.add( "/subramanian-sivam/saadhika-pirandhavar-neengal" );
			newReleasesPratilipiDataList.add( "/chithran-ragunath/tharunam" );
			newReleasesPratilipiDataList.add( "/kalki-r-krishnamoorthy/ponniyin-selvan-condense" );
		} else if( languageId == 5965057007550464L ){
			//LANGUAGE : GUJARATI
			topReadsPratilipiDataList.add( "/zaverchand-meghani/meghani-ni-navlikao-khand-2" );
			topReadsPratilipiDataList.add( "/p-p-kuntanpuri-yogi/akbar-ane-birbalni-ramuji-vartao" );
			topReadsPratilipiDataList.add( "/dalpatraam/mithybhiman" );
			topReadsPratilipiDataList.add( "/nimisha-dalal/kone-kahu-1" );
			topReadsPratilipiDataList.add( "/lalit-parikh/bhed-abhed" );
			topReadsPratilipiDataList.add( "/zaverchand-meghani/meghani-ni-navlikao-khand-2" );
			
			featuredPratilipiDataList.add( "/dalpatraam/mithybhiman" );
			featuredPratilipiDataList.add( "/suresh-jani/antarni-vani" );
			featuredPratilipiDataList.add( "/suresh-jani/200-avlokano" );
			featuredPratilipiDataList.add( "/nanhalaal-dalpatraam-kavi/pankhadio" );
			featuredPratilipiDataList.add( "/lalit-parikh/varta-re-varta-3" );
			featuredPratilipiDataList.add( "/zaverchand-meghani/tulsi-kyaro" );
			featuredPratilipiDataList.add( "/lalit-parikh/bhed-abhed" );
			
			newReleasesPratilipiDataList.add( "/zaverchand-meghani/meghani-ni-navlikao-khand-2" );
			newReleasesPratilipiDataList.add( "/lalit-parikh/bhed-abhed" );
			newReleasesPratilipiDataList.add( "/suresh-jani/200-avlokano" );
			newReleasesPratilipiDataList.add( "/suresh-jani/antarni-vani" );
			newReleasesPratilipiDataList.add( "/lalit-parikh/varta-re-varta-3" );
			newReleasesPratilipiDataList.add( "/zaverchand-meghani/tulsi-kyaro" );
			newReleasesPratilipiDataList.add( "/nimisha-dalal/kone-kahu-1" );
			newReleasesPratilipiDataList.add( "/nanhalaal-dalpatraam-kavi/pankhadio" );
		}
		List<String> responseDataList = new ArrayList<>();
		if( category.toLowerCase().equals( MobileInitApi.TOP_READS_CATEGORY_ID.toLowerCase() )){
			responseDataList = topReadsPratilipiDataList;
		} else if( category.toLowerCase().equals( MobileInitApi.FEATURED_CATEGORY_ID.toLowerCase() )){
			responseDataList = featuredPratilipiDataList;
		} else if( category.toLowerCase().equals( MobileInitApi.NEW_RELEASES_CATEGORY_ID.toLowerCase() )){
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
