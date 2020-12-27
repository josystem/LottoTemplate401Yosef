package com.coinsimkia.phunpapa;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

public class AppWebView extends AppCompatActivity {
    private WebView webView;
    private ProgressBar progressBar;
    private androidx.swiperefreshlayout.widget.SwipeRefreshLayout refreshLayout;
    private long BACK_BUTTON_TIME;
    private long BACK_BUTTON_EXIT_DELAY = 2000; // back button exit delay in millisecond
    private AdView adView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_web_view);
        webView = findViewById(R.id.web_view);
        progressBar = findViewById(R.id.web_view_progress);
        refreshLayout = findViewById(R.id.swipe_refresh);
        webView.getSettings().setJavaScriptEnabled(true); // enable javascript for webView
        webView.setWebViewClient(new AppWebClient());
        webView.loadUrl(Config.getUrl());
        refreshLayout.setOnRefreshListener(() -> {
            webView.reload();
            refreshLayout.setRefreshing(false);
        });

        if (Config.showADS()) {
            // replace with your facebook ads @param placement id
            adView = new AdView(this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50);
            AdSettings.setTestMode(true);// remove this in production
            LinearLayout adContainer = findViewById(R.id.banner_container);
            adContainer.addView(adView);
            adView.loadAd();
        }

    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else if (System.currentTimeMillis() - BACK_BUTTON_TIME < BACK_BUTTON_EXIT_DELAY) {
            super.onBackPressed();
        } else {
            BACK_BUTTON_TIME = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "Back again to exit app!", Toast.LENGTH_SHORT).show();

        }
    }

    class AppWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return !Config.isWebView();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }
}
