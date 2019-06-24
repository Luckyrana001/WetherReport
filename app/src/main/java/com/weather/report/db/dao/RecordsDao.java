package com.weather.report.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.weather.report.db.entity.RecordsDataDao;

import java.util.List;

@Dao
public interface RecordsDao {
    @Query("SELECT * FROM RecordsDataDao")
    List<RecordsDataDao> getAll();

    @Insert
    Long[] insertAll(RecordsDataDao... users);

    @Delete
    void delete(RecordsDataDao user);

    @Query("DELETE FROM RecordsDataDao")
    void deleteAll();
}