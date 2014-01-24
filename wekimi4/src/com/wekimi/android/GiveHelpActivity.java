package com.wekimi.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.PendingIntent;
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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager.OnCalloutOverlayListener;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
 
public class GiveHelpActivity extends  NMapActivity implements OnMapStateChangeListener, OnCalloutOverlayListener
{
	//Send SMS//
		private LocationManager locationManager;
		private String bestProvider;
		private String message = "";
		String s = "";
	    static double latitude,longitude;
	    
	    String policenumber="01012341234";
		String bystandernumber="01012341234";
		ArrayList<String> peopleToSend = new ArrayList<String>();

		//END of Send SMS//
	// API-KEY
	public static final String API_KEY = "9b1dce871309fd612badda7b5b0680b7 ";
	// ≥◊¿Ãπˆ ∏  ∞¥√º
	NMapView mMapView = null;
	// ∏  ƒ¡∆Æ∑—∑Ø
	NMapController mMapController = null;
	// ∏ ¿ª √ﬂ∞°«“ ∑π¿Ãæ∆øÙ
	LinearLayout MapContainer;	
	// ø¿πˆ∑π¿Ã¿« ∏Æº“Ω∫∏¶ ¡¶∞¯«œ±‚ ¿ß«— ∞¥√º
	NMapViewerResourceProvider mMapViewerResourceProvider = null;
	// ø¿πˆ∑π¿Ã ∞¸∏Æ¿⁄
	NMapOverlayManager mOverlayManager;
	
	
     @Override
     public void onCreate(Bundle savedInstanceState)
     {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.givhelp);
           //Button toMain = (Button)findViewById(R.id.toMain);
           //Button terminate = (Button)findViewById(R.id.terminateActivity);
           final CheckBox bystander = (CheckBox)findViewById(R.id.bystander);
           final CheckBox police = (CheckBox)findViewById(R.id.police);
        
           final EditText msg_text = (EditText)findViewById(R.id.givehelp_msg);
           TextView address3 = (TextView)findViewById(R.id.address3);
           Button sendGiv = (Button)findViewById(R.id.sendGiv);
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
        		 message += s;
        	}
            
            address3.setText(s);
            Log.d("abc","111111111111111111111"+address3);
            
        	
