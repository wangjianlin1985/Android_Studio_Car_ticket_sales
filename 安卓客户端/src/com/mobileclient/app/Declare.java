package com.mobileclient.app;



import android.app.Application;

public class Declare extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub 
		super.onCreate();
		
		CrashHandler crashHandler = CrashHandler.getInstance();    
	    crashHandler.init(getApplicationContext()); 
	}
	private int id;
    private String userName;
    //用户身份标识
    private String identify;  
    public String getIdentify() {
		return identify;
	}
	public void setIdentify(String identify) {
		this.identify = identify;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	} 
}
