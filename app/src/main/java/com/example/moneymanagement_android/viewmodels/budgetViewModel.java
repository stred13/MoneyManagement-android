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

public class budgetViewModel extends AndroidViewModel {

    private budgetRepository bRepository;
    private MutableLiveData<List<budget>> listBudget;
    private LiveData<List<budget>> l ;

    public budgetViewModel(@NonNull Application application) {
        super(application);
        bRepository = new budgetRepository(application);
        Log.d("b", "budgetViewModel: " + bRepository.getlistBudget().size());
        listBudget = new MutableLiveData<>();
        if (bRepository.getlistBudget() != null) {
            listBudget.setValue(bRepository.getlistBudget());
        } else
            listBudget = new MutableLiveData<>();
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

    public MutableLiveData<List<budget>> getListBudget(){
        return this.listBudget;
    }

}
