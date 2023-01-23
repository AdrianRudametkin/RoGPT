package com.example.appfutbol.chuck;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.appfutbol.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class ChuckNorris {
    private ArrayList<String> frases;
    private Context context;
    private static ChuckNorris instance;


    private ChuckNorris(Context context){
        this.context = context;
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
                if(!linea.isBlank())
                    frases.add(linea);
            }

        } catch (IOException e) {
            frases.add("Chuck norris esta cansado y no tiene ganas de decir nada.");
        }

        return frases;
    }

    public static ChuckNorris getInstance(Context context){
        if(instance==null)
            instance = new ChuckNorris(context);

        return instance;
    }

    public String getFrase(){
        Random r = new Random();
        int randomIndex = r.nextInt(frases.size());
        return frases.get(randomIndex);
    }


}
