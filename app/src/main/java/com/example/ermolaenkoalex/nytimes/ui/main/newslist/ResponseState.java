package com.example.ermolaenkoalex.nytimes.ui.main.newslist;

import com.example.ermolaenkoalex.nytimes.R;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

public class ResponseState {
    private boolean loading;
    private int errorMessageId = 0;
    @NonNull
    private List<NewsItem> data = new ArrayList<>();

    ResponseState(boolean loading) {
        this.loading = loading;
        this.errorMessageId = R.string.error_data_is_empty;
    }

    ResponseState(boolean loading, @NonNull List<NewsItem> data) {
        this.loading = loading;

        if (data.isEmpty()) {
            this.errorMessageId = R.string.error_data_is_empty;
        } else {
            this.data.addAll(data);
        }
    }

    ResponseState(boolean loading, @NonNull List<NewsItem> data, @IdRes int errorMessage) {
        this.loading = loading;

        if (!data.isEmpty()) {
            this.data = data;
        }

        this.errorMessageId = errorMessage;
    }

    public boolean isLoading() {
        return loading;
    }

    public boolean hasData() {
        return !data.isEmpty();
    }

    public List<NewsItem> getData() {
        return data;
    }

    public boolean hasError() {
        return errorMessageId != 0;
    }

    public int getErrorMessage() {
        return errorMessageId;
    }
}
