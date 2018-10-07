package com.example.ermolaenkoalex.NYTimes.ui.newslist;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ermolaenkoalex.NYTimes.events.NewsItemEvent;
import com.example.ermolaenkoalex.NYTimes.mock.DataUtils;
import com.example.ermolaenkoalex.NYTimes.ui.about.AboutActivity;
import com.example.ermolaenkoalex.NYTimes.R;
import com.example.ermolaenkoalex.NYTimes.ui.newsdetails.NewsDetailsActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsListActivity extends AppCompatActivity {

    private static final int NUM_COL_LAND = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new NewsRecyclerAdapter(this, DataUtils.generateNews()));

        recyclerView.setLayoutManager(isPortraitOrientation() ?
                new LinearLayoutManager(this) :
                new GridLayoutManager(this, NUM_COL_LAND));

        recyclerView.addItemDecoration(new ItemDecorationNewsList(
                getResources().getDimensionPixelSize(R.dimen.spacing_small),
                isPortraitOrientation() ? 1 : NUM_COL_LAND));
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onItemClicked(@NonNull NewsItemEvent event) {
        NewsDetailsActivity.start(this, event.getNewsItem());
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private boolean isPortraitOrientation() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}
