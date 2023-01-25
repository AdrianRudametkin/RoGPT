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

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.buttonChuck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificacionChuck(v);
            }
        });
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void notificacionChuck(View v){
        Context context = getContext();
        String CHUCK_CHANNEL = "canal_chuck";
        String CHUCK_CHANNEL_GROUPING = "com.android.example.CHUCK";

        String frase = ChuckNorris.getFrase();

        Notification notification = new NotificationCompat.Builder(context, CHUCK_CHANNEL)
                .setSmallIcon(R.drawable.icon_chuck)
                .setContentText(frase)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setGroup(CHUCK_CHANNEL_GROUPING)
                .setAutoCancel(true)
                .build();


        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, notification);
    }

}