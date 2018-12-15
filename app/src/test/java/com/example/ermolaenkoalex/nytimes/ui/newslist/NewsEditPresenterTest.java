package com.example.ermolaenkoalex.nytimes.ui.newslist;

import com.example.ermolaenkoalex.nytimes.db.NewsRepository;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;
import com.example.ermolaenkoalex.nytimes.ui.main.newsedit.NewsEditPresenter;
import com.example.ermolaenkoalex.nytimes.ui.main.newsedit.NewsEditView;

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
public class NewsEditPresenterTest {
    @Mock
    private
    NewsEditView editView;

    @Mock
    private
    NewsRepository repository;

    private NewsEditPresenter presenter;

    private NewsItem item = new NewsItem("", "", "", new Date(), "", "", false);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(repository.getItem(0)).thenReturn(Observable.just(item));
        when(repository.saveItem(item)).thenReturn(Completable.complete());

        // Mock scheduler using RxJava TestScheduler.
        Scheduler testScheduler = new TestScheduler();

        presenter = new NewsEditPresenter(0, repository, item, testScheduler, testScheduler);
    }

    @Test
    public void test() {
        presenter.attachView(editView);
        Mockito.verify(repository).getItem(0);

        String abstractText = "Some abstract text";
        String title = "Some title";
        String imageUrl = "www.aaa.ru";
        String newsUrl = "www.aaa.ru";
        presenter.saveData(title, abstractText, newsUrl, imageUrl);
        Mockito.verify(editView).showProgress(true);
        Mockito.verify(repository).saveItem(item);
    }

    @After
    public void tearDown() {
        editView = null;
    }
}
