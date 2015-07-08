package com.pratilipi.pagecontent.pratilipis;

import java.util.List;

import com.claymus.data.transfer.PageContent;
import com.pratilipi.common.type.PratilipiState;
import com.pratilipi.common.type.PratilipiType;
import com.pratilipi.commons.shared.PratilipiFilter;

public interface PratilipisContent extends PageContent {
	
	String getTitle();
	
	void setTitle( String title );
	
	List<Long> getPratilipiIdList();

	void setPratilipiIdList( List<Long> pratilipIdList );
	

	PratilipiType getPratilipiType();

	void setPratilipiType( PratilipiType pratilipiType );

	Long getLanguageId();

	void setLanguageId( Long languageId );
	
	Long getAuthorId();

	void setAuthorId( Long authorId );
	
	PratilipiState getPratilipiState();

	void setPratilipiState( PratilipiState state );
	
	PratilipiFilter toFilter();

}
