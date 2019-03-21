package com.example.finalproject.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.text.TextUtils;
import android.util.Log;

import com.example.finalproject.ChampionDetailActivity;
import com.example.finalproject.utils.LOLChampionUtils;

import java.util.List;

public class ChampionDetailRepository implements LoadChampionDetailTask.AsyncCallback {
    private static final String TAG = ChampionDetailRepository.class.getSimpleName();

    private MutableLiveData<List<ChampionDetailItem>> mChampionDetailItems;
    private MutableLiveData<Status> mLoadingStatus;

    private String mCurrentLanguage;
    private ChampionDetailActivity mStarttowork;

    public ChampionDetailRepository() {
        mChampionDetailItems = new MutableLiveData<>();
        mChampionDetailItems.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);

        mCurrentLanguage = null;
    }

    public void loadChampionDetail(String champion_name, String language) {
        Log.d(TAG, "loadChampionDetail: 2");
//        if (shouldFetchChampionDetail(language)) {                  //this mightttttttttttt cause the same champion problem
            mCurrentLanguage = language;
            mChampionDetailItems.setValue(null);
            mLoadingStatus.setValue(Status.LOADING);
            champion_name = champion_name + ".json";
//            Log.d(TAG, "champion before call url is: " + champion_name);
//            Log.d(TAG, "language before call url is: " + language);
            String url = LOLChampionUtils.buildChampionDetailURL(champion_name, language);
            new LoadChampionDetailTask(url, this).execute();
//        } else {
//            Log.d(TAG, "using cached champion detail data");
//        }
    }

    public LiveData<List<ChampionDetailItem>> getChampionDetail() {
//        Log.d(TAG, "getChampionDetail: 5");
        return mChampionDetailItems; }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    private boolean shouldFetchChampionDetail(String language) {
        if (!TextUtils.equals(language, mCurrentLanguage)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onChampionDetailLoadFinished(List<ChampionDetailItem> championDetailItems) {
        mChampionDetailItems.setValue(championDetailItems);
//        String asdads = m
        if (championDetailItems != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
//            mStarttowork.fillInLayout(championDetailItems);
        } else {
            mLoadingStatus.setValue(Status.ERROR);
        }
    }
}
