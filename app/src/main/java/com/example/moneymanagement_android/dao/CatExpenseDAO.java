package com.example.moneymanagement_android.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.arch.persistence.room.Query;

import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.models.catexpense;

import java.util.List;

@Dao
public interface CatExpenseDAO {
    @Insert
    void insert(catexpense exp);

    @Update
    void update(catexpense exp);

    @Delete
    void delete(catexpense exp);

    //LiveData<catincome> getCatIncomeById(String id);
    @Query("SELECT * FROM catexpense")
    //@Query("Select * from expense Group by idcatex")
    LiveData<List<catexpense>> getListCatExpense();

    @Query("SELECT * FROM catexpense where name = :name")
    List<catexpense> getCatExpenseByName(String name);

    @Query("SELECT * FROM catexpense where id IN(:ceid)")
    catexpense getCatExpenseById(int ceid);
}
