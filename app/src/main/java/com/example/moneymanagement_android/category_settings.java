package com.example.moneymanagement_android;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moneymanagement_android.adapters.ImageAdapter;
import com.example.moneymanagement_android.adapters.ManageExpenseRecyclerViewAdapter;
import com.example.moneymanagement_android.adapters.ManageIncomeRecyclerViewAdapter;
import com.example.moneymanagement_android.adapters.budgetRecyclerViewAdapter;
import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.models.catexpense;
import com.example.moneymanagement_android.models.catincome;
import com.example.moneymanagement_android.viewmodels.CatExpenseViewModel;
import com.example.moneymanagement_android.viewmodels.CatIncomeViewModel;
import com.example.moneymanagement_android.viewmodels.budgetViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class category_settings extends AppCompatActivity {
    private CatExpenseViewModel eViewModel;
    private RecyclerView myRecyclerViewExpense;
    private ManageExpenseRecyclerViewAdapter manageExpenseRecyclerViewAdapter;
    private List<catexpense> listCatExpense = new ArrayList<>();

    private CatIncomeViewModel iViewModel;
    private RecyclerView myRecyclerViewIncome;
    private ManageIncomeRecyclerViewAdapter manageIncomeRecyclerViewAdapter;
    private List<catincome> listCatIncome = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quản lý nhóm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//turn back arrow

        //show list catexpense

        myRecyclerViewExpense = (RecyclerView) findViewById(R.id.recylerExpense);
        manageExpenseRecyclerViewAdapter = new ManageExpenseRecyclerViewAdapter();
        manageExpenseRecyclerViewAdapter.setListCatExpense(listCatExpense);
        myRecyclerViewExpense.setLayoutManager((new LinearLayoutManager(this)));
        myRecyclerViewExpense.setAdapter(manageExpenseRecyclerViewAdapter);
        manageExpenseRecyclerViewAdapter.setOnItemExpenseClickListener(onItemExpenseClickListener);
        //recycleViewAdapter.setOnItemLongClickListener(onItemLongClickListener);

        try {
            eViewModel = new CatExpenseViewModel(this.getApplication());
            eViewModel = ViewModelProviders.of(this).get(CatExpenseViewModel.class);
            eViewModel.getAllCatExpense().observe(this, new Observer<List<catexpense>>() {
                @Override
                public void onChanged(@Nullable List<catexpense> catexpenses) {
                    if (catexpenses != null) {
                        listCatExpense = catexpenses;
                        manageExpenseRecyclerViewAdapter.setListCatExpense(listCatExpense);
                       // Toast.makeText(getApplication(), "on change: " + catexpenses.size(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


        //show list catincome
        myRecyclerViewIncome = (RecyclerView) findViewById(R.id.recylerIncome);
        manageIncomeRecyclerViewAdapter = new ManageIncomeRecyclerViewAdapter();
        manageIncomeRecyclerViewAdapter.setListCatIncome(listCatIncome);
        myRecyclerViewIncome.setLayoutManager((new LinearLayoutManager(this)));
        myRecyclerViewIncome.setAdapter(manageIncomeRecyclerViewAdapter);
        manageIncomeRecyclerViewAdapter.setOnItemIncomeClickListener(onItemIncomeClickListener);
        //recycleViewAdapter.setOnItemLongClickListener(onItemLongClickListener);

        try {
            iViewModel = new CatIncomeViewModel(this.getApplication());
            iViewModel = ViewModelProviders.of(this).get(CatIncomeViewModel.class);
            iViewModel.getAllCatIncome().observe(this, new Observer<List<catincome>>() {
                @Override
                public void onChanged(@Nullable List<catincome> catincomes) {
                    if (catincomes != null) {
                        listCatIncome = catincomes;
                        manageIncomeRecyclerViewAdapter.setListCatIncome(listCatIncome);
                        //Toast.makeText(getApplication(), "on change: " + catincomes.size(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_category_edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuAddCategory:
                Intent intent = new Intent(getApplicationContext(),category_add.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //click item expense.
    private View.OnClickListener onItemExpenseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ManageExpenseRecyclerViewAdapter.MyViewHolder viewHolder = (ManageExpenseRecyclerViewAdapter.MyViewHolder) v.getTag();
            int pos = viewHolder.getAdapterPosition();
            catexpense b = listCatExpense.get(pos);

            Intent category_edit = new Intent(getApplicationContext(), category_edit.class);
            category_edit.putExtra("catexpense", b);
            category_edit.putExtra("category","expense");
            startActivity(category_edit);
        }
    };
    //click item income
    private View.OnClickListener onItemIncomeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ManageIncomeRecyclerViewAdapter.MyViewHolder viewHolder = (ManageIncomeRecyclerViewAdapter.MyViewHolder) v.getTag();
            int pos = viewHolder.getAdapterPosition();
            catincome b = listCatIncome.get(pos);

            Intent category_edit = new Intent(getApplicationContext(), category_edit.class);
            category_edit.putExtra("catincome", b);
            category_edit.putExtra("category","income");
            startActivity(category_edit);
        }
    };
}
