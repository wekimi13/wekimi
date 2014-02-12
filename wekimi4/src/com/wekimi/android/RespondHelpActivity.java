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
	String pName;
	
     @Override
     public void onCreate(Bundle savedInstanceState)
     { 
           super.onCreate(savedInstanceState);
           setContentView(R.layout.respond);
           
           TextView pName1 = (TextView)findViewById(R.id.pName);
           
           
           pName= ((Person)this.getApplication()).getName();
           Log.v("passed name:",""+pName);
           pName1.append(pName);
     }
}
