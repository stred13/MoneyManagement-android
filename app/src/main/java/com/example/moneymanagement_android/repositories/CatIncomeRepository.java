package com.example.moneymanagement_android.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.moneymanagement_android.dao.CatIncomeDAO;
import com.example.moneymanagement_android.datamanagers.roomDatabase;
import com.example.moneymanagement_android.models.catincome;

import java.util.List;

public class CatIncomeRepository {

    private CatIncomeDAO catIncomeDAO;
    private LiveData<List<catincome>> listLiveData;

    public CatIncomeRepository(Application application) {
        roomDatabase rtdb = roomDatabase.getInstance(application);
        this.catIncomeDAO = rtdb.catIncomeDAO();
    }

    public void insertCatIncome(catincome c) {

    }

    private static class InsertCatIncomeAsyncTask extends AsyncTask<catincome, Void, Void> {

        private CatIncomeDAO catIncomeDAO;

        public InsertCatIncomeAsyncTask(CatIncomeDAO catIncomeDAO) {
            this.catIncomeDAO = catIncomeDAO;
        }

        @Override
        protected Void doInBackground(catincome... catincomes) {
            catIncomeDAO.insert(catincomes[0]);
            return null;
        }
    }
}
