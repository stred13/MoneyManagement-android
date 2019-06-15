package com.example.moneymanagement_android;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneymanagement_android.adapters.incomeRecyclerViewAdapter;
import com.example.moneymanagement_android.models.catexpense;
import com.example.moneymanagement_android.models.catincome;
import com.example.moneymanagement_android.models.expense;
import com.example.moneymanagement_android.models.income;
import com.example.moneymanagement_android.viewmodels.CatExpenseViewModel;
import com.example.moneymanagement_android.viewmodels.CatIncomeViewModel;
import com.example.moneymanagement_android.viewmodels.IncomeViewModel;
import com.example.moneymanagement_android.viewmodels.expenseViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class infoExpense extends AppCompatActivity {

    EditText etNmoney;
    TextView tvCatExpense, textViewChonNgay;
    EditText etNote;
    ImageView imCatex,btndelete;
    LinearLayout lnCategory;
    CardView cardViewChiTieu;
    Button btnSave;
    CatExpenseViewModel catExpenseViewModel;
    CatIncomeViewModel catIncomeViewModel;
    expenseViewModel expenseVM;
    IncomeViewModel incomeVM;
    Date datecreated;


    int kcat = 0;

    private catincome catIncome;
    private catexpense catExpense;
    private expense ex;
    private income in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_expense);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chi tiêu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etNmoney = (EditText) findViewById(R.id.etEnmoney);
        tvCatExpense = (TextView) findViewById(R.id.textViewChonNhom);
        etNote = (EditText) findViewById(R.id.etNote);
        imCatex = (ImageView) findViewById(R.id.imageViewChonNhom);
        lnCategory = (LinearLayout) findViewById(R.id.lnCategory);
        textViewChonNgay = (TextView) findViewById(R.id.textViewChonNgay);
        cardViewChiTieu = (CardView) findViewById(R.id.CalenderChiTieu);
        btnSave = (Button) findViewById(R.id.btnSave);
        btndelete = (ImageView) findViewById(R.id.btndelExpense);

        Intent iExpense = getIntent();
        Intent iIncome = getIntent();
        ex = (expense) iExpense.getSerializableExtra("infoexpense");
        in = (income) iIncome.getSerializableExtra("infoincome");

        if (ex != null) {
            Toast.makeText(this, "chi tiêu " + ex.getNmoney(), Toast.LENGTH_SHORT).show();
            kcat = 0;
            etNmoney.setText(String.valueOf(ex.getNmoney()));
            etNote.setText(String.valueOf(ex.getNote()));
            try {
                expenseVM = new expenseViewModel(getApplication());
                catExpenseViewModel = new CatExpenseViewModel(this.getApplication());
                catExpenseViewModel = ViewModelProviders.of(this).get(CatExpenseViewModel.class);
                catExpense = (catexpense) catExpenseViewModel.getCatExpenseById(ex.getIdcatex());
                tvCatExpense.setText(catExpense.getName().toString());

                SimpleDateFormat sdf = new SimpleDateFormat("dd");
                String day = sdf.format(ex.getDcreated());
                sdf = new SimpleDateFormat("MM");
                String month = sdf.format(ex.getDcreated());
                sdf = new SimpleDateFormat("yyyy");
                String year = sdf.format(ex.getDcreated());

                textViewChonNgay.setText(day + "/" + month + "/" + year);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (in != null) {
            Toast.makeText(this, "thu nhập " + in.getNmoney(), Toast.LENGTH_SHORT).show();
            kcat = 1;
            etNmoney.setText(String.valueOf(in.getNmoney()));
            etNote.setText(String.valueOf(in.getNote()));
            try {
                incomeVM = new IncomeViewModel(getApplication());
                catIncomeViewModel = new CatIncomeViewModel(getApplication());
                catIncomeViewModel = ViewModelProviders.of(this).get(CatIncomeViewModel.class);
                catIncome = (catincome) catIncomeViewModel.getCatIncomeById(in.getIdcatin());
                tvCatExpense.setText(catIncome.getName().toString());

                SimpleDateFormat sdf = new SimpleDateFormat("dd");
                String day = sdf.format(in.getDcreated());
                sdf = new SimpleDateFormat("MM");
                String month = sdf.format(in.getDcreated());
                sdf = new SimpleDateFormat("yyyy");
                String year = sdf.format(in.getDcreated());

                textViewChonNgay.setText(day + "/" + month + "/" + year);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        lnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), singleCategory.class);
                intent.putExtra("singlecat", kcat);
                startActivityForResult(intent, kcat);
            }
        });

        cardViewChiTieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ex!=null)
                    expenseVM.delete(ex);
                if(in!=null)
                    incomeVM.delete(in);
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ex!=null){
                    try {
                        ex.setNote(etNote.getText().toString());
                        ex.setNmoney(Long.parseLong(etNmoney.getText().toString()));
                        ex.setIdcatex(catExpense.getId());

                        datecreated = new SimpleDateFormat("dd/MM/yyyy").parse(textViewChonNgay.getText().toString());

                        ex.setDcreated(datecreated);
                   /* Log.d("info ex", " onClick: " + ex.getNmoney() + " " + ex.getIdcatex() + " " + ex.getDcreated() + " " + ex.getIdbudget()
                            + " " + ex.getNote() + " " + ex.getId());*/
                        expenseVM.update(ex);
                        finish();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(in!=null){
                    try {
                        in.setNote(etNote.getText().toString());
                        in.setNmoney(Long.parseLong(etNmoney.getText().toString()));
                        in.setIdcatin(catIncome.getId());

                        datecreated = new SimpleDateFormat("dd/MM/yyyy").parse(textViewChonNgay.getText().toString());

                        in.setDcreated(datecreated);
                        incomeVM.update(in);
                        finish();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private void ChonNgay() {
        final Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(textViewChonNgay.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                textViewChonNgay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            catExpense = (catexpense) data.getSerializableExtra("catexSelected");
            //Log.d("", "onActivityResult: "+catex.getName());
            tvCatExpense.setText(catExpense.getName().toString());
            imCatex.setImageResource(catExpense.getImage());
        }
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            catIncome = (catincome) data.getSerializableExtra("catinSelected");
            tvCatExpense.setText(catIncome.getName().toString());
            imCatex.setImageResource(catIncome.getImage());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
