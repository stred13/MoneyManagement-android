package com.example.moneymanagement_android.fragments;


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

import com.example.moneymanagement_android.R;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticFragment extends Fragment {

    private PieChart pieChart,pieChartIncome;
    private Calendar calendar = Calendar.getInstance();
    private List<PieEntry> pieEntries = new ArrayList<>();
    private List<PieEntry> pieIncome = new ArrayList<>();

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
        initialPieChart();
        initialPieChartIncome();
        return v;
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
