package com.example.moneymanagement_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.moneymanagement_android.fragments.BudgetFragment;
import com.example.moneymanagement_android.fragments.ExpenseFragment;
import com.example.moneymanagement_android.fragments.IncomeFragment;
import com.example.moneymanagement_android.fragments.StatisticFragment;
import com.example.moneymanagement_android.models.budget;

public class infobudget extends AppCompatActivity {

    private ExpenseFragment expenseFragment;
    private IncomeFragment incomeFragment;
    private ViewPager mainViewInfo;
    private TabLayout tabLayoutInfo;

    public static budget b = new budget();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infobudget);
        Toolbar toolbar = findViewById(R.id.toolbar);

        Intent i = getIntent();
        b = (budget) i.getSerializableExtra("budget");
        //Toast.makeText(this, "budget: "+b.getId(), Toast.LENGTH_SHORT).show();

        setSupportActionBar(toolbar);
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

        ViewPaperAdapter viewPaperAdapter = new ViewPaperAdapter(getSupportFragmentManager());
        viewPaperAdapter.addFragment(expenseFragment, "Chi Tiêu");
        viewPaperAdapter.addFragment(incomeFragment, "Thu Nhập");

        mainViewInfo.setAdapter(viewPaperAdapter);
        tabLayoutInfo.setupWithViewPager(mainViewInfo);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
               Intent i = new Intent(getApplication(),expense_creating.class);
               startActivity(i);
            }
        });
    }

}
