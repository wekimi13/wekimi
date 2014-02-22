package com.wekimi.android;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class ViewMyProfile extends TabActivity implements OnTabChangeListener {

	/** Called when the activity is first created. */
	TabHost tabHost;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        setContentView(R.layout.viewmyprofile);
        
        // Get TabHost Refference
        tabHost = getTabHost();
         
        // Set TabChangeListener called when tab changed
        tabHost.setOnTabChangedListener(this);
     
        TabHost.TabSpec spec;
        Intent intent;
        
        /************* TAB1 ************/
        // Create  Intents to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, Tab1.class);
        spec = tabHost.newTabSpec("First").setIndicator("")
                      .setContent(intent);
         
        //Add intent to tab
        tabHost.addTab(spec);
   
        /************* TAB2 ************/
        intent = new Intent().setClass(this, Tab2.class);
        spec = tabHost.newTabSpec("Second").setIndicator("")
                      .setContent(intent);  
        tabHost.addTab(spec);
   
 
   
        // Set drawable images to tab
        tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.helpedevent_button);
        //tabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.tab3);
           
        // Set Tab1 as Default tab and change image   
        tabHost.getTabWidget().setCurrentTab(0);
        tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.myevent_button_pressed);
        
        ////////
        TextView tvName = (TextView)findViewById(R.id.tvName2);
        TextView tvGender = (TextView)findViewById(R.id.tvGender2);
        TextView tvDescription = (TextView)findViewById(R.id.tvDescription2);
        
        
        tvName.setText(((Person)this.getApplication()).getName());
        
        tvName.setTextColor(Color.parseColor("#2EAFB2"));
  		
        tvDescription.setText(((Person)this.getApplication()).getCharacter());
  		tvDescription.setTextColor(Color.parseColor("#231F20"));
	
        
  		tvGender.setText(((Person)this.getApplication()).getGender());
  		tvGender.setTextColor(Color.parseColor("#9D9FA2"));

	    // TODO Auto-generated method stub
	}
	@Override
    public void onTabChanged(String tabId) {
         
        /************ Called when tab changed *************/
         
        //********* Check current selected tab and change according images *******/
         
        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            if(i==0)
                tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.myevent_button);
            else if(i==1)
                tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.helpedevent_button);
            /*else if(i==2)
                tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab3);*/
        }
         
         
        Log.i("tabs", "CurrentTab: "+tabHost.getCurrentTab());
         
    if(tabHost.getCurrentTab()==0)
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.myevent_button_pressed);
    else if(tabHost.getCurrentTab()==1)
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.helpedevent_button_pressed);
    /*else if(tabHost.getCurrentTab()==2)
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.tab3_over);*/
         
    }

}
