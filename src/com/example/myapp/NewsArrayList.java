package com.example.myapp;

import java.util.ArrayList;

import android.annotation.SuppressLint;

public class NewsArrayList{
	
	ArrayList<ArrayList<News>> allNews;
	
	public ArrayList<ArrayList<News>> getAllNews() 	{		return this.allNews;	}
	public void setAllNews(ArrayList<ArrayList<News>> _allNews)	{	this.allNews = _allNews;	}


	@SuppressLint({ "NewApi", "NewApi" })
	public ArrayList<News> getNewsByCategory(int category){
		switch (category){
		case 61:
			return allNews.get(0);
		case 81:
			return allNews.get(1);
		case 84:
			return allNews.get(2);
		case 330:
			return allNews.get(3);
		default:
			return null;
		}
	}
	
	protected static ArrayList<String> getHtmls(ArrayList<News> newsList){
		ArrayList<String> htmls = new ArrayList<String>();
		for (News news : newsList){
			htmls.add(news.getHtml());
		}
		return htmls;
	}
	public static ArrayList<String> getUrls(ArrayList<News> newsList) {
		ArrayList<String> htmls = new ArrayList<String>();
		for (News news : newsList){
			htmls.add(news.getDetails());
		}
		return htmls;
	}
}
