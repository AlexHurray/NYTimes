package com.example.ermolaenkoalex.nytimes.ui.main.newslist;

import com.example.ermolaenkoalex.nytimes.R;
import com.google.gson.annotations.SerializedName;

public enum Section {

    @SerializedName("home") HOME(R.string.section_home),
    @SerializedName("opinion") OPINION(R.string.section_opinion),
    @SerializedName("world") WORLD(R.string.section_world),
    @SerializedName("national") NATIONAL(R.string.section_national),
    @SerializedName("politics") POLITICS(R.string.section_politics),
    @SerializedName("upshot") UPSHOT(R.string.section_upshot),
    @SerializedName("nyregion") NYREGION(R.string.section_nyregion),
    @SerializedName("business") BUSINESS(R.string.section_business),
    @SerializedName("technology") TECHNOLOGY(R.string.section_technology),
    @SerializedName("science") SCIENCE(R.string.section_science),
    @SerializedName("health") HEALTH(R.string.section_health),
    @SerializedName("sports") SPORTS(R.string.section_sports),
    @SerializedName("arts") ARTS(R.string.section_arts),
    @SerializedName("books") BOOKS(R.string.section_books),
    @SerializedName("movies") MOVIES(R.string.section_movies),
    @SerializedName("theater") THEATHER(R.string.section_theater),
    @SerializedName("sundayreview") SUNDAYREVIEW(R.string.section_sundayreview),
    @SerializedName("fashion") FASHION(R.string.section_fashion),
    @SerializedName("tmagazine") TMAGSZINE(R.string.section_tmagazine),
    @SerializedName("food") FOOD(R.string.section_food),
    @SerializedName("travel") TRAVEL(R.string.section_travel),
    @SerializedName("magazine") MAGAZINE(R.string.section_magazine),
    @SerializedName("realestate") REALESTATE(R.string.section_realestate),
    @SerializedName("automobiles") AUTOMOBILES(R.string.section_automobiles),
    @SerializedName("obituaries") OBITUARIES(R.string.section_obituaries),
    @SerializedName("insider") INSIDER(R.string.section_insider);

    private int sectionNameResId;

    Section(int sectionNameResId) {
        this.sectionNameResId = sectionNameResId;
    }

    public int getSectionNameResId() {
        return sectionNameResId;
    }
}
