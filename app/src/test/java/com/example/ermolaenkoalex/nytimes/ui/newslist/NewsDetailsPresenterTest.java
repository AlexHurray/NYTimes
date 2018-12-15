package com.example.ermolaenkoalex.nytimes.ui.newslist;

import com.example.ermolaenkoalex.nytimes.db.NewsRepository;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;
import com.example.ermolaenkoalex.nytimes.ui.main.newsdetails.NewsDetailsPresenter;
import com.example.ermolaenkoalex.nytimes.ui.main.newsdetails.NewsDetailsView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NewsDetailsPresenterTest {
    @Mock
    private
    NewsDetailsView detailsView;

    @Mock
    private
    NewsRepository repository;

    private NewsDetailsPresenter presenter;

    private NewsItem item = new NewsItem("", "", "", new Date(), "", "", false);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(repository.getItem(0)).thenReturn(Observable.just(item));
        when(repository.deleteItem(0)).thenReturn(Completable.complete());

        // Mock scheduler using RxJava TestScheduler.
        Scheduler testScheduler = new TestScheduler();

        presenter = new NewsDetailsPresenter(0, repository, testScheduler, testScheduler);
    }

    @Test
    public void test() {
        presenter.attachView(detailsView);
        Mockito.verify(repository).getItem(0);

        presenter.delete();
        Mockito.verify(detailsView).showProgress(true);
        Mockito.verify(repository).deleteItem(0);
    }

    @After
    public void tearDown() {
        detailsView = null;
    }
}
