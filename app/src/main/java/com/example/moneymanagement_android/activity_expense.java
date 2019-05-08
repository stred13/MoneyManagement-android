package com.example.moneymanagement_android;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.moneymanagement_android.models.budget;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class activity_expense extends AppCompatActivity {
    private PieChart pieChart;
    private Calendar calendar = Calendar.getInstance();
    private List<PieEntry> pieEntries = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chi Tiêu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        pieChart = findViewById(R.id.pie_chart);
        initialPieChart();

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
            pieEntries.add(new PieEntry((float) (value * 100) * 1f, i % 2 == 0 ? "Người yêu" : "Du lịch"));
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
}
