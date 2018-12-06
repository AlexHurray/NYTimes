package com.example.ermolaenkoalex.nytimes.ui.main.newslist;

import android.util.Log;

import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.common.BasePresenter;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsListPresenter extends BasePresenter<NewsListView> {

    private static final String LOG_TAG = "NewsListVM";

    @Nullable
    private Disposable disposable;

    @NonNull
    private List<NewsItem> newsList = new ArrayList<>();

    @NonNull
    private Section currentSection = Section.HOME;

    public NewsListPresenter() {
        Disposable disposable = repository.getDataObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsItems -> {
                    Log.d(LOG_TAG, newsItems.toString());
                    newsList = newsItems;
                    showState(new ResponseState(false, newsList));
                }, throwable -> Log.e(LOG_TAG, throwable.toString()));

        compositeDisposable.add(disposable);
    }

    public void getNews(boolean forceReload) {
        getNews(forceReload, currentSection);
    }

    public void getNews(boolean forceReload, Section section) {
        currentSection = section;

        if (newsList.isEmpty() || forceReload) {
            loadDataFromInternetAndSave2Db();
        } else {
            view.showState(new ResponseState(false, newsList));
        }
    }

    @Override
    protected void onCleared() {
        dispose();
        super.onCleared();
    }

    private void loadDataFromInternetAndSave2Db() {
        dispose();

        showState(new ResponseState(true, newsList));

        disposable = repository.updateNews(currentSection)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccess, this::handleError);
    }

    private void showState(@NonNull ResponseState state) {
        if (view != null) {
            view.showState(state);
        }
    }

    private void handleError(Throwable throwable) {
        Log.d(LOG_TAG, "handleError");

        int errorMessageId = throwable instanceof IOException ? R.string.error_network : R.string.error_request;
        showState(new ResponseState(false, newsList, errorMessageId));
    }

    private void handleSuccess() {
        Log.d(LOG_TAG, "handleSuccess");
    }

    private void dispose() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }
}
