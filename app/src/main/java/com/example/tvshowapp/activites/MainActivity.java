package com.example.tvshowapp.activites;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.tvshowapp.R;
import com.example.tvshowapp.adapters.TVShowsAdapter;
import com.example.tvshowapp.databinding.ActivityMainBinding;
import com.example.tvshowapp.listeners.TvShowListener;
import com.example.tvshowapp.models.TVShow;
import com.example.tvshowapp.viewModels.MostPopularTVShowsViewModel;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TvShowListener {

    private ActivityMainBinding activityMainBinding;
    private MostPopularTVShowsViewModel viewModel;
    private List<TVShow> tvShows = new ArrayList<>();
    private TVShowsAdapter tvShowsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        doInitialization();
//        setContentView(R.layout.activity_main);

    }

    private void doInitialization() {
        activityMainBinding.tvShowsRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTVShowsViewModel.class);
        tvShowsAdapter = new TVShowsAdapter(tvShows,this);
        activityMainBinding.tvShowsRecyclerView.setAdapter(tvShowsAdapter);
        getMostPopularTvShows();
    }

    private void getMostPopularTvShows() {
        activityMainBinding.setIsLoading(true);
        viewModel.getMostPopularTVShows(0).observe(this, mostPopularTvShowsResponse -> {
                    activityMainBinding.setIsLoading(false);
                    if (mostPopularTvShowsResponse != null) {
                        if (mostPopularTvShowsResponse.getTvShows() != null) {
                            tvShows.addAll(mostPopularTvShowsResponse.getTvShows());
                            tvShowsAdapter.notifyDataSetChanged();
                        }
                    }
                }
        );
    }

    @Override
    public void onTvShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(),TvShowDetailsActivity.class);
        intent.putExtra("id",tvShow.getId());
        intent.putExtra("name",tvShow.getName());
        intent.putExtra("startDate",tvShow.getStart_date());
        intent.putExtra("country",tvShow.getCountry());
        intent.putExtra("network",tvShow.getNetwork());
        intent.putExtra("status",tvShow.getStatus());
        startActivity(intent);
    }
}