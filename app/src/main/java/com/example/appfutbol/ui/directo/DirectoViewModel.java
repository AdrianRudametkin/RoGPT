package com.example.appfutbol.ui.directo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
/**
 * Rogelio Rodriguez
 */
public class DirectoViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public DirectoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Pagina de directos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}