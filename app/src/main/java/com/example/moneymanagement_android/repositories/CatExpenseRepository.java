package com.example.moneymanagement_android.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.moneymanagement_android.dao.CatExpenseDAO;
import com.example.moneymanagement_android.datamanagers.roomDatabase;
import com.example.moneymanagement_android.models.catexpense;

import java.util.List;

public class CatExpenseRepository {
    private CatExpenseDAO catExpenseDAO;
    private LiveData<List<catexpense>> listLiveData;

    public CatExpenseRepository(Application application) {
        roomDatabase rtdb = roomDatabase.getInstance(application);
        this.catExpenseDAO = rtdb.catExpenseDAO();
    }

    public void insertCatExpense(catexpense c) {
        new InsertCatExpenseAsyncTask(catExpenseDAO).execute(c);
    }

    private static class InsertCatExpenseAsyncTask extends AsyncTask<catexpense, Void, Void> {

        private CatExpenseDAO catExpenseDAO;

        public InsertCatExpenseAsyncTask(CatExpenseDAO catExpenseDAO) {
            this.catExpenseDAO = catExpenseDAO;
        }

        @Override
        protected Void doInBackground(catexpense... catexpenses) {
            catExpenseDAO.insert(catexpenses[0]);
            return null;
        }
    }
}
