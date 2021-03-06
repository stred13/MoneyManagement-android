package com.nhom10.moneymanagement_android.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nhom10.moneymanagement_android.models.catincome;

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

    @Query("SELECT * FROM catincome where name = :name")
    List<catincome> getCatIncomeByName(String name);

    @Query("SELECT * FROM catincome WHERE id IN(:cinid)")
    catincome getCatIncomeById(int cinid);
}
