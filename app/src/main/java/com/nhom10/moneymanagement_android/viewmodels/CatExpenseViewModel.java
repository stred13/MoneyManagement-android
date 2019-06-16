package com.nhom10.moneymanagement_android.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nhom10.moneymanagement_android.models.catexpense;
import com.nhom10.moneymanagement_android.repositories.CatExpenseRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CatExpenseViewModel extends AndroidViewModel {

    private CatExpenseRepository catExpenseRepository;
    private LiveData<List<catexpense>> listLiveData;

    public CatExpenseViewModel(@NonNull Application application) throws ExecutionException, InterruptedException {
        super(application);
        catExpenseRepository = new CatExpenseRepository(application);
        listLiveData = catExpenseRepository.getAllCatExpense();
    }

    public void insertCatExpense(catexpense c) {
        catExpenseRepository.insertCatExpense(c);
    }

    public void updateCatExpense(catexpense catex) {
        catExpenseRepository.updateCatExpense(catex);
    }

    public void deleteCatExpense(catexpense catex) {
        catExpenseRepository.deleteCatExpense(catex);
    }

    public LiveData<List<catexpense>> getAllCatExpense() throws ExecutionException, InterruptedException {
        return this.listLiveData;
    }
    public boolean checkCatExpenseByName(String name) throws ExecutionException, InterruptedException {
        return this.catExpenseRepository.checkCatExpenseByName(name);
    }

    public catexpense getCatExpenseById(int id) throws ExecutionException, InterruptedException {
        catexpense  c = this.catExpenseRepository.getCatExpenseById(id);
        if(c==null)
            Log.d("", "getCatExpenseById: ");
        return this.catExpenseRepository.getCatExpenseById(id);
    }
}
