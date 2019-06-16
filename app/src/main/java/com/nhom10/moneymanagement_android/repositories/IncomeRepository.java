package com.nhom10.moneymanagement_android.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.nhom10.moneymanagement_android.dao.incomeDao;
import com.nhom10.moneymanagement_android.datamanagers.roomDatabase;
import com.nhom10.moneymanagement_android.models.income;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class IncomeRepository {
    private com.nhom10.moneymanagement_android.dao.incomeDao incomeDao;
    private LiveData<List<income>> listIncome;

    public IncomeRepository(Application application) {
        roomDatabase rdtb = roomDatabase.getInstance(application.getApplicationContext());
        this.incomeDao = rdtb.icDao();
    }

    public void update(income in){
        new updateIncomeAsynctask(this.incomeDao).execute(in);
    }

    public LiveData<List<income>> getAllIncome() throws ExecutionException, InterruptedException {
        return new getAllIncomeAsynctask(this.incomeDao).execute().get();
    }

    public LiveData<List<income>> getAllIncomebyBudget(int id) throws ExecutionException, InterruptedException {
        Integer in = new Integer(id);
        LiveData<List<income>> l = new getAllIncomebyBudgetAsynctask(incomeDao).execute(in).get();
        // Log.d("", "getAllExpensebyBudget: "+l.getValue().li);
        if (l.getValue() != null) {
            Log.d("listsize", "getAllExpensebyBudget: " + l.getValue().size());
        }
        return l;
    }

    public LiveData<List<income>> getAllIncomeByDate(String time) throws ExecutionException, InterruptedException {
        LiveData<List<income>> listLiveData = new getAllIncomeByDateAsyncTask(incomeDao).execute(time).get();
        return listLiveData;
    }

    public LiveData<List<income>> getAllIncomeBeforeDate(String time) throws ExecutionException, InterruptedException {
        LiveData<List<income>> listLiveData = new getAllIncomeBeforeDateAsyncTask(incomeDao).execute(time).get();
        return listLiveData;
    }

    public LiveData<List<income>> getAllIncomeByDateBudget(String timeFrom, String timeTo, int id) throws ExecutionException, InterruptedException {
        String[] timeId = {timeFrom, timeTo, id + ""};
        LiveData<List<income>> listLiveData = new getAllIncomeByDateBudgetAsyncTask(incomeDao).execute(timeId).get();
        return listLiveData;
    }

    public LiveData<List<income>> getAllIncomeBudgetRangeTime(String timeFrom, String timeTo, int id) throws ExecutionException, InterruptedException {
        String[] timeId = {timeFrom, timeTo, id + ""};
        LiveData<List<income>> listLiveData = new getAllIncomeeBudgetRangeTimeAsynctask(incomeDao).execute(timeId).get();
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

    private static class getAllIncomeBeforeDateAsyncTask extends AsyncTask<String, Void, LiveData<List<income>>> {

        private incomeDao incomeDao;

        public getAllIncomeBeforeDateAsyncTask(incomeDao incomeDao) {
            this.incomeDao = incomeDao;
        }

        @Override
        protected LiveData<List<income>> doInBackground(String... strings) {
            return incomeDao.getIncomeBeforeDate(strings[0]);
        }

        @Override
        protected void onPostExecute(LiveData<List<income>> listLiveData) {
            super.onPostExecute(listLiveData);
        }
    }

    public void insert(income e) {
        new insertIncomeAsyncTask(this.incomeDao).execute(e);
    }

    public void delete(income e) {
        new deleteIncomeAsyncTask(this.incomeDao).execute(e);
    }

    private static class updateIncomeAsynctask extends AsyncTask<income,Void,Void>{
        private incomeDao inDao;

        public updateIncomeAsynctask(incomeDao inDao) {
            this.inDao = inDao;
        }

        @Override
        protected Void doInBackground(income... incomes) {
            inDao.update(incomes[0]);
            return null;
        }
    }

    private static class insertIncomeAsyncTask extends AsyncTask<income, Void, Void> {
        private incomeDao inDao;

        public insertIncomeAsyncTask(incomeDao inDao) {
            this.inDao = inDao;
        }

        @Override
        protected Void doInBackground(income... incomes) {
            inDao.insert(incomes[0]);
            return null;
        }
    }
    private static class deleteIncomeAsyncTask extends AsyncTask<income, Void, Void> {
        private incomeDao inDao;

        public deleteIncomeAsyncTask(incomeDao inDao) {
            this.inDao = inDao;
        }

        @Override
        protected Void doInBackground(income... incomes) {
            inDao.delete(incomes[0]);
            return null;
        }
    }

    private static class getAllIncomeAsynctask extends AsyncTask<Void,Void,LiveData<List<income>>>{
        private incomeDao iDaoAsynctask;

        public getAllIncomeAsynctask(incomeDao iDao) {
            this.iDaoAsynctask = iDao;
        }

        @Override
        protected LiveData<List<income>> doInBackground(Void... voids) {
            return iDaoAsynctask.getListincome();
        }

        @Override
        protected void onPostExecute(LiveData<List<income>> listLiveData) {
            super.onPostExecute(listLiveData);
        }
    }

    private static class getAllIncomebyBudgetAsynctask extends AsyncTask<Integer, Void, LiveData<List<income>>> {
        private incomeDao iDao;

        public getAllIncomebyBudgetAsynctask(incomeDao iDao) {
            this.iDao = iDao;
        }

        @Override
        protected LiveData<List<income>> doInBackground(Integer... integers) {
            return iDao.getAllIncomebyBudget(integers[0]);
        }

        @Override
        protected void onPostExecute(LiveData<List<income>> listLiveData) {
            super.onPostExecute(listLiveData);
        }
    }

    private static class getAllIncomeByDateBudgetAsyncTask extends AsyncTask<String, Void, LiveData<List<income>>> {

        private incomeDao incomeDao;

        public getAllIncomeByDateBudgetAsyncTask(incomeDao inDao) {
            this.incomeDao = inDao;
        }


        @Override
        protected LiveData<List<income>> doInBackground(String... strings) {

            return incomeDao.getincomeByDateBudget(strings[0],strings[1], Integer.parseInt(strings[2]));
        }

        @Override
        protected void onPostExecute(LiveData<List<income>> listLiveData) {
            super.onPostExecute(listLiveData);
        }
    }

    private static class getAllIncomeeBudgetRangeTimeAsynctask extends AsyncTask<String, Void, LiveData<List<income>>> {
        private incomeDao incomeDao;

        public getAllIncomeeBudgetRangeTimeAsynctask(incomeDao incomeDao) {
            this.incomeDao = incomeDao;
        }

        @Override
        protected LiveData<List<income>> doInBackground(String... strings) {
            return incomeDao.getIncomeBudgetRangeTime(strings[0], strings[1], Integer.parseInt(strings[2]));
        }

        @Override
        protected void onPostExecute(LiveData<List<income>> listLiveData) {
            super.onPostExecute(listLiveData);
        }
    }
}
