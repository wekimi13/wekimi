package com.wekimi.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class WekimiMainActivity extends FunctionActivity implements OnClickListener
{
  	
	
	public static String E_message = new String();            //First msg to send when in Emergency 
	private String E_link = "[Wekimi]즉시 앱으로 연결하여";           //Second msg to send with link attached at the back
	static String link2 = "";
	static ArrayList<String> peopleToSend = new ArrayList<String>();

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	
    	
    	this.registerReceiver(this.WifiStateChangedReceiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
    	WifiManager wifiManager = (WifiManager)getBaseContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);

        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        //Intent F = new Intent(this, FunctionActivity.class);
        //startActivity(F);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
           	
        ImageButton emergency = (ImageButton)findViewById(R.id.emergency);
        ImageButton req_help = (ImageButton)findViewById(R.id.req_help);
        ImageButton giv_help = (ImageButton)findViewById(R.id.giv_help);
        ImageButton siren = (ImageButton)findViewById(R.id.siren);
        ImageButton settings = (ImageButton)findViewById(R.id.settings);
        //Button data = (Button)findViewById(R.id.data);
        
        E_message = "[Wekimi 위급알림]" + ((Person)this.getApplication()).getName()+ "님 매우 위급!\n 위치 :";
        E_link += ((Person)this.getApplication()).getName() + "님을 도와주세요!!\n 앱 연결 :bit.ly/18ll06r ";
        
       
        
        siren.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
                   Intent intent = new Intent(WekimiMainActivity.this, SirenActivity.class);
                   startActivity(intent);
            }
         });
        emergency.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
            	peopleToSend.add(otherphone[0]);
                //peopleToSend.add(otherphone[1]);
                //peopleToSend.add(otherphone[2]);
                Log.v("peopleToSend", peopleToSend.toString());
            	E_message += ((FunctionActivity)FunctionActivity.FunctionContext).getAddress();
            	Log.v("the message to send is" , ":"+E_message+E_link+link2);
                //sendSMS(E_message);
                //sendSMS(E_link);
                //((FunctionActivity)FunctionActivity.FunctionContext).sendSMS(link2, peopleToSend);
            	//Intent intent = new Intent(WekimiMainActivity.this, EmergencyActivity.class);
                //startActivity(intent);
            }
         });
        
         req_help.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
                   Intent intent = new Intent(WekimiMainActivity.this, RequestHelpActivity.class);
                   startActivity(intent);
            }
         });

         giv_help.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
                   Intent intent = new Intent(WekimiMainActivity.this, GiveHelpActivity.class);
                   startActivity(intent);
            }
         });
         settings.setOnClickListener(new Button.OnClickListener()
         {
             public void onClick(View v)
             {
                    Intent intent = new Intent(WekimiMainActivity.this, Settings.class);
                    startActivity(intent);
             }
          });
         //data.setOnClickListener(this);

    }
    
    public void onClick(View view) {
    	
    	
    	Log.v("my telephone number ", ": "+myTel+((Person)this.getApplication()).getName());
     	 
        /*switch(view.getId()){
            case R.id.data:
          	  Toast.makeText(getBaseContext(), "Clicked", Toast.LENGTH_SHORT).show();
                // call AsynTask to perform network operation on separate thread
          	  //HttpAsyncTask get = new HttpAsyncTask();
          	  //get.execute("http://wekimi13.cafe24app.com/user?phone=01040395540");
          	  
            break;
        }*/
    }
    
    public void onStart(){
    	super.onStart();
    	Log.v("onStart", "onStart");
    }
    
   
    
    private BroadcastReceiver WifiStateChangedReceiver
    = new BroadcastReceiver(){

   @Override
   public void onReceive(Context context, Intent intent) {
    // TODO Auto-generated method stub
    
    int extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE ,
      WifiManager.WIFI_STATE_UNKNOWN);
    
    switch(extraWifiState){
    case WifiManager.WIFI_STATE_DISABLED:
     Toast.makeText(getBaseContext(), "WIFI STATE DISABLED", Toast.LENGTH_LONG).show();
    case WifiManager.WIFI_STATE_DISABLING:
     Toast.makeText(getBaseContext(),"WIFI STATE DISABLING", Toast.LENGTH_LONG).show();
     break;
    case WifiManager.WIFI_STATE_ENABLED:
     Toast.makeText(getBaseContext(),"WIFI STATE ENABLED",Toast.LENGTH_LONG).show();
     break;
    case WifiManager.WIFI_STATE_ENABLING:
     Toast.makeText(getBaseContext(),"WIFI STATE ENABLING",Toast.LENGTH_LONG).show();
     break;
    case WifiManager.WIFI_STATE_UNKNOWN:
     Toast.makeText(getBaseContext(),"WIFI STATE UNKNOWN",Toast.LENGTH_LONG).show();
     break;
    }
    
 }};
 
 
   
    
}