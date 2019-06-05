package com.example.moneymanagement_android.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneymanagement_android.R;
import com.example.moneymanagement_android.activity_expense;
import com.example.moneymanagement_android.adapters.ParenExpenseRecyclerViewAdapter;
import com.example.moneymanagement_android.adapters.expenseRecyclerViewAdapter;
import com.example.moneymanagement_android.category_update;
import com.example.moneymanagement_android.future_amounts;
import com.example.moneymanagement_android.infoExpense;
import com.example.moneymanagement_android.infobudget;
import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.models.catexpense;
import com.example.moneymanagement_android.models.expense;
import com.example.moneymanagement_android.utils.Util;
import com.example.moneymanagement_android.viewmodels.CatExpenseViewModel;
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
public class ExpenseBudgetFragment extends Fragment {

    CardView cardViewTongQuanChiTieu;
    View v;
    ParenExpenseRecyclerViewAdapter exRVAdapter;
    RecyclerView exRecyclerView;
    List<expense> listExpense = new ArrayList<>();
    List<catexpense> catexpenseList = new ArrayList<>();

    expenseViewModel eViewModel;
    CatExpenseViewModel catExpenseViewModel;
    ImageView editThuoc;
    View largestExpenseFragnemntErrorHolder;
    TextView txtExpenseTitle;
    TextView txtExpenseRangeTime;

    private Date selectedDate;
    private Calendar calendar = Calendar.getInstance();

    private expenseViewModel expenseViewModel;
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

    public ExpenseBudgetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_expense_budget, container, false);


        cardViewTongQuanChiTieu = (CardView) v.findViewById(R.id.cardViewTongQuanChiTieu);
        cardViewTongQuanChiTieu.setOnClickListener(onCardViewClickListener);
        exRecyclerView = (RecyclerView) v.findViewById(R.id.ParentRV);
        largestExpenseFragnemntErrorHolder = v.findViewById(R.id.largestExpenseFragnemntErrorHolder);
        txtExpenseBudget = v.findViewById(R.id.txtExpenseBudget);
        txtExpenseTitle = v.findViewById(R.id.txtExpenseTitle);
        txtExpenseRangeTime = v.findViewById(R.id.txtExpenseRangeTime);


        exRVAdapter = new ParenExpenseRecyclerViewAdapter(getActivity(), catexpenseList);
        //getCategoryExpense();
        //exRVAdapter.setCatexpenseList(catexpenseList);
        exRVAdapter.setExpenseBudgetList(listExpense);
        //exRVAdapter.setOnItemClickListener(onItemClickListener);
        exRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        exRecyclerView.setAdapter(exRVAdapter);

        retriveDataFromdb();

        return v;
    }

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            expenseRecyclerViewAdapter.MyViewHolder viewHolder = (expenseRecyclerViewAdapter.MyViewHolder) view.getTag();
            int pos = viewHolder.getAdapterPosition();
            expense e = listExpense.get(pos);

            Intent infoEx = new Intent(getContext().getApplicationContext(), infoExpense.class);
            infoEx.putExtra("infoexpense",e);
            startActivity(infoEx);
        }
    };

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
            expenseViewModel = new expenseViewModel(getActivity().getApplication());
            expenseViewModel = ViewModelProviders.of(this).get(expenseViewModel.class);
            expenseViewModel.getAllExpenseBudgetRangeTime(dateFrom, dateTo, b.getId()).observe(this, new Observer<List<expense>>() {
                @Override
                public void onChanged(@Nullable List<expense> expenses) {
                    listExpense = expenses;
                    totalExpense = 0;
                    for (expense expense : expenses) {
                        totalExpense += expense.getNmoney();
                    }
                    txtExpenseBudget.setText(Util.formatCurrency(totalExpense));
                    exRVAdapter.setExpenseBudgetList(listExpense);
                    getCategoryExpense();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCategoryExpense() {
        try {
            catExpenseViewModel = new CatExpenseViewModel(getActivity().getApplication());
            catExpenseViewModel = ViewModelProviders.of(this).get(CatExpenseViewModel.class);
            catExpenseViewModel.getAllCatExpense().observe(this, new Observer<List<catexpense>>() {
                @Override
                public void onChanged(@Nullable List<catexpense> catexpenses) {
                    catexpenseList = catexpenses;
                    if (!listExpense.isEmpty()) {
                        exRVAdapter.setCatexpenseList(catexpenseList);
                        largestExpenseFragnemntErrorHolder.setVisibility(View.GONE);
                        expense largestExpense = listExpense.get(0);

                    } else {
                        largestExpenseFragnemntErrorHolder.setVisibility(View.VISIBLE);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Intent i = this.getActivity().getIntent();
        b = (budget) i.getSerializableExtra("budget");
    }

    private View.OnClickListener onCardViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent infoBudget = new Intent(getContext().getApplicationContext(), activity_expense.class);
            infoBudget.putExtra("budget", b);
            infoBudget.putExtra("dayFrom", statisticDayFrom);
            infoBudget.putExtra("dayTo", statisticDayTo);
            startActivity(infoBudget);
        }
    };

//    private View.OnClickListener onEditItem = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent infoBudget = new Intent(getContext().getApplicationContext(), category_update.class);
//            //infoBudget.putExtra("budget", b);
//            startActivity(infoBudget);
//        }
//    };

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
                Toast.makeText(getActivity(),dateFrom,Toast.LENGTH_SHORT).show();
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
                //txtExpenseRangeTime.setText(Util.FromatStringToDateDDMM(dateFrom) + " ---> " + Util.FromatStringToDateDDMM(dateTo));
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
