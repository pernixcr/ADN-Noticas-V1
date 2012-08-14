package com.example.myapp;

import java.util.ArrayList;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;

public class LastestNewsUpdater extends TimerTask{
    	ArrayList<ArrayList<News>> allNews;
    	int newsIndex = 1;
        private final Button subject;
        Context context;
        ArrayList<News> newsList;
    	ArrayList<News> lastestNews;

        public LastestNewsUpdater(Button subject, Context context, ArrayList<ArrayList<News>> newsList, ArrayList<News> lastestNews) {
            this.subject = subject;
            this.context = context;
            this.newsList = newsList.get(0);
            this.allNews = newsList;
            this.lastestNews = lastestNews;
        }
        
        public int getCategoryOfArrayNews(int newsIndex){
        	switch (newsIndex%8) {
			case 0: case 1:
				return 0;
			case 2: case 3:
				return 1;
			case 4: case 5:
				return 2;
			default:
				return 3;
			}
        }

        @Override
        public void run() {
        	if (allNews != null){
        	int catergoryOfArray = getCategoryOfArrayNews(newsIndex);
        	newsList = allNews.get(catergoryOfArray);
            subject.post(new Runnable() {
                public void run() {
                	subject.setOnClickListener(new WebView.OnClickListener() {
            		            public void onClick(View v) {
            		            	Intent intent = new Intent(context,WebViewPagerActivity.class);
            		            	Bundle bundle = new Bundle();
            		            	bundle.putStringArrayList("HTML",NewsArrayList.getHtmls(newsList));
            		            	bundle.putInt("POSITION",(newsIndex+1)%2);
            		            	intent.putExtras(bundle);
            		            	context.startActivity(intent);
            		           }});
                	subject.setText(Html.fromHtml("<b><font color='#980000'>LO ULTIMO: </font></b>"+lastestNews.get(newsIndex%8).getTitle()));
                    newsIndex ++;
                    Animation a = AnimationUtils.loadAnimation(context, R.anim.textanimation);
        			a.reset();
        			subject.startAnimation(a);
                }
            });
        }
        }
}
