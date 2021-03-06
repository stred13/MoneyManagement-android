package com.nhom10.moneymanagement_android.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.nhom10.moneymanagement_android.dao.budgetDao;
import com.nhom10.moneymanagement_android.datamanagers.roomDatabase;
import com.nhom10.moneymanagement_android.models.budget;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class budgetRepository {
    private budgetDao bdDao;
    private LiveData<List<budget>> lBudget;
    private LiveData<budget> b;
    public budgetRepository(Application application){
        roomDatabase rdtb = roomDatabase.getInstance(application.getApplicationContext());
        bdDao = rdtb.bdDao();
        this.lBudget = bdDao.getListBudget();
    }

    public List<budget> getListbudgetByName(String name) throws ExecutionException, InterruptedException {
        return new getListbudgetByNameAsynctask(this.bdDao).execute(name).get();
    }

    public LiveData<List<budget>> getAllbudget() throws ExecutionException, InterruptedException {
        return new getLiveListBudgetAsynctask(this.bdDao).execute().get();
    }

    public void insertBudget(budget b){
        new insertBudgetAsyncTask(bdDao).execute(b);
    }

    public void deleteBudget(budget b){
        new deleteBudgetAsyncTask(bdDao).execute(b);
    }

    public void updateBudget(budget b){
        new updateBudgetAsyncTask(bdDao).execute(b);
    }

    public LiveData<budget> getBudgetbyID(int id) throws ExecutionException, InterruptedException {
        return new getBudgetbyIdAsyntask(this.bdDao).execute(id).get();
    }

    private static class getBudgetbyIdAsyntask extends AsyncTask<Integer,Void,LiveData<budget>>{
        private budgetDao bDao;

        public getBudgetbyIdAsyntask(budgetDao bDao) {
            this.bDao = bDao;
        }

        @Override
        protected LiveData<budget> doInBackground(Integer... integers) {
            return bDao.getBudgetbyId(integers[0]);
        }

        @Override
        protected void onPostExecute(LiveData<budget> budget) {
            super.onPostExecute(budget);
        }
    }

    private static class insertBudgetAsyncTask extends AsyncTask<budget,Void,Void> {
        private budgetDao bAsyncTaskDao;

        private insertBudgetAsyncTask(budgetDao bdDao) {
            this.bAsyncTaskDao = bdDao;
        }

        @Override
        protected Void doInBackground(budget... budgets) {
            bAsyncTaskDao.insert(budgets[0]);
            return null;
        }
    }

    private static class deleteBudgetAsyncTask extends AsyncTask<budget,Void,Void>{
        private budgetDao bAsyncTaskDao;
        private deleteBudgetAsyncTask(budgetDao bdDao){
            bAsyncTaskDao = bdDao;
        }

        @Override
        protected Void doInBackground(budget... budgets) {
            bAsyncTaskDao.delete(budgets[0]);
            return null;
        }
    }

    private static class updateBudgetAsyncTask extends AsyncTask<budget,Void,Void>{
        private budgetDao bAsyncTaskDao;
        private updateBudgetAsyncTask(budgetDao bdDao){
            bAsyncTaskDao = bdDao;
        }

        @Override
        protected Void doInBackground(budget... budgets) {
            bAsyncTaskDao.update(budgets[0]);
            return null;
        }
    }

    private static class getLiveListBudgetAsynctask extends AsyncTask<Void,Void,LiveData<List<budget>>>{
        private budgetDao dao;

        public getLiveListBudgetAsynctask(budgetDao dao) {
            this.dao = dao;
        }

        @Override
        protected LiveData<List<budget>> doInBackground(Void... voids) {
           // Log.d("xyc", "doInBackground: "+Thread.currentThread().getName());
            return dao.getListBudget();
        }

        @Override
        protected void onPostExecute(LiveData<List<budget>> listLiveDataData) {
            super.onPostExecute(listLiveDataData);
        }
    }

    private static class getListbudgetByNameAsynctask extends AsyncTask<String,Void,List<budget>>{
        private budgetDao dao;

        public getListbudgetByNameAsynctask(budgetDao dao) {
            this.dao = dao;
        }

        @Override
        protected List<budget> doInBackground(String... strings) {
            return dao.getBudgetByName(strings[0]);
        }

        @Override
        protected void onPostExecute(List<budget> budgets) {
            super.onPostExecute(budgets);
        }
    }

}
