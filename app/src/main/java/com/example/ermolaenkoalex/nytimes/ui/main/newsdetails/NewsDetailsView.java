package com.example.ermolaenkoalex.nytimes.ui.main.newsdetails;

import com.example.ermolaenkoalex.nytimes.model.NewsItem;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

public interface NewsDetailsView {

    void setData(@NonNull NewsItem newsItem);

    void close(@IdRes int errorMessage);
}
