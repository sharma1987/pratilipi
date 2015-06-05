package com.pratilipi.api.shared;

import java.util.List;

import com.claymus.api.shared.GenericResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pratilipi.data.transfer.shared.PratilipiData;

@SuppressWarnings( "serial" )
public class GetMobileInitResponse extends GenericResponse {

	private String response;
	
	public GetMobileInitResponse(){
		response = new String();
	}

	public void attachToResponse( String name, String id, List<PratilipiData> content ){
		Gson gson = new GsonBuilder().create();
		JsonArray jsonArray = new JsonArray();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty( "name", name );
		jsonObject.addProperty( "id", id );
		
		jsonObject.addProperty( "content",  gson.toJson( content ));

		jsonArray.add( jsonObject );
		
		response = response + gson.toJson( jsonArray );
	}
}
