package com.example.moneymanagement_android.fragments;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moneymanagement_android.R;
import com.example.moneymanagement_android.adapters.ManageExpenseRecyclerViewAdapter;
import com.example.moneymanagement_android.adapters.ManageIncomeRecyclerViewAdapter;
import com.example.moneymanagement_android.adapters.budgetRecyclerViewAdapter;
import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.models.catexpense;
import com.example.moneymanagement_android.models.catincome;
import com.example.moneymanagement_android.viewmodels.CatIncomeViewModel;
import com.example.moneymanagement_android.viewmodels.budgetViewModel;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class IncomeCategoryFragment extends Fragment {
    View v;
    private CatIncomeViewModel iViewModel;
    private RecyclerView myRecyclerViewIncome;
    private ManageIncomeRecyclerViewAdapter manageIncomeRecyclerViewAdapter;
    private List<catincome> listCatIncome = new ArrayList<>();

    public IncomeCategoryFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_income_category, container, false);
        myRecyclerViewIncome = (RecyclerView) v.findViewById(R.id.recylerIncome);
        manageIncomeRecyclerViewAdapter = new ManageIncomeRecyclerViewAdapter();
        manageIncomeRecyclerViewAdapter.setListCatIncome(listCatIncome);
        myRecyclerViewIncome.setLayoutManager((new LinearLayoutManager(getActivity())));
        myRecyclerViewIncome.setAdapter(manageIncomeRecyclerViewAdapter);
        manageIncomeRecyclerViewAdapter.setOnItemIncomeClickListener(onItemIncomeSelectClickListener);
        //manageIncomeRecyclerViewAdapter.setOnItemLongClickListener(onItemLongClickListener);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            iViewModel = new CatIncomeViewModel(getActivity().getApplication());
            iViewModel = ViewModelProviders.of(this).get(CatIncomeViewModel.class);
            iViewModel.getAllCatIncome().observe(this, new Observer<List<catincome>>() {
                @Override
                public void onChanged(@Nullable List<catincome> catincomes) {
                    if (catincomes != null) {
                        listCatIncome = catincomes;
                        manageIncomeRecyclerViewAdapter.setListCatIncome(listCatIncome);
                        //Toast.makeText(getApplication(), "on change: " + catincomes.size(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener onItemIncomeSelectClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ManageIncomeRecyclerViewAdapter.MyViewHolder viewHolder = (ManageIncomeRecyclerViewAdapter.MyViewHolder) v.getTag();
            int pos = viewHolder.getAdapterPosition();
            catincome b = listCatIncome.get(pos);

            Intent intent = new Intent();
            intent.putExtra("category","catincome");
            intent.putExtra("catincome", b);

            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        }
    };



}
