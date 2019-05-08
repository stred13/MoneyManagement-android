package com.example.moneymanagement_android.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.moneymanagement_android.infobudget;
import com.example.moneymanagement_android.models.budget;
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
        //listLiveEx = null;
    }

    public void insert(expense e){
        eRepo.insert(e);
    }

    public LiveData<List<expense>> getAllExpense() throws ExecutionException, InterruptedException {
        return this.listLiveEx;
    }

    public LiveData<List<expense>> getAllExpensebyBudget(int id) throws ExecutionException, InterruptedException {
        //this.listLiveEx = eRepo.getAllExpensebyBudget(id);
        return eRepo.getAllExpensebyBudget(id);
    }

    public LiveData<List<expense>> getAllExpenseByDate(String time) throws ExecutionException, InterruptedException {
        return eRepo.getAllExpenseByDate(time);
    }
}
