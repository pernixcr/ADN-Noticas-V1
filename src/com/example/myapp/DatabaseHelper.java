package com.example.myapp;

 
import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
 
public class DatabaseHelper extends SQLiteOpenHelper {
 
    //Sentencia SQL para crear la tabla de Usuarios
    String sqlCreate = "CREATE TABLE if not exists News (news_id TEXT, category_id TEXT, title TEXT, details TEXT, image BLOB, html TEXT);";
    
    
    public DatabaseHelper(Context contexto, String nombre,
                               CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
        //db.execSQL(sqlTrigger);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS News");
        db.execSQL(sqlCreate);
    }
    
    
    public void delete(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS News");
        db.execSQL(sqlCreate);
    }
    
	public boolean existNews(int news_id){
    	SQLiteDatabase db2 = this.getReadableDatabase();     
        Cursor c = db2.rawQuery("SELECT * FROM News WHERE news_id=?", new String[]{Integer.toString(news_id)});  
        if (c.moveToFirst()) {
            	return true;
        }
        c.close();
        db2.close();
        return false;
    }
	
	public String getNewsHtml(int news_id){
    	SQLiteDatabase db2 = this.getReadableDatabase();     
        Cursor c = db2.rawQuery("SELECT html FROM News WHERE news_id=?", new String[]{Integer.toString(news_id)});  
        if (c.moveToFirst()) {
            	String html = c.getString(0);
            	return html;
        }
        c.close();
        db2.close();
        return null;
    }
	
	protected ArrayList<String> getNewsHtmlFromDatabase(int category){
    	ArrayList<String> newsHtmlList = new ArrayList<String>();
    	SQLiteDatabase db2 = this.getReadableDatabase();     
        Cursor c = db2.rawQuery("SELECT html FROM News WHERE category_id=? order by news_id desc limit 10", new String[]{Integer.toString(category)});  
        if (c.moveToFirst()) {        	
             do {
                  String html = c.getString(0);
                  newsHtmlList.add(html);
             } while(c.moveToNext());
        }
        c.close();
        db2.close();
        return newsHtmlList;
    }
	
	protected void insertNewsToDatabase(News news, UIOperationsActivity uiMaker)

	
    {  
      SQLiteDatabase db = this.getWritableDatabase();
      
  	if(db != null){
	    	String news_id = Integer.toString(news.getId());
	    	String category_id = Integer.toString(news.getCategory());
	    	String title = news.getTitle();
	    	String details = news.getDetails();
	    	String html = news.getHtml();
	    	
	    	ContentValues newNews = new ContentValues();
	    	newNews.put("news_id", Integer.parseInt(news_id));
	    	newNews.put("category_id",Integer.parseInt(category_id));
	    	newNews.put("title",title);
	    	newNews.put("details",details);
	    	Drawable image = news.getImage();
	        
	        byte [] blobImage;
	        if (image != null){
	        	 blobImage = uiMaker.getByteFromDrawable();
	        }
	        else{
	        	blobImage = uiMaker.getByteIconFromDrawable();
	        }
	        newNews.put("image",blobImage);
	        
	        newNews.put("html", html);
	        
	        db.insert("News", null, newNews);
  	}
  	db.close();   
    }
	

	protected ArrayList<News> getNewsFromDatabase(int category){
    	ArrayList<News> newsList = new ArrayList<News>();
    	SQLiteDatabase db2 = this.getReadableDatabase();     
        Cursor c = db2.rawQuery("SELECT * FROM News WHERE category_id=? order by news_id desc limit 10", new String[]{Integer.toString(category)});  
        if (c.moveToFirst()) {        	
             do {
                  int news_id = Integer.parseInt(c.getString(0));
                  int category_id = Integer.parseInt(c.getString(1));
                  String title = c.getString(2);
                  String details = c.getString(3);
                  byte[] imageByteArray=c.getBlob(4);
                  String html = c.getString(5);
                  ByteArrayInputStream imageStream = new ByteArrayInputStream(imageByteArray);
                  @SuppressWarnings("deprecation")
                  Drawable theImage= new BitmapDrawable(imageStream);
                  
                  News news = new News(news_id,category_id,title,details, theImage, html);
                  newsList.add(news);
             } while(c.moveToNext());
        }
        c.close();
        db2.close();
        return newsList;
    }
}