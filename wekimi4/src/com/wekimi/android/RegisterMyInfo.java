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


//import com.wekimi.android.RegisterFriend.InsertContact;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterMyInfo extends Activity {
	
	static String name = new String();
	static String phone = new String();
	static String gender = new String();
	static String character = new String();
	static String[] othername = new String[10];
	static String[] otherphone = new String[10];


    //private String url_contact = "http://wekimi13.dothome.co.kr/insertContact.php";

    List<NameValuePair> nameValuePairs;
	
	EditText etname, etphone, etgender,etcharacter,etothername1, etothername2,
	etothername3, etothername4, etotherphone1,etotherphone2,etotherphone3
	,etotherphone4;
	
	ImageButton register_next;

   
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        setContentView(R.layout.registertest);
       
        etname = (EditText)findViewById(R.id.RegetName);
        etphone = (EditText)findViewById(R.id.RegetPhone);
        ImageButton register_next = (ImageButton)findViewById(R.id.register_next);  
        register_next.setOnClickListener(new ImageButton.OnClickListener()
              {
                    public void onClick(View v)
                    {
            			
                         Intent intent = new Intent(RegisterMyInfo.this, RegisterContact.class);
                         startActivity(intent);
                    }
              });
              
	}

     
     public class RadioGroup_tom extends Activity
     {
         private RadioGroup mRadioGroup = null;

         /** Called when the activity is first created. */
         @Override
         public void onCreate(Bundle savedInstanceState)
         {
             super.onCreate(savedInstanceState);
             setContentView(R.layout.registertest);

             mRadioGroup = (RadioGroup)findViewById(R.id.radiogroup);
         }
     }

     
	 
}