//end of sending sms
           
           
           
      		
           /******************* ¡ˆµµ √ ±‚»≠ Ω√¿€ ********************/
   		// ≥◊¿Ãπˆ ¡ˆµµ∏¶ ≥÷±‚ ¿ß«— LinearLayout ƒƒ∆˜≥Õ∆Æ
   		MapContainer = (LinearLayout) findViewById(R.id.MapContainer);
   		// ≥◊¿Ãπˆ ¡ˆµµ ∞¥√º ª˝º∫
   		mMapView = new NMapView(this);	
   		// ¡ˆµµ ∞¥√º∑Œ∫Œ≈Õ ƒ¡∆Æ∑—∑Ø √ﬂ√‚
   		mMapController = mMapView.getMapController();
   		// ≥◊¿Ãπˆ ¡ˆµµ ∞¥√ºø° APIKEY ¡ˆ¡§
   		mMapView.setApiKey(API_KEY);
   		// ª˝º∫µ» ≥◊¿Ãπˆ ¡ˆµµ ∞¥√º∏¶ LinearLayoutø° √ﬂ∞°Ω√≈≤¥Ÿ.
   		MapContainer.addView(mMapView);
   		// ¡ˆµµ∏¶ ≈Õƒ°«“ ºˆ ¿÷µµ∑œ ø…º« »∞º∫»≠
   		mMapView.setClickable(true);  		
   		// »Æ¥Î/√‡º“∏¶ ¿ß«— ¡‹ ƒ¡∆Æ∑—∑Ø «•Ω√ ø…º« »∞º∫»≠
   		mMapView.setBuiltInZoomControls(true, null);	
   		// ¡ˆµµø° ¥Î«— ªÛ≈¬ ∫Ø∞Ê ¿Ã∫•∆Æ ø¨∞·
   		mMapView.setOnMapStateChangeListener(this);
   		/******************* ¡ˆµµ √ ±‚»≠ ≥° ********************/
   		
   		
   		/******************* ø¿πˆ∑π¿Ã ∞¸∑√ ƒ⁄µÂ Ω√¿€ ********************/
   	// ø¿πˆ∑π¿Ã ∏Æº“Ω∫ ∞¸∏Æ∞¥√º «“¥Á
   		mMapViewerResourceProvider = new NMapViewerResourceProvider(this);
   		// ø¿πˆ∑π¿Ã ∞¸∏Æ¿⁄ √ﬂ∞°
   		mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);  		
   		// ø¿πˆ∑π¿ÃµÈ¿ª ∞¸∏Æ«œ±‚ ¿ß«— id∞™ ª˝º∫
   		int markerId = NMapPOIflagType.PIN;
   		// «•Ω√«“ ¿ßƒ° µ•¿Ã≈Õ∏¶ ¡ˆ¡§«—¥Ÿ. -- ∏∂¡ˆ∏∑ ¿Œ¿⁄∞° ø¿πˆ∑π¿Ã∏¶ ¿ŒΩƒ«œ±‚ ¿ß«— id∞™
   		NMapPOIdata poiData = new NMapPOIdata(1, mMapViewerResourceProvider);
   		poiData.beginPOIdata(1);
   		poiData.addPOIitem(GiveHelpActivity.longitude, GiveHelpActivity.latitude, "¿ßƒ°1", markerId, 0);
   		poiData.endPOIdata();
   		// ¿ßƒ° µ•¿Ã≈Õ∏¶ ªÁøÎ«œø© ø¿πˆ∑π¿Ã ª˝º∫
   		NMapPOIdataOverlay poiDataOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);  		
   		// id∞™¿Ã 0¿∏∑Œ ¡ˆ¡§µ» ∏µÁ ø¿πˆ∑π¿Ã∞° «•Ω√µ«∞Ì ¿÷¥¬ ¿ßƒ°∑Œ ¡ˆµµ¿« ¡ﬂΩ…∞˙ ZOOM¿ª ¿Áº≥¡§
   		poiDataOverlay.showAllPOIdata(0);
   		// ø¿πˆ∑π¿Ã ¿Ã∫•∆Æ µÓ∑œ
   		mOverlayManager.setOnCalloutOverlayListener(this);
   		/******************* ø¿πˆ∑π¿Ã ∞¸∑√ ƒ⁄µÂ ≥° ********************/
   		
        sendGiv.setOnClickListener(new Button.OnClickListener() {
        	   
    		  @Override
    		  public void onClick(View v) {
    			
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
     
     
     /**
 	 * ¡ˆµµ∞° √ ±‚»≠µ» »ƒ »£√‚µ»¥Ÿ.
 	 * ¡§ªÛ¿˚¿∏∑Œ √ ±‚»≠µ«∏È errorInfo ∞¥√º¥¬ null¿Ã ¿¸¥ﬁµ«∏Á,
 	 * √ ±‚»≠ Ω«∆– Ω√ errorInfo∞¥√ºø° ø°∑Ø ø¯¿Œ¿Ã ¿¸¥ﬁµ»¥Ÿ
 	 */
 	@Override
 	public void onMapInitHandler(NMapView mapview, NMapError errorInfo) {
 		if (errorInfo == null) { // success
 			//mMapController.setMapCenter(new NGeoPoint(126.978371, 37.5666091), 11);
 		} else { // fail
 			android.util.Log.e("NMAP", "onMapInitHandler: error=" + errorInfo.toString());
 		}
 	}

 	/**
 	 * ¡ˆµµ ∑π∫ß ∫Ø∞Ê Ω√ »£√‚µ«∏Á ∫Ø∞Êµ» ¡ˆµµ ∑π∫ß¿Ã ∆ƒ∂ÛπÃ≈Õ∑Œ ¿¸¥ﬁµ»¥Ÿ.
 	 */
 	@Override
 	public void onZoomLevelChange(NMapView mapview, int level) {}

 	/**
 	 * ¡ˆµµ ¡ﬂΩ… ∫Ø∞Ê Ω√ »£√‚µ«∏Á ∫Ø∞Êµ» ¡ﬂΩ… ¡¬«•∞° ∆ƒ∂ÛπÃ≈Õ∑Œ ¿¸¥ﬁµ»¥Ÿ.
 	 */
 	@Override
 	public void onMapCenterChange(NMapView mapview, NGeoPoint center) {}

 	/**
 	 * ¡ˆµµ æ÷¥œ∏ﬁ¿Ãº« ªÛ≈¬ ∫Ø∞Ê Ω√ »£√‚µ»¥Ÿ.
 	 * animType : ANIMATION_TYPE_PAN or ANIMATION_TYPE_ZOOM
 	 * animState : ANIMATION_STATE_STARTED or ANIMATION_STATE_FINISHED
 	 */
 	@Override
 	public void onAnimationStateChange(NMapView arg0, int animType, int animState) {}

 	@Override
 	public void onMapCenterChangeFine(NMapView arg0) {}

 	/** ø¿πˆ∑π¿Ã∞° ≈¨∏Øµ«æ˙¿ª ∂ß¿« ¿Ã∫•∆Æ */
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