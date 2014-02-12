package com.wekimi.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
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

import android.content.*;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

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
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
 
public class FunctionActivity extends Activity implements LocationListener
{
	public static LocationManager locationManager;
	public static String bestProvider;

    public static double latitude,longitude;
	
	//String sendList[] = {"01094585713"};

	String s = "";
	public static Context FunctionContext;
	
	public static JSONArray others;
 	String[] othername = new String[10];
	String[] otherphone = new String[10];
	String name;
	String phone;
	String gender;
	String character;
	String myTel;
	static String myName;
	
	TelephonyManager mTelephonyMgr;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	super.onCreate(savedInstanceState);
        FunctionContext = this;
        
        mTelephonyMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        myTel = mTelephonyMgr.getLine1Number();
        Log.v("myTel",myTel);

        new HttpAsyncTask().execute("http://wekimi13.cafe24app.com/user?phone=" + myTel);
        
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
    	List<String> providers = locationManager.getAllProviders();
        Criteria criteria = new Criteria();
    	bestProvider = locationManager.getBestProvider(criteria, false);


    	Log.v("Entered", "entered"+s);

    	//getAddress();
    	//sendSMS();
    }

    
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
		
		myName = ((Person)this.getApplication()).getName();
		Log.v("myName1(success)", myName);
		
	   
	   
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
	              	Log.i("Chaine JSON", stringBuilder.toString());
	              	
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
   
	public void sendSMS(String m)
    {      
		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
		PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
		
		String[] sendinglist = new String[(RequestHelpActivity.peopleToSend).size()];
        sendinglist = RequestHelpActivity.peopleToSend.toArray(sendinglist);
        	
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
        for( String phoneNo : sendinglist)
        {
        	sms.sendTextMessage(phoneNo, null, m, sentPI, deliveredPI);  
        }
			Toast.makeText(getBaseContext(), "위급상황 문자전송이 완료되었습니다!", 
					Toast.LENGTH_SHORT).show();
    } 
    
    public String getAddress()
    {
    	
    	String result = "";
    	Geocoder geocoder = new Geocoder(getApplicationContext(),Locale.getDefault());
    	locationManager.requestLocationUpdates(bestProvider, 10000, 0, this);
    	//List<String> providers = locationManager.getAllProviders();
        
    	
    	Location location = locationManager.getLastKnownLocation(bestProvider);
    	
    	Log.v("successfully" , "came into getAddress function!");
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
    @Override
    protected void onResume()
    {
          super.onResume();
          locationManager.requestLocationUpdates(bestProvider,20000,1,this);

          String s = "[onResume]";
          //Location location = locationManager.getLastKnownLocation(bestProvider);
          Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    @Override
     protected void onPause()
     {
           super.onPause();
           locationManager.removeUpdates(this);
      }

      public void onLocationChanged(Location location)
      {
    	  locationManager.requestLocationUpdates(bestProvider,20000,1,this);
           String s = "[onLocationChanged]";
           //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
           //Toast.makeText(getApplicationContext(),RequestHelpActivity.currAddress,Toast.LENGTH_LONG).show();
      }

      public void onProviderDisabled(String provider){}
      public void onProviderEnabled(String provider){}
      public void onStatusChanged(String provider, int status, Bundle extras){}
    
}