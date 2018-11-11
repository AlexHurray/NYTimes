package com.example.ermolaenkoalex.nytimes.ui.newslist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ermolaenkoalex.nytimes.common.BaseActivity;
import com.example.ermolaenkoalex.nytimes.ui.about.AboutActivity;
import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.ui.newsdetails.NewsDetailsActivity;
import com.google.android.material.chip.Chip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.TextViewCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
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

    @BindView(R.id.tv_error)
    @NonNull
    TextView tvError;

    @BindView(R.id.ll_sections)
    @NonNull
    LinearLayout llSections;

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
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(numCol, StaggeredGridLayoutManager.VERTICAL));

        refresher.setOnRefreshListener(this);

        addChips();
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
    public void showState(@NonNull ResponseState state) {
        if (state.isLoading()) {
            refresher.setRefreshing(true);
            return;
        }

        refresher.setRefreshing(false);
        if (state.hasData()) {
            recyclerView.setVisibility(View.VISIBLE);
            adapter.setData(state.getData());
            recyclerView.scrollToPosition(0);

            tvError.setVisibility(View.GONE);

            if (state.hasError()) {
                Toast.makeText(this, state.getErrorMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            recyclerView.setVisibility(View.GONE);

            if (state.hasError()) {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText(state.getErrorMessage());
            }
        }
    }

    private void addChips() {

        for (Section section : Section.values()) {
            Chip chip = new Chip(this);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            TextViewCompat.setTextAppearance(chip, R.style.TextAppearance_AppCompat_Title_Inverse);

            int spacing = getResources().getDimensionPixelSize(R.dimen.spacing_standard);
            params.setMargins(spacing, spacing, spacing, spacing);
            params.setMarginStart(spacing);
            params.setMarginEnd(spacing);

            chip.setLayoutParams(params);
            chip.setChipBackgroundColorResource(R.color.colorPrimaryDark);
            chip.setText(section.getSectionNameResId());
            chip.setOnClickListener(view -> presenter.getNews(true, section));

            llSections.addView(chip);
        }
    }
}
