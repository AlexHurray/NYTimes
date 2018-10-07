package com.example.ermolaenkoalex.NYTimes.events;

import com.example.ermolaenkoalex.NYTimes.model.NewsItem;

public class NewsItemEvent {
    private NewsItem newsItem;

    public NewsItemEvent(NewsItem newsItem) {
        this.newsItem = newsItem;
    }

    public NewsItem getNewsItem() {
        return newsItem;
    }
}
