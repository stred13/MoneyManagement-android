package com.example.moneymanagement_android;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.viewmodels.budgetViewModel;

public class budget_update extends AppCompatActivity {

    TextView tv_namev;
    Spinner spCurrency;
    TextView tv_note;
    Button btnupdate;
    budgetViewModel bViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_update);

        tv_namev = findViewById(R.id.etTenV);
        tv_note = findViewById(R.id.mtGhichu);
        spCurrency = findViewById(R.id.spLoaiT);
        btnupdate = findViewById(R.id.btnUpdateB);

        Intent i = getIntent();

        final budget b = (budget) i.getSerializableExtra("budget");

        tv_namev.setText(b.getName());
        tv_note.setText(b.getNote());

        bViewModel = ViewModelProviders.of(this).get(budgetViewModel.class);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bname=tv_namev.getText().toString();
                String bnote=tv_note.getText().toString();
                b.setName(bname);
                b.setNote(bnote);
                bViewModel.updateBudget(b);
            }
        });
    }
}
