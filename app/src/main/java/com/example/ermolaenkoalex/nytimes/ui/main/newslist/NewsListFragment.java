package com.example.ermolaenkoalex.nytimes.ui.main.newslist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.common.BaseFragment;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
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

    @NonNull
    private NewsListPresenter presenter;

    @NonNull
    private NewsRecyclerAdapter adapter;

    @Nullable
    private NewsListFragmentListener listener;

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
        setRetainInstance(true);
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        presenter = ViewModelProviders.of(this).get(NewsListPresenter.class);

        adapter = new NewsRecyclerAdapter(getContext(), this::onClickNews);
        recyclerView.setAdapter(adapter);

        int numCol = getResources().getInteger(R.integer.news_columns_count);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(numCol, StaggeredGridLayoutManager.VERTICAL));

        refresher.setOnRefreshListener(this);

        setTitle(getString(R.string.app_name), false);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.bind(this);
        presenter.getNews(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unbind();
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
                Toast.makeText(getContext(), state.getErrorMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            recyclerView.setVisibility(View.GONE);

            if (state.hasError()) {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText(state.getErrorMessage());
            }
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
    }
}
