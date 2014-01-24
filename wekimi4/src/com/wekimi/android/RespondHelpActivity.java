package com.wekimi.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapLocationManager.OnLocationChangeListener;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager.OnCalloutOverlayListener;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

import com.wekimi.android.*;
 

public class RespondHelpActivity extends NMapActivity implements OnMapStateChangeListener, OnCalloutOverlayListener
{
	private LocationManager locationManager;
	private String bestProvider;
	private String message = "";
	String s = "";
    double latitude,longitude;
	
	String myState = "도움요청 합니다.";
	String myLocation = " 택시 안에서 ";

	String name = new String();
	//set numbers//
	String policenumber="01012341234";
	String bystandernumber="01012341234";
	ArrayList<String> peopleToSend = new ArrayList<String>();
	//String sendList[] = {"01040395540"};
	
	// API-KEY
	public static final String API_KEY = "9b1dce871309fd612badda7b5b0680b7 ";

	NMapView mMapView = null;
	NMapController mMapController = null;
	LinearLayout MapContainer;	
	NMapViewerResourceProvider mMapViewerResourceProvider = null;
	NMapOverlayManager mOverlayManager;
	
     @Override
     public void onCreate(Bundle savedInstanceState)
     {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.respond);
           //
           Button viewProfile = (Button)findViewById(R.id.viewProfile);
           Button sendreq = (Button)findViewById(R.id.sendReq);
           Button state_scared = (Button)findViewById(R.id.state_scared);
           Button state_reqhelp = (Button)findViewById(R.id.state_reqhelp);
           Button location_taxi= (Button)findViewById(R.id.location_taxi);
           Button location_downtown= (Button)findViewById(R.id.location_downtown);
           Button location_street= (Button)findViewById(R.id.location_street);
           Button location_onmywayhome= (Button)findViewById(R.id.location_onmywayhome);
           final EditText msg_text = (EditText)findViewById(R.id.msg_text);
           //
           TextView tvName = (TextView)findViewById(R.id.tvName);
           TextView tvDescription = (TextView)findViewById(R.id.tvDescription);
          //
           final CheckBox other1 = (CheckBox)findViewById(R.id.other1);
           final CheckBox other2 = (CheckBox)findViewById(R.id.other2);
           final CheckBox other3 = (CheckBox)findViewById(R.id.other3);
           final CheckBox other4 = (CheckBox)findViewById(R.id.other4);
           final CheckBox other5 = (CheckBox)findViewById(R.id.other5);
           final CheckBox other6 = (CheckBox)findViewById(R.id.other6);
           
           final CheckBox police = (CheckBox)findViewById(R.id.police);
           final CheckBox bystander = (CheckBox)findViewById(R.id.bystander);
           
           TextView address2 = (TextView)findViewById(R.id.address2);

          //set data to textview field
           name = ((Person)this.getApplication()).getName()+"님이";
           
            tvName.setText(((Person)this.getApplication()).getName());
      		tvDescription.setText(((Person)this.getApplication()).getPhone()+"/"+((Person)this.getApplication()).getGender()+"/"+((Person)this.getApplication()).getCharacter());

      		final String[] othername = ((Person)this.getApplication()).getOthername();
      		final String[] otherphone = ((Person)this.getApplication()).getOtherphone();
      		
      		other1.setText(othername[0]);
      		other2.setText(othername[1]);
      		other3.setText(othername[2]);
      		other4.setText(othername[3]);
      		other5.setText(othername[4]);
      		other6.setText(othername[5]);
      		//FRom here.. code for sending SMS//
      		locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        	//List<String> providers = locationManager.getAllProviders();
            Criteria criteria = new Criteria();
        	bestProvider = locationManager.getBestProvider(criteria, false);
        	Location location = locationManager.getLastKnownLocation(bestProvider);
        	    	 
            if(location!=null)
        	{
        		 latitude = location.getLatitude();
        		 longitude = location.getLongitude();
        		 s += getAddress(latitude, longitude);
        		 //message += s;
        	}
            
            address2.setText(s);
            Log.d("abc","111111111111111111111"+address2);
            
        	
//end of sending sms
           

