package com.example.finalproject.utils;

import android.net.Uri;
import android.util.Log;

import com.example.finalproject.data.AllChampionItem;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.security.Key;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import static android.support.constraint.Constraints.TAG;

public class LOLChampionUtils {

    private final static String LOL_ALL_CHAMPION_LIST_URL = "http://ddragon.leagueoflegends.com/cdn/6.24.1/data/en_US/champion.json";
//    private final static String ENDING = "champion.json";

    private final static String LOL_CHAMPION_IMAGE_BASE_URL = "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/champion";

    private final static String LOL_CHAMPION_DETAIL_BASE_URL = "http://ddragon.leagueoflegends.com/cdn/6.24.1/data/en_US/champion";
    private final static String LOL_CHAMPION_DETAIL_QUERY_PARAM = "";

    static class AllChampionResults {
        Map<String, ChampionData> data;
    }

    static class ChampionData {
        String name;
    }

    static class ChampionDetailResult {
        ChampionDetail data;
    }


    static class ChampionDetail {
        String name;
        String lore;
        String stats;
        ChampionDetailPassive passive;
        ChampionDetailSpells[] spells;
    }

    static class ChampionDetailPassive {
        String name;
        String description;
    }

    static class ChampionDetailSpells {
        String name;
        String description;
    }

    public static String buildChampionURL(String language) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("ddragon.leagueoflegends.com")
                .appendPath("cdn")
                .appendPath("6.24.1")
                .appendPath("data")
                .appendPath(language)
                .appendPath("champion.json");
        String allChampionURL = builder.build().toString();
        Log.d(TAG, "buildChampionURL is : " + allChampionURL);
        return allChampionURL;
    }

    public static String buildChampionIconURL(String champ_name){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("ddragon.leagueoflegends.com")
                .appendPath("cdn")
                .appendPath("6.24.1")
                .appendPath("img")
                .appendPath("champion")
                .appendPath(champ_name);
        String championIconURL = builder.build().toString();
        return championIconURL;
    }

    public static String buildChampionDetailURL(String champion_name){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("ddragon.leagueoflegends.com")
                .appendPath("cdn")
                .appendPath("6.24.1")
                .appendPath("data")
                .appendPath("en_US")
                .appendPath("champion.json")
                .appendPath(champion_name);
        String championDetailURL = builder.build().toString();
        return championDetailURL;
    }

    public static ArrayList<AllChampionItem> parseChampionJSON(String championJSON){
        Gson gson = new Gson();
        AllChampionResults results = gson.fromJson(championJSON, AllChampionResults.class);
        if (results != null){
            ArrayList<AllChampionItem> championItems = new ArrayList<>();
            for (Map.Entry<String, ChampionData> name : results.data.entrySet()) {
                AllChampionItem championItem = new AllChampionItem();
                championItem.champion_name = name.getKey();
                championItems.add(championItem);
//                Log.d(TAG, "champion name is :"+ results.data.values());
            }
            return championItems;
        } else {
            return null;
        }

    }



}
