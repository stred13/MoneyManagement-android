package com.example.moneymanagement_android;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneymanagement_android.adapters.StatisticExpenseBudgetAdapter;
import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.models.catexpense;
import com.example.moneymanagement_android.models.expense;
import com.example.moneymanagement_android.models.income;
import com.example.moneymanagement_android.utils.Util;
import com.example.moneymanagement_android.viewmodels.CatExpenseViewModel;
import com.example.moneymanagement_android.viewmodels.IncomeViewModel;
import com.example.moneymanagement_android.viewmodels.expenseViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class activity_expense extends AppCompatActivity {
    private PieChart pieChart;
    private TextView txtExpenseBudget;
    private TextView txtErrorExpenseBudgetList;
    private View largestExpenseBudgetErrorHolder;
    private RecyclerView rvExpenseBudget;
    private CatExpenseViewModel catExpenseViewModel;
    private StatisticExpenseBudgetAdapter expenseAdapter;
    private View largestExpenseBudgetContainer;
    private ImageView imgExpenseBudgetMax;
    private TextView txtExpenseBudgetMax;
    private TextView txtExpenseBudgetDateMax;
    private TextView txtExpenseBudgetMoneyMax;


    private RecyclerView.LayoutManager expenseLayoutManager;
    private List<expense> expenseList = new ArrayList<>();
    private List<catexpense> catexpenseList = new ArrayList<>();

    private Calendar calendar = Calendar.getInstance();
    private List<PieEntry> pieEntries = new ArrayList<>();
    private Date selectedDate;

    private expenseViewModel expenseViewModel;
    budget b;
    int totalExpense = 0;
    String statisticDayFrom = "";
    String statisticDayTo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Intent i = getIntent();
        b = (budget) i.getSerializableExtra("budget");
        statisticDayFrom = i.getStringExtra("dayFrom");
        statisticDayTo = i.getStringExtra("dayTo");

        Toast.makeText(this,statisticDayFrom + "--" + statisticDayTo,Toast.LENGTH_SHORT).show();

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chi Tiêu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        pieChart = findViewById(R.id.pie_chart_expense_budget);
        txtExpenseBudget = findViewById(R.id.txtExpenseBudget);
        txtErrorExpenseBudgetList = findViewById(R.id.txtErrorExpenseBudgetList);
        largestExpenseBudgetErrorHolder = findViewById(R.id.largestExpenseBudgetErrorHolder);
        rvExpenseBudget = findViewById(R.id.rvListExpenseBudget);

        largestExpenseBudgetContainer = findViewById(R.id.largestExpenseBudgetContainer);
        imgExpenseBudgetMax = findViewById(R.id.imgExpenseBudgetMax);
        txtExpenseBudgetMax = findViewById(R.id.txtExpenseBudgetMax);
        txtExpenseBudgetDateMax = findViewById(R.id.txtExpenseBudgetDateMax);
        txtExpenseBudgetMoneyMax = findViewById(R.id.txtExpenseBudgetMoneyMax);


        expenseLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvExpenseBudget.setLayoutManager(expenseLayoutManager);

        expenseList = new ArrayList<>();
        expenseAdapter = new StatisticExpenseBudgetAdapter(expenseList, getApplicationContext());
        expenseAdapter.setCatexpenseList(catexpenseList);
        rvExpenseBudget.setAdapter(expenseAdapter);



        //initialPieChart();
        retriveDataFromdb();


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void initialPieChart() {

        pieEntries.clear();
        if (totalExpense == 0) {
            pieChart.invalidate();
            pieChart.clear();
            return;
        }
        Map<catexpense, Long> expenseByCate = calculateExpenseByCategory();
        for (Map.Entry<catexpense, Long> entry : expenseByCate.entrySet()) {
            catexpense catexpense = entry.getKey();
            long value = entry.getValue();
            if(value!= 0) {
                pieEntries.add(new PieEntry((float) ((value * 1.0) / totalExpense) * 100, catexpense.getName()));
            }
        }

        pieChart.invalidate();
        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(10f);
        dataSet.setValueLinePart1Length(1f);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        PieData pieData = new PieData(dataSet);
        pieData.setValueTextColor(Color.WHITE);
        pieData.setValueTextSize(12f);
        pieChart.setData(pieData);
        pieChart.getDescription().setText("");
        pieChart.animateY(500);
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(15);
        pieChart.setHoleColor(android.R.color.transparent);
        pieChart.setTransparentCircleRadius(10);
        pieChart.setRotationAngle(0);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setRotationEnabled(true);
    }

    private void retriveDataFromdb() {
//        Calendar c = Calendar.getInstance();   // this takes current date
//        Date date = c.getTime();
//        selectedDate = date;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String dateStr = sdf.format(date);
//        Log.d("AAAA",dateStr);
        setupExpense(statisticDayFrom,statisticDayTo);

    }
    private void setupExpense(String timeFrom, String timeTo) {
        try {
            expenseViewModel = new expenseViewModel(getApplication());
            expenseViewModel = ViewModelProviders.of(this).get(expenseViewModel.class);
            expenseViewModel.getAllExpenseByDateBudget(timeFrom,timeTo ,b.getId()).observe(this, new Observer<List<expense>>() {
                @Override
                public void onChanged(@Nullable List<expense> expenses) {
                    expenseList = expenses;
                    totalExpense = 0;
                    for (expense expense : expenses) {
                        totalExpense += expense.getNmoney();
                    }
                    txtExpenseBudget.setText(Util.formatCurrency(totalExpense));


                    getCategoryExpense();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCategoryExpense() {
        try {
            catExpenseViewModel = new CatExpenseViewModel(this.getApplication());
            catExpenseViewModel = ViewModelProviders.of(this).get(CatExpenseViewModel.class);
            catExpenseViewModel.getAllCatExpense().observe(this, new Observer<List<catexpense>>() {
                @Override
                public void onChanged(@Nullable List<catexpense> catexpenses) {
                    catexpenseList = catexpenses;
                    if (!expenseList.isEmpty()) {
                        expenseAdapter.setCatexpenseList(catexpenseList);
                        largestExpenseBudgetErrorHolder.setVisibility(View.GONE);
                        largestExpenseBudgetContainer.setVisibility(View.VISIBLE);
                        expense largestExpense = expenseList.get(0);
                        for (catexpense catex : catexpenseList) {
                            if (catex.getId() == largestExpense.getIdcatex()) {
                                //Picasso.get().load(catex.getImage()).error(R.drawable.breakfast).into(imgExpenseMax);
                                break;
                            }
                        }
                        setupUI();
                        initialPieChart();
                    } else {
                        largestExpenseBudgetContainer.setVisibility(View.GONE);
                        largestExpenseBudgetErrorHolder.setVisibility(View.VISIBLE);
                    }
                    setupExpenseAdapter();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupExpenseAdapter() {
        if (expenseList.isEmpty()) {
            txtErrorExpenseBudgetList.setVisibility(View.VISIBLE);
            rvExpenseBudget.setVisibility(View.GONE);
            return;
        } else {
            txtErrorExpenseBudgetList.setVisibility(View.GONE);
            rvExpenseBudget.setVisibility(View.VISIBLE);
        }
        expenseAdapter.setExpenseuBudgetList(expenseList);
    }

//    private void setupBalanceUI() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String dateStr = sdf.format(getPreviousDate());
//        try {
//            expenseViewModel.getAllExpenseByDate(dateStr).observe(this, new Observer<List<expense>>() {
//                @Override
//                public void onChanged(@Nullable List<expense> expenses) {
//                    totalPrevExpense = 0;
//                    for (expense expense : expenses) {
//                        totalPrevExpense += expense.getNmoney();
//                    }
//                    txtFirstBalance.setText(Util.formatCurrency(totalPrevIncome - totalPrevExpense));
//                }
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private Date getPreviousDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(selectedDate);
        calendar.add(Calendar.MONTH, -1);
        Date previousDate = calendar.getTime();
        return previousDate;
    }

    private Map<catexpense, Long> calculateExpenseByCategory() {
        Map<catexpense, Long> values = new HashMap<>();
        for (catexpense catexpense : catexpenseList) {
            long total = 0;
            values.put(catexpense, total);
            for (expense expense : expenseList) {
                if (expense.getIdcatex() == catexpense.getId()) {
                    total += expense.getNmoney();
                }
            }
            values.put(catexpense, total);
        }
        return values;
    }

    private void setupUI() {
        if (expenseList.isEmpty()) return;
        expense largestExpense = expenseList.get(0);
        for(catexpense cat : catexpenseList){
            if(largestExpense.getIdcatex() == cat.getId()){
                txtExpenseBudgetMax.setText(cat.getName());
                imgExpenseBudgetMax.setImageResource(cat.getImage());
                break;
            }
        }
        // the largest one on top because the list has been sorted in descending order by the price
        txtExpenseBudgetMoneyMax.setText(Util.formatCurrency(largestExpense.getNmoney()));
        txtExpenseBudgetDateMax.setText(Util.formatDate(largestExpense.getDcreated()));

    }
}
