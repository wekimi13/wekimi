package com.wekimi.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SirenActivity extends Activity {

    private static final String TAG = "MyActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siren);

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.firealarm);
        
        mp.setLooping(true);
        mp.start();
        	  
                
         ImageButton sirenstop = (ImageButton)findViewById(R.id.sirenstop);
              
         sirenstop.setOnClickListener(new Button.OnClickListener()
         {
        	 public void onClick(View v)
        	 {
        	        mp.setLooping(false);
        		 mp.stop();

        		 }
        	 });     
            
        }
}
