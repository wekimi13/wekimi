package com.wekimi.android;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity implements OnClickListener {
	
	static String name = new String();
	static String phone = new String();
	static String gender = new String();
	static String character = new String();
	static String[] othername = new String[10];
	static String[] otherphone = new String[10];
	
	EditText etname, etphone, etgender,etcharacter,etothername1, etothername2,
	etothername3, etothername4, etotherphone1,etotherphone2,etotherphone3
	,etotherphone4;
	
	Button sendReg;

   
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        //
        etname = (EditText)findViewById(R.id.RegetName);
        etphone = (EditText)findViewById(R.id.RegetPhone);
        etgender = (EditText)findViewById(R.id.RegetGender);
        etcharacter = (EditText)findViewById(R.id.RegetCharacter);
        etothername1 = (EditText)findViewById(R.id.RegetOtherName1);
        etotherphone1 = (EditText)findViewById(R.id.RegetOtherPhone1);
        etothername2 = (EditText)findViewById(R.id.RegetOtherName1);
        etotherphone2 = (EditText)findViewById(R.id.RegetOtherPhone1);
        etothername3 = (EditText)findViewById(R.id.RegetOtherName1);
        etotherphone3 = (EditText)findViewById(R.id.RegetOtherPhone1);
        etothername4 = (EditText)findViewById(R.id.RegetOtherName1);
        etotherphone4 = (EditText)findViewById(R.id.RegetOtherPhone1);
    	

        sendReg = (Button)findViewById(R.id.sendReg);
        //

        //
      //set data to textview field

        sendReg.setOnClickListener(this);

	    // TODO Auto-generated method stub
	}
	/*
	 public static String POST(String url) throws JSONException{
	        InputStream inputStream = null;
	        String result = "";
	        try {
	 
	            // 1. create HttpClient
	            HttpClient httpclient = new DefaultHttpClient();
	 
	            // 2. make POST request to the given URL
	            HttpPost httpPost = new HttpPost(url);
	 
	            String json = "";
	            

	            
	            
	            //jsonObject.put
	            //String sname= name.getText().toString();
	            //String sphone=  phone.getText().toString();
	            
	 
	            // 3. build jsonObject
	            JSONObject jsonObject = new JSONObject();
	            jsonObject.put("name", name);
	            jsonObject.put("phone", phone);
	            jsonObject.put("gender", gender);
	            jsonObject.put("character", character);
	            jsonObject.put( "othername1", othername[0]);
	            jsonObject.put( "othername2", othername[1]);
	            jsonObject.put( "othername3", othername[2]);
	            jsonObject.put( "othername4", othername[3]);
	            jsonObject.put( "othername5", "John");
	            jsonObject.put( "othername6", "John");
	            jsonObject.put( "othername7", "John");
	            jsonObject.put( "othername8", "John");
	            jsonObject.put( "othername9", "John");
	            jsonObject.put( "othername10", "John");
	            
	            jsonObject.put( "otherphone1", otherphone[0]);
	            jsonObject.put( "otherphone2", otherphone[1]);
	            jsonObject.put( "otherphone3", otherphone[2]);
	            jsonObject.put( "otherphone4", otherphone[3]);
	            jsonObject.put( "otherphone5", "01012341234");
	            jsonObject.put( "otherphone6", "01012341234");
	            jsonObject.put( "otherphone7", "01012341234");
	            jsonObject.put( "otherphone8", "01012341234");
	            jsonObject.put( "otherphone9", "01012341234");
	            jsonObject.put( "otherphone10", "01012341234");
	         
	 
	            // 4. convert JSONObject to JSON to String
	            json = jsonObject.toString();
	            Log.d("name",name);

	            Log.d("sending JSONOBJECT to server",json);
	            
	          
	 
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
	 
	        } catch (Exception e) {
	            Log.d("InputStream", e.getLocalizedMessage());
	        }
	 
	        // 11. return result
	        return result;
	    }
	*/
	    public void onClick(View view) {
	    	
	    	  name = etname.getText().toString();
	          phone=etphone.getText().toString();
	          gender=etgender.getText().toString();
	          character=etcharacter.getText().toString();
	          
	          othername[0]=etothername1.getText().toString();
	          othername[1]=etothername2.getText().toString();
	          othername[2]=etothername3.getText().toString();
	          othername[3]=etothername4.getText().toString();
	          
	          otherphone[0]=etotherphone1.getText().toString();
	          otherphone[1]=etotherphone2.getText().toString();
	          otherphone[2]=etotherphone3.getText().toString();
	          otherphone[3]=etotherphone4.getText().toString();  
	    	 
	        switch(view.getId()){
	            case R.id.sendReg:
	                new HttpAsyncTask_POST().execute("http://wekimi13.cafe24app.com/user");
	            break;
	        }
	 
	    }

	    public JSONObject BuildJSON() throws JSONException {
	           JSONObject jsonObject = new JSONObject();
	            jsonObject.put("name", name);
	            jsonObject.put("phone", phone);
	            jsonObject.put("gender", gender);
	            jsonObject.put("character", character);
	            jsonObject.put( "othername1", othername[0]);
	            jsonObject.put( "othername2", othername[1]);
	            jsonObject.put( "othername3", othername[2]);
	            jsonObject.put( "othername4", othername[3]);
	            jsonObject.put( "othername5", "John");
	            jsonObject.put( "othername6", "John");
	            jsonObject.put( "othername7", "John");
	            jsonObject.put( "othername8", "John");
	            jsonObject.put( "othername9", "John");
	            jsonObject.put( "othername10", "John");
	            
	            jsonObject.put( "otherphone1", otherphone[0]);
	            jsonObject.put( "otherphone2", otherphone[1]);
	            jsonObject.put( "otherphone3", otherphone[2]);
	            jsonObject.put( "otherphone4", otherphone[3]);
	            jsonObject.put( "otherphone5", "01012341234");
	            jsonObject.put( "otherphone6", "01012341234");
	            jsonObject.put( "otherphone7", "01012341234");
	            jsonObject.put( "otherphone8", "01012341234");
	            jsonObject.put( "otherphone9", "01012341234");
	            jsonObject.put( "otherphone10", "01012341234");
	            
	            return jsonObject;
	    	
	    }
	    
	    private class HttpAsyncTask_POST extends AsyncTask<String, Void, String> {
	        @Override
	        protected String doInBackground(String... urls) {
	        	 InputStream inputStream = null;
	 	        String result = "";
	 	        try {
	 	 
	 	            // 1. create HttpClient
	 	            HttpClient httpclient = new DefaultHttpClient();
	 	 
	 	            // 2. make POST request to the given URL
	 	            HttpPost httpPost = new HttpPost(urls[0]);
	 	            
	 	            String json = "";
	 	            
	 	           JSONObject jsonObject = new JSONObject();
		            
	 	           jsonObject = BuildJSON();
		            //Log.d("name",name);

		            // 4. convert JSONObject to JSON to String
		            json = jsonObject.toString();
		            Log.d("sending JSONOBJECT to server",json);
		            
		          
		 
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
		            Log.d("sent data","sent data");

		 /*
		            // 9. receive response as inputStream
		            inputStream = httpResponse.getEntity().getContent();
		            Log.d("Here","");
		 
		            // 10. convert inputstream to string
		            if(inputStream != null)
		                result = convertInputStreamToString(inputStream);
		            else
		                result = "Did not work!";*/
		 
	 	       } 
	 	        catch (ClientProtocolException e) {
	               e.printStackTrace();
	               } catch (IOException e) {
	               e.printStackTrace();
	       } catch (JSONException e) {
	   				// TODO Auto-generated catch block
	   				e.printStackTrace();
	   			}
				return result;
		 
		        // 11. return result
		        //return null;
	 	  
	        }
	        // onPostExecute displays the results of the AsyncTask.
	        protected void onPostExecute(String result) {
	            //Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
	       }
	        
	    }
	 //.  
	    private boolean validate(){

	            return true;    
	    }
	    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
	        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
	        String line = "";
	        String result = "";
	        while((line = bufferedReader.readLine()) != null)
	            result += line;
	 
	        inputStream.close();
	        return result;
	 
	    }  
}
