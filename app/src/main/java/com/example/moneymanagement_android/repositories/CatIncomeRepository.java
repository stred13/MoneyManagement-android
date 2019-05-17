package com.example.moneymanagement_android.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.moneymanagement_android.dao.CatExpenseDAO;
import com.example.moneymanagement_android.dao.CatIncomeDAO;
import com.example.moneymanagement_android.datamanagers.roomDatabase;
import com.example.moneymanagement_android.models.catexpense;
import com.example.moneymanagement_android.models.catincome;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CatIncomeRepository {

    private CatIncomeDAO catIncomeDAO;
    private LiveData<List<catincome>> listLiveData;

    public CatIncomeRepository(Application application) {
        roomDatabase rtdb = roomDatabase.getInstance(application);
        catIncomeDAO = rtdb.catIncomeDAO();
        this.catIncomeDAO = rtdb.catIncomeDAO();
    }

    public LiveData<List<catincome>> getAllCatIncome() throws ExecutionException, InterruptedException {
        return new getLiveListCatIncomeAsynctask(this.catIncomeDAO).execute().get();
    }

    public void insertCatIncome(catincome c) {
        new InsertCatIncomeAsyncTask(catIncomeDAO).execute(c);
    }

    public void deleteCatIncome(catincome b){
        new deleteCatIncomeAsyncTask(catIncomeDAO).execute(b);
    }

    public void updateCatIncome(catincome b){
        new updateCatIncomeAsyncTask(catIncomeDAO).execute(b);
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

    private static class deleteCatIncomeAsyncTask extends AsyncTask<catincome,Void,Void>{
        private CatIncomeDAO bAsyncTaskDao;
        private deleteCatIncomeAsyncTask(CatIncomeDAO bdDao){
            bAsyncTaskDao = bdDao;
        }

        @Override
        protected Void doInBackground(catincome... catincomes) {
            bAsyncTaskDao.delete(catincomes[0]);
            return null;
        }
    }

    private static class updateCatIncomeAsyncTask extends AsyncTask<catincome,Void,Void>{
        private CatIncomeDAO bAsyncTaskDao;
        private updateCatIncomeAsyncTask(CatIncomeDAO bdDao){
            bAsyncTaskDao = bdDao;
        }

        @Override
        protected Void doInBackground(catincome... catincomes) {
            bAsyncTaskDao.update(catincomes[0]);
            return null;
        }
    }


    private static class getLiveListCatIncomeAsynctask extends AsyncTask<Void,Void,LiveData<List<catincome>>>{
        private CatIncomeDAO dao;

        public getLiveListCatIncomeAsynctask(CatIncomeDAO dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<List<catincome>> doInBackground(Void... voids) {
            // Log.d("xyc", "doInBackground: "+Thread.currentThread().getName());
            return dao.getListCatIncome();
        }

        @Override
        protected void onPostExecute(LiveData<List<catincome>> listLiveDataData) {
            super.onPostExecute(listLiveDataData);
        }
    }
}
