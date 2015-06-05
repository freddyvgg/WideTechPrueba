package co.widetech.widetechprueba.jsonparser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.JsonReader;
import android.util.JsonToken;
import co.widetech.widetechprueba.to.GP;
import co.widetech.widetechprueba.to.Main;
import co.widetech.widetechprueba.to.Response;

/**
 * Parses the Json weather data returned from the Weather Services API
 * and returns a List of JsonWeather objects that contain this data.
 */
public class JsonParser {

	
	
	public JSONObject parseMainToJson(Main main)
	{
		if(main==null)
			return null;
		JSONObject result = new JSONObject();
		
		try {
			result.put(Main.GP_JSON, parseArrayGPToJson(main.getGP()));
			result.put(Main.Response_JSON,parseResponseToJson(main.getResponse()));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
    private JSONArray parseArrayGPToJson(List<GP> gp) {
    	if(gp==null)
			return null;
		JSONArray result = new JSONArray();
		for(GP item:gp)
		{
			result.put(parseGPToJson(item));
		}
		return result;
	}

    public JSONObject parseGPToJson(GP gp)
	{
    	if(gp==null)
			return null;
		JSONObject result = new JSONObject();
		
		try {
			result.put(GP.Name_JSON, gp.getName());
			result.put(GP.Value_JSON,gp.getValue());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result;
	}
    
    public JSONObject parseResponseToJson(Response arg)
	{
    	if(arg==null)
			return null;
		JSONObject result = new JSONObject();
		
		try {
			result.put(Response.Code_JSON, arg.getCode());
			result.put(Response.Desc_JSON, arg.getDesc());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	/**
     * Parse the @a inputStream and convert it into a List of JsonWeather
     * objects.
     */
    public Main parseJsonStream(InputStream inputStream)
        throws IOException {
        // TODO -- you fill in here.
    	try (JsonReader reader =
                new JsonReader(new InputStreamReader(inputStream,
                                                     "UTF-8"))) {
    		return parseMain(reader);
    	}
    }

    public Main parseMain(JsonReader reader) 
            throws IOException {

    		Main result = new Main();
            reader.beginObject();
            
            try{
            	while(reader.hasNext()){
            		String name = reader.nextName();
            		if(reader.peek()!=JsonToken.NULL){
	            		switch(name){
	            			
	            		case Main.GP_JSON:
	            			result.setGP(parseArrayGP(reader));
	            			break;
	            			
	            		case Main.Response_JSON:
	            			result.setResponse(parseResponse(reader));
	            			break;
	            		
	            			default:
	            				reader.skipValue();
	            				break;
	            		}
            		}else{
            			reader.skipValue();
            		}
            		
            		
            	}
            }finally{
            	reader.endObject();
            }
            
            return result;
        }
    
    /**
     * Parse a Json stream and convert it into a List of JsonWeather
     * objects.
     */
    public List<GP> parseArrayGP(JsonReader reader)
        throws IOException {
    	
        // TODO -- you fill in here.
    	List<GP> result = new ArrayList<GP>();
    	if(reader.peek()==JsonToken.BEGIN_ARRAY){
	    	reader.beginArray();
	    	
	    	try{
		    	while(reader.hasNext())
		    	{
		    		result.add(parseGP(reader));
		    	}
	    	}finally{
	    		reader.endArray();
	    	}

    	}else
    	{
    		result.add(parseGP(reader));
    	}
    	
    	return result;
    }
    
    public GP parseGP(JsonReader reader) 
            throws IOException {

    	GP result = new GP();
            reader.beginObject();
            
            try{
            	while(reader.hasNext()){
            		String name = reader.nextName();
            		if(reader.peek()!=JsonToken.NULL){
	            		switch(name){
	            			
	            		case GP.Name_JSON:
	            			result.setName(reader.nextString());
	            			break;
	            			
	            		case GP.Value_JSON:
	            			result.setValue(reader.nextString());
	            			break;
	            		
	            			default:
	            				reader.skipValue();
	            				break;
	            		}
	            	}else{
	        			reader.skipValue();
	        		}
            		
            		
            	}
            }finally{
            	reader.endObject();
            }
            
            return result;
        }
    
    public Response parseResponse(JsonReader reader) 
            throws IOException {

    	Response result = new Response();
            reader.beginObject();
            
            try{
            	while(reader.hasNext()){
            		String name = reader.nextName();
            		if(reader.peek()!=JsonToken.NULL){
	            		switch(name){
	            			
	            		case Response.Code_JSON:
	            			result.setCode(reader.nextInt());
	            			break;
	            			
	            		case Response.Desc_JSON:
	            			result.setDesc(reader.nextString());
	            			break;
	            		
	            			default:
	            				reader.skipValue();
	            				break;
	            		}
	            	}else{
	        			reader.skipValue();
	        		}
            		
            		
            	}
            }finally{
            	reader.endObject();
            }
            
            return result;
        }
}
