package com.wekimi.android;


import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ContactRowDetail {

	public static void display(final Activity activity, ImageButton btn)
	{
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				LinearLayout scrollViewlinerLayout = (LinearLayout) activity.findViewById(R.id.linearLayoutForm);
	
				java.util.ArrayList<String> msg = new ArrayList<String>();
				
				for (int i = 0; i < scrollViewlinerLayout.getChildCount(); i++)
				{
					LinearLayout innerLayout = (LinearLayout) scrollViewlinerLayout.getChildAt(i);
					EditText edit = (EditText) innerLayout.findViewById(R.id.RegtvOtherName5);
					EditText edit2 = (EditText) innerLayout.findViewById(R.id.RegtvOtherPhone5);
					msg.add(edit.getText().toString()+edit2.getText().toString());

					
				}
				
				Toast t = Toast.makeText(activity.getApplicationContext(), msg.toString(), Toast.LENGTH_SHORT);
				t.show();
			}
		});
	}
	
	public static void add(final Activity activity, ImageButton btn)
	{
		final LinearLayout linearLayoutForm = (LinearLayout) activity.findViewById(R.id.linearLayoutForm);;	
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final LinearLayout newView = (LinearLayout)activity.getLayoutInflater().inflate(R.layout.rowregisterfriendtest, null);
				newView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

				linearLayoutForm.addView(newView);
			}
		});
	}
}



