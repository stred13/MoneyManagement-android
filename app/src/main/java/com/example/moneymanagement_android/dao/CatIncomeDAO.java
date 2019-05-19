package com.example.moneymanagement_android.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.moneymanagement_android.models.catincome;

import java.util.List;

@Dao
public interface CatIncomeDAO {

    @Insert
    void insert(catincome catincome);

    @Query("Select * from catincome")
    LiveData<List<catincome>> getListCatIncome();
}
