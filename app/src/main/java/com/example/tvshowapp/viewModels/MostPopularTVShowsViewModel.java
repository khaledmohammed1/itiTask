package com.example.tvshowapp.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tvshowapp.repositories.MostPopularTVShowsRepository;
import com.example.tvshowapp.responses.TVShowResponse;

public class MostPopularTVShowsViewModel extends ViewModel {
    private MostPopularTVShowsRepository mostPopularTVShowsRepository;

    public MostPopularTVShowsViewModel(){
        mostPopularTVShowsRepository = new MostPopularTVShowsRepository();
    }

    public LiveData<TVShowResponse> getMostPopularTVShows(int page){
        return  mostPopularTVShowsRepository.getMostPopularTVShows(page);
    }
}
