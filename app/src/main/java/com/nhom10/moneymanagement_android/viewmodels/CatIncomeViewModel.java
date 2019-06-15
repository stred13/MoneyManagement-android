package com.nhom10.moneymanagement_android.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nhom10.moneymanagement_android.models.catincome;
import com.nhom10.moneymanagement_android.repositories.CatIncomeRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CatIncomeViewModel extends AndroidViewModel {

    private CatIncomeRepository catIncomeRepository;
    private LiveData<List<catincome>> listLiveData;

    public CatIncomeViewModel(@NonNull Application application) throws ExecutionException, InterruptedException {
        super(application);
        catIncomeRepository = new CatIncomeRepository(application);
        listLiveData = catIncomeRepository.getAllCatIncome();
    }

    public void insertCatIncome(catincome c) {
        catIncomeRepository.insertCatIncome(c);
    }

    public void updateCatIncome(catincome b) {
        catIncomeRepository.updateCatIncome(b);
    }

    public void deleteCatIncome(catincome b) {
        catIncomeRepository.deleteCatIncome(b);
    }

    public LiveData<List<catincome>> getAllCatIncome() throws ExecutionException, InterruptedException {
        return this.listLiveData;
    }
    public boolean checkCatIncomeByName(String name) throws ExecutionException, InterruptedException {
        return this.catIncomeRepository.checkCatIncomeByName(name);
    }

    public catincome getCatIncomeById(int id) throws ExecutionException, InterruptedException {
        return this.catIncomeRepository.getCatIncomeById(id);
    }
}
