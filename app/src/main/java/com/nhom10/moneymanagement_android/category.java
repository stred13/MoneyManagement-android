package com.nhom10.moneymanagement_android;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nhom10.moneymanagement_android.fragments.ExpenseCategoryFragment;
import com.nhom10.moneymanagement_android.fragments.IncomeCategoryFragment;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_category_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuEditCategory:
                Intent intent = new Intent(getApplicationContext(),category_settings.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
