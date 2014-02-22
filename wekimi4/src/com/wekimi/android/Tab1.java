package com.wekimi.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.app.Activity;
 
 
public class Tab1 extends Activity {
	private String jsonResult;
    private String url = "http://wekimi13.dothome.co.kr/selectMyEvent.php";

    private ListView listView;
    List<NameValuePair> nameValuePairs;
 
    /** Called when the activity is first created. */
       
      @Override
      public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.tab1);
          listView = (ListView)findViewById(R.id.MyEventView);
          
          SelectMyEvent task = new SelectMyEvent();
		    // passes values for the urls string array 
		    task.execute(new String[] { url });
        }
      
      private class SelectMyEvent extends AsyncTask<String, Void, String> {
    	  @Override
    	  protected String doInBackground(String... params) {
    	   HttpClient httpclient = new DefaultHttpClient();
    	   HttpPost httppost = new HttpPost(params[0]); //url
    	   
    	   try {
   
    		   ////
    		   nameValuePairs = new ArrayList<NameValuePair>(1);
    		   nameValuePairs.add(new BasicNameValuePair("userPhone", "01040395540"));
    	

    		   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    		   /////
    	    HttpResponse response = httpclient.execute(httppost);
    	    jsonResult = inputStreamToString(
    	      response.getEntity().getContent()).toString();
    	   }
    	 
    	   catch (ClientProtocolException e) {
    	    e.printStackTrace();
    	   } catch (IOException e) {
    	    e.printStackTrace();
    	   }
    	   return null;
    	  }
    	 
    	  private StringBuilder inputStreamToString(InputStream is) {
    	   String rLine = "";
    	   StringBuilder answer = new StringBuilder();
    	   BufferedReader rd = new BufferedReader(new InputStreamReader(is));
    	 
    	   try {
    	    while ((rLine = rd.readLine()) != null) {
    	     answer.append(rLine);
    	    }
    	   }
    	 
    	   catch (IOException e) {
    	    // e.printStackTrace();
    	    Toast.makeText(getApplicationContext(),
    	      "Error..." + e.toString(), Toast.LENGTH_LONG).show();
    	   }
    	   return answer;
    	  }
    	 
    	  @Override
    	  protected void onPostExecute(String result) {
    	   ListDrwaer();
    	  }
    	 }// end async task
    	 
     

     
     
     // build hash set for list view
     public void ListDrwaer() {
    	 List<Map<String, String>> employeeList = new ArrayList<Map<String, String>>();
    	// List<Map<String, String>> dateList = new ArrayList<Map<String, String>>();


   	  try {
   		  
   	   JSONObject jsonResponse = new JSONObject(jsonResult);
   	  // Toast.makeText(getApplicationContext(),
   		   	     // "jsonResult" + jsonResult, Toast.LENGTH_LONG).show();
   		   	   
   	   JSONArray jsonMainNode = jsonResponse.optJSONArray("event");

   	 
   	   for (int i = 0; i < jsonMainNode.length(); i++) {
   	    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
   	    String edate = jsonChildNode.optString("edate");
   	    String etype = jsonChildNode.optString("etype");
   	    String emsg = jsonChildNode.optString("emsg");

   
   	    String outPut = "안성은님이 " +etype + " 했습니다.";
   	    String output_date = edate;
   	    //employeeList.add(createEmployee("myevents", outPut));
   	    //employeeList.add(createDate("dates", output_date));
   	    employeeList.add(createItem(outPut, output_date));


   	    //employeeList.add(createEmployee("myevents", outPut));
   	    //dateList.add(createDate("dates", output_date));

   	    //String outPut = "회원님의 상태는 : " + etype + "발생시간 : " + edate;

   	   
   	    
   	    //eventView.setText(outPut);
   	   }
   	  } catch (JSONException e) {
   	   Toast.makeText(getApplicationContext(), "Error" + e.toString(),
   	     Toast.LENGTH_LONG).show();
   	  }
   	
		 
		  SimpleAdapter simpleAdapter = new SimpleAdapter(this, employeeList, 
		    R.layout.custom_list_item2,
		    new String[] { "CONTENT", "DATE"}, new int[] { R.id.list_content, R.id.list_content2});
		  listView.setAdapter(simpleAdapter);
		  
		  
		 }
		 /*
		  *     public Map<String,?> createItem(String title, String caption) {  
        Map<String,String> item = new HashMap<String,String>();  
        item.put(ITEM_TITLE, title);  
        item.put(ITEM_CAPTION, caption);  
        return item;  
    }
		  */
     private HashMap<String, String> createItem(String content, String date) {
		  HashMap<String, String> item = new HashMap<String, String>();
		  item.put("CONTENT", content);
		  item.put("DATE", date);
		  return item;
		 }
     
		 private HashMap<String, String> createEmployee(String name, String phone) {
		  HashMap<String, String> employeeNameNo = new HashMap<String, String>();
		  employeeNameNo.put(name, phone);
		  return employeeNameNo;
		 }
		 
		 
		 private HashMap<String, String> createDate(String name, String phone) {
		  HashMap<String, String> employeeNameNo2 = new HashMap<String, String>();
		  employeeNameNo2.put(name, phone);
		  return employeeNameNo2;
		 }
    	 
     
}
