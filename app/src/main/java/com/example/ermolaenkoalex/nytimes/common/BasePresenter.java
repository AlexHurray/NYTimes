package com.example.ermolaenkoalex.nytimes.common;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;
import com.example.ermolaenkoalex.nytimes.db.NewsRepository;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<T extends MvpView> extends MvpPresenter<T> {
    @NonNull
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    @NonNull
    @Inject
    protected NewsRepository repository;

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        super.onDestroy();
    }
}
