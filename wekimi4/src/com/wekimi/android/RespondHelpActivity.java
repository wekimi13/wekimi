package com.wekimi.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
 

public class RespondHelpActivity extends Activity
{
	String[] othername = new String[10];
	String[] otherphone = new String[10];
	public static JSONArray others;
	String name;
	String phone;
	String gender;
	String character;
	String pTel;
	String pName;
	
     @Override
     public void onCreate(Bundle savedInstanceState)
     {
    	 
    	 
           super.onCreate(savedInstanceState);
           setContentView(R.layout.respond);
           
           Uri uri = getIntent().getData();
           String uriString = uri.toString();
           //String passedData = uri.getSchemeSpecificPart();
           Log.v("phone : ", ""+uriString);
           if (uriString != null) {
        	   Log.v("test","inside if statement");
               pTel = uriString.split("//")[1];
               Log.v("result splitting by // : ",""+pTel);
               //String verification = vars[0].split("=")[1];
               //TODO: handle verification and username from here.
           }
           
           new HttpAsyncTask().execute("http://wekimi13.cafe24app.com/user?phone=" + pTel);
           
           TextView pName1 = (TextView)findViewById(R.id.pName);
           
           
           pName= ((Person)this.getApplication()).getName();
           Log.v("passed name:",""+pName);
           pName1.append(pName);
     }
           
           public void Setting(JSONObject jsonObject) throws JSONException {
        		 //JSONObject jsonResultSet = jsonObject.getJSONObject("name");
        	   	  //Toast.makeText(getBaseContext(), "Setting", Toast.LENGTH_SHORT).show();

        		name = jsonObject.getString("name");
        	   	phone = jsonObject.getString("phone");
        	   	gender = jsonObject.getString("gender");
        	   	character = jsonObject.getString("character");

        	   	
        	   	others = jsonObject.getJSONArray("others");
        	   	
        	   	for(int i = 0; i < others.length(); i++){
        	           JSONObject c = others.getJSONObject(i);
        	           othername[i] = c.getString("name");
        	           otherphone[i] = c.getString("phone");     
        	   	}
        	   	
        	   	
        	   	
        	   	((Person)this.getApplication()).setName(name);
        		((Person)this.getApplication()).setPhone(phone);
        		((Person)this.getApplication()).setGender(gender);
        		((Person)this.getApplication()).setCharacter(character);
        		((Person)this.getApplication()).setOthername(othername);
        		((Person)this.getApplication()).setOtherphone(otherphone);
        	   
        	   
        	   }
        	    private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
        	        @Override
        	        protected Void doInBackground(String... urls) {

        	            //Toast.makeText(getBaseContext(), "DoinBackground", Toast.LENGTH_LONG).show();

        	            InputStream inputStream =null;
        	            String result = "";
        	            
        	          try {
        	          	 
        	              // 1. create HttpClient
        	              HttpClient httpclient = new DefaultHttpClient();
        	              HttpGet httpGet = new HttpGet(urls[0]);
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
        	              	
        	              	Setting(jsonObject);
        	     	
        	              
        	              }
        	              
        	          } catch (ClientProtocolException e) {
        	              e.printStackTrace();
        	              } catch (IOException e) {
        	              e.printStackTrace();
        	      } catch (JSONException e) {
        	  				// TODO Auto-generated catch block
        	  				e.printStackTrace();
        	  			}


        	  		return null;
        	        }
     }
    
}