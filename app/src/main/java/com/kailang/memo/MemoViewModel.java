package com.kailang.memo;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MemoViewModel extends AndroidViewModel {
    private MemoRepository memoRepository;
    public MemoViewModel(@NonNull Application application) {
        super(application);
        memoRepository=new MemoRepository(application);
    }
    LiveData<List<Memo>>getAllMemosLive(){
        return memoRepository.getAllMemosLive();
    }
    LiveData<List<Memo>> findMemoWithPattern(String pattern){
        return memoRepository.findMemoWithPattern(pattern);
    }
    void insertMemo(Memo... memos){
        memoRepository.insertMemo(memos);
    }

    void updateMemo(Memo... memos){
        memoRepository.updateMemo(memos);
    }
    void deleteMemo(Memo... memos){
        memoRepository.deleteMemo(memos);
    }
}
