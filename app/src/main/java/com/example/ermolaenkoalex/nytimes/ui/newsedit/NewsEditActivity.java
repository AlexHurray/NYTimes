package com.example.ermolaenkoalex.nytimes.ui.newsedit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.common.BaseActivity;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;

import javax.inject.Inject;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;

public class NewsEditActivity extends BaseActivity implements NewsEditView {

    private static final String KEY_ID = "KEY_ID";

    @BindView(R.id.et_title)
    EditText etTitle;

    @BindView(R.id.et_preview_text)
    EditText etPreviewText;

    @BindView(R.id.et_news_url)
    EditText etNewsUrl;

    @BindView(R.id.et_image_url)
    EditText etImageUrl;

    private int id;

    @NonNull
    @Inject
    NewsEditPresenter presenter;

    public static void start(Activity activity, int id) {
        Intent startIntent = new Intent(activity, NewsEditActivity.class);
        startIntent.putExtra(KEY_ID, id);
        activity.startActivity(startIntent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_edit);

        presenter = ViewModelProviders.of(this).get(NewsEditPresenter.class);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(R.string.title_edit_news);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        id = getIntent().getIntExtra(KEY_ID, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.bind(this);
        presenter.getNews(id);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbind();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_apply:
                presenter.saveData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setData(NewsItem data) {
        etTitle.setText(data.getTitle());
        etPreviewText.setText(data.getPreviewText());
        etNewsUrl.setText(data.getItemUrl());
        etImageUrl.setText(data.getImageUrl());
    }

    @Override
    public void updateData(NewsItem newsItem) {
        newsItem.setTitle(etTitle.getText().toString());
        newsItem.setPreviewText(etPreviewText.getText().toString());
        newsItem.setImageUrl(etImageUrl.getText().toString());
        newsItem.setItemUrl(etNewsUrl.getText().toString());
    }

    @Override
    public void close(@IdRes int errorMessage) {
        if (errorMessage != 0) {
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }

        finish();
    }
}
