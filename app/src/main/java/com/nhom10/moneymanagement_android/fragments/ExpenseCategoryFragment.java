package com.nhom10.moneymanagement_android.fragments;

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

import com.nhom10.moneymanagement_android.R;
import com.nhom10.moneymanagement_android.adapters.ManageExpenseRecyclerViewAdapter;
import com.nhom10.moneymanagement_android.models.catexpense;
import com.nhom10.moneymanagement_android.viewmodels.CatExpenseViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ExpenseCategoryFragment extends Fragment {

    View v;
    private CatExpenseViewModel eViewModel;
    private RecyclerView myRecyclerViewIncome;
    private ManageExpenseRecyclerViewAdapter manageExpenseRecyclerViewAdapter;
    private List<catexpense> listCatExpense = new ArrayList<>();

    public ExpenseCategoryFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_expense_category, container, false);
        myRecyclerViewIncome = (RecyclerView) v.findViewById(R.id.recylerExpense);
        manageExpenseRecyclerViewAdapter = new ManageExpenseRecyclerViewAdapter();
        manageExpenseRecyclerViewAdapter.setListCatExpense(listCatExpense);
        myRecyclerViewIncome.setLayoutManager((new LinearLayoutManager(getActivity())));
        myRecyclerViewIncome.setAdapter(manageExpenseRecyclerViewAdapter);
        manageExpenseRecyclerViewAdapter.setOnItemExpenseClickListener(onItemExpenseSelectClickListener);
        //manageIncomeRecyclerViewAdapter.setOnItemLongClickListener(onItemLongClickListener);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            eViewModel = new CatExpenseViewModel(getActivity().getApplication());
            eViewModel = ViewModelProviders.of(this).get(CatExpenseViewModel.class);
            eViewModel.getAllCatExpense().observe(this, new Observer<List<catexpense>>() {
                @Override
                public void onChanged(@Nullable List<catexpense> catexpenses) {
                    if (catexpenses != null) {
                        listCatExpense = catexpenses;
                        manageExpenseRecyclerViewAdapter.setListCatExpense(listCatExpense);
                        //Toast.makeText(getApplication(), "on change: " + catincomes.size(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    //click item select expense.
    private View.OnClickListener onItemExpenseSelectClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ManageExpenseRecyclerViewAdapter.MyViewHolder viewHolder = (ManageExpenseRecyclerViewAdapter.MyViewHolder) v.getTag();
            int pos = viewHolder.getAdapterPosition();
            catexpense b = listCatExpense.get(pos);

            Intent intent = new Intent();
            intent.putExtra("category","catexpense");
            intent.putExtra("catexpense", b);

            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        }
    };


}
