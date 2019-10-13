package com.kailang.memo;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

class MemoRepository {
    private LiveData<List<Memo>> allMemosLive;
    private MemoDao memoDao;

    MemoRepository(Context context){
        MemoDatabase memoDatabase = MemoDatabase.getDatabase(context.getApplicationContext());
        memoDao = memoDatabase.getMemoDao();
        allMemosLive = memoDao.getAllMemosLive();
    }

    void insertMemo(Memo... memos){
        new InsertAsyncTask(memoDao).execute(memos);
    }

    void updateMemo(Memo... memos){
        new UpdateAsyncTask(memoDao).execute(memos);
    }
    void deleteMemo(Memo... memos){
        new DeleteAsyncTask(memoDao).execute(memos);
    }
    LiveData<List<Memo>> getAllMemosLive(){
        return allMemosLive;
    }

    static class InsertAsyncTask extends AsyncTask<Memo,Void,Void> {
        private MemoDao memoDao;
        public InsertAsyncTask(MemoDao memoDao) {
            Log.e("xxxxx","repositoryASyncTask");
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
