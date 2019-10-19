package com.kailang.memo.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class TagSelectedViewModel extends AndroidViewModel {
    private final MutableLiveData<String> selected;
    public TagSelectedViewModel(@NonNull Application application) {
        super(application);
        selected = new MutableLiveData<String>();
        select("请选择一个标签");
    }

    public void select(String string) {
        Log.e("xxxxx","TagSelectedViewModel_select: "+string);
        selected.setValue(string);
    }

    public MutableLiveData<String> getSelected() {
        return selected;
    }
}
