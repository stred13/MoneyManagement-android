package com.example.moneymanagement_android.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moneymanagement_android.R;
import com.example.moneymanagement_android.models.catexpense;
import com.example.moneymanagement_android.models.catincome;
import com.example.moneymanagement_android.models.expense;
import com.example.moneymanagement_android.models.income;
import com.example.moneymanagement_android.repositories.CatIncomeRepository;
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

    private List<expense> expenseList = new ArrayList<>();
    private List<income> incomeList = new ArrayList<>();

    private TextView txtIncome, txtExpense;

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
        initialPieChart();
        initialPieChartIncome();
        retriveDataFromdb();
        return v;
    }

    private void retriveDataFromdb() {
        try {
            expenseViewModel = new expenseViewModel(getActivity().getApplication());
            expenseViewModel = ViewModelProviders.of(this).get(expenseViewModel.class);
            expenseViewModel.getAllExpenseByDate("2019-05-01").observe(this, new Observer<List<expense>>() {
                @Override
                public void onChanged(@Nullable List<expense> expenses) {
                    expenseList = expenses;
                    long totalExpense = 0;
                    for (expense expense : expenses) {
                        totalExpense += expense.getNmoney();
                    }
                    txtExpense.setText(Util.formatCurrency(totalExpense));
                }
            });

            incomeViewModel = new IncomeViewModel(getActivity().getApplication());
            incomeViewModel = ViewModelProviders.of(this).get(IncomeViewModel.class);
            incomeViewModel.getAllIncomeByDate("2019-05-01").observe(this, new Observer<List<income>>() {
                @Override
                public void onChanged(@Nullable List<income> incomes) {
                    incomeList = incomes;
                    long totalIncome = 0;
                    for (income income : incomes) {
                        totalIncome += income.getNmoney();
                    }
                    txtIncome.setText(Util.formatCurrency(totalIncome));
                }
            });

            catExpenseViewModel = ViewModelProviders.of(this).get(CatExpenseViewModel.class);
            String name = "Ăn uống";
            String image = "123";
            catexpense catexpense = new catexpense(name, image);
            catExpenseViewModel.insertCatExpense(catexpense);

            catIncomeViewModel = ViewModelProviders.of(this).get(CatIncomeViewModel.class);
            String name2 = "Lương";
            String image2 = "123";
            catincome catincome = new catincome(name2, image2);
            catIncomeViewModel.insertCatIncome(catincome);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void initialPieChart() {
        pieEntries.clear();
        Random rand = new Random();
        for (int i = 0; i < 2; i++) {
            int value = rand.nextInt(50);
            pieEntries.add(new PieEntry((float) (value * 100) * 1f, i % 2 == 0 ? "Ăn uống" : "Du lịch"));
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
        pieIncome.clear();
        Random rand = new Random();
        for (int i = 0; i < 2; i++) {
            int value = rand.nextInt(50);
            pieIncome.add(new PieEntry((float) (value * 100) * 1f, i % 2 == 0 ? "Lương" : "Bán đồ"));
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
