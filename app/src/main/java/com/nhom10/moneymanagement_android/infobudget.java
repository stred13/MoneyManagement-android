package com.nhom10.moneymanagement_android;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nhom10.moneymanagement_android.fragments.ExpenseBudgetFragment;
import com.nhom10.moneymanagement_android.fragments.ExpenseFragment;
import com.nhom10.moneymanagement_android.fragments.IncomeBudgetFragment;
import com.nhom10.moneymanagement_android.fragments.IncomeFragment;
import com.nhom10.moneymanagement_android.models.budget;
import com.nhom10.moneymanagement_android.viewmodels.IncomeViewModel;
import com.nhom10.moneymanagement_android.viewmodels.budgetViewModel;
import com.nhom10.moneymanagement_android.viewmodels.expenseViewModel;

import java.util.concurrent.ExecutionException;

public class infobudget extends AppCompatActivity {

    private ExpenseFragment expenseFragment;
    private IncomeFragment incomeFragment;
    private ExpenseBudgetFragment expenseBudgetFragment;
    private IncomeBudgetFragment incomeBudgetFragment;
    private ViewPager mainViewInfo;
    private TabLayout tabLayoutInfo;

    private budgetViewModel bViewMd;
    private IncomeViewModel inViewMd;
    private expenseViewModel exViewMd;

    public static budget b = new budget();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infobudget);
        Toolbar toolbar = findViewById(R.id.toolbar);

        Intent i = getIntent();
        b = (budget) i.getSerializableExtra("budget");
        //Toast.makeText(this, "budget: "+b.getId(), Toast.LENGTH_SHORT).show();

        bViewMd = ViewModelProviders.of(this).get(budgetViewModel.class);

        try {
            bViewMd.getBudgetbyId(b.getId()).observe(this, new Observer<budget>() {
                @Override
                public void onChanged(@Nullable budget budget) {
                    getSupportActionBar().setTitle("Tổng tiền "+budget.getBmoney());
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Thông tin ví "+infobudget.b.getBmoney());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//turn back arrow

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(getApplication(),MainActivity.class);
                startActivity(intent);*/
                finish();
            }
        });

        mainViewInfo = findViewById(R.id.mainViewInfo);
        tabLayoutInfo = findViewById(R.id.tabLayoutInfo);

        expenseFragment = new ExpenseFragment();
        incomeFragment = new IncomeFragment();
        expenseBudgetFragment = new ExpenseBudgetFragment();
        incomeBudgetFragment = new IncomeBudgetFragment();

        ViewPaperAdapter viewPaperAdapter = new ViewPaperAdapter(getSupportFragmentManager());
        //viewPaperAdapter.addFragment(expenseFragment, "Chi Tiêu 333");
        //viewPaperAdapter.addFragment(incomeFragment, "Thu Nhập");
        viewPaperAdapter.addFragment(expenseBudgetFragment, "Chi Tiêu");
        viewPaperAdapter.addFragment(incomeBudgetFragment,"Thu Nhập");

        mainViewInfo.setAdapter(viewPaperAdapter);
        tabLayoutInfo.setupWithViewPager(mainViewInfo);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplication(), expense_creating.class);
                startActivity(i);
            }
        });
    }
}
