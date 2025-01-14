package com.example.movie;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DataClass.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase sInstance;
    public abstract DAO dao();
    public static void clearInstance(Context context) {
        if (sInstance != null) {
            sInstance.close();
            sInstance = null;
            context.deleteDatabase("AppDatabase");
        }
    }
}
