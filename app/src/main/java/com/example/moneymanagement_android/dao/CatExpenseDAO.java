package com.example.moneymanagement_android.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.example.moneymanagement_android.models.catexpense;

@Dao
public interface CatExpenseDAO {
    @Insert
    void insert(catexpense exp);

    //LiveData<catincome> getCatIncomeById(String id);
}
