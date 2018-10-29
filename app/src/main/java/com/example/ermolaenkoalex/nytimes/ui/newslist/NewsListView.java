package com.example.ermolaenkoalex.nytimes.ui.newslist;

import com.example.ermolaenkoalex.nytimes.model.NewsItem;

import java.util.List;

import androidx.annotation.NonNull;

public interface NewsListView {

    void showLoading();

    void hideLoading();

    void setData(@NonNull List<NewsItem> data);

    void showErrorToast();
}
