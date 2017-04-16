package com.csform.android.uiapptemplate;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class ParallaxMediaActivity extends ActionBarActivity
{
	private VideoEnabledWebView webView;
	private VideoEnabledWebChromeClient webChromeClient;
	ActionBar bar;
	ProgressDialog progressDialog;
	ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{



		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_example);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#317EEC")));


progressBar=(ProgressBar)findViewById(R.id.progressBar);
		progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#317EEC"), PorterDuff.Mode.MULTIPLY);
		// Save the web view
		webView = (VideoEnabledWebView)findViewById(R.id.webView);
		webView.clearCache(true);


		// Initialize the VideoEnabledWebChromeClient and set event handlers
		View nonVideoLayout = findViewById(R.id.nonVideoLayout); // Your own view, read class comments
		ViewGroup videoLayout = (ViewGroup)findViewById(R.id.videoLayout); // Your own view, read class comments
		//noinspection all
		View loadingView = getLayoutInflater().inflate(R.layout.view_loading_video, null); // Your own view, read class comments
		webChromeClient = new VideoEnabledWebChromeClient(nonVideoLayout, videoLayout, loadingView, webView) // See all available constructors...
		{
			// Subscribe to standard events, such as onProgressChanged()...
			@Override
			public void onProgressChanged(WebView view, int progress)
			{
				// Your code...
			}
		};
		webChromeClient.setOnToggledFullscreen(new VideoEnabledWebChromeClient.ToggledFullscreenCallback()
		{
			@Override
			public void toggledFullscreen(boolean fullscreen)
			{
				// Your code to handle the full-screen change, for example showing and hiding the title bar. Example:
				if (fullscreen)
				{
					WindowManager.LayoutParams attrs = getWindow().getAttributes();
					attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
					attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
					getSupportActionBar().hide();
					getWindow().setAttributes(attrs);
					if (android.os.Build.VERSION.SDK_INT >= 14)
					{
						//noinspection all
						getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
					}
				}
				else
				{
					WindowManager.LayoutParams attrs = getWindow().getAttributes();
					attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
					attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
					getWindow().setAttributes(attrs);
					getSupportActionBar().show();
					if (android.os.Build.VERSION.SDK_INT >= 14)
					{
						//noinspection all
						getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
					}
				}

			}
		});
		webView.setWebChromeClient(webChromeClient);
		// Call private class InsideWebViewClient
		webView.setWebViewClient(new MyAppWebViewClient());

		// Navigate anywhere you want, but consider that this classes have only been tested on YouTube's mobile site
		webView.loadUrl("https://www.tuition.in/online-course/appsc/appsc_android.html");




	}

	private class InsideWebViewClient extends WebViewClient {
		@Override
		// Force links to be opened inside WebView and not in Default Browser
		// Thanks http://stackoverflow.com/a/33681975/1815624
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
	public class MyAppWebViewClient extends WebViewClient {

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			//view.findViewById(R.id.progressBar1).setVisibility(View.GONE);
			Log.i("pageFinished", "yesss");
			progressBar.setVisibility(View.INVISIBLE);
			//progressBar.setVisibility(View.GONE);
		}




		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

	}

	@Override
	public void onBackPressed()
	{
		// Notify the VideoEnabledWebChromeClient, and handle it ourselves if it doesn't handle it
		if (!webChromeClient.onBackPressed())
		{
			if (webView.canGoBack())
			{
				webView.goBack();
			}
			else
			{
				// Standard back button implementation (for example this could close the app)
				super.onBackPressed();
			}
		}
	}
	@Override
	public boolean onSupportNavigateUp(){
		finish();
		return true;
	}


}
