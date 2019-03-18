package com.example.finalproject;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.Preference;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.finalproject.data.AllChampionItem;
import com.example.finalproject.data.ChampionDetailItem;
import com.example.finalproject.data.Status;
import com.example.finalproject.utils.LOLChampionUtils;

import java.util.List;

public class ChampionDetailActivity extends AppCompatActivity {

    private static final String TAG = ChampionDetailActivity.class.getSimpleName();
    private ImageView mChampionSplashArtIV;
    private TextView mChampionNameTV;
    private ImageView mChampionIconIV;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;
    private LinearLayout mlllll;

    private String mLanguage;
    private ChampionDetailItem mChampionDetailItem;
    private ChampionDetailViewModel mChampionDetailViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champion_item_detail);

        mChampionSplashArtIV = findViewById(R.id.iv_champion_splash_art);
        mChampionNameTV = findViewById(R.id.tv_champion_name);
        mChampionIconIV = findViewById(R.id.iv_champion_icon);
        mLoadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = findViewById(R.id.tv_loading_error_message);
        mlllll = findViewById(R.id.detailact);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mLanguage = sharedPreferences.getString(
                getString(R.string.pref_lang_key),                         //need change
                getString(R.string.pref_lang_default_value)
        );
//        Log.d(TAG, "Language in detail activity is: " + mLanguage);

        Intent intent = getIntent();
        String champion_name = intent.getStringExtra("champion_name");
//        Log.d(TAG, "champion name is :" + champion_name);

        mChampionDetailViewModel = ViewModelProviders.of(this).get(ChampionDetailViewModel.class);

//        mChampionDetailViewModel.getForecast().observe(this, new Observer<List<ChampionDetailItem>>() {
//            @Override
//            public void onChanged(@Nullable List<ChampionDetailItem> championDetailItems) {
//                mForecastAdapter.updateForecastItems(forecastItems);
//            }
//        });

        mChampionDetailViewModel.getLoadingStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(@Nullable Status status) {
                if (status == Status.LOADING) {
                    mLoadingIndicatorPB.setVisibility(View.VISIBLE);
                } else if (status == Status.SUCCESS) {                //is the status in the way?
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
                    mlllll.setVisibility(View.VISIBLE);
                } else {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mlllll.setVisibility(View.INVISIBLE);
                    mLoadingErrorMessageTV.setVisibility(View.VISIBLE);
                }
            }
        });

;
        loadChampionDetail(champion_name, mLanguage);

    }

    private void loadChampionDetail(String champion_name, String language) {
        mChampionDetailViewModel = ViewModelProviders.of(this).get(ChampionDetailViewModel.class);
//        Log.d(TAG, "name is: " + champion_name +"lang is: "+ language);
        mChampionDetailViewModel.loadChampionDetail(champion_name, language);
        fillInLayout(mChampionDetailItem);
    }

    private void  fillInLayout(ChampionDetailItem championDetailItem) {
        mChampionDetailViewModel = ViewModelProviders.of(this).get(ChampionDetailViewModel.class);
        mChampionDetailItem = new ChampionDetailItem();
        Log.d(TAG, "can it get here");
//        Log.d(TAG, "empty? " + championDetailItem.champion_name);
//        String champion_name = mChampionNameTV.getContext().getString(R.string.champion_name, championDetailItem.champion_name);
//        String champion_title = getString(R.string.champion_title, championDetailItem.champion_title);

//        mChampionNameTV.setText(champion_name);

    }
}
