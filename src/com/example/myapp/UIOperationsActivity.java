package com.example.myapp;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class UIOperationsActivity extends MainActivity {
	
	Bitmap bitmapImage;
	Bitmap icon;
	
	Resources resources;
	Drawable launcherIcon;
	Drawable newsDefaultIcon;
	Context context;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		}
	
	public UIOperationsActivity(Context _context){
		this.context = _context;
		resources = this.context.getResources();
		launcherIcon = resources.getDrawable(R.drawable.appicon_114);
		newsDefaultIcon = modifyIcon(launcherIcon,100,100);
	}
	
	@SuppressWarnings("deprecation")
	protected Drawable modifyImage(Drawable image,int newWidth, int newHeight){
		Bitmap resizedImage = resizeImage(image, newWidth, newHeight);
        
        Canvas canvas = new Canvas(resizedImage);
        
        final RectF rectF = new RectF();
        final Paint paint = new Paint();
        paint.setARGB(200, 0, 0, 0);
        paint.setStrokeWidth(6);

        rectF.set(-10,newHeight/2, newWidth+20, newHeight+20);
        canvas.drawRoundRect(rectF, 0,0, paint);
        bitmapImage= resizedImage;
        image = new BitmapDrawable(resizedImage);
        return image;
	}
	
	@SuppressWarnings("deprecation")
	protected Drawable modifyIcon(Drawable image,int newWidth, int newHeight){
		Bitmap resizedImage = resizeImage(image, newWidth, newHeight);
        
        Canvas canvas = new Canvas(resizedImage);
        
        final RectF rectF = new RectF();
        final Paint paint = new Paint();
        paint.setARGB(200, 0, 0, 0);
        paint.setStrokeWidth(6);

        rectF.set(-10,newHeight/2, newWidth+20, newHeight+20);
        canvas.drawRoundRect(rectF, 0,0, paint);
        icon= resizedImage;
        image = new BitmapDrawable(resizedImage);
        return image;
	}
	
	protected Bitmap resizeImage(Drawable image,int newWidth, int newHeight){
		Bitmap bitmapOrg = ((BitmapDrawable) image).getBitmap();
        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        
        Matrix matrix = new Matrix();
        
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,
        width, height, matrix, true);
        
        return resizedBitmap;
	}
	
	protected byte [] getByteFromDrawable(){
	    ByteArrayOutputStream stream = new ByteArrayOutputStream();
	    bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
	    byte[] imageInByte = stream.toByteArray();
	    return imageInByte;
  }
	
	protected byte [] getByteIconFromDrawable(){
	    ByteArrayOutputStream stream = new ByteArrayOutputStream();
	    icon.compress(Bitmap.CompressFormat.JPEG, 100, stream);
	    byte[] imageInByte = stream.toByteArray();
	    return imageInByte;
  }

	public void showAlertMessage(String message){
	    AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
	    alertbox.setMessage(message);
	    alertbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	    	public void onClick(DialogInterface arg0, int arg1) {
	        }});	
	    alertbox.show();
	}
	
	
	public void createViewPager(final ArrayList<News> allNews, int position, Context context){
		Intent intent = new Intent(context,WebViewPagerActivity.class);
    	Bundle bundle = new Bundle();
    	bundle.putStringArrayList("HTML",NewsArrayList.getHtmls(allNews));
    	bundle.putStringArrayList("URLS", NewsArrayList.getUrls(allNews));
    	bundle.putInt("POSITION",position);
    	intent.putExtras(bundle);
    	context.startActivity(intent);
	}
	
	protected boolean createSharingPopUp(final News newsItem,
			final int position, final ArrayList<News> newsList) {
		final String title = newsItem.getTitle();
		final String url = newsItem.getDetails();

		Intent intent = new Intent(context, PopUpIntentActivity.class);
		Bundle bundle = new Bundle();
		bundle.putStringArrayList("HTML", NewsArrayList.getHtmls(newsList));
		bundle.putInt("POSITION", position);
		bundle.putString("TITLE", title);
		bundle.putString("URL", url);
		intent.putExtras(bundle);
		context.startActivity(intent);
		return true;
	}
}
