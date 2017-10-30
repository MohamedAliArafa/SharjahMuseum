package com.asgatech.sharjahmuseums.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.asgatech.sharjahmuseums.R;

public class OpenWebViewActivity extends AppCompatActivity {
    private WebView webView;
    private String bookLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_webview);
        if (getIntent().getStringExtra("bookLink") != null) {
            bookLink = getIntent().getStringExtra("bookLink");
        }
        if (getIntent().getStringExtra("attachUrl") != null) {
            bookLink = getIntent().getStringExtra("attachUrl");
        }
        webView = findViewById(R.id.webView1);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //  String url = URLS.URL_BASE + bookLink  + "&lang=" + new UserData().getLocalization(this);
        String url = bookLink;
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }


}
