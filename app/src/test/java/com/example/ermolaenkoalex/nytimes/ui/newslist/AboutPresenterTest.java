package com.example.ermolaenkoalex.nytimes.ui.newslist;

import com.example.ermolaenkoalex.nytimes.ui.about.AboutPresenter;
import com.example.ermolaenkoalex.nytimes.ui.about.AboutView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AboutPresenterTest {
    @Mock
    private
    AboutView aboutView;

    private AboutPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        presenter = new AboutPresenter();
        presenter.attachView(aboutView);
    }

    @Test
    public void test() {
        String s1 = "ermolaenkoalex@mail.ru";
        String s2 = "aaaaa";

        presenter.onClickSendMessage(s1, s2);
        Mockito.verify(aboutView).openEmailClient(s1, s2);

        presenter.onOpenLink("");
        Mockito.verify(aboutView).openLink("");
    }

    @After
    public void tearDown() {
        aboutView = null;
    }
}
