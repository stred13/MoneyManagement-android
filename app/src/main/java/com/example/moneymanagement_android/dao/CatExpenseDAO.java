package com.example.moneymanagement_android.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.moneymanagement_android.models.catexpense;

import java.util.List;

@Dao
public interface CatExpenseDAO {
    @Insert
    void insert(catexpense exp);

    @Query("Select * from catexpense")
    LiveData<List<catexpense>> getListCatExpense();
}
