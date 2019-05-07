package com.example.moneymanagement_android;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.moneymanagement_android.fragments.ExpenseFragment;
import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.models.expense;
import com.example.moneymanagement_android.viewmodels.expenseViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class expense_creating extends AppCompatActivity {

    Button btnChonNgay;
    EditText editTextChonNgay;
    EditText et_nMoney;
    EditText et_note;
    EditText et_name;
    Button btnAcc;
    expenseViewModel eViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_creating);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tạo chi tiêu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextChonNgay = (EditText) findViewById(R.id.editTextChonNgay);
        et_name = (EditText) findViewById(R.id.etEname);
        et_nMoney = (EditText) findViewById(R.id.etEnmoney);
        et_note = (EditText) findViewById(R.id.mtEnote);
        btnAcc = (Button) findViewById(R.id.btnAccept);

        eViewModel = ViewModelProviders.of(this).get(expenseViewModel.class);

        final expense e = new expense();

        btnAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                int nmoney = Integer.parseInt(et_nMoney.getText().toString());
                String note = et_note.getText().toString();
                int loai = 1;

                e.setIdbudget(infobudget.b.getId());
                e.setDcreated(new Date());
                e.setName(name);
                e.setIdcatex(loai);
                e.setNmoney(nmoney);
                e.setNote(note);

                eViewModel.insert(e);
            }
        });

        editTextChonNgay.setOnClickListener(new View.OnClickListener() {
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
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                editTextChonNgay.setText(simpleDateFormat.format(calendar.getTime()));
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
