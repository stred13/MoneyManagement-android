package com.nhom10.moneymanagement_android.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.nhom10.moneymanagement_android.models.budget;
import com.nhom10.moneymanagement_android.repositories.budgetRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class budgetViewModel extends AndroidViewModel {

    private budgetRepository bRepository;
    private LiveData<List<budget>> listBudget;

    public budgetViewModel(@NonNull Application application) throws ExecutionException, InterruptedException {
        super(application);
        bRepository = new budgetRepository(application);
        listBudget = bRepository.getAllbudget();
    }

    public void insertBudget(budget b) {
        bRepository.insertBudget(b);
    }

    public void updateBudget(budget b) {
        bRepository.updateBudget(b);
    }

    public void deleteBudget(budget b) {
        bRepository.deleteBudget(b);
    }

    public LiveData<budget> getBudgetbyId(int id) throws ExecutionException, InterruptedException {
        return this.bRepository.getBudgetbyID(id);
    }

    public LiveData<List<budget>> getListBudget() throws ExecutionException, InterruptedException {
        //this.listBudget.setValue(bRepository.getlistBudget());
        return this.listBudget;
    }
}
