package com.kailang.memo.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//singleton 单实例
@Database(entities = {Memo.class},version = 1,exportSchema = false)
public abstract class MemoDatabase extends RoomDatabase {
    private static MemoDatabase INSTANCE;

    static synchronized MemoDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),MemoDatabase.class,"memo_database")
                    .build();
        }
        return INSTANCE;
    }
    public abstract MemoDao getMemoDao();
}
