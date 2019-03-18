package com.example.finalproject.utils;

import android.net.Uri;
import android.util.Log;

import com.example.finalproject.data.AllChampionItem;
import com.example.finalproject.data.ChampionDetailItem;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.security.Key;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import static android.support.constraint.Constraints.TAG;

public class LOLChampionUtils {

    public static final String EXTRA_CHAMPION_DETAIL_ITEM = "com.example.finalproject.utils.ChampionDetailItem";

    private final static String LOL_ALL_CHAMPION_LIST_URL = "http://ddragon.leagueoflegends.com/cdn/6.24.1/data/en_US/champion.json";

    private final static String LOL_CHAMPION_IMAGE_BASE_URL = "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/champion";

    private final static String LOL_CHAMPION_DETAIL_BASE_URL = "http://ddragon.leagueoflegends.com/cdn/6.24.1/data/en_US/champion";

    private final static String LOL_CHAMPION_SPLASH_ART = "http://ddragon.leagueoflegends.com/cdn/img/champion/splash";

    private final static String LOL_CHAMPION_SPELL_URL = "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/spell";

    private final static String LOL_CHAMPION_PASSIVE_URL = "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/passive";

    static class AllChampionResults {
        Map<String, ChampionData> data;
    }

    static class ChampionData {
        String name;
        String id;
    }
                                                  //Champion detail here
    static class ChampionDetailResult {
        Map<String, ChampionDetailData> data;
    }

    static class ChampionDetailData {
        String name;
        String id;
        String title;
        ChampionDetailImage image;
        ChampionDetailSkin[] skins;
        String lore;
        ChampionDetailSpells[] spells;
        ChampionDetailPassive passive;
    }

    static class ChampionDetailImage {
        String full;
    }

    static class ChampionDetailSkin {                       //multiple
        int num;
        String name;
    }

    static class ChampionDetailSpells {                    //multiple
        String id;
        String name;
        String description;
    }

    static class ChampionDetailPassive {
        String name;
        String description;
        ChampionDetailPassiveImage image;
    }

    static class ChampionDetailPassiveImage{
        String full;
    }


                                                   //Build URL here

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

    public static String buildChampionIconURL(String champion_name){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("ddragon.leagueoflegends.com")
                .appendPath("cdn")
                .appendPath("6.24.1")
                .appendPath("img")
                .appendPath("champion")
                .appendPath(champion_name);
        String championIconURL = builder.build().toString();
        return championIconURL;
    }

    public static String buildChampionDetailURL(String champion_name, String language){
        Uri.Builder builder = new Uri.Builder();
        Log.d(TAG, "what the fuck is language: " + language);
        builder.scheme("https")
                .authority("ddragon.leagueoflegends.com")
                .appendPath("cdn")
                .appendPath("6.24.1")
                .appendPath("data")
                .appendPath(language)
                .appendPath("champion")
                .appendPath(champion_name);
        String championDetailURL = builder.build().toString();
        return championDetailURL;
    }

    public static String buildChampionSplashArtURL(String champion_name) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("ddragon.leagueoflegends.com")
                .appendPath("cdn")
                .appendPath("img")
                .appendPath("champion")
                .appendPath("splash")
                .appendPath(champion_name);
        String championSplashArtURL = builder.build().toString();
        return championSplashArtURL;
    }

    public static String buildChampionSpellURL(String champion_spell_name) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("ddragon.leagueoflegends.com")
                .appendPath("cdn")
                .appendPath("6.24.1")
                .appendPath("img")
                .appendPath("spell")
                .appendPath(champion_spell_name);
        String championSpellURL = builder.build().toString();
        return championSpellURL;
    }

    public static String buildChampionPassiveURL(String champion_passive_name) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("ddragon.leagueoflegends.com")
                .appendPath("cdn")
                .appendPath("6.24.1")
                .appendPath("img")
                .appendPath("passive")
                .appendPath(champion_passive_name);
        String championPassiveURL = builder.build().toString();
        return championPassiveURL;
    }

    public static ArrayList<AllChampionItem> parseChampionJSON(String championJSON){
        Gson gson = new Gson();
        AllChampionResults results = gson.fromJson(championJSON, AllChampionResults.class);
        if (results != null){
            ArrayList<AllChampionItem> championItems = new ArrayList<>();
            for (Map.Entry<String, ChampionData> name : results.data.entrySet()) {
                AllChampionItem championItem = new AllChampionItem();
                championItem.champion_name = name.getValue().name;
                championItem.champion_id = name.getValue().id;
                championItems.add(championItem);
//                Log.d(TAG, "champion name is :"+ name.getValue().name);
            }
            return championItems;
        } else {
            return null;
        }
    }

    public static ArrayList<ChampionDetailItem> parseChampionDetailJSON(String championDetailJSON){
        Gson gson = new Gson();
        ChampionDetailResult result = gson.fromJson(championDetailJSON, ChampionDetailResult.class);
        if (result != null){
            ArrayList<ChampionDetailItem> championDetailItems = new ArrayList<>();
            for (Map.Entry<String, ChampionDetailData> championData : result.data.entrySet()) {
                ChampionDetailItem championDetailItem = new ChampionDetailItem();
                championDetailItem.champion_name = championData.getValue().name;
                championDetailItem.champion_id = championData.getValue().id;
                championDetailItem.champion_title = championData.getValue().title;
                championDetailItem.champion_icon = championData.getValue().image.full;
                championDetailItem.champion_default_skin = "_0,jpg";
                championDetailItem.champion_lore = championData.getValue().lore;

                //needed more skin and spell

                championDetailItem.champion_passive_name = championData.getValue().passive.name;
                championDetailItem.champion_passive_description = championData.getValue().passive.description;
                championDetailItem.champion_passive_image = championData.getValue().passive.image.full;

                championDetailItems.add(championDetailItem);

//                Log.d(TAG, "champion name is :"+ championDetailItem.champion_name);
//                Log.d(TAG, "champion id is :"+ championDetailItem.champion_id);
//                Log.d(TAG, "champion title is :"+ championDetailItem.champion_title);
//                Log.d(TAG, "champion icon is :"+ championDetailItem.champion_icon);
//                Log.d(TAG, "champion lore is :"+ championDetailItem.champion_lore);
//                Log.d(TAG, "champion passive name is :"+ championDetailItem.champion_passive_name);
//                Log.d(TAG, "champion passive description is :"+ championDetailItem.champion_passive_description);
//                Log.d(TAG, "champion passive image is :"+ championDetailItem.champion_passive_image);

            }
            return championDetailItems;
        } else {
            return null;
        }

    }

}
