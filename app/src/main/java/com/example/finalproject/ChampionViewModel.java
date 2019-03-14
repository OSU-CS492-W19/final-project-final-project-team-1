package com.example.finalproject;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;

import com.example.finalproject.data.AllChampionItem;
import com.example.finalproject.data.Status;

import java.util.List;

public class ChampionViewModel extends ViewModel {
    private LiveData<List<AllChampionItem>> mAllChampionItems;
    private LiveData<Status> mLoadingStatus;

}
