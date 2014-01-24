package com.wekimi.android;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.*;
import android.telephony.SmsManager;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
//import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
 
public class FunctionActivity extends Activity
{
	private LocationManager locationManager;
	private String bestProvider;
	//private String message = "[Wekimi]Sooyeon and Minjeong are in Emergency!!!\n Location :";


    static double latitude,longitude;
	
	String sendList[] = {"01094585713"};

	String s = "";


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.emer);


    	Log.v("Entered", "entered"+s);
        
        Button toMain = (Button)findViewById(R.id.toMain);
        Button terminate = (Button)findViewById(R.id.terminateActivity);


        

        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
    	List<String> providers = locationManager.getAllProviders();
        Criteria criteria = new Criteria();
    	bestProvider = locationManager.getBestProvider(criteria, false);
    	
    	//getAddress();
    	//sendSMS();

        toMain.setOnClickListener(new Button.OnClickListener()
        {
              public void onClick(View v)
              {
                   Intent intent = new Intent(FunctionActivity.this, WekimiMainActivity.class);
                   startActivity(intent);
              }
        });

        terminate.setOnClickListener(new Button.OnClickListener()
        {
              public void onClick(View v)
              {
                   finish();
              }          
        });
    }

   
	public void sendSMS(String m)
    {      
		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
		PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
        	
        registerReceiver(new BroadcastReceiver(){
        	@Override
        	public void onReceive(Context arg0, Intent arg1) {

        	}
        }, new IntentFilter(SENT));
            
        
        registerReceiver(new BroadcastReceiver(){
        	@Override
        	public void onReceive(Context arg0, Intent arg1) {
        		switch (getResultCode())
        		{
        			case Activity.RESULT_OK:
        				Toast.makeText(getBaseContext(), "SMS delivered", 
        						Toast.LENGTH_SHORT).show();
        				break;
        			case Activity.RESULT_CANCELED:
        				Toast.makeText(getBaseContext(), "SMS not delivered", 
        						Toast.LENGTH_SHORT).show();
        				break;					    
        		}
        	}
        }, new IntentFilter(DELIVERED));        

    	
        SmsManager sms = SmsManager.getDefault();
        for( String phoneNo : sendList)
        {
        	sms.sendTextMessage(phoneNo, null, m, sentPI, deliveredPI);  
        }
			Toast.makeText(getBaseContext(), "위급상황 문자전송이 완료되었습니다!", 
					Toast.LENGTH_SHORT).show();
    } 
    
    public String getAddress()
    {
    	String result = "";
    	Location location = locationManager.getLastKnownLocation(bestProvider);
    	Geocoder geocoder = new Geocoder(this, Locale.getDefault());
    	Log.v("successfully" , "came into getAddress function!"+geocoder);
        if(location!=null)
    	{
    		 latitude = location.getLatitude();
    		 longitude = location.getLongitude();
    		 Log.v("the latitude is : ", "this : " +latitude);
    	}
    	
    	Log.v("successfully" , "came into getAddress function!");
    	

    	try
    	{
    		//locationManager.requestLocationUpdates(bestProvider, 20000, 1, this);
    		List<Address> list = geocoder.getFromLocation(latitude,longitude,1);
    		Log.v("the list is ", "this" + list);
    		if(list != null && list.size() > 0)
    		{
    			Address address = list.get(0);
    			Log.v("the address is" , "this : "+ address);
    			result = address.getAddressLine(0)+","+address.getLocality();
    		}
    	}
    	catch(IOException e)
    	{
    		Log.e("failed","impossible to connect to geocoder", e);
  			Toast.makeText(getBaseContext(), "impossible to connect to geocoder", 
						Toast.LENGTH_SHORT).show();
    	}
		return result;
        //address1.setText(result);
    }
    
}