package com.example.myapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

public class LoadOnlineNewsAsyncTask extends AsyncTask<Object, String, String> {
	MainActivity callerActivity;
	ProgressDialog pDialog ;
	Context context;
	JSONParser jsonParser;
	HTMLParser htmlParser;
	
		protected void showProgressDialog(){
			pDialog = new ProgressDialog(context);
			pDialog.setMessage("Cargando Noticias ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}
		
		
		public LoadOnlineNewsAsyncTask(Context context, boolean isRefreshing){
			this.context = context;
			callerActivity = (MainActivity)context;
			jsonParser = new JSONParser();
			htmlParser = new HTMLParser();
			
			if (!isRefreshing){
				showProgressDialog();
			}
			if(callerActivity.usdbh == null){
				callerActivity.usdbh = new DatabaseHelper(context, "DBNews", null, 1);
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		protected void createNews(JSONObject JSONNews) throws JSONException{
			int id = Integer.parseInt(JSONNews.getString("TabID"));
			boolean existNews = callerActivity.usdbh.existNews(id);
			if (!existNews){
				callerActivity.usdbh.insertNewsToDatabase(createSingleArticle(JSONNews),callerActivity.uiMaker);
			}
		}
	
		public News createSingleArticle(JSONObject JSONNews) throws JSONException{
			int id = Integer.parseInt(JSONNews.getString("TabID"));
			int category = Integer.parseInt(JSONNews.getString("CategoryTabID"));
			String title = JSONNews.getString("Title");
			String image = JSONNews.getString("ImageURL");
			String details = JSONNews.getString("DetailsURL");
			
			Drawable drawableImage = downloadImage(image);
			String html = getHtmlFromURL(details);
			News newArticle = new News(id, category, title, details, drawableImage,html);
			return newArticle;
		}
		
		protected String getHtmlFromURL(String url){
			return htmlParser.getURLContent(url);
		}
		
		@Override
		protected String doInBackground(Object... args) {
			String WEBSERVICEADN = "http://www.adn.fm/ADNMobileWeb/JSONNewsSummary.aspx";
			
			List<NameValuePair> requestParams = new ArrayList<NameValuePair>();
			requestParams.add(new BasicNameValuePair("recordsPerCategory",
					"10"));
			
			try {
				JSONObject json = jsonParser.makeHttpRequest(WEBSERVICEADN, requestParams);
				if (json != null){
				//SQLiteDatabase db = usdbh.getWritableDatabase();
				//usdbh.delete(db);
				JSONArray news = json.getJSONArray("news");
				for (int i = 0; i < news.length(); i++) {
					JSONObject JSONNews = news.getJSONObject(i);						
					createNews(JSONNews);
					}
				}
				else{
					try {
						super.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}
		
		private Drawable downloadImage(String imageUrl)
		
         {	
            	 if(!imageUrl.equals("http://www.adn.fm")){
                 URL url;
                 InputStream is;
				try {					
					url = new URL(imageUrl);
					is = (InputStream)url.getContent();
					Drawable image = callerActivity.uiMaker.modifyImage(Drawable.createFromStream(is, null),100,100);
					is.close();
					return image;
				} catch (MalformedURLException e) {
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}						
             }
				return null;
       }
		
		@Override
		protected void onPostExecute(String string) {
			pDialog.dismiss();
			callerActivity.setContentView(R.layout.mainpage);
			callerActivity.createMainPage();
			callerActivity.usdbh.close();
		}
		
		
}
