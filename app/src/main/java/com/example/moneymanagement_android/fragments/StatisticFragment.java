package com.example.moneymanagement_android.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moneymanagement_android.R;
import com.example.moneymanagement_android.adapters.StatisticExpenseAdapter;
import com.example.moneymanagement_android.adapters.StatisticIncomeAdapter;

import com.example.moneymanagement_android.models.catexpense;
import com.example.moneymanagement_android.models.catincome;
import com.example.moneymanagement_android.models.expense;
import com.example.moneymanagement_android.models.income;
import com.example.moneymanagement_android.utils.Util;
import com.example.moneymanagement_android.viewmodels.CatExpenseViewModel;
import com.example.moneymanagement_android.viewmodels.CatIncomeViewModel;
import com.example.moneymanagement_android.viewmodels.IncomeViewModel;
import com.example.moneymanagement_android.viewmodels.expenseViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticFragment extends Fragment {

    private PieChart pieChart,pieChartIncome;
    private Calendar calendar = Calendar.getInstance();
    private List<PieEntry> pieEntries = new ArrayList<>();
    private List<PieEntry> pieIncome = new ArrayList<>();
    private expenseViewModel expenseViewModel;
    private IncomeViewModel incomeViewModel;
    private CatExpenseViewModel catExpenseViewModel;
    private CatIncomeViewModel catIncomeViewModel;
    private RecyclerView rvExpense, rvIncome;
    private RecyclerView.LayoutManager expenseLayoutManager, incomeLayoutManager;
    private StatisticExpenseAdapter expenseAdapter;
    private StatisticIncomeAdapter incomeAdapter;

    private List<expense> expenseList = new ArrayList<>();
    private List<income> incomeList = new ArrayList<>();

    private TextView txtIncome, txtExpense, txtExpenseMax, txtExpenseDateMax, txtExpenseMoneyMax;
    private ImageView imgExpenseMax;
    private long totalExpense = 0;
    private long totalIncome = 0;

    public StatisticFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_statistic, container, false);
        pieChart = v.findViewById(R.id.pie_chart);
        pieChartIncome = v.findViewById(R.id.pie_chart_income);
        txtExpense = v.findViewById(R.id.txtExpense);
        txtIncome = v.findViewById(R.id.txtIncome);
        txtExpenseMax = v.findViewById(R.id.txtExpenseMax);
        txtExpenseDateMax = v.findViewById(R.id.txtExpenseDateMax);
        txtExpenseMoneyMax = v.findViewById(R.id.txtExpenseMoneyMax);
        imgExpenseMax = v.findViewById(R.id.imgExpenseMax);
        rvExpense = v.findViewById(R.id.rvListExpense);
        rvIncome = v.findViewById(R.id.rvListIncome);

        expenseLayoutManager = new LinearLayoutManager(getContext());
        rvExpense.setLayoutManager(expenseLayoutManager);
        incomeLayoutManager = new LinearLayoutManager(getContext());
        rvIncome.setLayoutManager(incomeLayoutManager);


        retriveDataFromdb();
        return v;
    }

    private void setupUI() {
        if(expenseList.size()>0) {
            expense largestExpense = expenseList.get(0); // the largest one on top because the list has been sorted in descending order by the price
            txtExpenseMoneyMax.setText(Util.formatCurrency(largestExpense.getNmoney()));
            txtExpenseDateMax.setText(Util.formatDate(largestExpense.getDcreated()));
            txtExpenseMax.setText(largestExpense.getName());
        }
    }

    private void setupExpenseAdapter() {
        expenseAdapter = new StatisticExpenseAdapter(expenseList , getContext());
        rvExpense.setAdapter(expenseAdapter);
    }

    private void setupIncomeAdapter() {
        incomeAdapter = new StatisticIncomeAdapter(incomeList, getContext());
        rvIncome.setAdapter(incomeAdapter);
    }

    private void retriveDataFromdb() {
        try {
            expenseViewModel = new expenseViewModel(getActivity().getApplication());
            expenseViewModel = ViewModelProviders.of(this).get(expenseViewModel.class);
            expenseViewModel.getAllExpenseByDate("2019-05-01").observe(this, new Observer<List<expense>>() {
                @Override
                public void onChanged(@Nullable List<expense> expenses) {
                    expenseList = expenses;
                    totalExpense = 0;

                    for (expense expense : expenses) {
                        totalExpense += expense.getNmoney();
                    }
                    txtExpense.setText(Util.formatCurrency(totalExpense));
                    setupUI();
                    initialPieChart();
                    setupExpenseAdapter();
                }
            });

            incomeViewModel = new IncomeViewModel(getActivity().getApplication());
            incomeViewModel = ViewModelProviders.of(this).get(IncomeViewModel.class);
            incomeViewModel.getAllIncomeByDate("2019-05-01").observe(this, new Observer<List<income>>() {
                @Override
                public void onChanged(@Nullable List<income> incomes) {
                    incomeList = incomes;
                    totalIncome = 0;
                    for (income income : incomes) {
                        totalIncome += income.getNmoney();
                    }
                    txtIncome.setText(Util.formatCurrency(totalIncome));
                    initialPieChartIncome();
                    setupIncomeAdapter();
                }
            });

            catExpenseViewModel = ViewModelProviders.of(this).get(CatExpenseViewModel.class);

            //image (int)

//            String name = "Ăn uống";
//            String image = "123";
//            catexpense catexpense = new catexpense(name, image);
//            catExpenseViewModel.insertCatExpense(catexpense);
//
//            catIncomeViewModel = ViewModelProviders.of(this).get(CatIncomeViewModel.class);
//            String name2 = "Lương";
//            String image2 = "123";
//            catincome catincome = new catincome(name2, image2);
//            catIncomeViewModel.insertCatIncome(catincome);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void initialPieChart() {
        pieEntries.clear();
        if (totalExpense == 0) return;
        for (expense expense : expenseList) {
            pieEntries.add(new PieEntry((float) ((expense.getNmoney() * 1.0) / totalExpense) * 100, expense.getName()));
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(10f);
        dataSet.setValueLinePart1Length(1f);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
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

    private void initialPieChartIncome() {
        if (totalIncome == 0) return;
        pieIncome.clear();
        for (income income : incomeList) {
            pieIncome.add(new PieEntry((float) ((income.getNmoney() * 1.0) / totalIncome) * 100, income.getName()));
        }
        PieDataSet dataSet = new PieDataSet(pieIncome, "");
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        dataSet.setValueTextSize(10f);
        dataSet.setValueLinePart1Length(1f);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.custom_statistic_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_calendar:
                setupDatePicker();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDatePicker() {
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getActivity(), new MonthPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int selectedMonth, int selectedYear) {
                // TODO: handle selected month and year in here
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));

        builder.setActivatedMonth(Calendar.JANUARY)
                .setMinYear(1990)
                .setActivatedYear(2019)
                .setMaxYear(2050)
                .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER).build().show();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_settings);
        if (item != null) {
            item.setVisible(false);
        }
    }
}
