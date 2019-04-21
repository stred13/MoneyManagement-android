package com.example.moneymanagement_android.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

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
        listBudget = bRepository.lBudget;
       // l = Transformations.switchMap((LiveData<List<budget>>)listBudget,l->bRepository.getlistBudget());
        //Toast.makeText(getApplication(), "list trans: "+l.getValue().size(), Toast.LENGTH_SHORT).show();
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
