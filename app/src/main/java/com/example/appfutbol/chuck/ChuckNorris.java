package com.example.appfutbol.chuck;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class ChuckNorris extends Application {
    private static ArrayList<String> frases;
    private static Context context;

    @Override
    public void onCreate(){
        super.onCreate();
        context = getApplicationContext();
        frases = getFrasesFromFile();
    }

    private ArrayList<String> getFrasesFromFile(){
        AssetManager am = context.getAssets();
        frases = new ArrayList<>();
        try {
            InputStream is = am.open("norris.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String linea;

            while((linea=br.readLine()) != null) {
                if(!linea.trim().isEmpty())
                    frases.add(linea);
            }
            br.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            frases.add("Chuck norris esta cansado y no tiene ganas de decir nada.");
        }

        am.close();
        return frases;
    }

    public static String getFrase(){
        Random r = new Random();
        int randomIndex = r.nextInt(frases.size());
        return frases.get(randomIndex);
    }


}
