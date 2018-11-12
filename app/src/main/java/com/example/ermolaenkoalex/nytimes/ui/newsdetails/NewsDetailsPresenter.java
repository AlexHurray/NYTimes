package com.example.ermolaenkoalex.nytimes.ui.newsdetails;

import android.util.Log;

import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.common.BasePresenter;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsDetailsPresenter extends BasePresenter<NewsDetailsView> {

    private static final String LOG_TAG = "NewsDetailsVM";

    private NewsItem newsItem;

    private int id;

    public void getNews(int id) {
        if (newsItem == null) {
            getDataFromDb(id);
        } else if (view != null) {
            view.setData(newsItem);
        }
    }

    public void delete() {
        Disposable disposable = repository.deleteItem(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccessDelete, this::handleDeleteError);

        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    private void getDataFromDb(int id) {
        Disposable disposable = repository.getItem(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccessLoad, this::handleGetError);

        compositeDisposable.add(disposable);
    }

    private void onSuccessLoad(@NonNull NewsItem item) {
        id = item.getId();

        if (view != null) {
            view.setData(item);
        }
        newsItem = item;
    }

    private void onSuccessDelete() {
        if (view != null) {
            view.close(0);
        }
    }

    private void handleGetError(Throwable throwable) {
        Log.e(LOG_TAG, throwable.toString());
        if (view != null) {
            view.close(R.string.error_get_from_db);
        }
    }

    private void handleDeleteError(Throwable throwable) {
        Log.e(LOG_TAG, throwable.toString());
        if (view != null) {
            view.close(R.string.error_delete_in_db);
        }
    }
}
