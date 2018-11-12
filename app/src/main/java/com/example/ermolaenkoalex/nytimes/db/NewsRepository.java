package com.example.ermolaenkoalex.nytimes.db;

import com.example.ermolaenkoalex.nytimes.model.NewsItem;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class NewsRepository {

    @Inject
    NewsDao newsDao;

    public Completable saveData(final List<NewsItem> newsList) {
        return Completable.fromCallable((Callable<Void>) () -> {

            newsDao.deleteAll();
            NewsItem[] news = newsList.toArray(new NewsItem[newsList.size()]);
            newsDao.insertAll(news);

            return null;
        });
    }

    public Completable saveItem(final NewsItem newsItem) {
        return newsDao.insert(newsItem);
    }

    public Observable<List<NewsItem>> getDataObservable() {
        return newsDao.getAll();
    }

    public Observable<NewsItem> getItem(int id) {
        return newsDao.getNewsById(id);
    }

    public Completable deleteItem(int id) {
        return Completable.fromCallable((Callable<Void>) () -> {

            newsDao.delete(id);
            return null;
        });
    }
}