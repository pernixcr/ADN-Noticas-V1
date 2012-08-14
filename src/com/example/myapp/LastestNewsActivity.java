package com.example.myapp;

import java.util.ArrayList;
import java.util.Timer;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;

public class LastestNewsActivity extends Activity{

	Context context;
	NewsArrayList newsArrayList;
	final UIOperationsActivity uiMaker;
	static ArrayList<News> lastestNews = new ArrayList<News>();
	Button lastestNewsButton;
	
	public LastestNewsActivity(Context context, NewsArrayList newsArrayList, UIOperationsActivity uiMaker,Button lastestNewsButton){
		this.context = context;
		this.newsArrayList = newsArrayList;
		this.uiMaker = uiMaker;
		this.lastestNewsButton =lastestNewsButton;
	}
	
	public void getLastestNewsFromCategory(ArrayList<News> newsList){
		lastestNews.add(newsList.get(0));
		lastestNews.add(newsList.get(1));
    }
	
	protected void setLastestNews(){
		for (ArrayList<News> newsfromCategory : newsArrayList.getAllNews()){
			getLastestNewsFromCategory(newsfromCategory);
		}
	}
	
	protected void createLastestNewsSection(){
		updateLastestNewsButton();
		Timer timing = new Timer();
        timing.schedule(new LastestNewsUpdater(lastestNewsButton,context, newsArrayList.getAllNews(),lastestNews), 5000, 5000);
	}
	
	protected void updateLastestNewsButton(){
		setLastestNewsButtonAnimation();
		setLastestNewsButtonAction(newsArrayList.getNewsByCategory(61));
	}
	
	protected void setLastestNewsButtonAnimation(){
		Animation a = AnimationUtils.loadAnimation(context, R.anim.textanimation);
		a.reset();
		lastestNewsButton.startAnimation(a);
		lastestNewsButton.setText(Html.fromHtml("<b><font color='#980000'>LO ULTIMO: </font></b>"+ lastestNews.get(0).getTitle() + "."));
	}
	
	protected void setLastestNewsButtonAction(final ArrayList<News> allNews){
		lastestNewsButton.setOnClickListener(new WebView.OnClickListener() {
            public void onClick(View v) {
            	uiMaker.createViewPager(allNews,0,context);
           }});
	}
	
}
