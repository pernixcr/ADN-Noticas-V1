package com.example.myapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class WebViewPagerActivity extends Activity {

    private ViewPager awesomePager;
    private static int NUM_AWESOME_VIEWS = 10;
    private Context context;
    private AwesomePagerAdapter awesomeAdapter;
    private ArrayList<String> htmls;
    private ArrayList<String> urls;
    LayoutInflater inflater ;



    /** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    Bundle bundle = getIntent().getExtras();
    htmls = bundle.getStringArrayList("HTML");
    urls = bundle.getStringArrayList("URLS");
    int position = bundle.getInt("POSITION");
	
    setContentView(R.layout.viewpager);
    context = this;
    inflater = (LayoutInflater)
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    
    awesomeAdapter = new AwesomePagerAdapter();
    awesomePager = (ViewPager) findViewById(R.id.awesomepager);
    awesomePager.setAdapter(awesomeAdapter); 
    startAwesomePager(position);
    paintTrace(position);
    awesomePager.setOnPageChangeListener(
            new ViewPager.SimpleOnPageChangeListener() {
                @Override
				public void onPageSelected(int position) {
                	paintTrace(position);
                }
            });
    
}

@Override 
public boolean onCreateOptionsMenu(Menu menu) { 
      super.onCreateOptionsMenu(menu); 
      MenuInflater mi = getMenuInflater(); 
      mi.inflate(R.menu.webviewmenu, menu); 
      return true; 
}

@Override 
public boolean onMenuItemSelected(int featureId, MenuItem item) { 
     switch(item.getItemId()) { 
     case R.id.btShare:
   	  	onShareButton();
           return true; 
     } 
     return super.onMenuItemSelected(featureId, item); 
}

public void onShareButton(){
	Intent sharingIntent = new Intent(Intent.ACTION_SEND);
	sharingIntent.setType("text/plain");
	sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");
	sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
	startActivity(Intent.createChooser(sharingIntent, "Compartir en"));
}

public void paintTrace(int position){
	switch (position){
		case 0:
			buttonErase();
	    	View button = findViewById(R.id.View01);
	    	button.setBackgroundColor(Color.RED);
	    	break;
		case 1:
			buttonErase();
	    	View button2 = findViewById(R.id.View02);
	    	button2.setBackgroundColor(Color.RED);
	    	break;
		case 2:
			buttonErase();
	    	View button3 = findViewById(R.id.View03);
	    	button3.setBackgroundColor(Color.RED);
	    	break;
		case 3:
			buttonErase();
	    	View button4 = findViewById(R.id.View04);
	    	button4.setBackgroundColor(Color.RED);
	    	break;
		case 4:
			buttonErase();
	    	View button5 = findViewById(R.id.View05);
	    	button5.setBackgroundColor(Color.RED);
	    	break;
		case 5:
			buttonErase();
	    	View button6 = findViewById(R.id.View06);
	    	button6.setBackgroundColor(Color.RED);
	    	break;
		case 6:
			buttonErase();
	    	View button7 = findViewById(R.id.View07);
	    	button7.setBackgroundColor(Color.RED);
	    	break;
		case 7:
			buttonErase();
	    	View button8 = findViewById(R.id.View08);
	    	button8.setBackgroundColor(Color.RED);
	    	break;
		case 8:
			buttonErase();
	    	View button9 = findViewById(R.id.View09);
	    	button9.setBackgroundColor(Color.RED);
	    	break;
		case 9:
			buttonErase();
	    	View button10 = findViewById(R.id.View10);
	    	button10.setBackgroundColor(Color.RED);
	    	break;
	} 
}

public void buttonErase(){
	View button = findViewById(R.id.View01);
	View button2 = findViewById(R.id.View02);
	View button3 = findViewById(R.id.View03);
	View button4 = findViewById(R.id.View04);
	View button5 = findViewById(R.id.View05);
	View button6 = findViewById(R.id.View06);
	View button7 = findViewById(R.id.View07);
	View button8 = findViewById(R.id.View08);
	View button9 = findViewById(R.id.View09);
	View button10 = findViewById(R.id.View10);
	
	button.setBackgroundColor(Color.GRAY);
	button2.setBackgroundColor(Color.GRAY);
	button3.setBackgroundColor(Color.GRAY);
	button4.setBackgroundColor(Color.GRAY);
	button5.setBackgroundColor(Color.GRAY);
	button6.setBackgroundColor(Color.GRAY);
	button7.setBackgroundColor(Color.GRAY);
	button8.setBackgroundColor(Color.GRAY);
	button9.setBackgroundColor(Color.GRAY);
	button10.setBackgroundColor(Color.GRAY);
	
}

public void startAwesomePager(int position){
    awesomePager.setCurrentItem(position);
}

private class AwesomePagerAdapter extends PagerAdapter{

    @Override
    public int getCount() {
        return NUM_AWESOME_VIEWS;
    }


    @Override
    public Object instantiateItem(View collection, int position) {

        View layout; 
        LayoutInflater mInflater =  (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        layout  = mInflater.inflate(R.layout.webview, null);

        WebView mainContent = (WebView)layout.findViewById(R.id.webviewmain);
        WebSettings webSettings = mainContent.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        mainContent.requestFocusFromTouch();

        mainContent.setWebViewClient(new WebViewClient());
        mainContent.setWebChromeClient(new WebChromeClient());
        String htmlCss =  htmls.get(position) ;
        mainContent.loadDataWithBaseURL(urls.get(position), htmlCss,"text/html", "UTF-8", null);

        ((ViewPager) collection).addView(layout,0);

        return layout;
    }

    @Override
    public void destroyItem(View collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);
    }



    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==((View)object);
    }
    
    @Override
    public void finishUpdate(View arg0) {}


    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {}

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {}

	}
}