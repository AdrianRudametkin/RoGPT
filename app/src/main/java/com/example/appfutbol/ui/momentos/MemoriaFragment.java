package com.example.appfutbol.ui.momentos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appfutbol.R;
import com.example.appfutbol.databinding.FragmentHomeBinding;
import com.example.appfutbol.databinding.FragmentMemoriaBinding;
import com.example.appfutbol.ui.home.HomeViewModel;

public class MemoriaFragment extends Fragment {
    private FragmentMemoriaBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentMemoriaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}