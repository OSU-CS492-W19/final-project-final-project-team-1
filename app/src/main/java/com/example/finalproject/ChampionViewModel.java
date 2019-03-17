package com.example.finalproject;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.finalproject.data.AllChampionItem;
import com.example.finalproject.data.Status;
import com.example.finalproject.data.ChampionRepository;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class ChampionViewModel extends ViewModel {
    private LiveData<List<AllChampionItem>> mAllChampionItems;
    private LiveData<Status> mLoadingStatus;

    private ChampionRepository mChampionRepository;

    private AllChampionItem mCopy;

    public ChampionViewModel() {
        mChampionRepository = new ChampionRepository();
        mAllChampionItems = mChampionRepository.getChampion();
        mLoadingStatus = mChampionRepository.getLoadingStatus();
    }

    public void loadChampion(String language) {
        mChampionRepository.loadChampion(language);
    }

    public LiveData<List<AllChampionItem>> getChampion() {

        if (mAllChampionItems != null) {
            return mAllChampionItems;
        } else {
            Log.d(TAG, "is null");
            return null;
        }
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }
}
