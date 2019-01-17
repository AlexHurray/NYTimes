package com.example.ermolaenkoalex.nytimes.ui.main.newsedit;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.example.ermolaenkoalex.nytimes.MyApp;
import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.common.BasePresenter;
import com.example.ermolaenkoalex.nytimes.db.NewsRepository;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import toothpick.Toothpick;

@InjectViewState
public class NewsEditPresenter extends BasePresenter<NewsEditView> {

    private static final String LOG_TAG = "NewsEditVM";

    private NewsItem newsItem;
    private int id;

    private boolean inProgress = false;

    private Scheduler processScheduler;
    private Scheduler androidScheduler;

    NewsEditPresenter(int id) {
        this.id = id;
        Toothpick.inject(this, MyApp.getAppScope());
        this.processScheduler = Schedulers.io();
        this.androidScheduler = AndroidSchedulers.mainThread();
    }

    @VisibleForTesting
    public NewsEditPresenter(int id, NewsRepository repository, NewsItem item,
                             Scheduler processScheduler, Scheduler androidScheduler) {
        this.id = id;
        this.repository = repository;
        this.newsItem = item;
        this.processScheduler = processScheduler;
        this.androidScheduler = androidScheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getDataFromDb(id);
    }

    public void saveData(String title, String previewText, String imageUrl, String itemUrl) {
        if (inProgress) {
            return;
        } else {
            inProgress = true;
        }

        getViewState().showProgress(true);

        newsItem.setTitle(title);
        newsItem.setPreviewText(previewText);
        newsItem.setImageUrl(imageUrl);
        newsItem.setItemUrl(itemUrl);

        Disposable disposable = repository.saveItem(newsItem)
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe(this::onSuccessSave, this::handleSaveError);

        compositeDisposable.add(disposable);
    }

    private void getDataFromDb(int id) {
        Disposable disposable = repository.getItem(id)
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe(this::onSuccessLoad, this::handleGetError);

        compositeDisposable.add(disposable);
    }

    private void onSuccessLoad(@NonNull NewsItem item) {
        getViewState().setData(item);
        newsItem = item;
    }

    private void onSuccessSave() {
        inProgress = false;

        getViewState().showProgress(false);
        getViewState().close();
    }

    private void handleGetError(Throwable throwable) {
        Log.e(LOG_TAG, throwable.toString());

        getViewState().showErrorMessage(R.string.error_get_from_db);
        getViewState().close();
    }

    private void handleSaveError(Throwable throwable) {
        Log.e(LOG_TAG, throwable.toString());
        inProgress = false;

        getViewState().showProgress(false);
        getViewState().showErrorMessage(R.string.error_save_in_db);
    }
}
