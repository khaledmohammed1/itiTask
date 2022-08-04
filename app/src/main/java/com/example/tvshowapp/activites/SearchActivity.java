package com.example.tvshowapp.activites;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshowapp.R;
import com.example.tvshowapp.adapters.TVShowsAdapter;
import com.example.tvshowapp.databinding.ActivitySearchBinding;
import com.example.tvshowapp.listeners.TvShowListener;
import com.example.tvshowapp.models.TVShow;
import com.example.tvshowapp.viewModels.SearchViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity implements TvShowListener {

    private ActivitySearchBinding activitySearchBinding;
    private SearchViewModel viewModel;
    private List<TVShow> tvShows = new ArrayList<>();
    private TVShowsAdapter tvShowsAdapter;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        doInitialization();
    }

    private void doInitialization() {
        activitySearchBinding.imageBack.setOnClickListener(v -> onBackPressed());
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        tvShowsAdapter = new TVShowsAdapter(tvShows, this);
        activitySearchBinding.tvShowsRecyclerView.setAdapter(tvShowsAdapter);
        activitySearchBinding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                  if(!s.toString().trim().isEmpty()){
                      timer = new Timer();
                      timer.schedule(new TimerTask() {
                          @Override
                          public void run() {
                              new Handler(Looper.getMainLooper()).post(() -> searchTvShow(s.toString()));
                          }
                      },800);
                  }else{
                      tvShows.clear();
                      tvShowsAdapter.notifyDataSetChanged();
                  }
            }
        });
        activitySearchBinding.tvShowsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!activitySearchBinding.tvShowsRecyclerView.canScrollVertically(1)){
                    if(!activitySearchBinding.inputSearch.getText().toString().isEmpty()){
                        searchTvShow(activitySearchBinding.inputSearch.getText().toString());
                    }
                }
            }
        });
        activitySearchBinding.inputSearch.requestFocus();
    }

    private void searchTvShow(String query) {
        viewModel.searchTvShows(query, 1).observe(this, tvShowResponse -> {
            if (tvShowResponse.getTvShows() != null) {
                tvShows.addAll(tvShowResponse.getTvShows());
                tvShowsAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onTvShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), TvShowDetailsActivity.class);
        intent.putExtra("id", tvShow.getId());
        intent.putExtra("name", tvShow.getName());
        intent.putExtra("startDate", tvShow.getStart_date());
        intent.putExtra("country", tvShow.getCountry());
        intent.putExtra("network", tvShow.getNetwork());
        intent.putExtra("status", tvShow.getStatus());
        startActivity(intent);
    }
}