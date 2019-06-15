package com.example.moneymanagement_android;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.moneymanagement_android.adapters.SingleCatExpenseRecyclerAdapter;
import com.example.moneymanagement_android.adapters.SingleCatIncomeRecyclerAdapter;
import com.example.moneymanagement_android.models.catexpense;
import com.example.moneymanagement_android.models.catincome;
import com.example.moneymanagement_android.viewmodels.CatExpenseViewModel;
import com.example.moneymanagement_android.viewmodels.CatIncomeViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class singleCategory extends AppCompatActivity {

    RecyclerView singleCatRecyclerView;
    CatExpenseViewModel catExpenseVM;
    CatIncomeViewModel catIncomeVM;
    SingleCatExpenseRecyclerAdapter sgCatexpenseAdapter;
    SingleCatIncomeRecyclerAdapter sgCatincomeAdapter;

    List<catexpense> catexpenseList = new ArrayList<>();
    List<catincome> catincomeList = new ArrayList<>();
    int kcat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_category);

        Intent intent = getIntent();
        kcat = (int)intent.getSerializableExtra("singlecat");

        if(kcat==0){
            singleCatRecyclerView = (RecyclerView) findViewById(R.id.singlecat);
            singleCatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            sgCatexpenseAdapter = new SingleCatExpenseRecyclerAdapter(this.getApplicationContext());
            sgCatexpenseAdapter.setCatExpenses(catexpenseList);
            sgCatexpenseAdapter.setOnItemCatClickListener(onClickListener);
            singleCatRecyclerView.setAdapter(sgCatexpenseAdapter);

            try {
                catExpenseVM = new CatExpenseViewModel(this.getApplication());
                catExpenseVM = ViewModelProviders.of(this).get(CatExpenseViewModel.class);
                catExpenseVM.getAllCatExpense().observe(this, new Observer<List<catexpense>>() {
                    @Override
                    public void onChanged(@Nullable List<catexpense> catexpenses) {
                        if(catexpenses!= null){
                            catexpenseList = catexpenses;
                            sgCatexpenseAdapter.setCatExpenses(catexpenseList);
                        }
                    }
                });
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(kcat ==1){
            singleCatRecyclerView = (RecyclerView) findViewById(R.id.singlecat);
            singleCatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            sgCatincomeAdapter = new SingleCatIncomeRecyclerAdapter(this.getApplicationContext());
            sgCatincomeAdapter.setCatIncomes(catincomeList);
            sgCatincomeAdapter.setOnItemCatClickListener(onClickListener);
            singleCatRecyclerView.setAdapter(sgCatincomeAdapter);

            try {
                catIncomeVM = new CatIncomeViewModel(this.getApplication());
                catIncomeVM = ViewModelProviders.of(this).get(CatIncomeViewModel.class);
               catIncomeVM.getAllCatIncome().observe(this, new Observer<List<catincome>>() {
                   @Override
                   public void onChanged(@Nullable List<catincome> catincomes) {
                       if(catincomes!=null){
                            catincomeList = catincomes;
                            sgCatincomeAdapter.setCatIncomes(catincomeList);
                       }
                   }
               });
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(kcat ==0){
                SingleCatExpenseRecyclerAdapter.MyViewHolder viewHolder = (SingleCatExpenseRecyclerAdapter.MyViewHolder) view.getTag();
                int pos = viewHolder.getAdapterPosition();

                Intent intent = new Intent(getApplication(),infoExpense.class);
                intent.putExtra("catexSelected",catexpenseList.get(pos));
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
            if(kcat==1){
                SingleCatIncomeRecyclerAdapter.MyViewHolder viewHolder = (SingleCatIncomeRecyclerAdapter.MyViewHolder) view.getTag();
                int pos = viewHolder.getAdapterPosition();
                Intent intent = new Intent(getApplication(),infoExpense.class);
                intent.putExtra("catinSelected",catincomeList.get(pos));
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        }
    };
}
