package com.nhom10.moneymanagement_android.datamanagers;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.nhom10.moneymanagement_android.dao.CatExpenseDAO;
import com.nhom10.moneymanagement_android.dao.CatIncomeDAO;
import com.nhom10.moneymanagement_android.dao.budgetDao;
import com.nhom10.moneymanagement_android.dao.expenseDao;
import com.nhom10.moneymanagement_android.dao.incomeDao;
import com.nhom10.moneymanagement_android.models.budget;
import com.nhom10.moneymanagement_android.models.catexpense;
import com.nhom10.moneymanagement_android.models.catincome;
import com.nhom10.moneymanagement_android.models.expense;
import com.nhom10.moneymanagement_android.models.income;

@Database(entities = {budget.class, income.class, expense.class, catexpense.class, catincome.class}, version = 6)
public abstract class roomDatabase extends RoomDatabase {

    public abstract expenseDao exDao();
    public abstract budgetDao bdDao();
    public abstract incomeDao icDao();
    public abstract CatExpenseDAO catExpenseDAO();
    public abstract CatIncomeDAO catIncomeDAO();

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
