package com.example.shubham.searchtheweb;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class BrowserActivity extends AppCompatActivity {
    WebView webView;
    ProgressBar load;
    EditText url;
    Button goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        webView = (WebView) findViewById(R.id.webView);
        load = (ProgressBar) findViewById(R.id.load_bar);
        url = (EditText) findViewById(R.id.url);
        goButton = (Button) findViewById(R.id.go_button);
        load.setVisibility(View.GONE);
        load.setMax(100);
        url.setText(getIntent().getExtras().getString("url_query"));
        webView.loadUrl(url.getText().toString());

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
            goButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    webView.loadUrl("https://" + url.getText().toString());
                    //url.setText("");
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
            url.setText(currentURL);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back:
                if (webView.canGoBack())
                    webView.goBack();
                return true;

            case R.id.forward:
                if (webView.canGoForward())
                    webView.goForward();
                return true;

            case R.id.search_page:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
            webView.goBack();
        else {
            if (load.getProgress()<100)
                webView.stopLoading();
            else
                super.onBackPressed();
        }
    }
}