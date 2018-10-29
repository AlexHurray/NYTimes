package com.example.ermolaenkoalex.nytimes.ui.newslist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ermolaenkoalex.nytimes.common.BaseActivity;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;
import com.example.ermolaenkoalex.nytimes.ui.about.AboutActivity;
import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.ui.newsdetails.NewsDetailsActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

public class NewsListActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener, NewsListView {

    @BindView(R.id.recycler_view)
    @NonNull
    RecyclerView recyclerView;

    @BindView(R.id.refresher)
    @NonNull
    SwipeRefreshLayout refresher;

    @NonNull
    private NewsListPresenter presenter;

    @NonNull
    private NewsRecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        presenter = ViewModelProviders.of(this).get(NewsListPresenter.class);

        adapter = new NewsRecyclerAdapter(this, newsItem
                -> NewsDetailsActivity.start(this, newsItem));
        recyclerView.setAdapter(adapter);

        int numCol = getResources().getInteger(R.integer.news_columns_count);

        recyclerView.setLayoutManager(numCol == 1
                ? new LinearLayoutManager(this)
                : new GridLayoutManager(this, numCol));

        recyclerView.addItemDecoration(new ItemDecorationNewsList(
                getResources().getDimensionPixelSize(R.dimen.spacing_small), numCol));

        refresher.setOnRefreshListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.bind(this);
        presenter.getNews(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbind();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        presenter.getNews(true);
    }

    @Override
    public void showLoading() {
        refresher.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        refresher.setRefreshing(false);
    }

    @Override
    public void setData(@NonNull List<NewsItem> data) {
        adapter.setData(data);
    }

    @Override
    public void showErrorToast() {
        Toast.makeText(this, R.string.unknown_error, Toast.LENGTH_LONG).show();
    }
}
