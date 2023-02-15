package com.example.appfutbol.ui.home;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.appfutbol.R;
import com.example.appfutbol.firebase.ChuckNorris;
import com.example.appfutbol.databinding.FragmentHomeBinding;

import java.util.Random;
/**
 * Rogelio Rodriguez
 */
public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.buttonChuck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificacionChuck();
            }
        });

        // Inicializamos las frases
        ChuckNorris.init();

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Metodo que se llama cuando se pulsa el boton de chuck norris. Este metodo coge un frase de la
     * clase ChukNorris aleatoria y envia una notificación.
     */
    public void notificacionChuck(){
        // Variables necesarias para la creacion de notificaciones
        Context context = getContext();
        String CHUCK_CHANNEL = "canal_chuck";
        String CHUCK_CHANNEL_GROUPING = "com.android.example.CHUCK";

        // Crear un numero aleatorio de id para que una notificacion no sobre escriba a otra
        Random random = new Random();

        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        // Crear un canal para versiones de Android superiores a Android Oreo
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHUCK_CHANNEL,"Frases Chuck Norris", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);

        }
        // Notificación principal
        Notification notification = new NotificationCompat.Builder(context, CHUCK_CHANNEL)
                .setContentTitle("El cajón de Chuck")
                .setSmallIcon(R.drawable.icon_chuck)
                .setContentText(ChuckNorris.getFrase())
                .setStyle(new NotificationCompat.BigTextStyle())    // Para que se muestre tod0 el texto si la notificacion es larga
                .setGroup(CHUCK_CHANNEL_GROUPING)
                .setAutoCancel(true)
                .build();

        // Agrupador de notificaciones
        Notification summaryNotification = new NotificationCompat.Builder(context,CHUCK_CHANNEL)
                .setContentTitle("El cajón Chuck")
                .setContentText("El cajón de Chuck Norris")
                .setSmallIcon(R.drawable.icon_chuck)
                .setGroup(CHUCK_CHANNEL_GROUPING)
                .setGroupSummary(true)
                .build();

        // Lanzar las notificaciones
        notificationManager.notify(random.nextInt(), notification);
        notificationManager.notify(1,summaryNotification);
    }

}