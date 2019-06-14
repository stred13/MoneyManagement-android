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
import com.example.moneymanagement_android.models.catexpense;
import com.example.moneymanagement_android.viewmodels.CatExpenseViewModel;
import com.example.moneymanagement_android.viewmodels.CatIncomeViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class singleCategory extends AppCompatActivity {

    RecyclerView singleCatRecyclerView;
    CatExpenseViewModel catExpenseVM;
    CatIncomeViewModel catIncomeVM;
    SingleCatExpenseRecyclerAdapter sgCatxpenseAdapter;
    List<catexpense> catexpenseList = new ArrayList<>();
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
            sgCatxpenseAdapter = new SingleCatExpenseRecyclerAdapter(this.getApplicationContext());
            sgCatxpenseAdapter.setCatExpenses(catexpenseList);
            sgCatxpenseAdapter.setOnItemCatClickListener(onClickListener);
            singleCatRecyclerView.setAdapter(sgCatxpenseAdapter);

            try {
                catExpenseVM = new CatExpenseViewModel(this.getApplication());
                catExpenseVM = ViewModelProviders.of(this).get(CatExpenseViewModel.class);
                catExpenseVM.getAllCatExpense().observe(this, new Observer<List<catexpense>>() {
                    @Override
                    public void onChanged(@Nullable List<catexpense> catexpenses) {
                        if(catexpenses!= null){
                            catexpenseList = catexpenses;
                            sgCatxpenseAdapter.setCatExpenses(catexpenseList);
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

                Log.d("pos cat", "onClick: "+catexpenseList.get(pos).getName());

                Intent intent = new Intent(getApplication(),infoExpense.class);
                intent.putExtra("catexSelected",catexpenseList.get(pos));
                setResult(Activity.RESULT_OK,intent);
            }
            finish();
        }
    };
}
