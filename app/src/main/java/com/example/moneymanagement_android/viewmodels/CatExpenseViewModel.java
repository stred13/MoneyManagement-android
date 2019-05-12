package com.example.moneymanagement_android.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.moneymanagement_android.models.catexpense;
import com.example.moneymanagement_android.repositories.CatExpenseRepository;

import java.util.List;

public class CatExpenseViewModel extends AndroidViewModel {

    private CatExpenseRepository catExpenseRepository;
    private LiveData<List<catexpense>> listLiveData;

    public CatExpenseViewModel(@NonNull Application application) {
        super(application);
        catExpenseRepository = new CatExpenseRepository(application);
    }

    public void insertCatExpense(catexpense c) {
        catExpenseRepository.insertCatExpense(c);
    }
}
