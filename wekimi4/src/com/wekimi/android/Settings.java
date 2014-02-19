package com.wekimi.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
        Button register = (Button)findViewById(R.id.register);
        Button viewMyProfile = (Button)(Button)findViewById(R.id.viewMyProfile);
        register.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
                   Intent intent = new Intent(Settings.this, Register.class);
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
