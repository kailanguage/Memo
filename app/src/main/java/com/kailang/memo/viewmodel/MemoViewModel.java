package com.kailang.memo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kailang.memo.data.Memo;
import com.kailang.memo.data.MemoRepository;

import java.util.List;

public class MemoViewModel extends AndroidViewModel {
    private MemoRepository memoRepository;
    public MemoViewModel(@NonNull Application application) {
        super(application);
        memoRepository=new MemoRepository(application);
    }
    public LiveData<List<Memo>>getAllMemosLive(){
        return memoRepository.getAllMemosLive();
    }
    public LiveData<List<Memo>> findMemoWithPattern(String pattern){
        return memoRepository.findMemoWithPattern(pattern);
    }
    public void insertMemo(Memo... memos){
        memoRepository.insertMemo(memos);
    }

    public void updateMemo(Memo... memos){
        memoRepository.updateMemo(memos);
    }
    public void deleteMemo(Memo... memos){
        memoRepository.deleteMemo(memos);
    }
}
