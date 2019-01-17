package com.example.ermolaenkoalex.nytimes.db;

import com.example.ermolaenkoalex.nytimes.api.NewsEndpoint;
import com.example.ermolaenkoalex.nytimes.dto.ResultDTO;
import com.example.ermolaenkoalex.nytimes.dto.ResultsDTO;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;
import com.example.ermolaenkoalex.nytimes.ui.main.newslist.Section;
import com.example.ermolaenkoalex.nytimes.utils.NewsItemConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Observable;

public class NewsRepository {

    @NonNull
    @Inject
    NewsDao newsDao;

    @NonNull
    @Inject
    NewsEndpoint newsApi;

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

    public Completable updateNews(Section section) {
        return newsApi.getNews(section)
                .map(this::convert2NewsItemList)
                .flatMapCompletable(this::saveData);
    }

    private List<NewsItem> convert2NewsItemList(@NonNull ResultsDTO response) {
        final List<NewsItem> items = new ArrayList<>();
        final List<ResultDTO> results = response.getResults();
        if (results == null) {
            return items;
        }
        for (ResultDTO resultDTO : results) {
            items.add(NewsItemConverter.resultDTO2NewsItem(resultDTO));
        }
        return items;
    }

    private Completable saveData(final List<NewsItem> newsList) {
        return Completable.fromCallable((Callable<Void>) () -> {
            newsDao.deleteAll();
            NewsItem[] news = newsList.toArray(new NewsItem[newsList.size()]);
            newsDao.insertAll(news);
            return null;
        });
    }
}
