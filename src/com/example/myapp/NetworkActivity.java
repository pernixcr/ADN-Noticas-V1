package com.example.myapp;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkActivity extends Activity{
	
	private Context context;
	
	public NetworkActivity(Context context) {
		this.context = context;
	}
	
	public boolean networkAvailable(){
		ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);	
		return (connectivityManager != null) ? getNetworkInfo(connectivityManager):false;
	}

	public boolean getNetworkInfo(ConnectivityManager connectivityManager){
		NetworkInfo[] netInfo = connectivityManager.getAllNetworkInfo();
		
		return (netInfo != null) ? checkNetworkInfo(netInfo):false;
	}

	public boolean checkNetworkInfo(NetworkInfo[] netInfo){
		for (NetworkInfo net : netInfo) {
			if (net.getState() == NetworkInfo.State.CONNECTED) {
				return true;
			}
		}
		return false;
	}
	
}
