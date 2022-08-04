package com.example.tvshowapp.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
;

import com.example.tvshowapp.R;
import com.example.tvshowapp.adapters.ImageSliderAdapter;

import com.example.tvshowapp.databinding.ActivityTvShowDetailsBinding;
import com.example.tvshowapp.viewModels.TvShowDetailsViewModel;

public class TvShowDetailsActivity extends AppCompatActivity {

    private ActivityTvShowDetailsBinding activityTvShowDetailsBinding;
    private TvShowDetailsViewModel tvShowDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTvShowDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_tv_show_details);
        doInitialization();
    }
    private void doInitialization(){
        tvShowDetailsViewModel = new ViewModelProvider(this).get(TvShowDetailsViewModel.class);
        activityTvShowDetailsBinding.imageBack.setOnClickListener(v -> onBackPressed());
        getTvShowDetails();
    }

    private  void getTvShowDetails() {
        String tvShowId = String.valueOf(getIntent().getIntExtra("id",-1));
        tvShowDetailsViewModel.getTVShowDetails(tvShowId).observe(this,tvShowsDetailsResponse -> {
          if(tvShowsDetailsResponse.getTvShowsDetails() != null){
              if(tvShowsDetailsResponse.getTvShowsDetails().getPictures() != null){
                  loadImageSlider(tvShowsDetailsResponse.getTvShowsDetails().getPictures());
              }
              activityTvShowDetailsBinding.setTvShowImageURl(
                      tvShowsDetailsResponse.getTvShowsDetails().getImage_path()
              );
              activityTvShowDetailsBinding.imageTVShow.setVisibility(View.VISIBLE);
              activityTvShowDetailsBinding.setDescription(tvShowsDetailsResponse.getTvShowsDetails().getDescription());
              activityTvShowDetailsBinding.textDescription.setVisibility(View.VISIBLE);
              loadBasicTvShowDetails();

          }
        });
    }
    private void loadImageSlider(String[] sliderImages){
        activityTvShowDetailsBinding.sliderViewPager.setOffscreenPageLimit(1);
        activityTvShowDetailsBinding.sliderViewPager.setAdapter(new ImageSliderAdapter(sliderImages));
        activityTvShowDetailsBinding.sliderViewPager.setVisibility(View.VISIBLE);
        activityTvShowDetailsBinding.viewFadingEdge.setVisibility(View.VISIBLE);
    }

    private void loadBasicTvShowDetails(){
        activityTvShowDetailsBinding.setTvShowName(getIntent().getStringExtra("name"));
        activityTvShowDetailsBinding.setNetworkCountry(
                getIntent().getStringExtra("network") + " (" +
                        getIntent().getStringExtra("country") + ")"
        );
        activityTvShowDetailsBinding.setStatus(getIntent().getStringExtra("status"));
        activityTvShowDetailsBinding.setStartDate(getIntent().getStringExtra("startDate"));
        activityTvShowDetailsBinding.textName.setVisibility(View.VISIBLE);
        activityTvShowDetailsBinding.textNetworkCountry.setVisibility(View.VISIBLE);
        activityTvShowDetailsBinding.textStatus.setVisibility(View.VISIBLE);
        activityTvShowDetailsBinding.textStarted.setVisibility(View.VISIBLE);
    }
}