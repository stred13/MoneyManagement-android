package com.example.moneymanagement_android.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.moneymanagement_android.dao.incomeDao;
import com.example.moneymanagement_android.datamanagers.roomDatabase;
import com.example.moneymanagement_android.models.income;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class IncomeRepository {
    private incomeDao incomeDao;
    private LiveData<List<income>> listIncome;

    public IncomeRepository(Application application) {
        roomDatabase rdtb = roomDatabase.getInstance(application.getApplicationContext());
        this.incomeDao = rdtb.icDao();
    }

    public LiveData<List<income>> getAllIncomeByDate(String time) throws ExecutionException, InterruptedException {
        LiveData<List<income>> listLiveData = new getAllIncomeByDateAsyncTask(incomeDao).execute(time).get();
        return listLiveData;
    }

    private static class getAllIncomeByDateAsyncTask extends AsyncTask<String, Void, LiveData<List<income>>> {

        private incomeDao incomeDao;

        public getAllIncomeByDateAsyncTask(incomeDao incomeDao) {
            this.incomeDao = incomeDao;
        }

        @Override
        protected LiveData<List<income>> doInBackground(String... strings) {
            return incomeDao.getIncomeByDate(strings[0]);
        }

        @Override
        protected void onPostExecute(LiveData<List<income>> listLiveData) {
            super.onPostExecute(listLiveData);
        }
    }
}
