package com.example.appfutbol.ui.simulacion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SimulacionViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SimulacionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Pagina de simulación");
    }

    public LiveData<String> getText() {
        return mText;
    }
}