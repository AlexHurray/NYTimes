package com.example.ermolaenkoalex.nytimes.utils;

import com.example.ermolaenkoalex.nytimes.dto.MultimediaDTO;
import com.example.ermolaenkoalex.nytimes.dto.MultimediaFormat;
import com.example.ermolaenkoalex.nytimes.dto.MultimediaType;
import com.example.ermolaenkoalex.nytimes.dto.ResultDTO;
import com.example.ermolaenkoalex.nytimes.model.NewsItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class NewsItemConverterTest {
    private static final String MULTIMEDIA_NORMAL_URL = "www.normal.ru";
    private static final String MULTIMEDIA_THUMBLARGE_URL = "www.thumblarge.ru";
    private static final String SECTION_WORLD = "World";
    private static final String SECTION_US = "U.S.";
    private static final String TITLE = "TITLE";
    private static final String ABSTRACT_TEXT = "ABSTRACT_TEXT";
    private static final String TEST_URL = "www.aaa.ru";
    private static final Date TEST_DATE = new Date();

    private MultimediaDTO multimediaDTONormal =
            new MultimediaDTO(MULTIMEDIA_NORMAL_URL, MultimediaType.IMAGE, MultimediaFormat.NORMAL);
    private MultimediaDTO multimediaDTOThumblarge =
            new MultimediaDTO(MULTIMEDIA_THUMBLARGE_URL, MultimediaType.IMAGE, MultimediaFormat.THUMB_LARGE);

    private ResultDTO resultDTO1 = new ResultDTO(SECTION_WORLD,
            SECTION_US,
            TITLE,
            ABSTRACT_TEXT,
            TEST_URL,
            TEST_DATE,
            Arrays.asList(multimediaDTONormal, multimediaDTOThumblarge));

    private ResultDTO resultDTO2 = new ResultDTO(SECTION_WORLD,
            null,
            TITLE,
            ABSTRACT_TEXT,
            TEST_URL,
            TEST_DATE,
            Collections.singletonList(multimediaDTONormal));

    private NewsItem item1 = NewsItemConverter.resultDTO2NewsItem(resultDTO1);
    private NewsItem item2 = NewsItemConverter.resultDTO2NewsItem(resultDTO2);

    @Before
    public void setUp() {
    }

    @Test
    public void checkOtherFields() {
        assertEquals(item1.getItemUrl(), TEST_URL);
        assertEquals(item1.getPreviewText(), ABSTRACT_TEXT);
        assertEquals(item1.getTitle(), TITLE);
        assertEquals(item1.getPublishDate(), TEST_DATE);
    }

    @Test
    public void checkCategory() {
        assertEquals(item1.getCategory(), resultDTO1.getSubsection());
        assertEquals(item2.getCategory(), resultDTO2.getSection());
    }

    @Test
    public void checkIsUS() {
        assertEquals(item1.hasUsCategory(), true);
        assertEquals(item2.hasUsCategory(), false);
    }

    @Test
    public void checkMultimedia() {
        assertEquals(item1.getImageUrl(), MULTIMEDIA_THUMBLARGE_URL);
        assertEquals(item2.getImageUrl(), MULTIMEDIA_NORMAL_URL);
    }
}
