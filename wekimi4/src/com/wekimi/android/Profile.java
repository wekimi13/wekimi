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



import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity {
    private String jsonResult;
    private String url = "http://wekimi13.dothome.co.kr/selectEvent.php";
    private String url2 = "http://wekimi13.dothome.co.kr/selectActivity.php";

    private ListView listView;
    List<NameValuePair> nameValuePairs;
    TextView eventView;
    String eenum;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.viewprofile);
	
	    EditText etName = (EditText)findViewById(R.id.etName);
        EditText etPhone = (EditText)findViewById(R.id.etPhone);
        EditText etGender = (EditText)findViewById(R.id.etGender);
        EditText etCharacter = (EditText)findViewById(R.id.etCharacter);
        eventView = (TextView)findViewById(R.id.eventView);
        listView = (ListView)findViewById(R.id.activityView);

        
        etName.setText(((Person)this.getApplication()).getName());
        etPhone.setText(((Person)this.getApplication()).getPhone());
        etGender.setText(((Person)this.getApplication()).getGender());
        etCharacter.setText(((Person)this.getApplication()).getCharacter());
        
        SelectEvent task = new SelectEvent();
		    // passes values for the urls string array 
		    task.execute(new String[] { url });
		    
	        SelectActivity task2 = new SelectActivity();
			    // passes values for the urls string array 
			    task2.execute(new String[] { url2 });
        
	    // TODO Auto-generated method stub
	}
    private class SelectEvent extends AsyncTask<String, Void, String> {
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
    	 //List<Map<String, String>> employeeList = new ArrayList<Map<String, String>>();
    	 
    	  try {
    	   JSONObject jsonResponse = new JSONObject(jsonResult);
    	   JSONArray jsonMainNode = jsonResponse.optJSONArray("event");
    	 
    	   for (int i = 0; i < jsonMainNode.length(); i++) {
    	    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
    	    eenum = jsonChildNode.optString("eenum");
    	    String ephone = jsonChildNode.optString("ephone");
    	    String edate = jsonChildNode.optString("edate");
    	    String etype = jsonChildNode.optString("etype");
    	    String emsg = jsonChildNode.optString("emsg");

    	    String outPut = "회원님의 상태는 : " + etype + "발생시간 : " + edate;
    	    
    	    eventView.setText(outPut);
    	   }
    	  } catch (JSONException e) {
    	   Toast.makeText(getApplicationContext(), "Error" + e.toString(),
    	     Toast.LENGTH_LONG).show();
    	  }
    	 
    	  
    	 }
    	 
     private class SelectActivity extends AsyncTask<String, Void, String> {
   	  @Override
   	  protected String doInBackground(String... params) {
   	   HttpClient httpclient = new DefaultHttpClient();
   	   HttpPost httppost = new HttpPost(params[0]); //url
   	   
   	   try {
  
   		   ////
   		   nameValuePairs = new ArrayList<NameValuePair>(1);
   		   nameValuePairs.add(new BasicNameValuePair("number", eenum));
   	

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
   	   ListDrwaer2();
   	  }
   	 }// end async task
   	 


    
    
    // build hash set for list view
    public void ListDrwaer2() {
   	 List<Map<String, String>> activityList = new ArrayList<Map<String, String>>();
   	 
   	  try {
   	   JSONObject jsonResponse = new JSONObject(jsonResult);
   	   JSONArray jsonMainNode = jsonResponse.optJSONArray("activity");
   	 
   	   for (int i = 0; i < jsonMainNode.length(); i++) {
   	    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
   	    String aphone = jsonChildNode.optString("aphone");
   	    String adate = jsonChildNode.optString("adate");


   	    String outPut2 =  aphone + "님이 요청응답 " + adate;
   	    
   	    activityList.add(createActivity("activities", outPut2));
   	   }
   	  } catch (JSONException e) {
   	   Toast.makeText(getApplicationContext(), "Error" + e.toString(),
   	     Toast.LENGTH_LONG).show();
   	  }
	  SimpleAdapter simpleAdapter = new SimpleAdapter(this, activityList,
	    	    android.R.layout.simple_list_item_1,
	    	    new String[] { "activities" }, new int[] { android.R.id.text1 });
	    	  listView.setAdapter(simpleAdapter);
	    	 }
	    	 
	    	 private HashMap<String, String> createActivity(String name, String phone) {
	    	  HashMap<String, String> activityNo = new HashMap<String, String>();
	    	  activityNo.put(name, phone);
	    	  return activityNo;
	    	 }
   	 
   	  
   	 }
    /*
     * 
     * List<Map<String, String>> employeeList = new ArrayList<Map<String, String>>();
 
  try {
   JSONObject jsonResponse = new JSONObject(jsonResult);
   JSONArray jsonMainNode = jsonResponse.optJSONArray("member");
 
   for (int i = 0; i < jsonMainNode.length(); i++) {
    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
    String name = jsonChildNode.optString("name");
    String phone = jsonChildNode.optString("phone");
    String gender = jsonChildNode.optString("gender");
    String character = jsonChildNode.optString("character");
    String outPut = name + "-" + phone;
    employeeList.add(createEmployee("members", outPut));
   }
  } catch (JSONException e) {
   Toast.makeText(getApplicationContext(), "Error" + e.toString(),
     Toast.LENGTH_LONG).show();
  }
 
  SimpleAdapter simpleAdapter = new SimpleAdapter(this, employeeList,
    android.R.layout.simple_list_item_1,
    new String[] { "members" }, new int[] { android.R.id.text1 });
  listView.setAdapter(simpleAdapter);
 }
 
 private HashMap<String, String> createEmployee(String name, String phone) {
  HashMap<String, String> employeeNameNo = new HashMap<String, String>();
  employeeNameNo.put(name, phone);
  return employeeNameNo;
 }
     */
     


