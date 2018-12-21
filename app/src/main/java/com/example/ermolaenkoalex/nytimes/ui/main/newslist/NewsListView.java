package com.example.ermolaenkoalex.nytimes.ui.main.newslist;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;

import java.util.List;

import androidx.annotation.NonNull;

@StateStrategyType(value = SingleStateStrategy.class)
public interface NewsListView extends MvpView {
    void showRefresher(boolean show);

    void showData(List<NewsItem> data);

    void showError(int errorMessageId, boolean isToast);
}
