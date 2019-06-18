package com.nhom10.moneymanagement_android.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nhom10.moneymanagement_android.models.income;
import com.nhom10.moneymanagement_android.repositories.IncomeRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class IncomeViewModel extends AndroidViewModel {

    private IncomeRepository incomeRepository;
    private LiveData<List<income>> listLiveData;

    public IncomeViewModel(@NonNull Application application) {
        super(application);
        incomeRepository = new IncomeRepository(application);
    }

    public void insert(income in){
        incomeRepository.insert(in);
    }

    public void update(income in ){
        incomeRepository.update(in);
    }

    public void delete(income e){
        incomeRepository.delete(e);
    }

    public LiveData<List<income>> getAllIncome() throws ExecutionException, InterruptedException {
        return incomeRepository.getAllIncome();
    }

    public LiveData<List<income>> getAllIncomebyBudget(int id) throws ExecutionException, InterruptedException {
        return incomeRepository.getAllIncomebyBudget(id);
    }

    public LiveData<List<income>> getAllIncomeByDate(String time) throws ExecutionException, InterruptedException {
        return incomeRepository.getAllIncomeByDate(time);
    }

    public LiveData<List<income>> getAllIncomeInRange(String start, String end) throws ExecutionException, InterruptedException {
        return incomeRepository.getAllIncomeInRange(start, end);
    }

    public LiveData<List<income>> getAllIncomeBeforeDate(String time) throws ExecutionException, InterruptedException {
        return incomeRepository.getAllIncomeBeforeDate(time);
    }

    public LiveData<List<income>> getAllIncomeByDateBudget(String timeFrom, String timeTo, int id) throws ExecutionException, InterruptedException {
        return incomeRepository.getAllIncomeByDateBudget(timeFrom, timeTo, id);
    }

    public LiveData<List<income>> getAllIncomeBudgetRangeTime(String timeFrom, String timeTo, int id) throws ExecutionException, InterruptedException {
        return incomeRepository.getAllIncomeBudgetRangeTime(timeFrom, timeTo, id);
    }

    public List<income> getListIncomeByCatId(int id) throws ExecutionException, InterruptedException {
        return incomeRepository.getListIncomeByCatId(id);
    }

}
