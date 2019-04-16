package com.example.moneymanagement_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.moneymanagement_android.datamanagers.dataProvider;
import com.example.moneymanagement_android.fragments.BudgetFragment;
import com.example.moneymanagement_android.models.budget;

import java.io.Serializable;

public class budget_creating extends AppCompatActivity {
    public static final int REQUEST_CODE = 1;
    private EditText etTenV;
    private EditText etNote;
    private Spinner spCurrency;
    private EditText etSoT;
    private Button btnTaoV;
    RecyclerAdapter recAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_creating);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final dataProvider dbp = new dataProvider(this);

        etTenV = (EditText) findViewById(R.id.etTenV);
        etNote = (EditText) findViewById(R.id.mtGhichu);
        spCurrency = (Spinner) findViewById(R.id.spLoaiT);
        etSoT = (EditText) findViewById(R.id.etSoT);
        btnTaoV = (Button) findViewById(R.id.btnTaoV);

        btnTaoV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                budget b = createBudget();
                if(b!=null){
                    dbp.addBudget(b);
                    sendBudget_created(dbp.getLastBudget());
                }
            }
        });
    }

    private void sendBudget_created(budget b){
        Intent i = new Intent();
        i.putExtra("res",b.getbName());
        setResult(2,i);
       // Toast.makeText(this, "data cre "+b.getbName(), Toast.LENGTH_SHORT).show();

        finish();
    }

    private budget createBudget (){
        String bName = etTenV.getText().toString();
        String bcurrency = spCurrency.getSelectedItem().toString();
        String bsoT = etSoT.getText().toString();
        String bnote = etNote.getText().toString();
        budget b = new budget(bName,bcurrency,bnote,bsoT,R.drawable.hand_card_50);
        return b;
    }

}
