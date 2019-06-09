package com.example.moneymanagement_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.moneymanagement_android.models.expense;

public class infoExpense extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_expense);


        Intent intent = getIntent();
        expense e =(expense) intent.getSerializableExtra("infoexpense");
        Toast.makeText(this, "thong bao "+e.getNmoney(), Toast.LENGTH_SHORT).show();
    }
}
