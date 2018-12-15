package com.example.ermolaenkoalex.nytimes.ui.main.newslist;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.example.ermolaenkoalex.nytimes.MyApp;
import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.common.BasePresenter;
import com.example.ermolaenkoalex.nytimes.db.NewsRepository;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;
import com.example.ermolaenkoalex.nytimes.utils.SharedPreferencesHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import toothpick.Toothpick;

@InjectViewState
public class NewsListPresenter extends BasePresenter<NewsListView> {

    private static final String LOG_TAG = "NewsListPresenter";

    @Nullable
    private Disposable disposable;

    @NonNull
    private List<NewsItem> newsList = new ArrayList<>();

    @NonNull
    private Section currentSection = Section.HOME;

    @NonNull
    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    private Scheduler processScheduler;
    private Scheduler androidScheduler;

    NewsListPresenter() {
        Toothpick.inject(this, MyApp.getAppScope());
        this.processScheduler = Schedulers.io();
        this.androidScheduler = AndroidSchedulers.mainThread();
    }

    @VisibleForTesting
    public NewsListPresenter(NewsRepository repository, SharedPreferencesHelper sharedPreferencesHelper,
                             Scheduler processScheduler, Scheduler androidScheduler) {
        this.repository = repository;
        this.sharedPreferencesHelper = sharedPreferencesHelper;
        this.processScheduler = processScheduler;
        this.androidScheduler = androidScheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Disposable disposable = repository.getDataObservable()
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe(newsItems -> {
                    Log.d(LOG_TAG, newsItems.toString());
                    newsList = newsItems;
                    showState(new ResponseState(false, newsList));
                }, throwable -> Log.e(LOG_TAG, throwable.toString()));

        compositeDisposable.add(disposable);

        getNews(false);
    }

    public void getNews(boolean forceReload) {
        getNews(forceReload, currentSection);
    }

    public void getNews(boolean forceReload, Section section) {
        currentSection = section;
        sharedPreferencesHelper.setSection(currentSection);

        if (newsList.isEmpty() || forceReload) {
            loadDataFromInternetAndSave2Db();
        } else {
            getViewState().showState(new ResponseState(false, newsList));
        }
    }

    private void loadDataFromInternetAndSave2Db() {
        dispose();

        showState(new ResponseState(true, newsList));

        disposable = repository.updateNews(currentSection)
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe(this::handleSuccess, this::handleError);
    }

    private void showState(@NonNull ResponseState state) {
        getViewState().showState(state);
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
