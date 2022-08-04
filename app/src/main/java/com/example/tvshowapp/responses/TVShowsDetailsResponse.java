package com.example.tvshowapp.responses;

import com.example.tvshowapp.models.TvShowsDetails;
import com.google.gson.annotations.SerializedName;

public class TVShowsDetailsResponse {

    @SerializedName("tvShow")
    private TvShowsDetails tvShowDetails;

    public TvShowsDetails getTvShowsDetails(){
        return tvShowDetails;
    }
}
