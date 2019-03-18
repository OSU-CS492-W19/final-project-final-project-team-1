package com.example.finalproject;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.finalproject.data.AllChampionItem;
import com.example.finalproject.data.Status;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements ChampionAdapter.OnChampionItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mChampionItemsRV;
    private RecyclerView mDrawerItemRV;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;
    private DrawerLayout mDrawerLayout;

    private ChampionAdapter mChampionAdapter;
//    private DrawerAdapter mDrawerAdapter;
    private ChampionViewModel mChampionViewModel;
    private ChampionDetailViewModel mChampionDetailViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        mLoadingErrorMessageTV = findViewById(R.id.tv_loading_error_message);
        mChampionItemsRV = findViewById(R.id.rv_champion_items);
        mDrawerItemRV = findViewById(R.id.rv_drawer_items);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_nav_menu);

        mChampionAdapter = new ChampionAdapter(this);
        mChampionItemsRV.setAdapter(mChampionAdapter);
        mChampionItemsRV.setLayoutManager(new LinearLayoutManager(this));
        mChampionItemsRV.setHasFixedSize(true);

//        mDrawerAdapter = new DrawerAdapter(this);
//        mDrawerItemRV.setAdapter(mDrawerAdapter);
//        mDrawerItemRV.setLayoutManager(new LinearLayoutManager(this));
//        mDrawerItemRV.setHasFixedSize(true);

        getSupportActionBar().setElevation(0);

        mChampionViewModel = ViewModelProviders.of(this).get(ChampionViewModel.class);

        mChampionViewModel.getChampion().observe(this, new Observer<List<AllChampionItem>>() {
            @Override
            public void onChanged(@Nullable List<AllChampionItem> championItems) {
                mChampionAdapter.updateChampionItems(championItems);
            }
        });



        mChampionViewModel.getLoadingStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(@Nullable Status status) {
                if (status == Status.LOADING) {
                    mLoadingIndicatorPB.setVisibility(View.VISIBLE);
                } else if (status == Status.SUCCESS) {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mLoadingErrorMessageTV.setVisibility(View.INVISIBLE);
                    mChampionItemsRV.setVisibility(View.VISIBLE);
                } else {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mChampionItemsRV.setVisibility(View.INVISIBLE);
                    mLoadingErrorMessageTV.setVisibility(View.VISIBLE);
                }
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);
        loadChampion(preferences);
    }

    @Override
    protected void onDestroy() {
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    @Override
    public void onChampionItemClick(AllChampionItem championItem) {
//        Log.d(TAG, "champion is: " + championItem.champion_name);
        Intent intent = new Intent(this, ChampionDetailActivity.class);
        intent.putExtra("champion_name", championItem.champion_id);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadChampion(SharedPreferences preferences) {
        String language = preferences.getString(
                getString(R.string.pref_lang_key),           //this need to be fixed
                getString(R.string.pref_lang_default_value)
        );
//        Log.d(TAG, "language now is: "+ language);
        mChampionViewModel.loadChampion(language);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        mChampionViewModel = ViewModelProviders.of(this).get(ChampionViewModel.class);
        loadChampion(sharedPreferences);
    }


}
