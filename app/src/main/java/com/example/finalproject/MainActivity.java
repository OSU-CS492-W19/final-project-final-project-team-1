package com.example.finalproject;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.finalproject.data.AllChampionItem;

public class MainActivity extends AppCompatActivity
        implements ChampionAdapter.OnChampionItemClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mChampionItemsRV;
    private RecyclerView mDrawerItemRV;
    private ProgressBar mLoadingIndicatorPB;
    private TextView mLoadingErrorMessageTV;
    private DrawerLayout mDrawerLayout;

    private ChampionAdapter mChampionAdapter;
//    private DrawerAdapter mDrawerAdapter;


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



    }

    @Override
    public void onChampionItemClick(AllChampionItem forecastItem) {

    }
}
