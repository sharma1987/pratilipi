package com.pratilipi.common.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.pratilipi.common.exception.UnexpectedServerException;
import com.pratilipi.data.DataAccessor;
import com.pratilipi.data.DataAccessorFactory;

public class FacebookApi {

	private static final Logger logger =
			Logger.getLogger( FacebookApi.class.getName() );

	
	private static final Gson gson = new GsonBuilder().create();
	
	private static final String APP_PROPERTY_ID = "Facebook.Credentials";
	
	private static final String GRAPH_API_URL = "https://graph.facebook.com/v2.2";
	
	
	public static String getAppId() {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Map<String, String> facebookCredentials = dataAccessor.getAppProperty( APP_PROPERTY_ID ).getValue();
		return facebookCredentials.get( "appId" );
	}
	
	public static String getAccessToken() {
		DataAccessor dataAccessor = DataAccessorFactory.getDataAccessor();
		Map<String, String> facebookCredentials = dataAccessor.getAppProperty( APP_PROPERTY_ID ).getValue();
		return facebookCredentials.get( "appId" ) + "|" + facebookCredentials.get( "appSecret" );
	}
	
	public static long getUrlShareCount( String url ) throws UnexpectedServerException {
		try {
			String requestUrl = GRAPH_API_URL
					+ "?id=" + URLEncoder.encode( url, "UTF-8" )
					+ "&access_token=" + getAccessToken();

			String responsePayload = IOUtils.toString( new URL( requestUrl ).openStream(), "UTF-8" );
			logger.log( Level.INFO, "Response Payload: " + responsePayload );
			
			JsonElement responseJson = gson.fromJson( responsePayload, JsonElement.class );
			JsonElement shareJson = responseJson.getAsJsonObject().get( "share" );
			if( shareJson == null )
				return 0L;
			JsonElement shareCountJson = shareJson.getAsJsonObject().get( "share_count" );

			return shareCountJson != null ? shareCountJson.getAsLong() : 0L; 
		} catch( IOException e ) {
			throw new UnexpectedServerException();
		}
	}
	
}
