package com.example.moneymanagement_android.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.moneymanagement_android.dao.expenseDao;
import com.example.moneymanagement_android.datamanagers.roomDatabase;
import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.models.expense;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class expenseRepository {

    private expenseDao exDao;
    private LiveData<List<expense>> listExpense;
    public expenseRepository(Application application) {
        roomDatabase rdtb = roomDatabase.getInstance(application.getApplicationContext());
        this.exDao = rdtb.exDao();
        this.listExpense = this.exDao.getAllExpensebyBudget(1);
    }

    public LiveData<List<expense>> getAllExpense() throws ExecutionException, InterruptedException {

        return new getAllExpenseAsynctask(exDao).execute().get();
    }

    public LiveData<List<expense>> getAllExpensebyBudget(int id) throws ExecutionException, InterruptedException {
        LiveData<List<expense>> l =new getAllExpenseAsynctask(exDao).execute().get();
       // Log.d("", "getAllExpensebyBudget: "+l.getValue().li);
        return l;
    }

    public void insert(expense e){
        new insertExpenseAsyncTask(this.exDao).execute(e);
    }



    private static class insertExpenseAsyncTask extends AsyncTask<expense,Void,Void> {
        private expenseDao exDao;

        public insertExpenseAsyncTask(expenseDao exDao) {
            this.exDao = exDao;
        }

        @Override
        protected Void doInBackground(expense... expenses) {
            exDao.insert(expenses[0]);
            return null;
        }
    }

    private static class getAllExpenseAsynctask extends AsyncTask<Void,Void,LiveData<List<expense>>>{
        private expenseDao exDao;

        public getAllExpenseAsynctask(expenseDao exDao) {
            this.exDao = exDao;
        }

        @Override
        protected LiveData<List<expense>> doInBackground(Void... voids) {
            return this.exDao.getListExpense();
        }

        @Override
        protected void onPostExecute(LiveData<List<expense>> listLiveData) {
            super.onPostExecute(listLiveData);
        }
    }

    private static class getAllExpensebyBudgetAsynctask extends AsyncTask<Integer,Void,LiveData<List<expense>>>{
        private expenseDao exDao;

        public getAllExpensebyBudgetAsynctask(expenseDao exDao) {
            this.exDao = exDao;
        }

        @Override
        protected LiveData<List<expense>> doInBackground(Integer... integers) {
            return exDao.getAllExpensebyBudget(integers[0]);
        }

        @Override
        protected void onPostExecute(LiveData<List<expense>> listLiveData) {
            super.onPostExecute(listLiveData);
        }
    }
}
