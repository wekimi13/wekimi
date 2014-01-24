package com.wekimi.android;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

 
public class GetJSON extends Activity {
 
    TextView tvname, tvemail, tvtwitter;

    private static String url = "http://wekimi13.cafe24app.com/user?phone=01091495358";
 
    //Person person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
 
        // get reference to the views
        //tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
        //tvname = (TextView)findViewById(R.id.name);
        //tvemail = (TextView)findViewById(R.id.email);
        //tvtwitter = (TextView)findViewById(R.id.uid);
        //btnPost = (Button) findViewById(R.id.btnPost);
 

 
    
        InputStream inputStream = null;
        String result = "";
        try {
 
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            
            HttpResponse httpResponse = httpclient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            
            if(httpEntity != null) {
            
            	inputStream = httpEntity.getContent();
            	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            	StringBuilder stringBuilder = new StringBuilder();
            	
            	String ligneLue = bufferedReader.readLine();
            	
            	while(ligneLue != null){
                    stringBuilder.append(ligneLue + " \n");
                    ligneLue = bufferedReader.readLine();
                }
            	bufferedReader.close();
            	
            	JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            	Log.i("Chaine JSON", stringBuilder.toString());
            	
            	//JSONObject jsonResultSet = jsonObject.getJSONObject("name");
            	String result2 = jsonObject.getString("name");
            	String result3 = jsonObject.getString("phone");
                //tvname.setText(result2);
                //tvemail.setText(result3);
            	//result = jsonObject.getString("name");
            	

            }
 
            // 2. make POST request to the given URL
            //HttpPost httpPost = new HttpPost(url);
 /*
            String json = "";
 
            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "Choyeon Lee");
            jsonObject.put("phone", "01053197084");
            jsonObject.put("gender", "f");
            jsonObject.put("character", "tall");
            jsonObject.put( "othername1", "John");
            jsonObject.put( "othername2", "John");
            jsonObject.put( "othername3", "John");
            jsonObject.put( "othername4", "John");
            jsonObject.put( "othername5", "John");
            jsonObject.put( "othername6", "John");
            jsonObject.put( "othername7", "John");
            jsonObject.put( "othername8", "John");
            jsonObject.put( "othername9", "John");
            jsonObject.put( "othername10", "John");
            
            jsonObject.put( "otherphone1", "01012341234");
            jsonObject.put( "otherphone2", "01012341234");
            jsonObject.put( "otherphone3", "01012341234");
            jsonObject.put( "otherphone4", "01012341234");
            jsonObject.put( "otherphone5", "01012341234");
            jsonObject.put( "otherphone6", "01012341234");
            jsonObject.put( "otherphone7", "01012341234");
            jsonObject.put( "otherphone8", "01012341234");
            jsonObject.put( "otherphone9", "01012341234");
            jsonObject.put( "otherphone10", "01012341234");
            */
            
            /*
            
            JSONObject others = new JSONObject();
            others.put( "name", "John");
            others.put( "phone", "01012341234");
            
            JSONObject others2 = new JSONObject();
            others2.put( "name", "Cathy");
            others2.put( "phone", "01012341233");
            
            JSONObject others3 = new JSONObject();
            others3.put( "name", "Kate");
            others3.put( "phone", "01012399008");
            
            jsonObject.accumulate("others", others);
            jsonObject.accumulate("others", others2);
            jsonObject.accumulate("others", others3);
            
*/
            //jsonObject.accumulate("name", "sungeun An");
            
            
 /*
            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
            Log.d("aaaaaaaaaaa",json);
            Log.d("aaaaaaaaaaa","jsonString");
          
 
            // ** Alternative way to convert Person object to JSON string usin Jackson Lib 
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person); 
 
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);
 
            // 6. set httpPost Entity
            httpPost.setEntity(se);
 
            // 7. Set some headers to inform server about the type of the content   
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
 
            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);
 
            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 */
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

 
    }
}
 