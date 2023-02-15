package com.example.appfutbol.ui.momentos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appfutbol.R;
import com.example.appfutbol.databinding.FragmentMomentosBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
/**
 * Rogelio Rodriguez
 */
public class MomentosFragment extends Fragment {
    private FragmentMomentosBinding binding;
    private MemoriaAdapter adapter;
    private ArrayList<Momento> listaMomentos;
    private ClickListener listener;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Creamos lista nueva
        listaMomentos = new ArrayList<>();

        // Creamos un listener para cuando pulsemos una vista
        listener = new ClickListener() {
            @Override
            public void click(int index) {
                dialogoBorrar(index);
            }
        };

        // Creamos el binding
        binding = FragmentMomentosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Creamos el navegador para cambiar de fragments pulsando el boton de añadir
        NavController navController = NavHostFragment.findNavController(this);
        binding.fabNew.setOnClickListener(v -> navController.navigate(R.id.action_navigation_momentos_to_navigation_memoria));

        // Ponemos un layoutmanager al recyclerview (tiene que se un FrameLayout en el XML)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);

        // Le añadimos nuestro adaptador
        adapter = new MemoriaAdapter(listaMomentos, listener);
        binding.recyclerView.setAdapter(adapter);

        // Creamos la base de datos
        db = FirebaseFirestore.getInstance();
        // Creamos un listener que coja todos los documentos
        db.collection("momentos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        actualizarLista(document);
                    }
                }else{
                    Log.w("Firestore", "Error al recoger datos", task.getException());
                }
            }
        });

        return root;
    }

    private void dialogoBorrar(int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("¿Quieres borrar este momentazo?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        borrarMomento(index);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Momento no borrado", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    private void borrarMomento(int index) {

        db.collection("momentos").document(listaMomentos.get(index).getId()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        FirebaseStorage.getInstance().getReferenceFromUrl(listaMomentos.get(index).getImagenUrl());
                        listaMomentos.remove(index);
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "No se pudo borrar el momento", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Metod que añade todos los momentos de la base de datos y notifica al adapter para que los muestre.
     * @param document documento de la base de datos
     */
    private void actualizarLista(QueryDocumentSnapshot document) {
        Momento m = new Momento();
        m.setId(document.getId());
        m.setTitulo(document.get("titulo", String.class));
        m.setDescripcion(document.get("descripcion", String.class));
        m.setFecha(document.get("fecha", String.class));
        m.setImagenUrl(document.get("url_img", String.class));
        Log.i("En actualizarLista de MomentosFragment","imgurl:"+document.get("url_img", String.class));
        listaMomentos.add(m);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Clase abstracta para el listener de las vistas del RecyclerView
     */
    public abstract class ClickListener{
        public abstract void click(int index);
    }

}