package com.example.moneymanagement_android;

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

public class activity_income extends AppCompatActivity {

    private PieChart pieChartIncome;
    private Calendar calendar = Calendar.getInstance();
    private List<PieEntry> pieIncome = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        pieChartIncome = findViewById(R.id.pie_chart_income);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thu Nhập");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initialPieChartIncome();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
