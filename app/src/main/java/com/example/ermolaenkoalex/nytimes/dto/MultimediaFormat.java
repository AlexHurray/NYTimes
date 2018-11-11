package com.example.ermolaenkoalex.nytimes.dto;

import com.google.gson.annotations.SerializedName;

public enum MultimediaFormat {
    @SerializedName("Standard Thumbnail") ST_THUMBNAIL,
    @SerializedName("thumbLarge") THUMB_LARGE,
    @SerializedName("Normal") NORMAL,
    @SerializedName("mediumThreeByTwo210") MEDIUM_THREE_BY_TWO_210,
    @SerializedName("superJumbo") SUPER_JUMBO
}
