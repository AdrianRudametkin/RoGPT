package com.example.appfutbol.ui.momentos;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appfutbol.R;
import com.example.appfutbol.databinding.FragmentHomeBinding;
import com.example.appfutbol.databinding.FragmentMemoriaBinding;
import com.example.appfutbol.ui.home.HomeViewModel;

import java.io.IOException;

public class MemoriaFragment extends Fragment {
    private FragmentMemoriaBinding binding;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentMemoriaBinding.inflate(inflater, container, false);
        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void takePicture(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }else{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if(intent.resolveActivity(getActivity().getPackageManager()) != null){
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        }



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_IMAGE_CAPTURE){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            binding.imageView.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if(requestCode == REQUEST_CAMERA_PERMISSION){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            else
                Toast.makeText(getContext(), "No tienes permisos para hacer fotos.", Toast.LENGTH_SHORT).show();
        }
    }

}