package com.wekimi.android;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
 

public class RequestHelpActivity extends NMapActivity implements OnMapStateChangeListener, OnCalloutOverlayListener, LocationListener
{ 
	private String message = "[Wekimi도움요청]";
	private String message2;
	private String link = "[Wekimi]앱으로 연결하여 ";
	static String currAddress = "";
    //public static double latitude,longitude;
	
	String myState = "도움요청 합니다.";
	String myLocation = " 택시 안에서 ";

	String name = new String();
	
	String policenumber="01012341234";
	String bystandernumber="01012341234";
	static ArrayList<String> peopleToSend = new ArrayList<String>();

	
	public static final String API_KEY = "9b1dce871309fd612badda7b5b0680b7 ";
	NMapView mMapView = null;
	NMapController mMapController = null;
	LinearLayout MapContainer;	
	NMapViewerResourceProvider mMapViewerResourceProvider = null;
	NMapOverlayManager mOverlayManager;
	public static String myname;

	
     @Override
     public void onCreate(Bundle savedInstanceState)
     {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.reqhelp);
           myname = ((Person)this.getApplication()).getName();
           Log.v("myname",":"+((Person)this.getApplication()).getName()+myname);
           
           ImageButton viewProfile = (ImageButton)findViewById(R.id.viewProfile);
           ImageButton sendreq = (ImageButton)findViewById(R.id.sendReq);
           final ImageButton state_scared = (ImageButton)findViewById(R.id.state_scared);
           final ImageButton state_reqhelp = (ImageButton)findViewById(R.id.state_reqhelp);
           final ImageButton location_taxi= (ImageButton)findViewById(R.id.location_taxi);
           final ImageButton location_downtown= (ImageButton)findViewById(R.id.location_downtown);
           final ImageButton location_street= (ImageButton)findViewById(R.id.location_street);
           final ImageButton location_onmywayhome= (ImageButton)findViewById(R.id.location_onmywayhome);
           final EditText msg_text = (EditText)findViewById(R.id.msg_text);
           //
           TextView tvName = (TextView)findViewById(R.id.tvName);
           TextView tvGender = (TextView)findViewById(R.id.tvGender);
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
           name = myname+"님이";
           link += myname + "님을 한시빨리 도와주시기 바랍니다!\n 앱 연결 :bit.ly/18ll06r";
           Log.v("message1", name);
           Log.v("message2", link);
           
           msg_text.setText(name+myLocation+myState);
           
           tvName.setText(((Person)this.getApplication()).getName());
           tvName.setTextColor(Color.parseColor("#2EAFB2"));
     		tvDescription.setText(((Person)this.getApplication()).getCharacter());
     		tvDescription.setTextColor(Color.parseColor("#231F20"));
     		
     		tvGender.setText(((Person)this.getApplication()).getGender());
     		tvGender.setTextColor(Color.parseColor("#9D9FA2"));

      		final String[] othername = ((Person)this.getApplication()).getOthername();
      		final String[] otherphone = ((Person)this.getApplication()).getOtherphone();
      		
      		other1.setText(othername[0]);
      		other2.setText(othername[1]);
      		other3.setText(othername[2]);
      		other4.setText(othername[3]);
      		other5.setText(othername[4]);
      		other6.setText(othername[5]);
      		
      		currAddress = ((FunctionActivity)FunctionActivity.FunctionContext).getAddress();
      		double longitude = FunctionActivity.longitude;
      		double latitude = FunctionActivity.latitude;
      		address2.setText(currAddress);

      		//locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        	//List<String> providers = locationManager.getAllProviders();
            //Criteria criteria = new Criteria();
        	//bestProvider = locationManager.getBestProvider(criteria, false);
        	//Location location = locationManager.getLastKnownLocation(bestProvider);        	
       	    //if(location!=null)
       	    //{
       	    //	latitude = location.getLatitude();
       	    //	longitude = location.getLongitude();
       	    //	Log.v("the latitude is", "="+latitude);
       	    //	s = getAddress(latitude, longitude);
       	    //	Log.v("The address s is", "="+s);
       	    //}
          
