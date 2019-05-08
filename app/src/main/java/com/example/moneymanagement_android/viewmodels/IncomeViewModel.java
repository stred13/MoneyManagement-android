package com.example.moneymanagement_android.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.moneymanagement_android.models.income;
import com.example.moneymanagement_android.repositories.IncomeRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class IncomeViewModel extends AndroidViewModel {

    private IncomeRepository incomeRepository;
    private LiveData<List<income>> listLiveData;

    public IncomeViewModel(@NonNull Application application) {
        super(application);
        incomeRepository = new IncomeRepository(application);
    }

    public LiveData<List<income>> getAllIncomeByDate(String time) throws ExecutionException, InterruptedException {
        return incomeRepository.getAllIncomeByDate(time);
    }
}
