package com.example.appfutbol.ui.home;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.appfutbol.MainActivity;
import com.example.appfutbol.R;
import com.example.appfutbol.chuck.ChuckNorris;
import com.example.appfutbol.databinding.FragmentDirectoBinding;
import com.example.appfutbol.databinding.FragmentHomeBinding;
import com.example.appfutbol.ui.directo.DirectoViewModel;

import java.util.Random;

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
        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(random.nextInt(), notification);
        notificationManagerCompat.notify(1,summaryNotification);
    }

}