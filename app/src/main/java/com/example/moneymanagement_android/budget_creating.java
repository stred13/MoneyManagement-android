package com.example.moneymanagement_android;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.moneymanagement_android.fragments.BudgetFragment;
import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.viewmodels.budgetViewModel;

import java.io.Serializable;

public class budget_creating extends AppCompatActivity {
    public static final int REQUEST_CODE = 1;
    private EditText etTenV;
    private EditText etNote;
    private Spinner spCurrency;
    private EditText etSoT;
    private Button btnTaoV;
    budgetViewModel bViewModel;
    RecyclerAdapter recAdapter;
    budget b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_creating);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etTenV = (EditText) findViewById(R.id.etTenV);
        etNote = (EditText) findViewById(R.id.mtGhichu);
        spCurrency = (Spinner) findViewById(R.id.spLoaiT);
        etSoT = (EditText) findViewById(R.id.etSoT);
        btnTaoV = (Button) findViewById(R.id.btnTaoV);

        bViewModel = ViewModelProviders.of(this).get(budgetViewModel.class);


        btnTaoV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namev = etTenV.getText().toString();
                String note = etNote.getText().toString();
                String currency = spCurrency.getSelectedItem().toString();
                Toast.makeText(getApplication().getApplicationContext(), ""+namev, Toast.LENGTH_SHORT).show();

                //insert
                b = new budget(namev,currency,note);
                bViewModel.insertBudget(b);

            }
        });
    }

}
