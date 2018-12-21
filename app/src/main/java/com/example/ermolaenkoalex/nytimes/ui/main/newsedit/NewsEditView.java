package com.example.ermolaenkoalex.nytimes.ui.main.newsedit;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

@StateStrategyType(value = SkipStrategy.class)
public interface NewsEditView extends MvpView {
    void setData(@NonNull NewsItem newsItem);

    void close();

    void showErrorMessage(@IdRes int errorMessage);

    void showProgress(boolean show);
}
