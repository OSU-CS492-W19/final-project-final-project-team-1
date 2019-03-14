package com.example.finalproject.utils;

public class LOLChampionUtils {

    private final static String LOL_ALL_CHAMPION_LIST = "http://ddragon.leagueoflegends.com/cdn/6.24.1/data/en_US/champion.json";

    private final static String LOL_CHAMPION_IMAGE_BASE_URL = "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/champion/";
    private final static String LOL_CHAMPION_IMAGE_QUERY_PARAM = "";

    private final static String LOL_CHAMPION_DETAIL_BASE_URL = "http://ddragon.leagueoflegends.com/cdn/6.24.1/data/en_US/champion/";
    private final static String LOL_CHAMPION_DETAIL_QUERY_PARAM = "";

    static class AllChampionResults {
        AllChampionData[] data;
    }

    static class AllChampionData {
        String champion_name;
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





}
