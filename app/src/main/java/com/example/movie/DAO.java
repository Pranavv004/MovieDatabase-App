package com.example.movie;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DAO {

    @Insert
    void insertAll(DataClass... dataClasses);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(DataClass dataClass);

    @Delete
    void delete(DataClass dataClass);

    @Query("SELECT * FROM data")
    List<DataClass> getAll();

    @Query("SELECT added FROM data WHERE key = :id")
    boolean getDataById(int id);

    @Query("SELECT * FROM data WHERE key = :id")
    DataClass checkduplicateById(int id);

    @Query("DELETE FROM data WHERE key = :id")
    void deleteDataById(int id);

}
