package com.example.ermolaenkoalex.nytimes.dto;

import com.google.gson.annotations.SerializedName;

public class MultimediaDTO {

    @SerializedName("url")
    private String url;

    @SerializedName("type")
    private MultimediaType type;

    @SerializedName("format")
    private MultimediaFormat format;

    public String getUrl() {
        return url;
    }

    public MultimediaType getType() {
        return type;
    }

    public MultimediaFormat getFormat() {
        return format;
    }
}
