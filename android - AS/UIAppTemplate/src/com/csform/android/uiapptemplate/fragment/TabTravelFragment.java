package com.csform.android.uiapptemplate.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.csform.android.uiapptemplate.ParallaxMediaActivity;
import com.csform.android.uiapptemplate.ParallaxSocialActivity;
import com.csform.android.uiapptemplate.R;

public class TabTravelFragment extends Fragment  {

	private static final String ARG_POSITION = "position";

	private TextView mLike;
	private TextView mFavorite;
	private TextView mShare;
	WebView webView;
	ProgressBar progressBar;

	private int position;

	public static TabTravelFragment newInstance(int position) {
		TabTravelFragment f = new TabTravelFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		position = getArguments().getInt(ARG_POSITION);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_search_bar_media,
				container, false);
		progressBar=(ProgressBar)rootView.findViewById(R.id.progressBar);
		progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#317EEC"), PorterDuff.Mode.MULTIPLY);
		// Save the web view

		webView = (WebView) rootView.findViewById(R.id.webview);
		webView.setWebViewClient(new WebViewClient());
		webView.addJavascriptInterface(new WebAppInterface(getActivity()), "Android");
		webView.getSettings().setJavaScriptEnabled(true);
		//webView.setWebChromeClient(webChromeClient);
		// Call private class InsideWebViewClient
		webView.setWebViewClient(new MyAppWebViewClient());

		webView.loadUrl("https://www.tuition.in/index_android.php");
		ViewCompat.setElevation(rootView, 50);

		return rootView;
	}
	public class WebAppInterface {
		Context mContext;

		/** Instantiate the interface and set the context */
		WebAppInterface(Context c) {
			mContext = c;
		}

		/** Show a toast from the web page */
		@JavascriptInterface
		public void startNewActivity() {
			Intent intent = new Intent(getActivity(), ParallaxMediaActivity.class);
			startActivity(intent);
		}
		@JavascriptInterface
		public void startNewActivity1() {
			Intent intent = new Intent(getActivity(), ParallaxSocialActivity.class);
			startActivity(intent);
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


}
