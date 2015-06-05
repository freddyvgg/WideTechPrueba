package co.widetech.widetechprueba.utils;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import co.widetech.widetechprueba.jsonparser.JsonParser;
import co.widetech.widetechprueba.to.Main;


public class Utils {
    /**
     * Logging tag used by the debugger. 
     */
    private final static String TAG = Utils.class.getCanonicalName();


    private final static String sWeb_Service_URL =
        "http://taxisws.widetech.co/API/rest/CabAdvancedRequestRest";

    
    public static Main callWebService(Main request) {
	    	Log.d(TAG, "callWebService Utils called");
	    	final Main result;
    		JsonParser parser = new JsonParser();
            String requestString = parser.parseMainToJson(request).toString();

            try {
                // Append the location to create the full URL.
                final URL url =
                    new URL(sWeb_Service_URL);
                
                Log.d(TAG, "URL: "+url.toString());
                Log.d(TAG, "Request: "+requestString);
                
                
                String urlParameters  = requestString;
				byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
				HttpURLConnection conn= (HttpURLConnection) url.openConnection();           
				conn.setDoOutput( true );
				conn.setInstanceFollowRedirects( false );
				conn.setRequestMethod( "POST" );
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Accept-Encoding", "UTF-8"); 
				conn.setRequestProperty("Accept", "application/json"); 
				conn.setUseCaches( false );
				try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
				   wr.write( postData );
				}
                
				if(conn.getResponseCode()!=HttpURLConnection.HTTP_OK)
					return null;
                
                // Sends the GET request and reads the Json results.
                try (InputStream in =
                     new BufferedInputStream(conn.getInputStream())) {
                     // Create the parser.
                	//System.out.println(convertStreamToString(in));
                	result = parser.parseJsonStream(in);
                     
                } finally {
                	conn.disconnect();
                }
            } catch (IOException e) {
            	Log.d(TAG, "Error reading");
                e.printStackTrace();
                return null;
            }
            
           return result;
	}
    
    
    /**
     * This method is used to hide a keyboard after a user has
     * finished typing the url.
     */
    public static void hideKeyboard(Activity activity,
                                    IBinder windowToken) {
        InputMethodManager mgr =
           (InputMethodManager) activity.getSystemService
            (Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken,
                                    0);
    }

    /**
     * Show a toast message.
     */
    public static void showToast(Context context,
                                 String message) {
        Toast.makeText(context,
                       message,
                       Toast.LENGTH_SHORT).show();
    }

    /**
     * Ensure this class is only used as a utility.
     */
    private Utils() {
        throw new AssertionError();
    } 
    
    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
