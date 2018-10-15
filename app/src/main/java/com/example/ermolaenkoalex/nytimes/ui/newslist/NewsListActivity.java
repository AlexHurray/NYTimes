package com.example.ermolaenkoalex.nytimes.ui.newslist;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ermolaenkoalex.nytimes.common.BaseActivity;
import com.example.ermolaenkoalex.nytimes.mock.DataUtils;
import com.example.ermolaenkoalex.nytimes.ui.about.AboutActivity;
import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.ui.newsdetails.NewsDetailsActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsListActivity extends BaseActivity {

    private final NewsRecyclerAdapter.OnItemClickListener clickListener = newsItem
            -> NewsDetailsActivity.start(this, newsItem);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new NewsRecyclerAdapter(this, DataUtils.generateNews(), clickListener));

        final boolean isPortrait = isPortraitOrientation();
        final int NUM_COL_LAND = 2;

        recyclerView.setLayoutManager(isPortrait
                ? new LinearLayoutManager(this)
                : new GridLayoutManager(this, NUM_COL_LAND));

        recyclerView.addItemDecoration(new ItemDecorationNewsList(
                getResources().getDimensionPixelSize(R.dimen.spacing_small),
                isPortrait ? 1 : NUM_COL_LAND));
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

    private boolean isPortraitOrientation() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}
