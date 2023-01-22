package com.example.appfutbol.ui.recyclerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MatchRecyclerAdapter extends RecyclerView.Adapter<MatchRecyclerAdapter.ViewHolder> {

    private final String[] eq1 = {"0","1","2","3"};
    private final String[] eq2 = {"4","5","6","7"};
    /*private final int[] img1 = {
            R.drawable.ic_menu_camera,
            R.drawable.ic_menu_gallery,
            R.drawable.ic_menu_slideshow,
            R.drawable.ic_launcher_background
    };
    private final int[] img2 = {
            R.drawable.ic_launcher_background,
            R.drawable.ic_menu_slideshow,
            R.drawable.ic_menu_gallery,
            R.drawable.ic_menu_camera
    };*/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View v = LayoutInflater.from(parent.getContext())
                //.inflate(R.layout.match_card_view, parent, false);
        //return new ViewHolder(v);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       /* holder.imageViewTeam1.setImageResource(img1[position]);
        holder.imageViewTeam2.setImageResource(img2[position]);
        holder.textViewScore1.setText(eq1[position]);
        holder.textViewScore2.setText(eq2[position]);*/
    }

    @Override
    public int getItemCount() {
        //return img1.length;
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewTeam1, imageViewTeam2;
        TextView textViewScore1, textViewScore2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            /*imageViewTeam1 = itemView.findViewById(R.id.imageViewTeam1);
            imageViewTeam2 = itemView.findViewById(R.id.imageViewTeam2);
            textViewScore1 = itemView.findViewById(R.id.textViewScore1);
            textViewScore2 = itemView.findViewById(R.id.textViewScore2);*/
        }
    }
}
