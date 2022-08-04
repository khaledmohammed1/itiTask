package com.example.tvshowapp.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tvshowapp.network.APIService;
import com.example.tvshowapp.network.ApiClient;
import com.example.tvshowapp.responses.TVShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchTVShowRepository {
    private APIService apiService;

    public SearchTVShowRepository() {
        apiService = ApiClient.getRetrofit().create(APIService.class);
    }

    public LiveData<TVShowResponse> searchTvShow(String query, int page) {
        MutableLiveData<TVShowResponse> data = new MutableLiveData<>();
        apiService.searchTVShow(query, page).enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowResponse> call, @NonNull Response<TVShowResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVShowResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
