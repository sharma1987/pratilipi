package com.pratilipi.pagecontent.category;

import com.claymus.pagecontent.PageContentHelper;
import com.pratilipi.pagecontent.category.shared.CategoryContentData;

public class CategoryContentHelper extends PageContentHelper<
		CategoryContent,
		CategoryContentData,
		CategoryContentProcessor> {

	@Override
	public String getModuleName() {
		return "Category";
	}

	@Override
	public Double getModuleVersion() {
		return 5.3;
	}

}
