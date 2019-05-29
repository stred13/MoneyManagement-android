package com.example.moneymanagement_android.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneymanagement_android.R;
import com.example.moneymanagement_android.activity_expense;
import com.example.moneymanagement_android.activity_income;
import com.example.moneymanagement_android.adapters.budgetRecyclerViewAdapter;
import com.example.moneymanagement_android.adapters.expenseRecyclerViewAdapter;
import com.example.moneymanagement_android.adapters.incomeRecyclerViewAdapter;
import com.example.moneymanagement_android.infobudget;
import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.models.catexpense;
import com.example.moneymanagement_android.models.catincome;
import com.example.moneymanagement_android.models.expense;
import com.example.moneymanagement_android.models.income;
import com.example.moneymanagement_android.utils.Util;
import com.example.moneymanagement_android.viewmodels.CatExpenseViewModel;
import com.example.moneymanagement_android.viewmodels.CatIncomeViewModel;
import com.example.moneymanagement_android.viewmodels.IncomeViewModel;
import com.example.moneymanagement_android.viewmodels.expenseViewModel;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends Fragment {

    CardView cardViewTongQuanChiTieu;
    View v;
    incomeRecyclerViewAdapter exRVAdapter;
    RecyclerView exRecyclerView;
    List<income> listExpense = new ArrayList<>();
    List<catincome> catexpenseList = new ArrayList<>();

    IncomeViewModel eViewModel;
    CatIncomeViewModel catExpenseViewModel;
    ImageView editThuoc;
    View largestExpenseFragnemntErrorHolder;
    TextView txtExpenseTitle;
    TextView txtExpenseRangeTime;

    private Date selectedDate;
    private Calendar calendar = Calendar.getInstance();

    private IncomeViewModel expenseViewModel;
    private int totalExpense = 0;

    private TextView txtExpenseBudget;
    private TextView txtRangeTimeForm;
    private TextView txtRangeTimeTo;
    private TextView txtErroRangeTimeFrom;
    private TextView txtErroRangeTimeTo;
    private TextView txtErrorRangeTime;

    private String statisticDayFrom = "";
    private String statisticDayTo = "";

    budget b;

    public IncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_income, container, false);


        cardViewTongQuanChiTieu = (CardView) v.findViewById(R.id.cardViewTongQuanChiTieu);
        cardViewTongQuanChiTieu.setOnClickListener(onCardViewClickListener);
        exRecyclerView = (RecyclerView) v.findViewById(R.id.exRecyclerView);
        largestExpenseFragnemntErrorHolder = v.findViewById(R.id.largestExpenseFragnemntErrorHolder);
        txtExpenseBudget = v.findViewById(R.id.txtExpenseBudget);
        txtExpenseTitle = v.findViewById(R.id.txtExpenseTitle);
        txtExpenseRangeTime = v.findViewById(R.id.txtExpenseRangeTime);


        exRVAdapter = new incomeRecyclerViewAdapter(getActivity(), listExpense);
        exRVAdapter.setCatincomeList(catexpenseList);
        exRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        exRecyclerView.setAdapter(exRVAdapter);

        retriveDataFromdb();

        return v;
    }

    private View.OnClickListener onCardViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent infoBudget = new Intent(getContext().getApplicationContext(), activity_income.class);
            infoBudget.putExtra("budget", b);
            infoBudget.putExtra("dayFrom", statisticDayFrom);
            infoBudget.putExtra("dayTo", statisticDayTo);
            startActivity(infoBudget);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Intent i = this.getActivity().getIntent();
        b = (budget) i.getSerializableExtra("budget");

        try {
            eViewModel = new IncomeViewModel(getActivity().getApplication());
            eViewModel = ViewModelProviders.of(this).get(IncomeViewModel.class);
            eViewModel.getAllIncomebyBudget(infobudget.b.getId()).observe(this, new Observer<List<income>>() {
                @Override
                public void onChanged(@Nullable List<income> expenses) {
                    if (expenses != null) {
                        listExpense = expenses;
                        exRVAdapter.setIncomeBudgetList(listExpense);
                    }
                }
            });
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }
    private void retriveDataFromdb() {
        txtExpenseRangeTime.setVisibility(View.GONE);
        txtExpenseTitle.setText("Tổng quan chi tiêu trong tháng");
        Calendar c = Calendar.getInstance();
        Date dateCurrent = c.getTime();

        c.set(Calendar.DAY_OF_MONTH, 1);
        Date date = c.getTime();
        selectedDate = date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateFrom = sdf.format(date);
        String dateTo = sdf.format(dateCurrent);
        statisticDayFrom = dateFrom;
        statisticDayTo = dateTo;
        setupExpense(dateFrom, dateTo);
    }

    private void setupExpense(String dateFrom, String dateTo) {
        try {
            expenseViewModel = new IncomeViewModel(getActivity().getApplication());
            expenseViewModel = ViewModelProviders.of(this).get(IncomeViewModel.class);
            expenseViewModel.getAllIncomeBudgetRangeTime(dateFrom, dateTo, b.getId()).observe(this, new Observer<List<income>>() {
                @Override
                public void onChanged(@Nullable List<income> incomes) {
                    listExpense = incomes;
                    totalExpense = 0;
                    for (income expense : incomes) {
                        totalExpense += expense.getNmoney();
                    }
                    txtExpenseBudget.setText(Util.formatCurrency(totalExpense));


                    getCategoryExpense();

                    //setupBalanceUI();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCategoryExpense() {
        try {
            catExpenseViewModel = new CatIncomeViewModel(getActivity().getApplication());
            catExpenseViewModel = ViewModelProviders.of(this).get(CatIncomeViewModel.class);
            catExpenseViewModel.getAllCatIncome().observe(this, new Observer<List<catincome>>() {
                @Override
                public void onChanged(@Nullable List<catincome> catexpenses) {
                    catexpenseList = catexpenses;
                    if (!listExpense.isEmpty()) {
                        exRVAdapter.setCatincomeList(catexpenseList);
                        largestExpenseFragnemntErrorHolder.setVisibility(View.GONE);
                        income largestExpense = listExpense.get(0);
                        //setupUI();

                    } else {
                        largestExpenseFragnemntErrorHolder.setVisibility(View.VISIBLE);
                        //largestExpenseBudgetErrorHolder.setVisibility(View.VISIBLE);
                    }
                    //setupExpenseAdapter();
                    exRVAdapter.setIncomeBudgetList(listExpense);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.custom_infbudget_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuThongBao:
                Toast.makeText(getActivity(), "thong bao", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuKhoangThoiGian:
                //Toast.makeText(getApplicationContext(),"khoang thoi gian",Toast.LENGTH_SHORT).show();
                DialogRangeTime();
                break;
            case R.id.menuTuongLai:
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, 1);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateFrom = sdf.format(c.getTime());
                String dateTo = "2050-12-31";
                statisticDayFrom = dateFrom;
                statisticDayTo = dateTo;
                setupExpense(dateFrom, dateTo);
                txtExpenseRangeTime.setVisibility(View.GONE);
                txtExpenseTitle.setText("Tổng quan chi tiêu tương lai");
                break;
            case R.id.calendarInMonth:
                setupDatePicker();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void DialogRangeTime() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_range_time);
        dialog.setCanceledOnTouchOutside(false);
        Button btnDongY = (Button) dialog.findViewById(R.id.btnDongy);
        Button btnHuy = (Button) dialog.findViewById(R.id.btnHuy);
        txtRangeTimeForm = (TextView) dialog.findViewById(R.id.txtRangeTimeForm);
        txtRangeTimeTo = (TextView) dialog.findViewById(R.id.txtRangeTimeTo);
        txtErroRangeTimeFrom = (TextView) dialog.findViewById(R.id.txtErroRangeTimeForm);
        txtErroRangeTimeTo = (TextView) dialog.findViewById(R.id.txtErroRangeTimeTo);
        txtErrorRangeTime = (TextView) dialog.findViewById(R.id.txtErrorRangeTime);

        txtRangeTimeForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgayFrom();
            }
        });
        txtRangeTimeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgayTo();
            }
        });


        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtRangeTimeForm.getText().equals("../../....")) {
                    txtErroRangeTimeFrom.setVisibility(View.VISIBLE);
                    return;
                }
                if (txtRangeTimeTo.getText().equals("../../....")) {
                    txtErroRangeTimeFrom.setVisibility(View.GONE);
                    txtErroRangeTimeTo.setVisibility(View.VISIBLE);
                    return;
                }
                String dateFrom = (String) txtRangeTimeForm.getText();
                String dateTo = (String) txtRangeTimeTo.getText();
                dateFrom = Util.FromatStringToDate(dateFrom);
                dateTo = Util.FromatStringToDate(dateTo);


                int intDayFrom = Integer.parseInt(dateFrom.replace("-", ""));
                int intDayTo = Integer.parseInt(dateTo.replace("-", ""));
                Toast.makeText(getActivity(), intDayFrom + " -- " + intDayTo, Toast.LENGTH_SHORT).show();
                if (intDayFrom > intDayTo) {
                    txtErroRangeTimeFrom.setVisibility(View.GONE);
                    txtErroRangeTimeTo.setVisibility(View.GONE);
                    txtErrorRangeTime.setVisibility(View.VISIBLE);
                    return;
                }


                dialog.cancel();
                txtExpenseRangeTime.setVisibility(View.VISIBLE);
                txtExpenseTitle.setText("Tổng quan chi tiêu");
                txtExpenseRangeTime.setText(Util.FromatStringToDateDDMM(dateFrom) + " ---> " + Util.FromatStringToDateDDMM(dateTo));
                statisticDayFrom = dateFrom;
                statisticDayTo = dateTo;
                setupExpense(dateFrom, dateTo);


            }
        });

        dialog.show();
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
                String dateFrom = sdf.format(date);
                //get date end of month
                int dayEndMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                calendar.set(Calendar.DAY_OF_MONTH, dayEndMonth);
                date = calendar.getTime();
                String dateTo = sdf.format(date);
                statisticDayFrom = dateFrom;
                statisticDayTo = dateTo;
                setupExpense(dateFrom, dateTo);
                txtExpenseRangeTime.setVisibility(View.GONE);
                txtExpenseRangeTime.setText(Util.FromatStringToDateDDMM(dateFrom) + " ---> " + Util.FromatStringToDateDDMM(dateTo));
                txtExpenseTitle.setText("Tổng quan chi tiêu tháng " + (selectedMonth + 1));

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));

        builder.setActivatedMonth(calendar.JANUARY)
                .setMinYear(1990)
                .setActivatedYear(2019)
                .setMaxYear(2050)
                .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER).build().show();
    }

    private void ChonNgayFrom() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                txtRangeTimeForm.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void ChonNgayTo() {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                txtRangeTimeTo.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);


        datePickerDialog.show();
    }


}
