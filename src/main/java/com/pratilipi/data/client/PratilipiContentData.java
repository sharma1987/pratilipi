package com.pratilipi.data.client;

import java.util.List;
import java.util.UUID;


public class PratilipiContentData {
	
	public static class Chapter {

		private String title;

		private List<Page> pageList;

		
		public Chapter( String title, List<Page> pageList ) {
			this.title = title;
			this.pageList = pageList;
		}

		public String getTitle() {
			return title;
		}

		public int getPageCount() {
			return pageList == null ? 0 : pageList.size();
		}

		public Page getPage( int pageNo ) {
			return pageList == null || pageList.size() < pageNo ? null : pageList.get( pageNo - 1 );
		}

	}
	
	public static class Page {

		private List<Pagelet> pageletList;
		
		
		public Page( List<Pagelet> pageletList ) {
			this.pageletList = pageletList;
		}

		public int getPageletCount() {
			return pageletList == null ? 0 : pageletList.size();
		}

		public Pagelet getPagelet( int pageletNo ) {
			return pageletList == null || pageletList.size() < pageletNo ? null : pageletList.get( pageletNo - 1 );
		}

		public void clear() {
			this.pageletList = null;
		}
		
	}
	
	public static class Pagelet {
		
		private String id;

		private Object data;

		private PageletType type;
		

		public Pagelet( Object data, PageletType type ) {
			this.id = UUID.randomUUID().toString();
			this.data = data;
			this.type = type;
		}

		public String getId() {
			return id;
		}

		public Object getData() {
			return data;
		}

		public PageletType getType() {
			return type;
		}

	}
	
	public enum PageletType {
		HEADING, SUB_HEADING, TEXT, HTML, IMAGE
	}

	
	public List<Chapter> chapterList;

	
	public PratilipiContentData( List<Chapter> chapterList ) {
		this.chapterList = chapterList;
	}
	

	public int getChapterCount() {
		return chapterList == null ? 0 : chapterList.size();
	}

	public Chapter getChapter( int chapterNo ) {
		return chapterList == null || chapterList.size() < chapterNo ? null : chapterList.get( chapterNo - 1 );
	}
	
	public Page getPage( int pageNo ) {
		if( chapterList == null )
			return null;
		
		for( Chapter chapter : chapterList ) {
			if( pageNo > chapter.getPageCount() )
				pageNo = pageNo - chapter.getPageCount();
			else
				return chapter.getPage( pageNo );
		}
		
		return null;
	}
	
	public int getTotalPageCount() {
		if( chapterList == null )
			return 0;
		
		int totalPageCount = 0;
		for( Chapter chapter : chapterList )
			totalPageCount += chapter.getPageCount();
		return totalPageCount;
	}

}
