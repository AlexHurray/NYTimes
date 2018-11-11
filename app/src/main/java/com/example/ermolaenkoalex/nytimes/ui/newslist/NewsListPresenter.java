package com.example.ermolaenkoalex.nytimes.ui.newslist;

import android.util.Log;

import com.example.ermolaenkoalex.nytimes.api.RestApi;
import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.dto.ResultDTO;
import com.example.ermolaenkoalex.nytimes.dto.ResultsDTO;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;
import com.example.ermolaenkoalex.nytimes.utils.NewsItemConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsListPresenter extends ViewModel {

    private static final String LOG_TAG = "NewsListVM";

    @Nullable
    private Disposable disposable;

    @NonNull
    private List<NewsItem> newsList = new ArrayList<>();

    @Nullable
    private NewsListView newsListView;

    @NonNull
    private Section currentSection = Section.HOME;

    public void bind(@NonNull NewsListView newsListView) {
        this.newsListView = newsListView;
    }

    public void unbind() {
        newsListView = null;
    }

    public void getNews(boolean forceReload) {
        getNews(forceReload, currentSection);
    }

    public void getNews(boolean forceReload, Section section) {
        currentSection = section;

        if (newsList.isEmpty() || forceReload) {
            loadData();
        } else {
            newsListView.showState(new ResponseState(false, newsList));
        }
    }

    private void loadData() {
        dispose();

        showState(new ResponseState(true, newsList));

        disposable = RestApi.news()
                .getNews(currentSection)
                .map(this::convert2NewsItemList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::checkResponseAndShowState, this::handleError);
    }

    @Override
    protected void onCleared() {
        dispose();
        super.onCleared();
    }

    private void showState(@NonNull ResponseState state) {
        if (newsListView != null) {
            newsListView.showState(state);
        }
    }

    private List<NewsItem> convert2NewsItemList(@NonNull ResultsDTO response) {
        List<NewsItem> items = new ArrayList<>();
        final List<ResultDTO> results = response.getResults();
        if (results == null) {
            return items;
        }

        for (ResultDTO resultDTO : results) {
            items.add(NewsItemConverter.resultDTO2NewsItem(resultDTO));
        }

        return items;
    }

    private void handleError(Throwable throwable) {
        Log.d(LOG_TAG, "handleError");

        int errorMessageId = throwable instanceof IOException ? R.string.error_network : R.string.error_request;
        showState(new ResponseState(false, newsList, errorMessageId));
    }

    private void checkResponseAndShowState(@NonNull List<NewsItem> items) {
        if (items.isEmpty()) {
            ResponseState state = new ResponseState(false);
            showState(state);
            return;
        }

        newsList = items;
        showState(new ResponseState(false, newsList));
    }

    private void dispose() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }
}
