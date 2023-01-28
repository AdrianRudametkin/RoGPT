package com.example.appfutbol.firebase;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class MomentosDatabase {
    private static ArrayList<Momento> momentos;
    private static MomentosDatabase instance;

    private MomentosDatabase(){
        rellenarLista();
    }

    private void rellenarLista(){
        momentos = new ArrayList<>();
        Momento m;
        for(int i = 0; i<10; i++){
            m = new Momento();
            m.setTitulo("Titulo "+i);
            m.setDescripcion("Descripcion super random de prueba.");
            m.setFecha("12/12/2022");
            momentos.add(m);
        }

    }

    public static MomentosDatabase getInstance(){
        if(instance==null)
            instance = new MomentosDatabase();
        return instance;
    }

    public Momento getMomento(int pos){
        if(pos<0 || pos>getCuenta())
            return new Momento();

        return momentos.get(pos);
    }

    public int getCuenta(){
        if(momentos==null)
            return 0;
        return momentos.size();
    }

    public void insertarMomento(String titulo, String descripcion, String fecha, Bitmap imagen){
        Momento m = new Momento(titulo, descripcion, fecha, imagen);
        momentos.add(m);
    }
}
