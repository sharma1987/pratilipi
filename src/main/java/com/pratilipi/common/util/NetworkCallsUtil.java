package com.pratilipi.common.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.pratilipi.common.exception.UnexpectedServerException;

public class NetworkCallsUtil {
	
	private static final Logger logger = Logger.getLogger( NetworkCallsUtil.class.getName() );
	
	private static String getUrlParameterString( Map<String, String> keyValueParameters ) {
		// Forming URL parameter String
		String urlParameters = "";
		for ( Map.Entry<String, String> entry : keyValueParameters.entrySet() ) 
		    urlParameters = urlParameters + entry.getKey() + "=" + entry.getValue() + "&" ;
		
		return urlParameters.substring( 0, urlParameters.length() - 1 );
	}
	
	public static String makeGetCall( String targetURL, Map<String, String> keyValueParameters ) 
			throws UnexpectedServerException {
		
		String requestUrl = targetURL + "?" + getUrlParameterString( keyValueParameters );
		logger.log( Level.INFO, "Network GET Request : " + requestUrl );
		
		String response = null;
		try {
			response = IOUtils.toString( new URL( requestUrl ).openStream(), "UTF-8" );
			logger.log( Level.INFO, "Network GET Response : " + response );
		} catch ( IOException e ) {
			logger.log( Level.SEVERE, "GET call failed for URL : " + requestUrl, e );
			throw new UnexpectedServerException();
		}
		
		return new Gson().fromJson( response.toString(), JsonElement.class ).toString();
	}
	
	public static String makePostCall( String targetURL, Map<String, String> keyValueParameters )
			throws UnexpectedServerException {
		
		URL url = null;
		HttpURLConnection connection = null;
		String urlParameters = getUrlParameterString( keyValueParameters );
		
		// Making the network call
		try {
			//Create connection
			url = new URL( targetURL );
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod( "POST" );
			connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
			connection.setRequestProperty( "Content-Length", "" + Integer.toString( urlParameters.getBytes().length ) );
			connection.setUseCaches( false );
			connection.setDoInput( true );
			connection.setDoOutput( true );

			//Send request
			DataOutputStream dataOutputStream = new DataOutputStream( connection.getOutputStream() );
			dataOutputStream.writeBytes( urlParameters );
			dataOutputStream.flush();
			dataOutputStream.close();
			logger.log( Level.INFO, "Network POST Request : " + targetURL + "?" + urlParameters );

			//Get Response	
			InputStream inputStream = connection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( inputStream ) );
			String line;
			StringBuffer response = new StringBuffer();
			while( ( line = bufferedReader.readLine() ) != null )
				response.append( line + "\n" );
			
			bufferedReader.close();
			String responseString = new Gson().fromJson( response.toString(), JsonElement.class ).toString();
			logger.log( Level.INFO, "Network POST Response : " + responseString );
			return responseString;
		
		} catch ( IOException e ) {
			logger.log( Level.SEVERE, "POST call failed for URL : " + targetURL + "?" + urlParameters, e );
			throw new UnexpectedServerException();
			
		} finally {
			if( connection != null ) 
				connection.disconnect(); 
		}
	}
}
