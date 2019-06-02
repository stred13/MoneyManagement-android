package com.example.moneymanagement_android.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.models.catexpense;
import com.example.moneymanagement_android.models.expense;
import com.example.moneymanagement_android.repositories.CatExpenseRepository;

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

    public void updateCatExpense(catexpense b) {
        catExpenseRepository.updateCatExpense(b);
    }

    public void deleteCatExpense(catexpense b) {
        catExpenseRepository.deleteCatExpense(b);
    }

    public LiveData<List<catexpense>> getAllCatExpense() throws ExecutionException, InterruptedException {
        return this.listLiveData;
    }
    public boolean checkCatExpenseByName(String name) throws ExecutionException, InterruptedException {
        return this.catExpenseRepository.checkCatExpenseByName(name);
    }
}
