package com.example.ermolaenkoalex.nytimes.ui.newsdetails;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.common.BaseActivity;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;

import androidx.appcompat.app.ActionBar;
import butterknife.BindView;

public class NewsDetailsActivity extends BaseActivity {

    private static final String KEY_URL = "KEY_URL";
    private static final String KEY_CATEGORY = "KEY_CATEGORY";

    @BindView(R.id.web_view)
    WebView webView;

    public static void start(Activity activity, NewsItem newsItem) {
        Intent startIntent = new Intent(activity, NewsDetailsActivity.class);
        startIntent.putExtra(KEY_URL, newsItem.getItemUrl());
        startIntent.putExtra(KEY_CATEGORY, newsItem.getCategory());
        activity.startActivity(startIntent);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        String newsURL = getIntent().getStringExtra(KEY_URL);
        String category = getIntent().getStringExtra(KEY_CATEGORY);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(category.toUpperCase());
            ab.setDisplayHomeAsUpEnabled(true);
        }

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            private static final String NYTIMES_HOST = "www.nytimes.com";

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return shouldOverrideUrlLoading(view, request.getUrl().toString());
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (Uri.parse(newsURL).getHost().equalsIgnoreCase(NYTIMES_HOST)) {
                    return false;
                }

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
        });

        webView.loadUrl(newsURL);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
