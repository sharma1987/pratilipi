package com.pratilipi.api.shared;

import java.util.List;

import com.claymus.api.shared.GenericResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pratilipi.data.transfer.shared.PratilipiData;

@SuppressWarnings( "serial" )
public class GetMobileInitResponse extends GenericResponse {

	private JsonArray response;
	
	public GetMobileInitResponse(){
		response = new JsonArray();
	}

	public void attachToResponse( String name, Long id, List<PratilipiData> content ){
		Gson gson = new GsonBuilder().create();
		JsonElement element = gson.toJsonTree( content );
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty( "name", name );
		jsonObject.addProperty( "id", id );
		jsonObject.add( "content", element.getAsJsonArray() );	
		
		response.add( jsonObject );
	}
}
