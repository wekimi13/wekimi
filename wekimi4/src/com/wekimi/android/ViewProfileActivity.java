package com.wekimi.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
 
public class ViewProfileActivity extends Activity
{
     @Override
     public void onCreate(Bundle savedInstanceState)
     {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.viewprofile);
           Button toMain = (Button)findViewById(R.id.toMain);
           Button terminate = (Button)findViewById(R.id.terminateActivity);
 
           
           toMain.setOnClickListener(new Button.OnClickListener()
           {
                 public void onClick(View v)
                 {
                      Intent intent = new Intent(ViewProfileActivity.this, WekimiMainActivity.class);
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
}