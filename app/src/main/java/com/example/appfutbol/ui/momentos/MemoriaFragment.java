package com.example.appfutbol.ui.momentos;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.appfutbol.R;
import com.example.appfutbol.databinding.FragmentMemoriaBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Rogelio Rodriguez
 */
public class MemoriaFragment extends Fragment{
    private FragmentMemoriaBinding binding;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 1;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Bitmap imagenEntera;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMemoriaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        // Para esconder el teclado cuando pulsemos fuera de los TextEdit
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(root.getWindowToken(),0);
            }
        });

        // Listener para cambiar la foto
        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        binding.imageView.setImageResource(R.drawable.football);

        // Listener para cambiar la fecha
        binding.textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDateDialog();
            }
        });
        // Poner la fecha de hoy en el TextView
        binding.textViewDate.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(System.currentTimeMillis()));

        // Listener para el boton de agregar
        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
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

    private void saveData() {
        String titulo = binding.editTextTitulo.getText().toString();
        String fecha = binding.textViewDate.getText().toString();
        String descripcion = binding.editTextTextMultiLine.getText().toString();
        // Convertimos la imagen a bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if(imagenEntera!=null)
            imagenEntera.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        else
            ((BitmapDrawable)binding.imageView.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] imgData = baos.toByteArray();

        if(titulo.isEmpty()) {
            Toast.makeText(getContext(), "No puedes dejar el título vacio", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> momento = new HashMap<>();
        momento.put("titulo", titulo);
        momento.put("descripcion", descripcion);
        momento.put("fecha", fecha);

        FirebaseFirestore.getInstance().collection("momentos").add(momento)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        subirImagen(documentReference, imgData);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "No se pudo guardar en la base de datos.", Toast.LENGTH_SHORT).show();
                        binding.buttonAdd.setEnabled(true);
                    }
                });
        // Deshabilitamos el boton para que, mientras se suben los datos, el usuario no puede volver a enviarlos
        binding.buttonAdd.setEnabled(false);
    }

    private void subirImagen(DocumentReference documentReference, byte[] imgData){
        String imgUrl = "gs://rogpt-futbolapp.appspot.com/img/"+documentReference.getId()+".png";
            FirebaseStorage.getInstance()
                    .getReferenceFromUrl(imgUrl)
                    .putBytes(imgData)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            documentReference.update(Map.of("url_img",imgUrl));
                            binding.buttonAdd.setEnabled(true);
                            binding.textViewDate.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(System.currentTimeMillis()));
                            binding.editTextTitulo.setText("");
                            binding.editTextTextMultiLine.setText("");
                            binding.imageView.setImageResource(R.drawable.football);
                            imagenEntera = null;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "No se pudo subir la imagen. Pruebe otra vez.", Toast.LENGTH_SHORT).show();
                            binding.buttonAdd.setEnabled(true);
                        }
                    });
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

            imagenEntera = (Bitmap) extras.get("data");

            if(imagenEntera!=null) {
                binding.imageView.setImageBitmap(imagenEntera);
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