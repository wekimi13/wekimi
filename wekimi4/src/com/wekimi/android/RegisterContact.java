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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterContact extends Activity {
	

	static String[] othername = new String[10];
	static String[] otherphone = new String[10]; 
    /*
	private String jsonResult;

    private String url_contact = "http://wekimi13.dothome.co.kr/insertContact.php";
    private String url_event = "http://wekimi13.dothome.co.kr/insertEvent.php";

    //private String url_contact = "http://wekimi13.dothome.co.kr/insertContact.php"; */

    List<NameValuePair> nameValuePairs;
	
	EditText etname, etphone, etgender,etcharacter,etothername1, etothername2,
	etothername3, etothername4, etotherphone1,etotherphone2,etotherphone3
	,etotherphone4;
	
	ImageButton sendReg;
	
	ImageButton btnAdd;

   
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        setContentView(R.layout.registerfriendtest);

       // etcharacter = (EditText)findViewById(R.id.RegetCharacter);
        etothername1 = (EditText)findViewById(R.id.RegtvOtherName1);
        etotherphone1 = (EditText)findViewById(R.id.RegtvOtherPhone1);
        etothername2 = (EditText)findViewById(R.id.RegtvOtherName2);
        etotherphone2 = (EditText)findViewById(R.id.RegtvOtherPhone2);
        etothername3 = (EditText)findViewById(R.id.RegtvOtherName3);
        etotherphone3 = (EditText)findViewById(R.id.RegtvOtherPhone3);
        etothername4 = (EditText)findViewById(R.id.RegtvOtherName4);
        etotherphone4 = (EditText)findViewById(R.id.RegtvOtherPhone4);
        
btnAdd = (ImageButton) findViewById(R.id.register_plusfriend);	
		
		ContactRowDetail.add(this, btnAdd);

	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wekimi_main, menu);
		return true;
	}

	

	 
} 
