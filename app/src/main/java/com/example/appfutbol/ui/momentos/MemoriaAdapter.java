package com.example.appfutbol.ui.momentos;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfutbol.R;
import com.example.appfutbol.firebase.Momento;
import com.example.appfutbol.firebase.MomentosDatabase;

public class MemoriaAdapter extends RecyclerView.Adapter<MemoriaAdapter.ViewHolder> {
    private MomentosDatabase database;

    public MemoriaAdapter(MomentosDatabase database) {
        this.database = database;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View momentoView = inflater.inflate(R.layout.view_momento, parent, false);
        ViewHolder viewHolder = new ViewHolder(momentoView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Momento m = database.getMomento(position);

        holder.textViewTitulo.setText(m.getTitulo());
        holder.textViewDescripcion.setText(m.getDescripcion());
        holder.textViewFecha.setText("⏰ " + m.getFecha());
        if(m.getImagen()==null)
            holder.imageViewMomento.setImageResource(R.drawable.icon_chuck);
        else
            holder.imageViewMomento.setImageBitmap(m.getImagen());

        Log.i("ViewHolder", ("data is set. Title"+m.getTitulo()));
    }

    @Override
    public int getItemCount() {
        return database.getCuenta();
    }

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
