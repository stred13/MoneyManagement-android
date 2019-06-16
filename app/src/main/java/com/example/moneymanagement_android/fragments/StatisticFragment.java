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
import com.squareup.picasso.Picasso;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    // dung de xac dinh layout theo hang ngang hay doc
    private RecyclerView.LayoutManager expenseLayoutManager, incomeLayoutManager;

    // cau noi view va data
    private StatisticExpenseAdapter expenseAdapter;
    private StatisticIncomeAdapter incomeAdapter;

    // du lieu can de hien thi danh sach trong recyclerview
    private List<expense> expenseList = new ArrayList<>();
    private List<income> incomeList = new ArrayList<>();
    private List<catexpense> catexpenseList = new ArrayList<>();
    private List<catincome> catincomeList = new ArrayList<>();

    private TextView txtIncome, txtExpense, txtExpenseMax,
            txtExpenseDateMax, txtExpenseMoneyMax, txtBudget,
            txtFirstBalance, txtLastBalance, txtDifferent, txtErrorExpenseList, txtErrorIncomeList;
    private View largestExpenseContainer, largestExpenseErrorHolder;
    private ImageView imgExpenseMax;
    private long totalExpense = 0;
    private long totalIncome = 0;
    private long totalPrevExpense = 0;
    private long totalPrevIncome = 0;
    private Date selectedDate;

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
        txtBudget = v.findViewById(R.id.txtBudget);
        txtFirstBalance = v.findViewById(R.id.txtFirstBalance);
        txtLastBalance = v.findViewById(R.id.txtLastBalance);
        txtDifferent = v.findViewById(R.id.txtDifferent);
        largestExpenseContainer = v.findViewById(R.id.largestExpenseContainer);
        largestExpenseErrorHolder = v.findViewById(R.id.largestExpenseErrorHolder);
        txtErrorExpenseList = v.findViewById(R.id.txtErrorExpenseList);
        txtErrorIncomeList = v.findViewById(R.id.txtErrorIncomeList);
        imgExpenseMax = v.findViewById(R.id.imgExpenseMax);
        rvExpense = v.findViewById(R.id.rvListExpense);
        rvIncome = v.findViewById(R.id.rvListIncome);

        expenseLayoutManager = new LinearLayoutManager(getContext());
        rvExpense.setLayoutManager(expenseLayoutManager);
        incomeLayoutManager = new LinearLayoutManager(getContext());
        rvIncome.setLayoutManager(incomeLayoutManager);

        expenseList = new ArrayList<>();
        incomeList = new ArrayList<>();

        expenseAdapter = new StatisticExpenseAdapter(expenseList , getContext());
        expenseAdapter.setCatexpenseList(catexpenseList);
        rvExpense.setAdapter(expenseAdapter);

        incomeAdapter = new StatisticIncomeAdapter(incomeList, getContext());
        incomeAdapter.setCatincomes(catincomeList);
        rvIncome.setAdapter(incomeAdapter);

        retriveDataFromdb();
        return v;
    }

    private void setupUI() {
        if (expenseList.isEmpty()) return;
        expense largestExpense = expenseList.get(0); // the largest one on top because the list has been sorted in descending order by the price
        txtExpenseMoneyMax.setText(Util.formatCurrency(largestExpense.getNmoney()));
        txtExpenseDateMax.setText(Util.formatDate(largestExpense.getDcreated()));
    }

    private void setupExpenseAdapter() {
        if (expenseList.isEmpty()) {
            txtErrorExpenseList.setVisibility(View.VISIBLE);
            rvExpense.setVisibility(View.GONE);
            return;
        } else {
            txtErrorExpenseList.setVisibility(View.GONE);
            rvExpense.setVisibility(View.VISIBLE);
        }
        expenseAdapter.setExpenseList(expenseList);

    }

    private void setupIncomeAdapter() {
        if (incomeList.isEmpty()) {
            txtErrorIncomeList.setVisibility(View.VISIBLE);
            rvIncome.setVisibility(View.GONE);
            return;
        } else {
            txtErrorIncomeList.setVisibility(View.GONE);
            rvIncome.setVisibility(View.VISIBLE);
        }
        incomeAdapter.setIncomeList(incomeList);
    }

    private void setupExpenseAndIncome(String dateStr) {
        try {
            expenseViewModel = new expenseViewModel(getActivity().getApplication());
            expenseViewModel = ViewModelProviders.of(this).get(expenseViewModel.class);
            expenseViewModel.getAllExpenseByDate(dateStr).observe(this, new Observer<List<expense>>() {
                @Override
                public void onChanged(@Nullable List<expense> expenses) {
                    expenseList = expenses;
                    totalExpense = 0;
                    for (expense expense : expenses) {
                        totalExpense += expense.getNmoney();
                    }
                    txtExpense.setText(Util.formatCurrency(totalExpense));
                    long budget = totalIncome - totalExpense;
                    setupUI();
                    getCategoryExpense();
                    setBudget(budget);
                }
            });

            incomeViewModel = new IncomeViewModel(getActivity().getApplication());
            incomeViewModel = ViewModelProviders.of(this).get(IncomeViewModel.class);
            incomeViewModel.getAllIncomeByDate(dateStr).observe(this, new Observer<List<income>>() {
                @Override
                public void onChanged(@Nullable List<income> incomes) {
                    incomeList = incomes;
                    totalIncome = 0;
                    for (income income : incomes) {
                        totalIncome += income.getNmoney();
                    }
                    txtIncome.setText(Util.formatCurrency(totalIncome));
                    long budget = totalIncome - totalExpense;

                    getCategoryIncome();
                    setupIncomeAdapter();
                    setBudget(budget);
                    setupBalanceUI();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void retriveDataFromdb() {
        Calendar c = Calendar.getInstance();   // this takes current date
        c.set(Calendar.DAY_OF_MONTH, 1);
        Date date = c.getTime();
        selectedDate = date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        setupExpenseAndIncome(dateStr);
    }

    private Date getPreviousDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(selectedDate);
        calendar.add(Calendar.MONTH, -1);
        Date previousDate = calendar.getTime();
        return previousDate;
    }

    private void setupBalanceUI() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(selectedDate);
        try {
            expenseViewModel.getAllExpenseBeforeDate(dateStr).observe(this, new Observer<List<expense>>() {
                @Override
                public void onChanged(@Nullable List<expense> expenses) {
                    totalPrevExpense = 0;
                    for (expense expense : expenses) {
                        totalPrevExpense += expense.getNmoney();
                    }
                    txtFirstBalance.setText(Util.formatCurrency(totalPrevIncome - totalPrevExpense));
                }
            });
            incomeViewModel.getAllIncomeBeforeDate(dateStr).observe(this, new Observer<List<income>>() {
                @Override
                public void onChanged(@Nullable List<income> incomes) {
                    totalPrevIncome = 0;
                    for (income income : incomes) {
                        totalPrevIncome += income.getNmoney();
                    }
                    long firstBalance = totalPrevIncome - totalPrevExpense;
                    txtFirstBalance.setText(Util.formatCurrency(firstBalance));
                    long lastBalance = totalPrevIncome + totalIncome;
                    txtLastBalance.setText(Util.formatCurrency(lastBalance));
                    txtDifferent.setText(Util.formatCurrency(lastBalance - firstBalance));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setBudget(long budget) {
        txtBudget.setText(Util.formatCurrency(budget));
        if (budget < 0) {
            txtBudget.setTextColor(getContext().getResources().getColor(R.color.holo_red_light));
        } else {
            txtBudget.setTextColor(getContext().getResources().getColor(R.color.holo_blue_light));
        }
    }

    private void getCategoryIncome(){
        catIncomeViewModel = ViewModelProviders.of(this).get(CatIncomeViewModel.class);
        try {
            catIncomeViewModel.getAllCatIncome().observe(this, new Observer<List<catincome>>() {
                @Override
                public void onChanged(@Nullable List<catincome> catincomes) {
                    catincomeList = catincomes;
                    incomeAdapter.setCatincomes(catincomeList);
                    initialPieChartIncome();
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getCategoryExpense() {
        try {
            catExpenseViewModel = ViewModelProviders.of(this).get(CatExpenseViewModel.class);
            catExpenseViewModel.getAllCatExpense().observe(this, new Observer<List<com.example.moneymanagement_android.models.catexpense>>() {
                @Override
                public void onChanged(@Nullable List<com.example.moneymanagement_android.models.catexpense> catexpenses) {
                    catexpenseList = catexpenses;
                    expenseAdapter.setCatexpenseList(catexpenseList);
                    if (!expenseList.isEmpty()) {
                        largestExpenseErrorHolder.setVisibility(View.GONE);
                        largestExpenseContainer.setVisibility(View.VISIBLE);
                        expense largestExpense = expenseList.get(0);
                        for (catexpense catex : catexpenseList) {
                            if (catex.getId() == largestExpense.getIdcatex()) {
                                txtExpenseMax.setText(catex.getName());
                                Picasso.get().load(catex.getImage()).error(R.drawable.breakfast).into(imgExpenseMax);
                                break;
                            }
                        }
                        initialPieChart();
                    } else {
                        largestExpenseContainer.setVisibility(View.GONE);
                        largestExpenseErrorHolder.setVisibility(View.VISIBLE);
                    }
                    setupExpenseAdapter();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            pieEntries.add(new PieEntry((float) ((value * 1.0) / totalIncome) * 100, catexpense.getName()));
        }

        pieChart.invalidate();
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

    private void initialPieChartIncome() {
        if (totalIncome == 0) {
            pieChart.invalidate();
            pieChart.clear();
            return;
        }
        pieIncome.clear();
        Map<catincome, Long> incomeByCate = calculateIncomeByCategory();
        for (Map.Entry<catincome, Long> entry : incomeByCate.entrySet()) {
            catincome catincome = entry.getKey();
            long value = entry.getValue();
            pieIncome.add(new PieEntry((float) ((value * 1.0) / totalIncome) * 100, catincome.getName()));
        }

        pieChartIncome.invalidate();
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
                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.set(Calendar.MONTH, selectedMonth);
                calendar.set(Calendar.YEAR, selectedYear);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                Date date = calendar.getTime();
                selectedDate = date;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = sdf.format(date);
                setupExpenseAndIncome(dateStr);
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
