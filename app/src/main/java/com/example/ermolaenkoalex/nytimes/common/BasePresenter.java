package com.example.ermolaenkoalex.nytimes.common;

import com.example.ermolaenkoalex.nytimes.AppDelegate;
import com.example.ermolaenkoalex.nytimes.db.NewsRepository;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;
import toothpick.Toothpick;

public abstract class BasePresenter<T> extends ViewModel {
    @NonNull
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Nullable
    protected T view;

    @NonNull
    @Inject
    protected NewsRepository repository;

    public BasePresenter() {
        Toothpick.inject(this, AppDelegate.getAppScope());
    }

    public void bind(@NonNull T view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
