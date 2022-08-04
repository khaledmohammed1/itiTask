package com.example.tvshowapp.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tvshowapp.network.APIService;
import com.example.tvshowapp.network.ApiClient;
import com.example.tvshowapp.responses.TVShowsDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowDetailsRepository {
    private APIService apiService;

    public TVShowDetailsRepository(){
        apiService = ApiClient.getRetrofit().create(APIService.class);
    }

    public LiveData<TVShowsDetailsResponse> getTvShowDetails(String tvShowId) {
        MutableLiveData<TVShowsDetailsResponse> data  = new MutableLiveData<>();
        apiService.getTVShowDetails(tvShowId).enqueue(new Callback<TVShowsDetailsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowsDetailsResponse> call,@NonNull Response<TVShowsDetailsResponse> response) {
           data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVShowsDetailsResponse> call,@NonNull Throwable t) {
                data.setValue(null);

            }
        });
        return data;
    }
}