          //address2.setText(s);
        	
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
                	 Intent intent = new Intent(RequestHelpActivity.this, Profile.class);
                     startActivity(intent);                 }
           });
           state_scared.setOnClickListener(new Button.OnClickListener()
           {
                 public void onClick(View v)
                 {
                	 myState="불안합니다. ";
                	 msg_text.setText(name + myLocation + myState);
                	 if(state_scared.isSelected() == true) {
                	 state_scared.setSelected(false);
                	 state_reqhelp.setSelected(true);}
                	 else {
                		 state_scared.setSelected(true);
                		 state_reqhelp.setSelected(false);
                	 }
                	 
                 }
           });
           state_reqhelp.setOnClickListener(new Button.OnClickListener()
           {
                 public void onClick(View v)
                 {
                	 myState="도움요청 합니다. ";
                	 msg_text.setText(name + myLocation + myState);
                	 if(state_reqhelp.isSelected() == true) {
                    	 state_reqhelp.setSelected(false);
                    	 state_scared.setSelected(true);}
                    	 else {
                    		 state_reqhelp.setSelected(true);
                    		 state_scared.setSelected(false);
                    	 }
                    	 
                 }
           });
           location_taxi.setOnClickListener(new Button.OnClickListener()
           {
                 public void onClick(View v)
                 {
                	 myLocation=" 택시 안에서 ";
                	msg_text.setText(name + myLocation + myState);
                	if(location_taxi.isSelected() == true) {
                		location_taxi.setSelected(false);
                   	 }
                   	 else {
                   		 location_taxi.setSelected(true);
                   		location_downtown.setSelected(false);
                   		location_street.setSelected(false);
                   		location_onmywayhome.setSelected(false);
                   		 
                   	 }
                	
                 }
           });
           location_downtown.setOnClickListener(new Button.OnClickListener()
           {
                 public void onClick(View v)
                 {
                	 myLocation=" 시내에서 ";
                	msg_text.setText(name + myLocation + myState);
                  	if(location_downtown.isSelected() == true) {
                		location_downtown.setSelected(false);
                   	 }
                   	 else {
                   		 location_downtown.setSelected(true);
                   		location_taxi.setSelected(false);
                   		location_street.setSelected(false);
                   		location_onmywayhome.setSelected(false);
                   		 
                   	 }
                 }
           });
           location_street.setOnClickListener(new Button.OnClickListener()
           {
                 public void onClick(View v)
                 {
                	 myLocation=" 길거리에서 ";
                	msg_text.setText(name + myLocation + myState);
                	
                  	if(location_street.isSelected() == true) {
                		location_street.setSelected(false);
                   	 }
                   	 else {
                   		 location_street.setSelected(true);
                   		location_taxi.setSelected(false);
                   		location_downtown.setSelected(false);
                   		location_onmywayhome.setSelected(false);
                   		 
                   	 }
                 }
           });
           location_onmywayhome.setOnClickListener(new Button.OnClickListener()
           {
                 public void onClick(View v)
                 {
                	 myLocation=" 귀가길에서 ";
                	msg_text.setText(name + myLocation + myState);
                	
                  	if(location_onmywayhome.isSelected() == true) {
                		location_onmywayhome.setSelected(false);
                   	 }
                   	 else {
                   		 location_onmywayhome.setSelected(true);
                   		location_taxi.setSelected(false);
                   		location_street.setSelected(false);
                   		location_downtown.setSelected(false);
                   		 
                   	 }
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
       			
       			
                 
                 message += msg_text.getText().toString();
                 message2 += "\n위치 : "+ currAddress ;

                 ((FunctionActivity)FunctionActivity.FunctionContext).sendSMS(message, peopleToSend);
                 ((FunctionActivity)FunctionActivity.FunctionContext).sendSMS(message2, peopleToSend);
                 ((FunctionActivity)FunctionActivity.FunctionContext).sendSMS(link, peopleToSend);
                 //((FunctionActivity)FunctionActivity.FunctionContext).sendSMS(WekimiMainActivity.link2);
                 
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
	/*private void sendSMS(String[] sendList, String message)
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
    
    /*String getAddress(double lat, double lon)
    {

    	Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
    	Log.v("geocoder ", "="+geocoder);
    	String result = "";
    	try
    	{
    		List<Address> list = geocoder.getFromLocation(lat,lon,1);
    		Log.v("address list","="+list);
    		if(list != null && list.size() > 0)
    		{
    			Address address = list.get(0);
    			result = address.getAddressLine(0);
    		}
    	}
    	catch(IOException e)
    	{
    	}
    	return result;
    }*/
    @Override
    protected void onResume()
    {
          super.onResume();
          FunctionActivity.locationManager.requestLocationUpdates(FunctionActivity.bestProvider,20000,1,this);

          String s = "[onResume]";
          //Location location = FunctionActivity.locationManager.getLastKnownLocation(FunctionActivity.bestProvider);

          //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    @Override
     protected void onPause()
     {
           super.onPause();
           FunctionActivity.locationManager.removeUpdates(this);
      }

      public void onLocationChanged(Location location)
      {
           //String s = "[onLocationChanged]";
           //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
      }

      public void onProviderDisabled(String provider){}
      public void onProviderEnabled(String provider){}
      public void onStatusChanged(String provider, int status, Bundle extras){}

      /*public Location getCurrentLocation()
      {
    	  LocationManager locMngr = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	  Location currLoc = null;
           
    	  currLoc = locMngr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
           if(currLoc==null)
           currLoc = locMngr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
           Log.v("currLoc", "="+currLoc);

           return currLoc;
      }*/
}
    