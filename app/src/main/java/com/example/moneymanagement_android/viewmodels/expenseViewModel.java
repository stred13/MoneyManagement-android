package com.example.moneymanagement_android.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.moneymanagement_android.infobudget;
import com.example.moneymanagement_android.models.expense;
import com.example.moneymanagement_android.repositories.expenseRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class expenseViewModel extends AndroidViewModel {
    private expenseRepository eRepo;
    private LiveData<List<expense>> listLiveEx;
    public expenseViewModel(@NonNull Application application) throws ExecutionException, InterruptedException {
        super(application);
        eRepo = new expenseRepository(application);
        listLiveEx = eRepo.getAllExpensebyBudget(1);
    }

    public void insert(expense e){
        eRepo.insert(e);
    }

    public LiveData<List<expense>> getAllExpense() throws ExecutionException, InterruptedException {
        return eRepo.getAllExpense();
    }

    public LiveData<List<expense>> getAllExpensebyBudget(int id) throws ExecutionException, InterruptedException {
        return eRepo.getAllExpensebyBudget(id);
    }
}
