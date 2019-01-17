package com.example.ermolaenkoalex.nytimes.ui.main.newslist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.common.BaseFragment;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsListFragment extends BaseFragment
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

    @InjectPresenter
    NewsListPresenter presenter;

    @NonNull
    private NewsRecyclerAdapter adapter;

    @Nullable
    private NewsListFragmentListener listener;

    @ProvidePresenter
    NewsListPresenter providePresenter() {
        return new NewsListPresenter();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof NewsListFragmentListener) {
            listener = (NewsListFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        adapter = new NewsRecyclerAdapter(getContext(), this::onClickNews);
        recyclerView.setAdapter(adapter);

        int numCol = getResources().getInteger(R.integer.news_columns_count);
        recyclerView.setLayoutManager(numCol == 1
                ? new LinearLayoutManager(getContext())
                : new StaggeredGridLayoutManager(numCol, StaggeredGridLayoutManager.VERTICAL));

        refresher.setOnRefreshListener(this);

        setTitle(getString(R.string.app_name), false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_news_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                if (listener != null) {
                    listener.onAboutClicked();
                }
                return true;
            case R.id.action_preferences:
                if (listener != null) {
                    listener.onPreferencesClicked();
                }
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
    public void showRefresher(boolean show) {
        refresher.setRefreshing(show);
    }

    @Override
    public void showData(List<NewsItem> data) {
        recyclerView.setVisibility(View.VISIBLE);
        adapter.setData(data);
        recyclerView.scrollToPosition(0);
        tvError.setVisibility(View.GONE);
    }

    @Override
    public void showError(int errorMessageId, boolean isToast) {
        if (!isToast) {
            recyclerView.setVisibility(View.GONE);
            tvError.setVisibility(View.VISIBLE);
            tvError.setText(errorMessageId);
        } else {
            Toast.makeText(getContext(), errorMessageId, Toast.LENGTH_LONG).show();
        }
    }

    public void loadNews(Section section) {
        presenter.getNews(true, section);
    }

    private void onClickNews(NewsItem newsItem) {
        if (listener != null) {
            listener.onNewsClicked(newsItem.getId());
        }
    }

    public interface NewsListFragmentListener {
        void onNewsClicked(int id);

        void onAboutClicked();

        void onPreferencesClicked();
    }
}
