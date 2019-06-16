package com.nhom10.moneymanagement_android.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.nhom10.moneymanagement_android.models.expense;
import com.nhom10.moneymanagement_android.repositories.expenseRepository;

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

    public void delete(expense e){
        eRepo.delelte(e);
    }

    public void update(expense e){
        Log.d("", "update: "+e.getId()+" "+e.getNmoney());
        eRepo.update(e);
    }

    public LiveData<List<expense>> getAllExpense() throws ExecutionException, InterruptedException {
        return eRepo.getAllExpense();
    }

    public LiveData<List<expense>> getAllExpensebyBudget(int id) throws ExecutionException, InterruptedException {
        //this.listLiveEx = eRepo.getAllExpensebyBudget(id);
        return eRepo.getAllExpensebyBudget(id);
    }

    public LiveData<List<expense>> getAllExpenseByDate(String time) throws ExecutionException, InterruptedException {
        return eRepo.getAllExpenseByDate(time);
    }

    public LiveData<List<expense>> getAllExpenseBeforeDate(String time) throws ExecutionException, InterruptedException {
        return eRepo.getAllExpenseBeforeDate(time);
    }

    public LiveData<List<expense>> getAllExpenseByDateBudget(String timeFrom,String timeTo, int id) throws ExecutionException, InterruptedException {
        return eRepo.getAllExpenseByDateBudget(timeFrom, timeTo, id);
    }

    public LiveData<List<expense>> getAllExpenseBudgetRangeTime(String timeFrom, String timeTo, int id) throws ExecutionException, InterruptedException {
        return eRepo.getAllExpenseBudgetRangeTime(timeFrom, timeTo, id);
    }
}
