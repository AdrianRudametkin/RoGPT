package com.example.appfutbol.ui.momentos;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.appfutbol.R;
import com.example.appfutbol.chuck.ChuckNorris;
import com.example.appfutbol.databinding.FragmentMemoriaBinding;
import com.example.appfutbol.ui.home.HomeViewModel;

import java.text.DateFormat;
import java.util.Calendar;

public class MemoriaFragment extends Fragment{
    private FragmentMemoriaBinding binding;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMemoriaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        binding.textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDateDialog();
            }
        });

        // Listener (cuando se selecciona) para el dialogo por defecto de seleccion de fecha
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar mCalendar = Calendar.getInstance();
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String selectedDate = DateFormat.getDateInstance(DateFormat.SHORT).format(mCalendar.getTime());
                binding.textViewDate.setText(selectedDate);
            }
        };

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Método onClick para abrir la cámara, tomar una foto y guardarla.
     * Pedirá permisos al usuario si la aplicación no puede acceder a la camara.
     */
    private void takePicture(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }else{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if(intent.resolveActivity(getActivity().getPackageManager()) != null){
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        }



    }

    /**
     * Método onClick que abra el dialogo de selector de fecha con la fecha de hoy
     */
    public void openDateDialog(){
        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog d = new DatePickerDialog(getContext(),
                androidx.appcompat.R.style.Base_ThemeOverlay_AppCompat_Dialog,
                dateSetListener,
                year, month, dayOfMonth);
        d.show();
    }

    /**
     * Método listener que recoge la imagen una vez tomada. Si el usuario decidio cancelar, la imagen no se modifica ni se guarda.
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     *
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_IMAGE_CAPTURE){
            if(data == null)
                return;

            Bundle extras = data.getExtras();
            if(extras==null || !extras.keySet().contains("data"))
                return;

            Bitmap imageBitmap = (Bitmap) extras.get("data");

            if(imageBitmap!=null) {
                binding.imageView.setImageBitmap(imageBitmap);
            }
        }
    }

    /**
     * Método listener que recoge el resultado de la petición de permisos.
     * @param requestCode The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *     which is either {@link android.content.pm.PackageManager#PERMISSION_GRANTED}
     *     or {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Never null.
     *
     */
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