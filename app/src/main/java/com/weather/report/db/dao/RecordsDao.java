package com.weather.report.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.weather.report.db.entity.RecordsDataDao;
import com.weather.report.model.WeatherApiDataResponseModel;

import java.util.List;

@Dao
public interface RecordsDao {
    @Query("SELECT * FROM RecordsDataDao")
    List<RecordsDataDao> getAll();

    /*@Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);*/

    /*@Query("SELECT * FROM user WHERE first_name LIKE :first AND "
           + "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);*/

    @Insert
    void insertAll(RecordsDataDao... users);

    @Delete
    void delete(RecordsDataDao user);

    @Query("DELETE FROM RecordsDataDao")
    void deleteAll();
}