package com.example.ermolaenkoalex.nytimes.ui.main.newsdetails;

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
public class NewsDetailsPresenter extends BasePresenter<NewsDetailsView> {

    private static final String LOG_TAG = "NewsDetailsVM";

    private int id;

    private boolean inProgress = false;

    private Scheduler processScheduler;
    private Scheduler androidScheduler;

    NewsDetailsPresenter(int id) {
        this.id = id;
        Toothpick.inject(this, MyApp.getAppScope());
        this.processScheduler = Schedulers.io();
        this.androidScheduler = AndroidSchedulers.mainThread();
    }

    @VisibleForTesting
    public NewsDetailsPresenter(int id, NewsRepository repository,
                                Scheduler processScheduler, Scheduler androidScheduler) {
        this.id = id;
        this.repository = repository;
        this.processScheduler = processScheduler;
        this.androidScheduler = androidScheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getDataFromDb(id);
    }

    public void delete() {
        if (inProgress) {
            return;
        } else {
            inProgress = true;
        }

        getViewState().showProgress(true);

        Disposable disposable = repository.deleteItem(id)
                .subscribeOn(processScheduler)
                .observeOn(androidScheduler)
                .subscribe(this::onSuccessDelete, this::handleDeleteError);

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
        id = item.getId();

        getViewState().setData(item);
    }

    private void onSuccessDelete() {
        inProgress = false;

        getViewState().showProgress(false);
        getViewState().close();
    }

    private void handleGetError(Throwable throwable) {
        Log.e(LOG_TAG, throwable.toString());

        getViewState().showErrorMessage(R.string.error_get_from_db);
        getViewState().close();
    }

    private void handleDeleteError(Throwable throwable) {
        Log.e(LOG_TAG, throwable.toString());
        inProgress = false;

        getViewState().showProgress(false);
        getViewState().showErrorMessage(R.string.error_delete_in_db);
    }
}
