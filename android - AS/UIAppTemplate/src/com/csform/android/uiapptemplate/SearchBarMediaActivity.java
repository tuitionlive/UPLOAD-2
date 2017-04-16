package com.csform.android.uiapptemplate;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SearchBarMediaActivity extends Activity {

    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar_media);

        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl("https://www.tuition.in/index_android.php");

        //String customHtml = "<html><body><h2>Greetings from JavaCodeGeeks</h2></body></html>";
        //webView.loadData(customHtml, "text/html", "UTF-8");

    }

}



