package com.example.moneymanagement_android.fragments;


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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.moneymanagement_android.R;
import com.example.moneymanagement_android.activity_expense;
import com.example.moneymanagement_android.adapters.expenseRecyclerViewAdapter;
import com.example.moneymanagement_android.category_update;
import com.example.moneymanagement_android.infobudget;
import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.models.expense;
import com.example.moneymanagement_android.viewmodels.expenseViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment {

    CardView cardViewTongQuanChiTieu;
    View v;
    expenseRecyclerViewAdapter exRVAdapter;
    RecyclerView exRecyclerView;
    List<expense> listExpense = new ArrayList<>();
    expenseViewModel eViewModel;
    ImageView editThuoc;
    public ExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_expense,container,false);

        //-----
        editThuoc = (ImageView)v.findViewById(R.id.editThuoc);
        editThuoc.setOnClickListener(onEditItem);
        //---
        cardViewTongQuanChiTieu = (CardView) v.findViewById(R.id.cardViewTongQuanChiTieu);
        cardViewTongQuanChiTieu.setOnClickListener(onCardViewClickListener);
        exRecyclerView = (RecyclerView) v.findViewById(R.id.exRecyclerView);
        exRVAdapter = new expenseRecyclerViewAdapter();
        exRVAdapter.setListExpense(listExpense);
        exRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        exRecyclerView.setAdapter(exRVAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = this.getActivity().getIntent();
        budget b = (budget) i.getSerializableExtra("budget");
        Toast.makeText(getActivity().getApplication(), "b: " + b.getId(), Toast.LENGTH_SHORT).show();

        try {
            eViewModel = new expenseViewModel(getActivity().getApplication());
            eViewModel = ViewModelProviders.of(this).get(expenseViewModel.class);
            eViewModel.getAllExpensebyBudget(infobudget.b.getId()).observe(this, new Observer<List<expense>>() {
                @Override
                public void onChanged(@Nullable List<expense> expenses) {
                    if(expenses!=null){
                        listExpense = expenses;
                        exRVAdapter.setListExpense(listExpense);
                        Toast.makeText(getActivity().getApplication(), "Expense: " + listExpense.size(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (ExecutionException |InterruptedException e) {
            e.printStackTrace();
        }
    }
    private View.OnClickListener onCardViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent infoBudget = new Intent(getContext().getApplicationContext(), activity_expense.class);
            //infoBudget.putExtra("budget", b);
            startActivity(infoBudget);
        }
    };

    private View.OnClickListener onEditItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent infoBudget = new Intent(getContext().getApplicationContext(), category_update.class);
            //infoBudget.putExtra("budget", b);
            startActivity(infoBudget);
        }
    };
}
