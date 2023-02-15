package com.example.appfutbol.ui.clasificacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appfutbol.databinding.FragmentClasificacionBinding;
/**
 * Rogelio Rodriguez
 */
public class ClasificacionFragment extends Fragment {

    private FragmentClasificacionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ClasificacionViewModel clasificacionViewModel = new ViewModelProvider(this).get(ClasificacionViewModel.class);

        binding = FragmentClasificacionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textClasificacion;
        clasificacionViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}