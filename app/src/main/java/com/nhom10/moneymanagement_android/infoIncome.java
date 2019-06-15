package com.nhom10.moneymanagement_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.nhom10.moneymanagement_android.models.income;

public class infoIncome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_income);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thu nhập");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent iIncome = getIntent();
        income ic = (income) iIncome.getSerializableExtra("infoincome");

        Toast.makeText(this, "chi tiêu "+ic.getNmoney(), Toast.LENGTH_SHORT).show();


    }
}
