package com.example.shubham.searchtheweb;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BrowserActivity extends AppCompatActivity {
    WebView webView;
    ProgressBar load;
    TextView urlText;
    ImageButton closeButton;
    TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        webView = (WebView) findViewById(R.id.webView);
        load = (ProgressBar) findViewById(R.id.load_bar);
        urlText = (TextView) findViewById(R.id.url);
        closeButton = (ImageButton) findViewById(R.id.go_button);
        titleText = (TextView) findViewById(R.id.title);
        load.setVisibility(View.GONE);
        load.setMax(100);
        urlText.setText(getIntent().getExtras().getString("url_query"));
        webView.loadUrl(urlText.getText().toString());

        if (savedInstanceState != null)
            webView.restoreState(savedInstanceState);
        else {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.setBackgroundColor(Color.WHITE);
            webView.setWebViewClient(new OurViewClient());
            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int progress) {
                    load.setProgress(progress);
                    if (progress < 100 && progress > 0 && load.getVisibility() == View.GONE)
                        load.setVisibility(View.VISIBLE);
                    if (progress == 100 || progress == 0)
                        load.setVisibility(View.GONE);
                }
            });
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public class OurViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url_local) {
            view.loadUrl(url_local);
            CookieManager.getInstance().setAcceptCookie(true);
            String currentURL = view.getUrl();
            String pageTitle=view.getTitle();
            titleText.setText(pageTitle);
            urlText.setText(currentURL);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String currentURL = view.getUrl();
            String pageTitle=view.getTitle();
            titleText.setText(pageTitle);
            urlText.setText(currentURL);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
            webView.goBack();
        else {
            if (load.getProgress()<100 || load.getProgress()>0)
                webView.stopLoading();
            else
                closeButton.callOnClick();
        }
    }
}
