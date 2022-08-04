package com.example.tvshowapp.network;

import com.example.tvshowapp.responses.TVShowResponse;
import com.example.tvshowapp.responses.TVShowsDetailsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("most-popular")
    Call<TVShowResponse> getMostPopularTVShows(@Query("page") int page);

    @GET("show-details")
    Call<TVShowsDetailsResponse> getTVShowDetails(@Query("q") String tvShowId);

    @GET("search")
    Call<TVShowResponse> searchTVShow(@Query("q") String query,@Query("page")int page);
}
