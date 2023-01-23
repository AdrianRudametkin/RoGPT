package com.example.appfutbol.ui.clasificacion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClasificacionViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ClasificacionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Fragmento de clasificacion");
    }

    public LiveData<String> getText() {
        return mText;
    }
}