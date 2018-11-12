package com.example.ermolaenkoalex.nytimes.model;

import com.example.ermolaenkoalex.nytimes.db.DateConverter;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "news")
@TypeConverters(DateConverter.class)
public class NewsItem {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @Nullable
    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @NonNull
    @ColumnInfo(name = "category")
    private String category;

    @NonNull
    @ColumnInfo(name = "publish_date")
    private Date publishDate;

    @NonNull
    @ColumnInfo(name = "preview_text")
    private String previewText;

    @NonNull
    @ColumnInfo(name = "item_url")
    private String itemUrl;

    @NonNull
    @ColumnInfo(name = "has_us_category")
    private boolean hasUsCategory;

    public NewsItem() {
    }

    @Ignore
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getPreviewText() {
        return previewText;
    }

    public void setPreviewText(String previewText) {
        this.previewText = previewText;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public boolean hasUsCategory() {
        return hasUsCategory;
    }

    public void setHasUsCategory(boolean hasUsCategory) {
        this.hasUsCategory = hasUsCategory;
    }
}
