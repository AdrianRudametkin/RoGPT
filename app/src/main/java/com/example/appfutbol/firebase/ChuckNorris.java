package com.example.appfutbol.firebase;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

/**
 * El objetivo principal de esta clase es recoger una frase aleatoria sobre Chuck Norris.
 * Las frases estan almacenadas en una base de datos Firebase.
 *
 * Funcionamiento:
 *  1. Inicializar: llamar al metodo estático {ChuckNorris.init()}.
 *  2. Uso: llamar al metodo estático {ChuckNorris.getFrase()} para obtener una frase(String) aleatoria.
 */
public class ChuckNorris{
    // Variables de clase
    private static ArrayList<String> frases;
    private static ChuckNorris instance;

    /**
     * Constructor privado.
     * Solo puede haber una instancia de este objeto.
     */
    private ChuckNorris(){
        getFrasesFromFile();
    }

    /**
     * Método para inicializar el objeto y cargar las frases a memória.
     */
    public static void init(){
        // Instanciar el objeto si no existe
        if(instance==null)
            instance = new ChuckNorris();

    }


    /**
     * Método que recoge las frases de la base de datos Firebase y las almacena en el ArrayList (frases) de la clase.
     */
    private static void getFrasesFromFile(){
        // Nos conectamos al servidor FireBase
        // Los permisos y credenciales se guardan en la propia aplicacion con el archivo "google-services.json"
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Cogemos la referencia al archivo que queremos de la base de datos
        StorageReference chucktxt = storage.getReferenceFromUrl("gs://rogpt-futbolapp.appspot.com/ChuckNorris.txt");

        // Inicializamos el ArrayList
        frases = new ArrayList<>();

        try {
            // Creamos un fichero local temporal donde descargaremos el archivo de la base de datos
            final File tempFile = File.createTempFile("chuck","txt");

            // De la referencia añadimos un Listener que se ejecutará cuando el archivo se haya descargado
            // insertamos por parametro la variable del archivo local
            chucktxt.getFile(tempFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                // El archivo ya esta descargado
                @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)    // Para el uso de StandardCharsets
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    BufferedReader br = null;
                    try {
                        // Convertimos el archivo local a un lector de bytes
                        br = new BufferedReader(new FileReader(tempFile, Charset.forName(StandardCharsets.UTF_8.name())));
                        String linea;

                        // Leemos la lineas y solo metemos en el ArrayList las que no esten vacias
                        while((linea=br.readLine()) != null) {
                            if(!linea.trim().isEmpty())
                                frases.add(linea);
                        }
                        br.close();
                        tempFile.delete();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que devuelve una frase aleatoria del la lista de frases. En caso de que hubiese un
     * error de conxión, escritura o lectura del archivo, o de corrupcion de datos. Se devolverá una
     * frase predefinida y se reinentará inicializar la lista de frases.
     * @return una frase aleatoria sobre Chuck Norris.
     */
    public static String getFrase(){
        Random r = new Random();
        if(frases.isEmpty()) {
            getFrasesFromFile();
            return "Chuck Norris no se pudo conectar a internet. Inténtalo más tarde.";
        }

        int randomIndex = r.nextInt(frases.size());
        return frases.get(randomIndex);
    }


}
