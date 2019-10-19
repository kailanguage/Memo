package com.kailang.memo.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.kailang.memo.data.Memo;
import com.kailang.memo.data.MemoDao;
import com.kailang.memo.data.MemoDatabase;

import java.util.List;

public class MemoRepository {
    private LiveData<List<Memo>> allMemosLive;
    private MemoDao memoDao;

    public MemoRepository(Context context){
        MemoDatabase memoDatabase = MemoDatabase.getDatabase(context.getApplicationContext());
        memoDao = memoDatabase.getMemoDao();
        allMemosLive = memoDao.getAllMemosLive();
    }

   public void insertMemo(Memo... memos){
        new InsertAsyncTask(memoDao).execute(memos);
    }

    public void updateMemo(Memo... memos){
        new UpdateAsyncTask(memoDao).execute(memos);
    }
    public void deleteMemo(Memo... memos){
        new DeleteAsyncTask(memoDao).execute(memos);
    }
    public LiveData<List<Memo>> getAllMemosLive(){
        return allMemosLive;
    }
    public LiveData<List<Memo>>findMemoWithPattern(String pattern){
        return memoDao.findWordsWithPattern("%" + pattern + "%");
    }

    static class InsertAsyncTask extends AsyncTask<Memo,Void,Void> {
        private MemoDao memoDao;
        public InsertAsyncTask(MemoDao memoDao) {
            this.memoDao=memoDao;
        }

        @Override
        protected Void doInBackground(Memo... memos) {
            memoDao.insertMemos(memos);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Memo,Void,Void>{
        private MemoDao memoDao;
        public UpdateAsyncTask(MemoDao memoDao) {
            this.memoDao=memoDao;
        }

        @Override
        protected Void doInBackground(Memo... memos) {
            memoDao.updateMemos(memos);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<Memo,Void,Void>{
        private MemoDao memoDao;
        public DeleteAsyncTask(MemoDao memoDao) {
            this.memoDao=memoDao;
        }

        @Override
        protected Void doInBackground(Memo... memos) {
            memoDao.deleteMemos(memos);
            return null;
        }
    }
}
