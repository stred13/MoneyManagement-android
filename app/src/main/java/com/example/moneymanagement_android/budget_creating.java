package com.example.moneymanagement_android;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneymanagement_android.adapters.budgetRecyclerViewAdapter;
import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.viewmodels.budgetViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class budget_creating extends AppCompatActivity {
    public static final int REQUEST_CODE = 1;
    private EditText etTenV;
    private EditText etNote;
    private Button btnTaoV;
    private TextView tvChonNgay;
    budgetViewModel bViewModel;
    budgetRecyclerViewAdapter recAdapter;
    budget b;
    LinearLayout a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_creating);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tạo ví");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etTenV = (EditText) findViewById(R.id.etTenV);
        etNote = (EditText) findViewById(R.id.mtGhichu);
        btnTaoV = (Button) findViewById(R.id.btnTaoV);
        tvChonNgay = (TextView) findViewById(R.id.eNgayTaoVi);
        a = (LinearLayout) findViewById(R.id.llCalender);

        bViewModel = ViewModelProviders.of(this).get(budgetViewModel.class);

        Calendar c = Calendar.getInstance();
        int ngay = c.get(Calendar.DATE);
        int thang = c.get(Calendar.MONTH) + 1;
        int nam = c.get(Calendar.YEAR);

        tvChonNgay.setText(ngay + "/" + thang + "/" + nam);

        btnTaoV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namev = etTenV.getText().toString();
                String note = etNote.getText().toString();
                //String currency = spCurrency.getSelectedItem().toString();
                Toast.makeText(getApplication().getApplicationContext(), "" + namev, Toast.LENGTH_SHORT).show();
                //insert
                b = new budget(namev, "demo", note);
                bViewModel.insertBudget(b);

                finish();
            }
        });

        tvChonNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication().getApplicationContext(), "asdassss23", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    int f = 0;

    private void ChonNgay() {
        final Calendar calendar = Calendar.getInstance();

        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        int dayweek = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                tvChonNgay.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

}

