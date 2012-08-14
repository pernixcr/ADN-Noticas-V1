package com.example.myapp;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	static ArrayList<News> lastestNews;

	WebViewPagerActivity webviewsPager;
	NetworkActivity networkActivy;

	UIOperationsActivity uiMaker;
	Drawable newsDefaultIcon;
	DatabaseHelper usdbh;

	Button button;
	MediaPlayer mp;
	Boolean firstClick;
	Boolean onPause;

	NewsArrayList newsArrayList;
	LastestNewsActivity lastestNewsClass;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initializeGlobals();
		initializeApplication();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	protected void initializeGlobals() {
		networkActivy = new NetworkActivity(MainActivity.this);
		webviewsPager = new WebViewPagerActivity();
		uiMaker = new UIOperationsActivity(MainActivity.this);
		usdbh = new DatabaseHelper(MainActivity.this, "DBNews", null, 1);
		lastestNews = new ArrayList<News>();
		setIconDrawable();
	}

	protected void setIconDrawable() {
		Resources resources = getResources();
		Drawable launcherIcon = resources.getDrawable(R.drawable.appicon_114);
		newsDefaultIcon = uiMaker.modifyIcon(launcherIcon, 100, 100);
	}

	protected void loadOnlineApplication() {
		try {
			LoadOnlineNewsAsyncTask newtask = new LoadOnlineNewsAsyncTask(
					MainActivity.this, false);
			newtask.execute();
			TextView textLastUpdate = (TextView) findViewById(R.id.textViewLastUpdate);
			textLastUpdate.append(getCurrentTimeString());
		} catch (Exception e) {
		}
	}

	protected void initializeApplication() {
		if (networkActivy.networkAvailable())
			loadOnlineApplication();
		else
			loadOfflineApplication("No hay conexión, las noticias no se actualizarán. \n"
					+ "Y el servicio de Cabina en Vivo no funcionará \n");
	}

	protected void loadOfflineApplication(String message) {
		AlertDialog.Builder alertbox = new AlertDialog.Builder(
				MainActivity.this);
		alertbox.setMessage(message);
		alertbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				LoadOfflineNewsAsyncTask newtask = new LoadOfflineNewsAsyncTask();
				newtask.setContext(MainActivity.this);
				newtask.execute(MainActivity.this);
			}
		});
		alertbox.show();
	}

	public void onRefreshButton() {
		if (networkActivy.networkAvailable()) {
			loadOnlineApplication();
		} else {
			uiMaker.showAlertMessage("En este momento no se puede "
					+ "actualizar las noticias");
		}
	}

	public void showTermsAndServices(View v) {
		String termsAndServices = "http://www.adn.fm/ADNMobileWeb/JSONNewsSummary.aspx?recordsPerCategory=10";
		setContentView(R.layout.webview);
		WebView myWebView = (WebView) findViewById(R.id.webviewmain);
		myWebView.loadUrl(termsAndServices);
	}

	/*
	 * private void playStreaming() { String mFile = "./res/ADNRADIO.m3u"; mp =
	 * new MediaPlayer();
	 * 
	 * try { mp.setDataSource(mFile);
	 * mp.setAudioStreamType(AudioManager.STREAM_MUSIC); mp.prepareAsync();
	 * 
	 * mp.setOnPreparedListener(new OnPreparedListener() {
	 * 
	 * public void onPrepared(MediaPlayer mp) { dialog.dismiss(); mp.start();
	 * }}); } catch (Exception e) { } }
	 * 
	 * public void pauseStreaming(){ mp.pause(); button.setText("Play"); }
	 * 
	 * public void play(){ mp.start(); button.setText("Pause"); }
	 */

	protected void createLiveStreamButton() {
		ImageButton button = (ImageButton) findViewById(R.id.buttonLiveStreaming);
		final Streaming ADNLiveStreaming = new Streaming();
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				if (networkActivy.networkAvailable()) {
					ADNLiveStreaming.buttonAction();
				} else {
					uiMaker.showAlertMessage("No hay conexion a internet");
				}
			}
		});
	}

	protected void streamAudio(View v) {
		if (networkActivy.networkAvailable()) {
			final Streaming ADNLiveStreaming = new Streaming();
			ADNLiveStreaming.buttonAction();
		} else {
			uiMaker.showAlertMessage("No hay conexion a internet");
		}
	}

	protected ArrayList<ArrayList<News>> getNewsFromDatabase() {
		ArrayList<News> nationalNews = usdbh.getNewsFromDatabase(61);
		ArrayList<News> sportNews = usdbh.getNewsFromDatabase(81);
		ArrayList<News> adnhoyNews = usdbh.getNewsFromDatabase(84);
		ArrayList<News> worldNews = usdbh.getNewsFromDatabase(330);


		ArrayList<ArrayList<News>> allNews = new ArrayList<ArrayList<News>>();
		allNews.add(adnhoyNews);
		allNews.add(nationalNews);
		allNews.add(sportNews);
		allNews.add(worldNews);

		return allNews;
	}

	protected ArrayList<LinearLayout> getNewsContainers() {
		LinearLayout nationalNewsLayout = (LinearLayout) findViewById(R.id.nationalNewsContainer);
		LinearLayout sportNewsLayout = (LinearLayout) findViewById(R.id.sportNewsContainer);
		LinearLayout worldNewsLayout = (LinearLayout) findViewById(R.id.worldNewsContainer);
		LinearLayout adnHoyNewsLayout = (LinearLayout) findViewById(R.id.adnHoyNewsContainer);
		

		ArrayList<LinearLayout> NewsContainers = new ArrayList<LinearLayout>();
		NewsContainers.add(adnHoyNewsLayout);
		NewsContainers.add(nationalNewsLayout);
		NewsContainers.add(sportNewsLayout);
		NewsContainers.add(worldNewsLayout);

		return NewsContainers;
	}

	protected void createMainPageButtons() {
		ArrayList<LinearLayout> NewsContainers = getNewsContainers();		
		
		createInterfaceButtons(newsArrayList.getNewsByCategory(84),
				NewsContainers.get(0));
		createInterfaceButtons(newsArrayList.getNewsByCategory(61),
				NewsContainers.get(1));
		createInterfaceButtons(newsArrayList.getNewsByCategory(81),
				NewsContainers.get(2));
		createInterfaceButtons(newsArrayList.getNewsByCategory(330),
				NewsContainers.get(3));
		
	}

	protected void createLastestNewsButton() {
		setContentView(R.layout.mainpage);
		Button lastestNewsButton = (Button) findViewById(R.id.newNews);
		LastestNewsActivity lastestNewsClass = new LastestNewsActivity(
				MainActivity.this, newsArrayList, uiMaker, lastestNewsButton);

		lastestNewsClass.setLastestNews();
		lastestNewsClass.createLastestNewsSection();
	}

	protected void createMainPage() {
		newsArrayList = new NewsArrayList();
		newsArrayList.setAllNews(getNewsFromDatabase());
		createLastestNewsButton();
		createMainPageButtons();
	}

	protected Button buttonWithBackground(Drawable image) {
		Button button = new Button(MainActivity.this);
		image = getButtonBackgroundImage(image);
		button.setBackgroundDrawable(image);

		return button;
	}

	protected Drawable getButtonBackgroundImage(Drawable image) {
		return (image != null) ? image : newsDefaultIcon;
	}

	protected void setNewsButtonForeground(Button button, String title) {
		button.setText(title);
		button.setTextSize(10);
		button.setWidth(100);
		button.setMaxLines(4);
		button.setTextColor(Color.WHITE);
		button.setPadding(0, 50, 0, 1);
		button.setGravity(Gravity.LEFT);
	}

	protected void setNewsButtonAction(Button button, final News newsItem,
			final int position, final ArrayList<News> newsList) {
		button.setOnClickListener(new WebView.OnClickListener() {
			public void onClick(View v) {
				uiMaker.createViewPager(newsList, position, MainActivity.this);
			}
		});

		button.setOnLongClickListener(new WebView.OnLongClickListener() {
			public boolean onLongClick(View v) {
				return uiMaker.createSharingPopUp(newsItem, position, newsList);
			}
		});
	}

	protected Button setNewsButton(final News newsItem, final int position,
			final ArrayList<News> newsList) {
		Drawable image = newsItem.getImage();
		final String title = newsItem.getTitle();
		final Button button = buttonWithBackground(image);
		setNewsButtonAction(button, newsItem, position, newsList);
		setNewsButtonForeground(button, title);
		return button;
	}

	protected void createInterfaceButtons(ArrayList<News> news,
			LinearLayout layout) {
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.CENTER;
		layoutParams.setMargins(12, 12, 12, 12);
		for (int i = 0; i < news.size(); i++) {
			News singleNew = news.get(i);
			Button newsButton = setNewsButton(singleNew, i, news);
			layout.addView(newsButton, layoutParams);
		}
	}

	private String getCurrentTimeString() {
		Calendar calendar = Calendar.getInstance();
		String am_pm;
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		if (calendar.get(Calendar.AM_PM) == 0)
			am_pm = "AM";
		else
			am_pm = "PM";

		return String.format("%02d:%02d " + am_pm + " " + day + "/" + month
				+ "/" + year, hour, minute);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.mainmenu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.btRefresh:
			onRefreshButton();
			return true;
		}
		return super.onMenuItemSelected(featureId, item);
	}
}