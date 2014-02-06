package com.wekimi.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
   	String[] othername = new String[10];
	String[] otherphone = new String[10];
	public static JSONArray others;
	String name;
	String phone;
	String gender;
	String character;	
	
	public String myTel;
		
	TelephonyManager mTelephonyMgr;
	public static String E_message = new String();            //First msg to send when in Emergency 
	private String E_link = "[Wekimi]즉시 앱으로 연결하여";           //Second msg to send with link attached at the back
	static String link2 = "";

	
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
    	
        mTelephonyMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        myTel = mTelephonyMgr.getLine1Number();
        new HttpAsyncTask().execute("http://wekimi13.cafe24app.com/user?phone=" + myTel);
        String myName = ((Person)this.getApplication()).getName();
        
        
        
    	
    	Log.v("telephone number", ":"+myTel+myName);
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
            	E_message += ((FunctionActivity)FunctionActivity.FunctionContext).getAddress();
            	Log.v("the message to send is" , ":"+E_message+E_link+link2);
                //sendSMS(E_message);
                //sendSMS(E_link);
                ((FunctionActivity)FunctionActivity.FunctionContext).sendSMS(link2);
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
              	//Log.i("Chaine JSON", stringBuilder.toString());
              	
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
        // onPostExecute displays the results of the AsyncTask.
       
    }
    ///////////
}