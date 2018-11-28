package com.example.ermolaenkoalex.nytimes.utils;

import com.example.ermolaenkoalex.nytimes.api.NewsEndpoint;
import com.example.ermolaenkoalex.nytimes.db.NewsRepository;
import com.example.ermolaenkoalex.nytimes.dto.ResultDTO;
import com.example.ermolaenkoalex.nytimes.dto.ResultsDTO;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;
import com.example.ermolaenkoalex.nytimes.ui.main.newslist.Section;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Completable;

public class DownloadingNews {

    public static Completable updateNews(@NonNull NewsEndpoint news, @NonNull NewsRepository repository, Section section) {
        return news.getNews(section)
                .map(DownloadingNews::convert2NewsItemList)
                .flatMapCompletable(repository::saveData);
    }

    public static List<NewsItem> convert2NewsItemList(@NonNull ResultsDTO response) {
        List<NewsItem> items = new ArrayList<>();
        final List<ResultDTO> results = response.getResults();
        if (results == null) {
            return items;
        }

        for (ResultDTO resultDTO : results) {
            items.add(NewsItemConverter.resultDTO2NewsItem(resultDTO));
        }

        return items;
    }

    private DownloadingNews() {
        throw new AssertionError("No instances");
    }
}
