package com.wekimi.android;

import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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
import android.widget.ListView;
import android.widget.Toast;

public class Register extends Activity {
	
	static String name = new String();
	static String phone = new String();
	static String gender = new String();
	static String character = new String();
	static String[] othername = new String[10];
	static String[] otherphone = new String[10];
    private String jsonResult;

    private String url = "http://wekimi13.dothome.co.kr/insertMember.php";

    //private String url_contact = "http://wekimi13.dothome.co.kr/insertContact.php";

    List<NameValuePair> nameValuePairs;
	
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
        sendReg.setOnClickListener(new Button.OnClickListener() {
        	   
     		  @Override
     		  public void onClick(View v) {
     			 
     			
     			 
               

              
    			Toast.makeText(getBaseContext(), "요청즁 ... ", 
						Toast.LENGTH_SHORT).show();
    			InsertMember task = new InsertMember();

     		    // passes values for the urls string array 
     		    task.execute(new String[] { url });

  
     	 
     		  }
     		});


	}
	
	  
    private class InsertMember extends AsyncTask<String, Void, String> {
    	  @Override
    	  protected String doInBackground(String... params) {
    	   HttpClient httpclient = new DefaultHttpClient();
    	   HttpPost httppost = new HttpPost(params[0]); //url
    	   
    	   try {
   
  /*
    		   nameValuePairs = new ArrayList<NameValuePair>(8);
    		   nameValuePairs.add(new BasicNameValuePair("mname", etname.getText().toString()));
    		   nameValuePairs.add(new BasicNameValuePair("mphone", etphone.getText().toString()));
    		   nameValuePairs.add(new BasicNameValuePair("mgender", etgender.getText().toString()));
    		   nameValuePairs.add(new BasicNameValuePair("mcharacter", etcharacter.getText().toString()));
    		   nameValuePairs.add(new BasicNameValuePair("mlocation", "Cheonan"));
    		   nameValuePairs.add(new BasicNameValuePair("cname", etothername1.getText().toString()));
    		   nameValuePairs.add(new BasicNameValuePair("cphone", etotherphone1.getText().toString()));
    		   nameValuePairs.add(new BasicNameValuePair("cgroup", "friend"));
    		   */
    		   nameValuePairs = new ArrayList<NameValuePair>(8);
    		   nameValuePairs.add(new BasicNameValuePair("mname", "한글테스트"));
    		   nameValuePairs.add(new BasicNameValuePair("mphone", "01077477742"));
    		   nameValuePairs.add(new BasicNameValuePair("mgender", "여자"));
    		   nameValuePairs.add(new BasicNameValuePair("mcharacter", "까만 불테안경에 신장 160")); 
    		   nameValuePairs.add(new BasicNameValuePair("mlocation", "천안"));
    		   nameValuePairs.add(new BasicNameValuePair("cname", "한글친구영어"));
    		   nameValuePairs.add(new BasicNameValuePair("cphone", "01097977975"));
    		   nameValuePairs.add(new BasicNameValuePair("cgroup", "친구"));
    		   
    		
// String str = URLEncoder.encode(str1, "utf-8");

    		   httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    		   /////
    	    HttpResponse response = httpclient.execute(httppost);
    	    //str.getBytes(),"ISO-8859-1"
    	    jsonResult = inputStreamToString(
    	      response.getEntity().getContent()).toString();
    	    //jsonResult = new String(jsonResult.getBytes(),"ISO-8859-1");
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
     
      Toast.makeText(getBaseContext(), jsonResult, 
				Toast.LENGTH_LONG).show();
   
     
      
     }
     
	 
}
