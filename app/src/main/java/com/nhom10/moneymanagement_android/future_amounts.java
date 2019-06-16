package com.nhom10.moneymanagement_android;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class future_amounts extends AppCompatActivity {
    private PieChart pieChart,pieChartIncome;
    private Calendar calendar = Calendar.getInstance();
    private List<PieEntry> pieEntries = new ArrayList<>();
    private List<PieEntry> pieIncome = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_amounts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tương lai");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pieChart = findViewById(R.id.pie_chart);
        pieChartIncome = findViewById(R.id.pie_chart_income);
        initialPieChart();
        initialPieChartIncome();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
}
