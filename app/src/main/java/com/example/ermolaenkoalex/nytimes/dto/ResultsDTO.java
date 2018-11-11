package com.example.ermolaenkoalex.nytimes.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultsDTO {

    @SerializedName("results")
    private List<ResultDTO> results;

    public List<ResultDTO> getResults() {
        return results;
    }
}
