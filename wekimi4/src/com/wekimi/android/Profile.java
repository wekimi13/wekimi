package com.wekimi.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class Profile extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.viewprofile);
	
	    EditText etName = (EditText)findViewById(R.id.etName);
        EditText etPhone = (EditText)findViewById(R.id.etPhone);
        EditText etGender = (EditText)findViewById(R.id.etGender);
        EditText etCharacter = (EditText)findViewById(R.id.etCharacter);
        
        etName.setText(((Person)this.getApplication()).getName());
        etPhone.setText(((Person)this.getApplication()).getPhone());
        etGender.setText(((Person)this.getApplication()).getGender());
        etCharacter.setText(((Person)this.getApplication()).getCharacter());
        
        
	    // TODO Auto-generated method stub
	}

}
