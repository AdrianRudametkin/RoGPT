package com.example.appfutbol.ui.home;

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
        //setContext(getContext());
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.buttonChuck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!getActivity().isFinishing() || !getActivity().isDestroyed())
                    ((MainActivity)getContext()).notificacionChuck();
            }
        });

        // Crear el generador de frases

        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}