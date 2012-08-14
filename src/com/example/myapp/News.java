package com.example.myapp;

import android.graphics.drawable.Drawable;

public class News {
	private int id;
	private int category;
	private String title;
	private String details;
	private Drawable image;
	private String html;
	
	public News(int _id, int _category,String _title, 
				String _details, Drawable _image, String _html)
	{
		this.id = _id;
		this.category = _category;
		this.title = _title;
		this.details = _details;
		this.image = _image;
		this.html = _html;
	}
	public int getId()								{		return this.id;			}	
	public int getCategory()						{		return this.category;	}	
	public String getTitle()						{		return this.title;		}	
	public String getDetails()						{		return this.details;	}	
	public Drawable getImage()						{		return this.image;		}	
	public String getHtml()							{		return this.html;		}
	
	public void setId(int _id)									{	this.id = _id;				}
	public void setCategory(int _category)						{ 	this.category = _category; 	}
	public void setTitle(String _title)							{ 	this.title = _title; 		}	
	public void setDetails(String _details)						{ 	this.details = _details; 	}	
	public void setImage(Drawable _image)						{ 	this.image = _image;		}
	public void setHtml(String _html)							{ 	this.html = _html;			}
	}
