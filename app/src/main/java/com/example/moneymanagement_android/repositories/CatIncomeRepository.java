package com.example.moneymanagement_android.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.moneymanagement_android.dao.CatIncomeDAO;
import com.example.moneymanagement_android.datamanagers.roomDatabase;
import com.example.moneymanagement_android.models.catincome;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CatIncomeRepository {

    private CatIncomeDAO catIncomeDAO;
    private LiveData<List<catincome>> listLiveData;

    public CatIncomeRepository(Application application) {
        roomDatabase rtdb = roomDatabase.getInstance(application);
        this.catIncomeDAO = rtdb.catIncomeDAO();
    }

    public void insertCatIncome(catincome c) {

    }

    public LiveData<List<catincome>> getAllCatIncome() throws ExecutionException, InterruptedException {
        return new GetAllCatIncomeAsyncTask(this.catIncomeDAO).execute().get();
    }

    private static class GetAllCatIncomeAsyncTask extends AsyncTask<Void, Void, LiveData<List<catincome>>> {

        private CatIncomeDAO catIncomeDAO;

        public GetAllCatIncomeAsyncTask(CatIncomeDAO catIncomeDAO) {
            this.catIncomeDAO = catIncomeDAO;
        }

        @Override
        protected LiveData<List<catincome>> doInBackground(Void... voids) {
            return catIncomeDAO.getListCatIncome();
        }

        @Override
        protected void onPostExecute(LiveData<List<catincome>> listLiveData) {
            super.onPostExecute(listLiveData);
        }
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
