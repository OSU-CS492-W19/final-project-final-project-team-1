package com.example.finalproject.data;

import android.os.AsyncTask;

import com.example.finalproject.utils.LOLChampionUtils;
import com.example.finalproject.utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadChampionTask extends AsyncTask<Void, Void, String> {

    public interface AsyncCallback {
        void onChampionLoadFinished(List<AllChampionItem> championItems);
    }

    private String mURL;
    private AsyncCallback mCallback;

    LoadChampionTask(String url, AsyncCallback callback) {
        mURL = url;
        mCallback = callback;
    }

    protected String doInBackground(Void... voids) {
        String championJSON = null;
        try {
            championJSON = NetworkUtils.doHTTPGet(mURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return championJSON;
    }

    @Override
    protected void onPostExecute(String s) {
        ArrayList<AllChampionItem> championItems = null;
        if (s != null) {
            championItems = LOLChampionUtils.parseChampionJSON(s);
        }
        mCallback.onChampionLoadFinished(championItems);
    }
}
