package com.example.appfutbol;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.appfutbol.chuck.ChuckNorris;
import com.example.appfutbol.databinding.FragmentHomeBinding;
import com.example.appfutbol.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
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
    }

    @Override
    public boolean onSupportNavigateUp(){
        return navController.navigateUp();
    }

    private final static String CANAL_NOTIFICACION_CHUCK = "canal_chuck";
    private ChuckNorris chuck;

    public void notificacionChuck() {
        // Clase de ChuckNorris para sacar frases aleatorias
        Context context = getBaseContext();
        if(chuck == null)
            chuck = ChuckNorris.getInstance(context);

        String frase = chuck.getFrase();
        Log.i("APP",frase);

        // Para crear el canal de notificaciones
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Canal Chuck Norris";
            String descripcion = "Chistes de chuck norris";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel nChannel = new NotificationChannel(CANAL_NOTIFICACION_CHUCK, name, importance);
            nChannel.setDescription(descripcion);
            NotificationManager nManager = context.getSystemService(NotificationManager.class);
            nManager.createNotificationChannel(nChannel);
        }

        // Crear la notificacion
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CANAL_NOTIFICACION_CHUCK)
                .setSmallIcon(R.drawable.icon_chuck)
                .setContentTitle("Secretos Chuck")
                .setContentText(frase)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        // Enviar la notificacion
        NotificationManagerCompat nManager = NotificationManagerCompat.from(context);
        nManager.notify(1, builder.build());
    }
}