package com.example.moneymanagement_android.datamanagers;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.moneymanagement_android.dao.budgetDao;
import com.example.moneymanagement_android.dao.expenseDao;
import com.example.moneymanagement_android.dao.incomeDao;
import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.models.expense;
import com.example.moneymanagement_android.models.income;

@Database(entities = {budget.class,income.class,expense.class}, version = 3)
public abstract class roomDatabase extends RoomDatabase {

    public abstract expenseDao exDao();
    public abstract budgetDao bdDao();
    public abstract incomeDao icDao();

    private static roomDatabase instance;

    public static synchronized roomDatabase getInstance(final Context context) {
        if (instance == null) {
            //Log.d("db", "getInstance: ");
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    roomDatabase.class, "mn_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    /*private static roomDatabase.Callback roomCallback = new roomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private budgetDao dao;

        private PopulateDbAsyncTask(roomDatabase rb){
            dao = rb.bdDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.insert(new budget("11","22","33",0,5));
            return null;
        }
    }*/

}
