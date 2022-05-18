package com.danp.ges_books_01.ui.sugerencias;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SugerenciasViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SugerenciasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}