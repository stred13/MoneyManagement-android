package com.nhom10.moneymanagement_android;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhom10.moneymanagement_android.adapters.StatisticIncomeBudgetAdapter;
import com.nhom10.moneymanagement_android.models.budget;
import com.nhom10.moneymanagement_android.models.catincome;
import com.nhom10.moneymanagement_android.models.income;
import com.nhom10.moneymanagement_android.utils.Util;
import com.nhom10.moneymanagement_android.viewmodels.CatIncomeViewModel;
import com.nhom10.moneymanagement_android.viewmodels.IncomeViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_income extends AppCompatActivity {

    private PieChart pieChartIncome;
    private Calendar calendar = Calendar.getInstance();
    private List<PieEntry> pieIncome = new ArrayList<>();
    private List<income> incomeList;
    private List<catincome> catincomeList;
    private RecyclerView.LayoutManager incomeLayoutManager;
    private StatisticIncomeBudgetAdapter incomeBudgetAdapter;

    private TextView txtIncomeBudget;
    private View largestIncomeeBudgetErrorHolder;
    private View largestIncomeBudgetContainer;
    private ImageView imgIncomeBudgetMax;
    private TextView txtIncomeBudgetMax;
    private TextView txtIncomeBudgetDateMax;
    private TextView txtIncomeBudgetMoneyMax;
    private TextView txtErrorIncomeBudgetList;
    private RecyclerView rvListIncomeBudget;

    private Date selectedDate;

    private CatIncomeViewModel catIncomeViewModel;
    private IncomeViewModel incomeViewModel;

    int totalIncome = 0;
    String statisticDayFrom = "";
    String statisticDayTo = "";

    budget b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        pieChartIncome = findViewById(R.id.pie_chart_income);
        Intent i = getIntent();
        b = (budget) i.getSerializableExtra("budget");
        statisticDayFrom = i.getStringExtra("dayFrom");
        statisticDayTo = i.getStringExtra("dayTo");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thu Nhập");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtIncomeBudget = findViewById(R.id.txtIncomeBudget);
        largestIncomeeBudgetErrorHolder = findViewById(R.id.largestIncomeeBudgetErrorHolder);
        largestIncomeBudgetContainer = findViewById(R.id.largestIncomeBudgetContainer);
        imgIncomeBudgetMax = findViewById(R.id.imgIncomeBudgetMax);
        txtIncomeBudgetMax = findViewById(R.id.txtIncomeBudgetMax);
        txtIncomeBudgetDateMax = findViewById(R.id.txtIncomeBudgetDateMax);
        txtIncomeBudgetMoneyMax = findViewById(R.id.txtIncomeBudgetMoneyMax);
        txtErrorIncomeBudgetList = findViewById(R.id.txtErrorIncomeBudgetList);
        rvListIncomeBudget = findViewById(R.id.rvListIncomeBudget);


        incomeList = new ArrayList<>();
        catincomeList = new ArrayList<>();
        incomeLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvListIncomeBudget.setLayoutManager(incomeLayoutManager);

        incomeBudgetAdapter = new StatisticIncomeBudgetAdapter(getApplicationContext(), incomeList);
        incomeBudgetAdapter.setCatIncomeList(catincomeList);
        rvListIncomeBudget.setAdapter(incomeBudgetAdapter);


        retriveDataFromdb();


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initialPieChartIncome() {
        pieIncome.clear();

        if (totalIncome == 0) {
            pieChartIncome.invalidate();
            pieChartIncome.clear();
            return;
        }

        Map<catincome, Long> incomeByCate = calculateIncomeByCategory();
        for (Map.Entry<catincome, Long> entry : incomeByCate.entrySet()) {
            catincome catincome = entry.getKey();
            long value = entry.getValue();
            if (value != 0) {
                pieIncome.add(new PieEntry((float) ((value * 1.0) / totalIncome) * 100, catincome.getName()));
            }
        }


        pieChartIncome.invalidate();
        PieDataSet dataSet = new PieDataSet(pieIncome, "");
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        dataSet.setValueTextSize(10f);
        dataSet.setValueLinePart1Length(1f);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        PieData pieData = new PieData(dataSet);
        pieData.setValueTextColor(Color.WHITE);
        pieData.setValueTextSize(12f);
        pieChartIncome.setData(pieData);
        pieChartIncome.getDescription().setText("");
        pieChartIncome.animateY(500);
        pieChartIncome.setUsePercentValues(true);
        pieChartIncome.setDrawHoleEnabled(true);
        pieChartIncome.setHoleRadius(15);
        pieChartIncome.setHoleColor(android.R.color.transparent);
        pieChartIncome.setTransparentCircleRadius(10);
        pieChartIncome.setRotationAngle(0);
        pieChartIncome.setEntryLabelColor(Color.BLACK);
        pieChartIncome.setRotationEnabled(true);
    }

    private void retriveDataFromdb() {
        setupIncome(statisticDayFrom, statisticDayTo);
    }

    private void setupIncome(String dateFrom, String dateTo) {
        try {


            incomeViewModel = new IncomeViewModel(getApplication());
            incomeViewModel = ViewModelProviders.of(this).get(IncomeViewModel.class);
            incomeViewModel.getAllIncomeByDateBudget(dateFrom, dateTo, b.getId()).observe(this, new Observer<List<income>>() {
                @Override
                public void onChanged(@Nullable List<income> incomes) {
                    incomeList = incomes;
                    totalIncome = 0;
                    for (income income : incomes) {
                        totalIncome += income.getNmoney();
                    }
                    txtIncomeBudget.setText(Util.formatCurrency(totalIncome));

                    getCategoryIncome();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCategoryIncome() {
        try {
            catIncomeViewModel = new CatIncomeViewModel(getApplication());
            catIncomeViewModel = ViewModelProviders.of(this).get(CatIncomeViewModel.class);
            catIncomeViewModel.getAllCatIncome().observe(this, new Observer<List<catincome>>() {
                @Override
                public void onChanged(@Nullable List<catincome> catincomes) {
                    catincomeList = catincomes;
                    if (!incomeList.isEmpty()) {
                        incomeBudgetAdapter.setCatIncomeList(catincomeList);
                        largestIncomeeBudgetErrorHolder.setVisibility(View.GONE);
                        largestIncomeBudgetContainer.setVisibility(View.VISIBLE);

                        setupUI();
                        initialPieChartIncome();

                    } else {
                        largestIncomeBudgetContainer.setVisibility(View.GONE);
                        largestIncomeeBudgetErrorHolder.setVisibility(View.VISIBLE);
                    }
                    setupIncomeAdapter();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupUI() {
        if (incomeList.isEmpty())
            return;
        income income = incomeList.get(0);
        for (catincome catincome : catincomeList) {
            if (catincome.getId() == income.getIdcatin()) {
                txtIncomeBudgetMax.setText(catincome.getName());
                imgIncomeBudgetMax.setImageResource(catincome.getImage());
                break;
            }
        }
        txtIncomeBudgetMoneyMax.setText(Util.formatCurrency(income.getNmoney()));
        txtIncomeBudgetDateMax.setText(Util.formatDate(income.getDcreated()));

    }

    private Map<catincome, Long> calculateIncomeByCategory() {
        Map<catincome, Long> values = new HashMap<>();
        for (catincome catincome : catincomeList) {
            long total = 0;
            values.put(catincome, total);
            for (income income : incomeList) {
                if (income.getIdcatin() == catincome.getId()) {
                    total += income.getNmoney();
                }
            }
            values.put(catincome, total);
        }
        return values;
    }

    private void setupIncomeAdapter() {
        if (incomeList.isEmpty()) {
            txtErrorIncomeBudgetList.setVisibility(View.VISIBLE);
            rvListIncomeBudget.setVisibility(View.GONE);
        } else {
            txtErrorIncomeBudgetList.setVisibility(View.GONE);
            rvListIncomeBudget.setVisibility(View.VISIBLE);
        }
        incomeBudgetAdapter.setIncomeList(incomeList);
    }

}
