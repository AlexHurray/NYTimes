package com.example.ermolaenkoalex.nytimes.model;

import java.util.Date;

public class NewsItem {
    private final String title;
    private final String imageUrl;
    private final String category;
    private final Date publishDate;
    private final String previewText;
    private final String itemUrl;
    private final boolean hasUsCategory;

    public NewsItem(String title, String imageUrl, String category, Date publishDate,
                    String previewText, String itemUrl, boolean hasUsCategory) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.category = category;
        this.publishDate = publishDate;
        this.previewText = previewText;
        this.itemUrl = itemUrl;
        this.hasUsCategory = hasUsCategory;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public String getPreviewText() {
        return previewText;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public boolean hasUsCategory() {
        return hasUsCategory;
    }
}
