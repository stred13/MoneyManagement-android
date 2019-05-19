package com.example.moneymanagement_android;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.moneymanagement_android.fragments.ExpenseCategoryFragment;
import com.example.moneymanagement_android.fragments.IncomeCategoryFragment;

public class category extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    IncomeCategoryFragment incomeCategoryFragment;
    ExpenseCategoryFragment expenseCategoryFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thêm giao dịch");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.mainViewCategory);
        tabLayout = findViewById(R.id.tabLayoutCategory);

        incomeCategoryFragment = new IncomeCategoryFragment();
        expenseCategoryFragment = new ExpenseCategoryFragment();

        ViewPaperAdapter viewPaperAdapter = new ViewPaperAdapter(getSupportFragmentManager());
        viewPaperAdapter.addFragment(incomeCategoryFragment,"Khoản thu");
        viewPaperAdapter.addFragment(expenseCategoryFragment,"Khoản chi");

        viewPager.setAdapter(viewPaperAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
