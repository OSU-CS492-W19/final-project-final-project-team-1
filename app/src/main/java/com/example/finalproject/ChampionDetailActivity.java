package com.example.finalproject;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.Preference;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class ChampionDetailActivity extends AppCompatActivity
        implements ChampionDetailAdapter.OnChampionDetailItemClickListener{

    private static final String TAG = ChampionDetailActivity.class.getSimpleName();

    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;
    private RecyclerView mPageLayout;

    private String mLanguage;
    private ChampionDetailAdapter mChampionDetailAdapter;
    private ChampionDetailItem mChampionDetailItem;
    private ChampionDetailViewModel mChampionDetailViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champion_item_detail);

        mLoadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = findViewById(R.id.tv_loading_error_message);
        mPageLayout = findViewById(R.id.rv_champion_detail_items);

        mChampionDetailAdapter = new ChampionDetailAdapter(this, this);
        mPageLayout.setAdapter(mChampionDetailAdapter);
        mPageLayout.setLayoutManager(new LinearLayoutManager(this));
        mPageLayout.setHasFixedSize(true);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mLanguage = sharedPreferences.getString(
                getString(R.string.pref_lang_key),                         //need change
                getString(R.string.pref_lang_default_value)
        );

        Intent intent = getIntent();
        String champion_name = intent.getStringExtra("champion_name");

        mChampionDetailViewModel = ViewModelProviders.of(this).get(ChampionDetailViewModel.class);

        mChampionDetailViewModel.getChampionDetail().observe(this, new Observer<List<ChampionDetailItem>>() {
            @Override
            public void onChanged(@Nullable List<ChampionDetailItem> championDetailItems) {
                mChampionDetailAdapter.updateChampionItems(championDetailItems);
            }
        });

        mChampionDetailViewModel.getLoadingStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(@Nullable Status status) {
                if (status == Status.LOADING) {
                    mLoadingIndicatorPB.setVisibility(View.VISIBLE);
//                    Log.d(TAG, "is it loading?");
                } else if (status == Status.SUCCESS) {                //is the status in the way?
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
                    mPageLayout.setVisibility(View.VISIBLE);
//                    Log.d(TAG, "is it success");
                } else {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mPageLayout.setVisibility(View.INVISIBLE);
                    mLoadingErrorMessageTV.setVisibility(View.VISIBLE);
//                    Log.d(TAG, "is it failed");
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
    }

    @Override
    public void onChampionDetailItemClick(ChampionDetailItem championDetailItem) {

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.champion_item_detail, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_view_on_web:
//                viewChampionOnWeb();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    public void viewChampionOnWeb() {
//        ChampionDetailItem championDetailItem = new ChampionDetailItem();
//        String webURL = LOLChampionUtils.buildChampionWebURL(championDetailItem.champion_name);
//        Log.d(TAG, "champion url is: " + webURL);
//        Uri championURL = Uri.parse(webURL);
//        Intent intent = new Intent(Intent.ACTION_VIEW, championURL);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
//    }

}
