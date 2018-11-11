package com.example.ermolaenkoalex.nytimes.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;

public class ResultDTO {

    @SerializedName("section")
    private String section;

    @SerializedName("subsection")
    private String subsection;

    @SerializedName("title")
    private String title;

    @SerializedName("abstract")
    private String abstractText;

    @SerializedName("url")
    private String url;

    @SerializedName("published_date")
    private Date publishedDate;

    @SerializedName("multimedia")
    private List<MultimediaDTO> multimedia;

    public String getSection() {
        return section;
    }

    @Nullable
    public String getSubsection() {
        return subsection;
    }

    public String getTitle() {
        return title;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public String getUrl() {
        return url;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    @Nullable
    public List<MultimediaDTO> getMultimedia() {
        return multimedia;
    }
}
