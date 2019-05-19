package com.example.moneymanagement_android.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.moneymanagement_android.models.catexpense;
import com.example.moneymanagement_android.models.catincome;

import java.util.List;

@Dao
public interface CatIncomeDAO {

    @Insert
    void insert(catincome catincome);

    @Update
    void update(catincome catincome);

    @Delete
    void delete(catincome catincome);

    @Query("SELECT * FROM catincome")
    LiveData<List<catincome>> getListCatIncome();
}
