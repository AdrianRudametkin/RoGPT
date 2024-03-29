package com.example.appfutbol.ui.directo;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appfutbol.databinding.FragmentDirectoBinding;
/**
 * Rogelio Rodriguez
 */
public class DirectoFragment extends Fragment {

    private FragmentDirectoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DirectoViewModel directoViewModel = new ViewModelProvider(this).get(DirectoViewModel.class);

        binding = FragmentDirectoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDirecto;
        directoViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}