package com.pratilipi.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.common.type.Language;
import com.pratilipi.i18n.I18n;

public class SiteElementUtil {

	public static final String defaultLang = "en";

	
	public static void main( String args[] ) throws IOException,
			UnexpectedServerException, URISyntaxException,
			ClassNotFoundException {

		String elementFolderName = args[0];
		String i18nElementFolderNamePrefix = args[1];
		
		
		for( Language language : Language.values() ) {

			File elementFolder = new File( elementFolderName );
			for( String elementName : elementFolder.list() ) {
				if( ! elementName.endsWith( ".html" ) )
					continue;
				
				File element = new File( elementFolder, elementName );
				if( element.isDirectory() )
					continue;

				// Data model required for i18n element generation
				Map<String, Object> dataModel = new HashMap<>();
				dataModel.put( "lang", language.getCode() );
				dataModel.put( "language", language );
				dataModel.put( "languageList", Language.values() );
				dataModel.put( "domain", language.getHostName() );
				dataModel.put( "fbAppId", "293990794105516" );
				dataModel.put( "_strings", I18n.getStrings( language ) );

				// I18n element file output stream
				File i18nElementFolder = new File( i18nElementFolderNamePrefix + language.getCode() );
				i18nElementFolder.mkdir();
				File i18nElement = new File( i18nElementFolder, elementName );
				i18nElement.createNewFile();
				OutputStreamWriter i18nElementOS = new OutputStreamWriter( new FileOutputStream( i18nElement ), "UTF-8" );
				
				// The magic
				FreeMarkerUtil.processTemplate(
						dataModel,
						elementFolderName + "/" + elementName,
						i18nElementOS );

				// closing the output stream
				i18nElementOS.close();
			}
			
		}
	}
	
}
