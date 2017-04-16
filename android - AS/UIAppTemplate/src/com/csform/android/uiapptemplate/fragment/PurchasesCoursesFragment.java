package com.csform.android.uiapptemplate.fragment;

/**
 * Created by Krishna on 16/04/17.
 */

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



/**
 * Created by Krishna on 15/04/17.
 */

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
import com.csform.android.uiapptemplate.TabShopActivity;

public class PurchasesCoursesFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private TextView mLike;
    private TextView mFavorite;
    private TextView mShare;
    WebView webView1;
    ProgressBar progressBar;

    private int position;

    public static PurchasesCoursesFragment newInstance(int position) {
        PurchasesCoursesFragment f = new PurchasesCoursesFragment();
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
        View rootView = inflater.inflate(R.layout.classroom,
                container, false);
        progressBar=(ProgressBar)rootView.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#317EEC"), PorterDuff.Mode.MULTIPLY);
        // Save the web view

        webView1 = (WebView) rootView.findViewById(R.id.webview1);
        webView1.setWebViewClient(new WebViewClient());
        webView1.addJavascriptInterface(new WebAppInterface(getActivity()), "Android");
        webView1.getSettings().setJavaScriptEnabled(true);
        //webView.setWebChromeClient(webChromeClient);
        // Call private class InsideWebViewClient

        webView1.setWebViewClient(new MyAppWebViewClient());
        webView1.clearCache(true);
        System.out.print("HI this is krishna");
        webView1.loadUrl("https://www.tuition.in/enrolled_courses_android.html");
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
        public void startNewActivity(String url, String name) {
            Intent intent = new Intent(getActivity(), TabShopActivity.class);

intent.putExtra("url", url);
            intent.putExtra("name",name);

            System.out.println("url"+url+"name"+"krishna hi");
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
            view.loadUrl("javascript:showActivity(url) ");
        }




        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    }


}
