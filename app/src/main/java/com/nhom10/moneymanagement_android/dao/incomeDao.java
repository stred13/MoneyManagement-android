package com.nhom10.moneymanagement_android.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nhom10.moneymanagement_android.models.income;

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

    @Query("Select * from income Where idbudget = :id")
    LiveData<List<income>> getAllIncomebyBudget(int id);

    @Query("Select id, name, sum(nmoney) as nmoney , dcreated, note, idcatin, idbudget from income Where CAST(dcreated as INT) / 1000 >= CAST(strftime('%s', date(:time,'start of month')) AS INT)" +
            "AND CAST(dcreated as INT) / 1000 < CAST(strftime('%s', date(:time,'start of month', '+1 month')) AS INT)" +
            "Group by idcatin ORDER by nmoney DESC")
    LiveData<List<income>> getIncomeByDate(String time);

    @Query("Select id, name, sum(nmoney) as nmoney , dcreated, note, idcatin, idbudget from income Where CAST(dcreated as INT) / 1000 >= CAST(strftime('%s', date(:start)) AS INT)" +
            "AND CAST(dcreated as INT) / 1000 < CAST(strftime('%s', date(:end)) AS INT)" +
            "Group by idcatin ORDER by nmoney DESC")
    LiveData<List<income>> getIncomeInRange(String start, String end);

    @Query("Select * from income Where CAST(dcreated as INT) / 1000 < CAST(strftime('%s', date(:time,'start of month')) AS INT)" +
            "ORDER by nmoney DESC")
    LiveData<List<income>> getIncomeBeforeDate(String time);

    @Query("Select id, name, sum(nmoney) as nmoney , dcreated, note, idcatin, idbudget " +
            "from income Where CAST(dcreated as INT) / 1000 >= CAST(strftime('%s', date(:timeFrom,'+0 day')) AS INT)" +
            "AND CAST(dcreated as INT) / 1000 < CAST(strftime('%s',date(:timeTo,'+1 day')) AS INT)" +
            "AND idbudget = :id Group by idcatin ORDER by nmoney DESC ")
    LiveData<List<income>> getincomeByDateBudget(String timeFrom, String timeTo, int id);

    @Query("Select * " +
            "from income Where CAST(dcreated as INT) / 1000 >= CAST(strftime('%s', date(:timeFrom,'+0 day')) AS INT)" +
            "AND CAST(dcreated as INT) / 1000 < CAST(strftime('%s',date(:timeTo,'+1 day')) AS INT)" +
            "AND idbudget = :id ORDER by dcreated DESC ")
    LiveData<List<income>> getIncomeBudgetRangeTime(String timeFrom,String timeTo, int id);
}
