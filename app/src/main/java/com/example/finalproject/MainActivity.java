package com.example.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.finalproject.data.AllChampionItem;

public class MainActivity extends AppCompatActivity
        implements ChampionAdapter.OnChampionItemClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onChampionItemClick(AllChampionItem forecastItem) {

    }
}
