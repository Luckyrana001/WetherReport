package com.weather.report.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.weather.report.db.entity.RecordsDataDao;

import java.util.List;

@Dao
public interface RecordsDao {
    @Query("SELECT * FROM RecordsDataDao")
    List<RecordsDataDao> getAll();

    @Insert
    void insertAll(RecordsDataDao... users);

    @Delete
    void delete(RecordsDataDao user);

    @Query("DELETE FROM RecordsDataDao")
    void deleteAll();
}