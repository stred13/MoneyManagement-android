package com.example.moneymanagement_android.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.moneymanagement_android.models.income;

import java.util.List;

@Dao
public interface incomeDao {
    @Insert
    void insert(income i);

    @Delete
    void delete(income i);

    @Update
    void update(income i);

    @Query("Select * From income")
    LiveData<List<income>> getListincome();

    @Query("Select * from income Where CAST(dcreated as INT) / 1000 >= CAST(strftime('%s', date(:time,'start of month')) AS INT)" +
            "AND CAST(dcreated as INT) / 1000 < CAST(strftime('%s', date(:time,'start of month', '+1 month')) AS INT)" +
            "ORDER by nmoney DESC")
    LiveData<List<income>> getIncomeByDate(String time);

}
