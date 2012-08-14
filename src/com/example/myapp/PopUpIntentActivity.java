package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PopUpIntentActivity extends Activity {
	public static final String TITLE = "SEARCH_DIALOG";
	private Button viewArticleBtn;
	private Button shareArticleBtn;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.popup_window);
	viewArticleBtn = (Button)findViewById(R.id.viewArticleButton);
	final Bundle bundle = getIntent().getExtras();
	final String title = bundle.getString("TITLE");
	final String url = bundle.getString("URL");
	setTitle(title);
	
	viewArticleBtn.setOnClickListener(new OnClickListener(){
	public void onClick(View v) {
		Intent intent = new Intent(PopUpIntentActivity.this,WebViewPagerActivity.class);
    	intent.putExtras(bundle);
    	PopUpIntentActivity.this.startActivity(intent);
	}
	});
	
	shareArticleBtn = (Button)findViewById(R.id.shareArticleButton);
	shareArticleBtn.setOnClickListener(new OnClickListener(){
	public void onClick(View v) {
    	Intent sharingIntent = new Intent(Intent.ACTION_SEND);
    	sharingIntent.setType("text/plain");
    	sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, url);
    	sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
    	startActivity(Intent.createChooser(sharingIntent, "Compartir en"));
	}
	});
	}
}
