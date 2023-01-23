package com.example.appfutbol.ui.momentos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appfutbol.R;
import com.example.appfutbol.databinding.FragmentHomeBinding;
import com.example.appfutbol.databinding.FragmentMomentosBinding;
import com.example.appfutbol.ui.home.HomeViewModel;

public class MomentosFragment extends Fragment {

    private FragmentMomentosBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMomentosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        NavController navController = NavHostFragment.findNavController(this);

        binding.fabNew.setOnClickListener(v -> navController.navigate(R.id.action_navigation_momentos_to_navigation_memoria));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}