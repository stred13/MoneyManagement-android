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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneymanagement_android.models.catexpense;
import com.example.moneymanagement_android.models.catincome;
import com.example.moneymanagement_android.models.expense;
import com.example.moneymanagement_android.models.income;
import com.example.moneymanagement_android.viewmodels.CatExpenseViewModel;
import com.example.moneymanagement_android.viewmodels.CatIncomeViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class infoExpense extends AppCompatActivity {

    EditText etNmoney;
    TextView tvCatExpense,textViewChonNgay;
    EditText etNote;
    ImageView imCatex;
    LinearLayout lnCategory;
    CardView cardViewChiTieu;
    CatExpenseViewModel catExpenseViewModel;
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



        Intent iExpense = getIntent();
        Intent iIncome = getIntent();
        ex =(expense) iExpense.getSerializableExtra("infoexpense");
        in = (income) iIncome.getSerializableExtra("infoincome");


        if(ex!=null){
            Toast.makeText(this, "chi tiêu "+ex.getNmoney(), Toast.LENGTH_SHORT).show();
            kcat = 0;
            etNmoney.setText(String.valueOf(ex.getNmoney()));
            etNote.setText(String.valueOf(ex.getNote()));

            try {
                catExpenseViewModel = new CatExpenseViewModel(this.getApplication());
                catExpenseViewModel = ViewModelProviders.of(this).get(CatExpenseViewModel.class);
                catExpense= catExpenseViewModel.getCatExpenseById(ex.getId());
                tvCatExpense.setText(catExpense.getName());

                SimpleDateFormat sdf = new SimpleDateFormat("dd");
                String day = sdf.format(ex.getDcreated());
                sdf = new SimpleDateFormat("MM");
                String month = sdf.format(ex.getDcreated());
                sdf = new SimpleDateFormat("yyyy");
                String year = sdf.format(ex.getDcreated());

                textViewChonNgay.setText(day+"/"+month+"/"+year);


            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(in!=null){
            Toast.makeText(this, "thu nhập "+in.getNmoney(), Toast.LENGTH_SHORT).show();
            kcat = 1;
        }

        lnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("", "onClick: ");
                Intent intent = new Intent(getApplicationContext(), singleCategory.class);
                intent.putExtra("singlecat",kcat);
                startActivityForResult(intent,0);
            }
        });

        cardViewChiTieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
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
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                textViewChonNgay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == Activity.RESULT_OK){
            catexpense catex = (catexpense) data.getSerializableExtra("catexSelected");
            //Log.d("", "onActivityResult: "+catex.getName());
            tvCatExpense.setText(catex.getName().toString());
            imCatex.setImageResource(catex.getImage());
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
