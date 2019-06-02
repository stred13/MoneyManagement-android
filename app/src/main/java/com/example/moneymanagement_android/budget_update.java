package com.example.moneymanagement_android;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.viewmodels.budgetViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class budget_update extends AppCompatActivity {

    TextView tv_namev;
    //Spinner spCurrency;
    TextView tv_Date;
    TextView tv_note;
    Button btnupdate;
    budgetViewModel bViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_update);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cập nhật ví");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_namev = findViewById(R.id.etTenV);
        tv_note = findViewById(R.id.mtGhichu);
        tv_Date = findViewById(R.id.eNgayTaoVi);
        btnupdate = findViewById(R.id.btnUpdateB);

        Intent i = getIntent();

        final budget b = (budget) i.getSerializableExtra("b");

        tv_namev.setText(b.getName());
        tv_note.setText(b.getNote());
        Calendar c = Calendar.getInstance();
        int ngay = c.get(Calendar.DATE);
        int thang = c.get(Calendar.MONTH) + 1;
        int nam = c.get(Calendar.YEAR);

        tv_Date.setText(ngay + "/" + thang + "/" + nam);

        bViewModel = ViewModelProviders.of(this).get(budgetViewModel.class);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bname = tv_namev.getText().toString();
                String bnote = tv_note.getText().toString();
                b.setName(bname);
                b.setNote(bnote);
                bViewModel.updateBudget(b);
                finish();
            }
        });
        tv_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });
    }

    private void ChonNgay() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                tv_Date.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
