package com.example.appfutbol.ui.momentos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appfutbol.R;
import com.example.appfutbol.databinding.FragmentMomentosBinding;
import com.example.appfutbol.firebase.MomentosDatabase;

public class MomentosFragment extends Fragment {

    private FragmentMomentosBinding binding;
    private MemoriaAdapter adapter;
    private MomentosDatabase database;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMomentosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        NavController navController = NavHostFragment.findNavController(this);

        database = MomentosDatabase.getInstance();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MemoriaAdapter(database);
        binding.recyclerView.setAdapter(adapter);

        binding.fabNew.setOnClickListener(v -> navController.navigate(R.id.action_navigation_momentos_to_navigation_memoria));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}