package com.example.tvshowapp.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tvshowapp.repositories.SearchTVShowRepository;
import com.example.tvshowapp.responses.TVShowResponse;

public class SearchViewModel extends ViewModel {
    private SearchTVShowRepository searchTVShowRepository;
    public SearchViewModel(){
        searchTVShowRepository = new SearchTVShowRepository();
    }
    public LiveData<TVShowResponse> searchTvShows(String query,int page){
        return  searchTVShowRepository.searchTvShow(query,page);
    }
}
