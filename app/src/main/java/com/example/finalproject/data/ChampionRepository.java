package com.example.finalproject.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.text.TextUtils;
import android.util.Log;

import com.example.finalproject.utils.LOLChampionUtils;

import java.util.Date;
import java.util.List;

public class ChampionRepository implements LoadChampionTask.AsyncCallback {

    private static final String TAG = ChampionRepository.class.getSimpleName();

    private MutableLiveData<List<AllChampionItem>> mChampionItems;
    private MutableLiveData<Status> mLoadingStatus;

    private String mCurrentLanguage;

    public ChampionRepository() {
        mChampionItems = new MutableLiveData<>();
        mChampionItems.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);

        mCurrentLanguage = null;
    }

    public void loadChampion(String language) {
        if (shouldFetchChampion(language)) {
            mCurrentLanguage = language;
            mChampionItems.setValue(null);
            mLoadingStatus.setValue(Status.LOADING);
            String url = LOLChampionUtils.buildChampionURL(language);
            new LoadChampionTask(url, this).execute();
        } else {
            Log.d(TAG, "using cached champion data");
        }
    }

    public LiveData<List<AllChampionItem>> getChampion() {return mChampionItems; }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    private boolean shouldFetchChampion(String language) {
        if (!TextUtils.equals(language, mCurrentLanguage)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onChampionLoadFinished(List<AllChampionItem> championItems) {
        mChampionItems.setValue(championItems);
        if (championItems != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
        } else {
            mLoadingStatus.setValue(Status.ERROR);
        }
    }
}
