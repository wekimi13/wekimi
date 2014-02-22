package com.wekimi.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Settings extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
        ImageButton register = (ImageButton)findViewById(R.id.register);
        ImageButton modify = (ImageButton)findViewById(R.id.modify);
        ImageButton viewMyProfile = (ImageButton)findViewById(R.id.viewMyProfile);
        register.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
                   Intent intent = new Intent(Settings.this, RegisterMyInfo.class);
                   startActivity(intent);
            }
         });
        viewMyProfile.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
                   Intent intent = new Intent(Settings.this, ViewMyProfile.class);
                   startActivity(intent);
            }
         });

	
	    // TODO Auto-generated method stub
	}

}
