package com.example.moneymanagement_android.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.moneymanagement_android.models.budget;

import java.util.List;

@Dao
public interface budgetDao{

    @Insert
    void insert(budget b);

    @Update
    void update(budget b);

    @Delete
    void delete(budget b);

    @Query("SELECT * FROM budget")
    LiveData<List<budget>> getListBudget();

}
