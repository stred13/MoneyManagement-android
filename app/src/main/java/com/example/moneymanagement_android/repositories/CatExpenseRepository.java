package com.example.moneymanagement_android.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.moneymanagement_android.dao.CatExpenseDAO;
import com.example.moneymanagement_android.dao.budgetDao;
import com.example.moneymanagement_android.datamanagers.roomDatabase;
import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.models.catexpense;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CatExpenseRepository {
    private CatExpenseDAO catExpenseDAO;
    private LiveData<List<catexpense>> listLiveData;

    public CatExpenseRepository(Application application) {
        roomDatabase rtdb = roomDatabase.getInstance(application);
        catExpenseDAO = rtdb.catExpenseDAO();
        this.catExpenseDAO = rtdb.catExpenseDAO();
    }

    public LiveData<List<catexpense>> getAllCatExpense() throws ExecutionException, InterruptedException {
        return new getLiveListCatExpenseAsynctask(this.catExpenseDAO).execute().get();
    }
    public void insertCatExpense(catexpense c) {
        new InsertCatExpenseAsyncTask(catExpenseDAO).execute(c);
    }

    private static class GetAllCatExpenseAsyncTask extends AsyncTask<Void, Void, LiveData<List<catexpense>>> {

        private CatExpenseDAO catExpenseDAO;

        public GetAllCatExpenseAsyncTask(CatExpenseDAO catExpenseDAO) {
            this.catExpenseDAO = catExpenseDAO;
        }

        @Override
        protected LiveData<List<catexpense>> doInBackground(Void... voids) {
            return catExpenseDAO.getListCatExpense();
        }

        @Override
        protected void onPostExecute(LiveData<List<catexpense>> listLiveData) {
            super.onPostExecute(listLiveData);
        }
    }

    public void deleteCatExpense(catexpense b){
        new deleteCatExpenseAsyncTask(catExpenseDAO).execute(b);
    }

    public void updateCatExpense(catexpense b){
        new updateCatExpenseAsyncTask(catExpenseDAO).execute(b);
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

    private static class deleteCatExpenseAsyncTask extends AsyncTask<catexpense,Void,Void>{
        private CatExpenseDAO bAsyncTaskDao;
        private deleteCatExpenseAsyncTask(CatExpenseDAO bdDao){
            this.bAsyncTaskDao = bdDao;
        }

        @Override
        protected Void doInBackground(catexpense... catexpenses) {
            bAsyncTaskDao.delete(catexpenses[0]);
            return null;
        }
    }

    private static class updateCatExpenseAsyncTask extends AsyncTask<catexpense,Void,Void>{
        private CatExpenseDAO bAsyncTaskDao;
        private updateCatExpenseAsyncTask(CatExpenseDAO bdDao){
            bAsyncTaskDao = bdDao;
        }

        @Override
        protected Void doInBackground(catexpense... catexpenses) {
            bAsyncTaskDao.update(catexpenses[0]);
            return null;
        }
    }


    private static class getLiveListCatExpenseAsynctask extends AsyncTask<Void,Void,LiveData<List<catexpense>>>{
        private CatExpenseDAO dao;

        public getLiveListCatExpenseAsynctask(CatExpenseDAO dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<List<catexpense>> doInBackground(Void... voids) {

            return dao.getListCatExpense();
        }

        @Override
        protected void onPostExecute(LiveData<List<catexpense>> listLiveDataData) {
            super.onPostExecute(listLiveDataData);
        }
    }
}
