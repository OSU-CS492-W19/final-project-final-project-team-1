package com.example.finalproject;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.finalproject.data.AllChampionItem;
import com.example.finalproject.data.ChampionDetailItem;
import com.example.finalproject.data.ChampionDetailRepository;
import com.example.finalproject.data.ChampionRepository;
import com.example.finalproject.data.Status;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class ChampionDetailViewModel extends ViewModel {
    private LiveData<List<ChampionDetailItem>> mChampionDetailItems;
    private LiveData<Status> mLoadingStatus;

    private ChampionDetailRepository mChampionDetailRepository;

    public ChampionDetailViewModel() {
        mChampionDetailRepository = new ChampionDetailRepository();
        mChampionDetailItems = mChampionDetailRepository.getChampionDetail();
        mLoadingStatus = mChampionDetailRepository.getLoadingStatus();
    }

    public void loadChampionDetail(String champion_name, String language) {
//        Log.d(TAG, "language before repo is: "+ language);
        mChampionDetailRepository.loadChampionDetail(champion_name, language);
//        Log.d(TAG, "loadChampionDetail: 1");
    }

    public LiveData<List<ChampionDetailItem>> getChampionDetail() {

        if (mChampionDetailItems != null) {
            return mChampionDetailItems;
        } else {
            Log.d(TAG, "is null");
            return null;
        }
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }
}
