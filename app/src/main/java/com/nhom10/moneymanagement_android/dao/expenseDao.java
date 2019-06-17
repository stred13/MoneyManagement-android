package com.nhom10.moneymanagement_android.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nhom10.moneymanagement_android.models.expense;

import java.util.List;

@Dao
public interface expenseDao {
    @Insert
    void insert(expense exp);

    @Update
    void update(expense exp);

    @Delete
    void delete(expense exp);


    @Query("Select * from expense")
    LiveData<List<expense>> getListExpense();

    @Query("Select * from expense Where idbudget = :id")
    LiveData<List<expense>> getAllExpensebyBudget(int id);

    @Query("Select id, name, sum(nmoney) as nmoney , dcreated, note, idcatex, idbudget from expense Where CAST(dcreated as INT) / 1000 >= CAST(strftime('%s', date(:time,'start of month')) AS INT)" +
            "AND CAST(dcreated as INT) / 1000 < CAST(strftime('%s', date(:time,'start of month', '+1 month')) AS INT)" +
            "Group by idcatex ORDER by nmoney DESC")
    LiveData<List<expense>> getExpenseByDate(String time);

    @Query("Select id, name, sum(nmoney) as nmoney , dcreated, note, idcatex, idbudget from expense Where CAST(dcreated as INT) / 1000 >= CAST(strftime('%s', date(:start)) AS INT)" +
            "AND CAST(dcreated as INT) / 1000 <= CAST(strftime('%s', date(:end)) AS INT)" +
            "Group by idcatex ORDER by nmoney DESC")
    LiveData<List<expense>> getExpenseByRange(String start, String end);

    @Query("Select * from expense where CAST(dcreated as INT) / 1000 < CAST(strftime('%s', date(:time,'start of month')) AS INT)")
    LiveData<List<expense>> getAllExpenseBeforeDate(String time);

    @Query("Select id, name, sum(nmoney) as nmoney , dcreated, note, idcatex, idbudget " +
            "from expense Where CAST(dcreated as INT) / 1000 >= CAST(strftime('%s', date(:timeFrom,'+0 day')) AS INT)" +
            "AND CAST(dcreated as INT) / 1000 < CAST(strftime('%s',date(:timeTo,'+1 day')) AS INT)" +
            "AND idbudget = :id Group by idcatex ORDER by nmoney DESC ")
    LiveData<List<expense>> getExpenseByDateBudget(String timeFrom, String timeTo, int id);

    @Query("Select * " +
            "from expense Where CAST(dcreated as INT) / 1000 >= CAST(strftime('%s', date(:timeFrom,'+0 day')) AS INT)" +
            "AND CAST(dcreated as INT) / 1000 < CAST(strftime('%s',date(:timeTo,'+1 day')) AS INT)" +
            "AND idbudget = :id ORDER by dcreated DESC , idcatex")
    LiveData<List<expense>> getExpenseBudgetRangeTime(String timeFrom, String timeTo, int id);
}
