package com.example.moneymanagement_android.datamanagers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.moneymanagement_android.models.budget;

import java.util.ArrayList;
import java.util.List;

public class dataProvider extends SQLiteOpenHelper {

    private Context context;

    public static final String Database_Name = "MoneyManagement";

    private static final String BTable_Name = "budget";
    private static final String bID = "id";
    private static final String bname = "bName";
    private static final String bcurrency = "bcurrency";
    private static final String bnmoney = "bnmoney";
    private static final String bNote = "bNote";
    private static final String burlIm = "burlIm";

    String queryCreate_budget_tb = "CREATE TABLE "+BTable_Name +" (" +
            bID +" integer primary key, "+
            bname + " TEXT, "+
            bNote +" TEXT, "+
            bcurrency+" TEXT, " +
            bnmoney+" TEXT, "+
            burlIm +" INTEGER)";

    public dataProvider(Context context) {
        super(context, Database_Name,null,1);
        this.context = context;
        Toast.makeText(context, "db", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(queryCreate_budget_tb);
        Toast.makeText(context, "Create successfylly", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addBudget(budget bd){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues vl = new ContentValues();
        vl.put(bname,bd.getbName());
        vl.put(bNote,bd.getbNote());
        vl.put(bcurrency,bd.getbCurrency());
        vl.put(bnmoney,bd.getBnMoney());
        vl.put(bcurrency,bd.getBurlImage());

        db.insert(BTable_Name,null,vl);
        db.close();
    }

    public budget getLastBudget(){
        SQLiteDatabase db = this.getReadableDatabase();
        //String query = "SELECT MAX(id), bName, bNote, bcurrency, bnmoney, burlIm FROM "+BTable_Name+ " ORDER BY id DESC LIMIT 1";
        String query = "SELECT MAX(id), bName, bNote, bcurrency, bnmoney, burlIm FROM "+BTable_Name;
       // String query = "SELECT MAX(id) FROM "+BTable_Name;
        Cursor cursor = db.rawQuery(query,null);
        budget b = new budget();
       // cursor.moveToFirst()
       // Log.d("asdbc",": "+ cursor.ge);

        if(cursor.moveToFirst()) {
            b.setId(cursor.getInt(0));
            b.setbName(cursor.getString(1));
            b.setbNote(cursor.getString(2));
            b.setbCurrency(cursor.getString(3));
            b.setBnMoney(cursor.getString(4));
            b.setBurlImage(cursor.getInt(5));
        }
        cursor.close();
        db.close();
        return b;
    }

    public List<budget> getListBudget(){
        List<budget> listBudget = new ArrayList<budget>();

        String query = "Select * From "+BTable_Name;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                budget b =new budget();
                b.setId(cursor.getInt(0));
                b.setbName(cursor.getString(1));
                b.setbNote(cursor.getString(2));
                b.setbCurrency(cursor.getString(3));
                b.setBnMoney(cursor.getString(4));
                b.setBurlImage(cursor.getInt(5));
                listBudget.add(b);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  listBudget;
    }


}
