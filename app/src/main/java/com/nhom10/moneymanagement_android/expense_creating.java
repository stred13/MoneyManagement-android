package com.nhom10.moneymanagement_android;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nhom10.moneymanagement_android.models.budget;
import com.nhom10.moneymanagement_android.models.catexpense;
import com.nhom10.moneymanagement_android.models.catincome;
import com.nhom10.moneymanagement_android.models.expense;
import com.nhom10.moneymanagement_android.models.income;
import com.nhom10.moneymanagement_android.utils.Util;
import com.nhom10.moneymanagement_android.viewmodels.CatExpenseViewModel;
import com.nhom10.moneymanagement_android.viewmodels.CatIncomeViewModel;
import com.nhom10.moneymanagement_android.viewmodels.IncomeViewModel;
import com.nhom10.moneymanagement_android.viewmodels.budgetViewModel;
import com.nhom10.moneymanagement_android.viewmodels.expenseViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class expense_creating extends AppCompatActivity {

    EditText et_nMoney;
    EditText et_note;
    EditText et_name;
    Button btnAcc;
    expenseViewModel eViewModel;
    IncomeViewModel iViewModel;
    budgetViewModel bViewModel;
    CardView cardViewChiTieu;
    TextView textViewChonNgay;
    LinearLayout lnCategory;
    TextView textViewChonNhom;
    ImageView imageViewChonNhom;
    CatExpenseViewModel catExpenseViewModel;
    CatIncomeViewModel catIncomeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_creating);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tạo chi tiêu / thu nhập");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //editTextChonNgay = (EditText) findViewById(R.id.editTextChonNgay);
        cardViewChiTieu = (CardView) findViewById(R.id.CalenderChiTieu);
        textViewChonNgay = (TextView) findViewById(R.id.textViewChonNgay);
        lnCategory = (LinearLayout) findViewById(R.id.lnCategory);
        et_nMoney = (EditText) findViewById(R.id.etEnmoney);
        et_note = (EditText) findViewById(R.id.mtEnote);
        btnAcc = (Button) findViewById(R.id.btnAccept);
        textViewChonNhom = (TextView) findViewById(R.id.textViewChonNhom);
        imageViewChonNhom = (ImageView) findViewById(R.id.imageViewChonNhom);

        eViewModel = ViewModelProviders.of(this).get(expenseViewModel.class);
        iViewModel = ViewModelProviders.of(this).get(IncomeViewModel.class);
        bViewModel = ViewModelProviders.of(this).get(budgetViewModel.class);

        final expense e = new expense();
        final income i = new income();

        final budget b = infobudget.b;


        et_nMoney.addTextChangedListener(new TextWatcher() {
            boolean isEdiging;

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (isEdiging) return;
                isEdiging = true;

                String formatText = Util.formatPrice(s.toString());
                s.replace(0, s.length(), formatText);


                if (s.toString().equals("0")) {
                    et_nMoney.setText("");
                }
                isEdiging = false;
            }
        });

        btnAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String name = et_name.getText().toString();
                String editMoneyReplaceComma = et_nMoney.getText().toString().replace(",","");
                int nmoney = Integer.parseInt(editMoneyReplaceComma.equals("") ?  "0" : editMoneyReplaceComma );
                String note = et_note.getText().toString();
                if(nmoney == 0)
                {
                    Toast.makeText(getApplicationContext(),"Cần nhập số tiền",Toast.LENGTH_SHORT).show();
                    return;
                }
                Date dateObj = new Date();
                try {
                    String dateStr = textViewChonNgay.getText().toString();

                    SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
                    dateObj = curFormater.parse(dateStr);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                int loai;
                if(flash == 1) {
                    loai = catincome.getId();

                    i.setIdbudget(b.getId());
                    i.setDcreated(dateObj);
                    i.setName("");
                    i.setIdcatin(loai);
                    i.setNmoney(nmoney);
                    i.setNote(note);

                    Log.d("b", "onCreate: "+b.getId()+" "+b.getBmoney());

                    iViewModel.insert(i);

                    long bmoney = b.getBmoney()+i.getNmoney();
                    b.setBmoney(bmoney);
                    bViewModel.updateBudget(b);

                    finish();
                }else if(flash == 2){
                    loai = catexpense.getId();

                    e.setIdbudget(infobudget.b.getId());
                    e.setDcreated(dateObj);
                    e.setName("");
                    e.setIdcatex(loai);
                    e.setNmoney(nmoney);
                    e.setNote(note);
                    long bmoney = b.getBmoney()-e.getNmoney();
                    b.setBmoney(bmoney);
                    bViewModel.updateBudget(b);

                    eViewModel.insert(e);

                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Cần chọn nhóm",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        ChonNgayHienTai();
        cardViewChiTieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });

        lnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), category.class);
                startActivityForResult(intent , 1);

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
                textViewChonNgay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }
    private void ChonNgayHienTai(){
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        textViewChonNgay.setText(ngay + "/" + (thang + 1) + "/" + nam);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private int flash = 0; // 1: catincome,  2: catexpense
    private com.nhom10.moneymanagement_android.models.catincome catincome;
    private com.nhom10.moneymanagement_android.models.catexpense catexpense;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String cat = data.getStringExtra("category");
                if(cat.equals("catincome")){
                    catincome = (catincome) data.getSerializableExtra("catincome");
                    try {
                        UpdateChonNhomIncome();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else if(cat.equals("catexpense")){
                    catexpense = (catexpense) data.getSerializableExtra("catexpense");
                    try {
                        UpdateChonNhomExpense();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        }
    }

    private void UpdateChonNhomIncome() throws ExecutionException, InterruptedException {
        catIncomeViewModel = new CatIncomeViewModel(getApplication());
        catIncomeViewModel = ViewModelProviders.of(this).get(CatIncomeViewModel.class);
        catIncomeViewModel.getAllCatIncome().observe(this, new Observer<List<com.nhom10.moneymanagement_android.models.catincome>>() {
            @Override
            public void onChanged(@Nullable List<com.nhom10.moneymanagement_android.models.catincome> catincomes) {
                for(catincome ci : catincomes ){
                    if(ci.getId() == catincome.getId()){
                        textViewChonNhom.setText(ci.getName());
                        imageViewChonNhom.setImageResource(ci.getImage());
                        flash = 1;
                        return;
                    }
                }
                flash = 0;
                textViewChonNhom.setText("Chọn nhóm");
                imageViewChonNhom.setImageResource(R.drawable.list);
            }
        });
    }
    private void UpdateChonNhomExpense() throws ExecutionException, InterruptedException {
        catExpenseViewModel = new CatExpenseViewModel(getApplication());
        catExpenseViewModel = ViewModelProviders.of(this).get(CatExpenseViewModel.class);
        catExpenseViewModel.getAllCatExpense().observe(this, new Observer<List<com.nhom10.moneymanagement_android.models.catexpense>>() {
            @Override
            public void onChanged(@Nullable List<com.nhom10.moneymanagement_android.models.catexpense> catexpenses) {
                for(catexpense ce : catexpenses ){
                    if(ce.getId() == catexpense.getId()){
                        textViewChonNhom.setText(ce.getName());
                        imageViewChonNhom.setImageResource(ce.getImage());
                        flash = 2;
                        return;
                    }
                }
                flash = 0;
                textViewChonNhom.setText("Chọn nhóm");
                imageViewChonNhom.setImageResource(R.drawable.list);
            }
        });
    }



}
