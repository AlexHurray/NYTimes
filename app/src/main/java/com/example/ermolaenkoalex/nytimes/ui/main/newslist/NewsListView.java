package com.example.ermolaenkoalex.nytimes.ui.main.newslist;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import androidx.annotation.NonNull;

public interface NewsListView extends MvpView {
    @StateStrategyType(value = SingleStateStrategy.class)
    void showState(@NonNull ResponseState state);
}
