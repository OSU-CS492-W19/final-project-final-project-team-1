package com.example.finalproject.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.finalproject.utils.LOLChampionUtils;
import com.example.finalproject.utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadChampionDetailTask extends AsyncTask<Void, Void, String> {
    private static final String TAG = LoadChampionDetailTask.class.getSimpleName();
    public interface AsyncCallback {
        void onChampionDetailLoadFinished(List<ChampionDetailItem> championDetailItems);
    }

    private String mURL;
    private LoadChampionDetailTask.AsyncCallback mCallback;

    LoadChampionDetailTask(String url, LoadChampionDetailTask.AsyncCallback callback) {
        mURL = url;
        mCallback = callback;
    }

    protected String doInBackground(Void... voids) {
        String championDetailJSON = null;
        try {
            championDetailJSON = NetworkUtils.doHTTPGet(mURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return championDetailJSON;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: 3");
        ArrayList<ChampionDetailItem> championDetailItems = null;
        if (s != null) {
            championDetailItems = LOLChampionUtils.parseChampionDetailJSON(s);
        }
        mCallback.onChampionDetailLoadFinished(championDetailItems);
    }
}
