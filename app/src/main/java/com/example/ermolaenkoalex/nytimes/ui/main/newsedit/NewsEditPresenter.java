package com.example.ermolaenkoalex.nytimes.ui.main.newsedit;

import android.util.Log;

import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.common.BasePresenter;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;

import androidx.annotation.NonNull;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsEditPresenter extends BasePresenter<NewsEditView> {

    private static final String LOG_TAG = "NewsEditVM";

    private NewsItem newsItem;

    public void getNews(int id) {
        if (newsItem == null) {
            getDataFromDb(id);
        }
    }

    public void saveData() {
        if (view != null) {
            view.updateData(newsItem);
        }

        Disposable disposable = repository.saveItem(newsItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccessSave, this::handleSaveError);

        compositeDisposable.add(disposable);
    }

    private void getDataFromDb(int id) {
        Disposable disposable = repository.getItem(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccessLoad, this::handleGetError);

        compositeDisposable.add(disposable);
    }

    private void onSuccessLoad(@NonNull NewsItem item) {
        if (view != null) {
            view.setData(item);
        }
        newsItem = item;
    }

    private void onSuccessSave() {
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

    private void handleSaveError(Throwable throwable) {
        Log.e(LOG_TAG, throwable.toString());
        if (view != null) {
            view.close(R.string.error_save_in_db);
        }
    }
}
