package com.kailang.memo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao //Database access object
public interface MemoDao {
    @Insert
    void insertMemos(Memo... memos);

    @Update
    void updateMemos(Memo... memos);

    @Delete
    void deleteMemos(Memo... memos);

    @Query("SELECT * FROM MEMO ORDER BY ID DESC")
    LiveData<List<Memo>>getAllMemosLive();
}
