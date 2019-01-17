package com.example.ermolaenkoalex.nytimes.api;

import com.example.ermolaenkoalex.nytimes.dto.ResultsDTO;
import com.example.ermolaenkoalex.nytimes.ui.main.newslist.Section;

import androidx.annotation.NonNull;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NewsEndpoint {

    @GET("svc/topstories/v2/{section}.json")
    Single<ResultsDTO> getNews(@Path("section") @NonNull Section section);
}
