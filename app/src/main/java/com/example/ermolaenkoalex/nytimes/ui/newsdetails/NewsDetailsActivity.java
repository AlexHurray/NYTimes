package com.example.ermolaenkoalex.nytimes.ui.newsdetails;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.common.BaseActivity;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;
import com.example.ermolaenkoalex.nytimes.utils.StringUtils;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailsActivity extends BaseActivity {

    private static final String KEY_NEWS_ITEM = "KEY_NEWS_ITEM";

    @BindView(R.id.iv_image)
    ImageView imageView;

    @BindView(R.id.tv_title)
    TextView titleView;

    @BindView(R.id.tv_date)
    TextView dateView;

    @BindView(R.id.tv_full_text)
    TextView fullTextView;

    public static void start(Activity activity, NewsItem newsItem) {
        Intent startIntent = new Intent(activity, NewsDetailsActivity.class);
        startIntent.putExtra(KEY_NEWS_ITEM, newsItem);
        activity.startActivity(startIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        NewsItem newsItem = (NewsItem) getIntent().getSerializableExtra(KEY_NEWS_ITEM);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(newsItem.getCategory().getName().toUpperCase());
            ab.setDisplayHomeAsUpEnabled(true);
        }

        RequestOptions imageOption = new RequestOptions()
                .placeholder(R.drawable.placeholder_image)
                .fallback(R.drawable.placeholder_image)
                .centerCrop();
        Glide.with(this)
                .applyDefaultRequestOptions(imageOption)
                .load(newsItem.getImageUrl())
                .into(imageView);
        titleView.setText(newsItem.getTitle());
        dateView.setText(StringUtils.formatDate(this, newsItem.getPublishDate()));
        fullTextView.setText(newsItem.getFullText());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
