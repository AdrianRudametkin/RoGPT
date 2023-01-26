package com.example.appfutbol;

import android.os.Bundle;
import android.view.View;

import com.example.appfutbol.chuck.ChuckNorris;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.appfutbol.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    // Utilizar binding para facilitar el uso de views en el programa
    //https://developer.android.com/topic/libraries/view-binding#java
    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // Recoger el view binding de la vista activity_main
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        // Hacer este el layout para visualizar
        setContentView(binding.getRoot());


        // Crear el menu de navegacion
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Añadir los fragments que formaran parte del menu. Agregar todos los fragmets que sean de alto nivel
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_simulacion, R.id.navigation_clasificacion, R.id.navigation_directo, R.id.navigation_momentos)
                .build();
        // Crear y añadir el controlador de layouts
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Agregar el controlador a la vista
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Esconeder la barra de abajo cuando estemos en la patanlla de crear una nueva memoria
        navController.addOnDestinationChangedListener((navController1, navDestination, bundle) -> {
                if(navDestination.getId() == R.id.navigation_memoria) {
                    navView.setVisibility(View.GONE);
                    // Esconder la barra de arriba
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().show();
                    }
                } else {
                    navView.setVisibility(View.VISIBLE);
                    // Esconder la barra de arriba
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().hide();
                    }
                }
            });

        // Esconder la barra de arriba
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        return navController.navigateUp();
    }



}