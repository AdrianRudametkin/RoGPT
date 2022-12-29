package com.example.rogpt.api;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import okhttp3.OkHttpClient;
import okhttp3.Request;

// Examples in https://codedaily.in/okhttp-tutorial-example/

public class FootBallAPI {
    // API Variables
    private final static String API_KEY = "504deb1c5581a1631716dbfbc2c26b9e";
    private final static String API_URL = "v3.football.api-sports.io";
    private final static String API_REQUEST_URL = "https://v3.football.api-sports.io/";

    // Variables estaticas
    private static FootBallAPI fbApi;
    private OkHttpClient client;
    private Request request;
    private String myResponse;

    private FootBallAPI(){
        client = new OkHttpClient();
    }

    public static FootBallAPI getInstance(){
        if(fbApi == null)
            fbApi = new FootBallAPI();
        return fbApi;
    }

    public ArrayList<String> requestLeagues(){
        ArrayList<String> leagues = new ArrayList<>();
        request = new Request.Builder()
                .header("x-rapidapi-key", API_KEY)
                .header("x-rapidapi-host", API_URL)
                .url(API_REQUEST_URL+"leagues").build();

        try {
            String httpResponse = client.newCall(request).execute().body().string();
            JSONObject json = new JSONObject(httpResponse);
            JSONArray response = json.getJSONArray("response");
            for(int i=0; i<response.length(); i++){
                leagues.add(response.getJSONObject(i).getJSONObject("league").getString("name"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return leagues;
    }
}