   		MapContainer = (LinearLayout) findViewById(R.id.MapContainer);
   		mMapView = new NMapView(this);	
   		mMapController = mMapView.getMapController();
   		mMapView.setApiKey(API_KEY);
   		MapContainer.addView(mMapView);
  		mMapView.setClickable(true);  		
   		mMapView.setBuiltInZoomControls(true, null);	
   		mMapView.setOnMapStateChangeListener(this);

   		
   		mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
   		mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);  
   		
   		int markerId = NMapPOIflagType.PIN;
   		NMapPOIdata poiData = new NMapPOIdata(1, mMapViewerResourceProvider);
   		poiData.beginPOIdata(1);
   		poiData.addPOIitem(longitude, latitude, "¿ßƒ°1", markerId, 0);
   		poiData.endPOIdata();

   		NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);  		
   		poiDataOverlay.showAllPOIdata(0);
   		
   		mOverlayManager.setOnCalloutOverlayListener(this);
   		
           viewProfile.setOnClickListener(new Button.OnClickListener()
           {
                 public void onClick(View v)
                 {
                	 Intent intent = new Intent(RespondHelpActivity.this, Profile.class);
                     startActivity(intent);                 }
           });
           state_scared.setOnClickListener(new Button.OnClickListener()
           {
                 public void onClick(View v)
                 {
                	 myState="불안합니다. ";
                	 msg_text.setText(name + myLocation + myState);
                 }
           });
           state_reqhelp.setOnClickListener(new Button.OnClickListener()
           {
                 public void onClick(View v)
                 {
                	 myState="도움요청 합니다. ";
                	 msg_text.setText(name + myLocation + myState);
                 }
           });
           location_taxi.setOnClickListener(new Button.OnClickListener()
           {
                 public void onClick(View v)
                 {
                	 myLocation=" 택시 안에서 ";
                	msg_text.setText(name + myLocation + myState);
                 }
           });
           location_downtown.setOnClickListener(new Button.OnClickListener()
           {
                 public void onClick(View v)
                 {
                	 myLocation=" 시내에서 ";
                	msg_text.setText(name + myLocation + myState);
                 }
           });
           location_street.setOnClickListener(new Button.OnClickListener()
           {
                 public void onClick(View v)
                 {
                	 myLocation=" 길거리에서 ";
                	msg_text.setText(name + myLocation + myState);
                 }
           });
           location_onmywayhome.setOnClickListener(new Button.OnClickListener()
           {
                 public void onClick(View v)
                 {
                	 myLocation=" 귀가길에서 ";
                	msg_text.setText(name + myLocation + myState);
                 }
           });
           sendreq.setOnClickListener(new Button.OnClickListener() {
           	   
       		  @Override
       		  public void onClick(View v) {
       			  if(other1.isChecked()) {peopleToSend.add(otherphone[0]);}
       			if(other2.isChecked()) {peopleToSend.add(otherphone[1]);}
       			if(other3.isChecked()) {peopleToSend.add(otherphone[2]);}
       			if(police.isChecked()) {peopleToSend.add(policenumber);}
       			if(bystander.isChecked()) {peopleToSend.add(bystandernumber);}
       			
       			String[] sendinglist = new String[peopleToSend.size()];
                 sendinglist = peopleToSend.toArray(sendinglist);
                 
                 message = msg_text.getText().toString();
                 
                 sendSMS(sendinglist, message);
      			Toast.makeText(getBaseContext(), "전송하였습니다!", 
 						Toast.LENGTH_SHORT).show();
     			Toast.makeText(getBaseContext(),"Message :"+ message, 
						Toast.LENGTH_LONG).show();
       	 
       		  }
       		});

      }

     
 	@Override
 	public void onMapInitHandler(NMapView mapview, NMapError errorInfo) {
 		if (errorInfo == null) { // success
 			//mMapController.setMapCenter(new NGeoPoint(126.978371, 37.5666091), 11);
 		} else { // fail
 			android.util.Log.e("NMAP", "onMapInitHandler: error=" + errorInfo.toString());
 		}
 	}


 	@Override
 	public void onZoomLevelChange(NMapView mapview, int level) {}


 	@Override
 	public void onMapCenterChange(NMapView mapview, NGeoPoint center) {}


 	@Override
 	public void onAnimationStateChange(NMapView arg0, int animType, int animState) {}

 	@Override
 	public void onMapCenterChangeFine(NMapView arg0) {}

 	public NMapCalloutOverlay onCreateCalloutOverlay1(NMapOverlay arg0,NMapOverlayItem arg1, Rect arg2) {
 		Toast.makeText(this, arg1.getTitle(), Toast.LENGTH_SHORT).show();
 		return null;
 	}
	@Override
	public NMapCalloutOverlay onCreateCalloutOverlay(NMapOverlay arg0,
			NMapOverlayItem arg1, Rect arg2) {
		// TODO Auto-generated method stub
		return null;
	}
	private void sendSMS(String[] sendList, String message)
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
        sms.sendTextMessage(phoneNo, null, message, sentPI, deliveredPI);               
    } 
    
    String getAddress(double lat, double lon)
    {
    	Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
    	String result = "";
    	try
    	{
    		List<Address> list = geocoder.getFromLocation(lat,lon,1);
    		if(list != null && list.size() > 0)
    		{
    			Address address = list.get(0);
    			result = address.getAddressLine(0)+","+address.getLocality();
    		}
    	}
    	catch(IOException e)
    	{
    	}
    	return result;
    }
    
}