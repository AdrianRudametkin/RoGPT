package com.example.appfutbol.ui.momentos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.appfutbol.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import kotlin.jvm.internal.Ref;
/**
 * Rogelio Rodriguez
 */
// Adaptador para el recycler view
public class MemoriaAdapter extends RecyclerView.Adapter<MemoriaAdapter.ViewHolder> {
    private ArrayList<Momento> lista;
    private Context context;
    private MomentosFragment.ClickListener listener;

    public MemoriaAdapter(ArrayList<Momento> lista, MomentosFragment.ClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View momentoView = inflater.inflate(R.layout.view_momento, parent, false);
        ViewHolder viewHolder = new ViewHolder(momentoView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        Momento m = lista.get(position);

        holder.textViewTitulo.setText(m.getTitulo());
        holder.textViewDescripcion.setText(m.getDescripcion());
        holder.textViewFecha.setText("⏰ " + m.getFecha());
        cargarImagen(holder, m.getImagenUrl());

        holder.imageViewMomento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.click(index);
                Log.i("MemoriaAdapter", "OnClick activado: "+index);
            }
        });

        Log.i("ViewHolder", ("data is set. Title"+m.getTitulo()));
    }

    /**
     * Metodo que coge la imagen de la base de datos y la une al ImageView
     * @param holder Vista que se quiere modificar.
     * @param imgUrl URL entera de la ubicación de la imagen.
     */
    public void cargarImagen(ViewHolder holder, String imgUrl){
        // Ponesmo la imagen de error de momento
        holder.imageViewMomento.setImageResource(R.drawable.image_error);

        if(imgUrl==null)
            return;
        // Conectamos a la base de datos
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Cogemos la referencia de la imgagen

        try{
            StorageReference img = storage.getReferenceFromUrl(imgUrl);

            // Creamos un archivo temporal donde guardaremos la imgagen
            final File tempFile = File.createTempFile("img","jpg");
            // Cargamos el archivo
            img.getFile(tempFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Convertimos la imgagen a Bitmap
                    Bitmap bitmap = BitmapFactory.decodeFile(tempFile.getPath());
                    holder.imageViewMomento.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    int errorCode = ((StorageException) e).getErrorCode();
                    switch (errorCode){
                        case StorageException.ERROR_NOT_AUTHENTICATED:
                    }
                }
            });
        }catch(IllegalArgumentException ex){
            Log.w("Error Firebase", "Url de la imagen no es válida. "+ex.getMessage());
        }catch (IOException e) {
            Log.w("Error ImgUrl", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    // Clase que indica la vista de la lista del recycler view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitulo, textViewDescripcion, textViewFecha;
        ImageView imageViewMomento;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.textViewTitulo);
            textViewDescripcion = itemView.findViewById(R.id.textViewDescripcion);
            textViewFecha = itemView.findViewById(R.id.textViewFecha);
            imageViewMomento = itemView.findViewById(R.id.imageViewMomento);
        }
    }
}
