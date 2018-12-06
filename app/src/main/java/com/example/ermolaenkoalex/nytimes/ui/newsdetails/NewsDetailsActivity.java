package com.example.ermolaenkoalex.nytimes.ui.newsdetails;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.common.BaseActivity;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;
import com.example.ermolaenkoalex.nytimes.ui.newsedit.NewsEditActivity;
import com.example.ermolaenkoalex.nytimes.utils.StringUtils;

import javax.inject.Inject;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;

public class NewsDetailsActivity extends BaseActivity implements NewsDetailsView {
    private static final String KEY_ID = "KEY_ID";

    @BindView(R.id.iv_image)
    ImageView imageView;

    @BindView(R.id.tv_title)
    TextView titleView;

    @BindView(R.id.tv_date)
    TextView dateView;

    @BindView(R.id.tv_full_text)
    TextView fullTextView;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private int id;

    @NonNull
    @Inject
    NewsDetailsPresenter presenter;

    public static void start(Activity activity, int id) {
        Intent startIntent = new Intent(activity, NewsDetailsActivity.class);
        startIntent.putExtra(KEY_ID, id);
        activity.startActivity(startIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        presenter = ViewModelProviders.of(this).get(NewsDetailsPresenter.class);

        id = getIntent().getIntExtra(KEY_ID, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.bind(this);
        presenter.initNews(id);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbind();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                NewsEditActivity.start(this, id);
                return true;
            case R.id.action_delete:
                presenter.delete();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setData(NewsItem newsItem) {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(newsItem.getCategory().toUpperCase());
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
        fullTextView.setText(newsItem.getPreviewText());
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void showErrorMessage(@IdRes int errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }
}
