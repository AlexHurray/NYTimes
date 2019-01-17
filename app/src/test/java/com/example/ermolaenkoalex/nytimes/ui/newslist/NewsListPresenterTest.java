package com.example.ermolaenkoalex.nytimes.ui.newslist;

import com.example.ermolaenkoalex.nytimes.db.NewsRepository;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;
import com.example.ermolaenkoalex.nytimes.ui.main.newslist.NewsListPresenter;
import com.example.ermolaenkoalex.nytimes.ui.main.newslist.NewsListView;
import com.example.ermolaenkoalex.nytimes.ui.main.newslist.ResponseState;
import com.example.ermolaenkoalex.nytimes.ui.main.newslist.Section;
import com.example.ermolaenkoalex.nytimes.utils.SharedPreferencesHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NewsListPresenterTest {
    @Mock
    private
    NewsListView listView;

    @Mock
    private
    NewsRepository repository;

    @Mock
    private
    SharedPreferencesHelper sharedPreferencesHelper;

    private NewsListPresenter presenter;

    private NewsItem item = new NewsItem("", "", "", new Date(), "", "", false);
    private List<NewsItem> newsList = Collections.singletonList(item);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(repository.getDataObservable()).thenReturn(Observable.just(newsList));
        when(repository.updateNews(Section.HOME)).thenReturn(Completable.complete());

        // Mock scheduler using RxJava TestScheduler.
        Scheduler testScheduler = new TestScheduler();

        presenter = new NewsListPresenter(repository, sharedPreferencesHelper, testScheduler, testScheduler);
    }

    @Test
    public void test() {
        presenter.attachView(listView);
        Mockito.verify(repository).getDataObservable();

        Mockito.verify(listView).showState(any(ResponseState.class));
        Mockito.verify(repository).updateNews(Section.HOME);
    }

    @After
    public void tearDown() {
        listView = null;
    }
}
