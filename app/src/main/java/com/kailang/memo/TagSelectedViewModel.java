package com.kailang.memo;

import android.content.ClipData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TagSelectedViewModel extends ViewModel {
    private final MutableLiveData<String> selected = new MutableLiveData<String>();

    public void select(String string) {
        selected.setValue(string);
    }

    public LiveData<String> getSelected() {
        return selected;
    }
}
