package com.example.moneymanagement_android.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.moneymanagement_android.models.catexpense;
import com.example.moneymanagement_android.models.catincome;
import com.example.moneymanagement_android.repositories.CatExpenseRepository;
import com.example.moneymanagement_android.repositories.CatIncomeRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CatIncomeViewModel extends AndroidViewModel {

    private CatIncomeRepository catIncomeRepository;
    private LiveData<List<catincome>> listLiveData;

    public CatIncomeViewModel(@NonNull Application application) {
        super(application);
        catIncomeRepository = new CatIncomeRepository(application);
    }

    public void insertCatIncome(catincome c) {
        catIncomeRepository.insertCatIncome(c);
    }

    public LiveData<List<catincome>> getAllCatIncome() throws ExecutionException, InterruptedException {
        return catIncomeRepository.getAllCatIncome();
    }
}
