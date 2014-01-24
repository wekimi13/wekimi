package com.wekimi.android;

import android.app.Activity;
import android.app.Application;

public class Person extends Application{
 
    private String name;
    private String phone;
    private String gender;
    private String character;
    private String[] othername;
    private String[] otherphone;
    
	public void setName(String name) {
		this.name = name;	
	}
	public void setPhone(String phone) {
		this.phone = phone;
		// TODO Auto-generated method stub
		
	}
	public void setCharacter(String character) {
		this.character = character;
		// TODO Auto-generated method stub
		
	}
	public void setGender(String gender) {
		this.gender = gender;
		// TODO Auto-generated method stub
		
	}
	public void setOthername(String[] othername) {
		this.othername = othername;
		// TODO Auto-generated method stub
		
	}
	public void setOtherphone(String[] otherphone) {
		this.otherphone = otherphone;
		// TODO Auto-generated method stub
		
	}
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}
	public String getPhone() {
		// TODO Auto-generated method stub
		return this.phone;
	}
	public String getGender() {
		// TODO Auto-generated method stub
		return this.gender;
	}
	public String getCharacter() {
		// TODO Auto-generated method stub
		return this.character;
	}
	public String[] getOthername() {
		// TODO Auto-generated method stub
		return this.othername;
	}
	public String[] getOtherphone() {
		// TODO Auto-generated method stub
		return this.otherphone;
	}
 
//getters & setters....
 
}


