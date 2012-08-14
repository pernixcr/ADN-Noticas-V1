package com.example.myapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class LoadOfflineNewsAsyncTask extends AsyncTask<Object, String, String> {
	MainActivity callerActivity;
	ProgressDialog pDialog ;
	Context context;
	
		public void setContext(Context context){
			this.context = context;
			callerActivity = (MainActivity)context;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(context);
			pDialog.setMessage("Cargando Noticias ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
			}
		
		@Override
		protected String doInBackground(Object... params) {
			try {
				Thread.sleep(500);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String response) {
			callerActivity.setContentView(R.layout.mainpage);
			callerActivity.createMainPage();
			callerActivity.usdbh.close();
			pDialog.dismiss();
		}


		
}
