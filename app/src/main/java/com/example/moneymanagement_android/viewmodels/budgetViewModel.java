package com.example.moneymanagement_android.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.repositories.budgetRepository;

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

    public void insertBudget(budget b){

        bRepository.insertBudget(b);
    }

    public void updateBudget(budget b){
        bRepository.updateBudget(b);
    }

    public void deleteBudget(budget b){
        bRepository.deleteBudget(b);
    }

    public LiveData<List<budget>> getListBudget() throws ExecutionException, InterruptedException {
        //this.listBudget.setValue(bRepository.getlistBudget());
        return  this.listBudget;
    }

}
