package com.example.moneymanagement_android.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.example.moneymanagement_android.models.catincome;

@Dao
public interface CatIncomeDAO {

    @Insert
    void insert(catincome catincome);
}
