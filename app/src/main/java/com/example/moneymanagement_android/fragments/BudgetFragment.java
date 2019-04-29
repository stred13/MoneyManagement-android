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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.moneymanagement_android.budget_update;
import com.example.moneymanagement_android.infobudget;
import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.R;
import com.example.moneymanagement_android.RecyclerAdapter;
import com.example.moneymanagement_android.budget_creating;
import com.example.moneymanagement_android.viewmodels.budgetViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class BudgetFragment extends Fragment {
    View v;
    private budgetViewModel bViewModel;
    private RecyclerView myRecyclerView;
    private RecyclerAdapter recycleViewAdapter;
    private List<budget> listBudget = new ArrayList<>();

    public BudgetFragment() {
        // Required empty public constructor
    }
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerAdapter.MyViewHolder viewHolder = (RecyclerAdapter.MyViewHolder) v.getTag();
            int pos = viewHolder.getAdapterPosition();
            budget b = listBudget.get(pos);
            Toast.makeText(getContext(), "You Clicked: "+b.getName(), Toast.LENGTH_SHORT).show();
            Intent inBudget = new Intent(getContext().getApplicationContext(), budget_update.class);
            inBudget.putExtra("budget", b);
            startActivity(inBudget);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_budget, container, false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.recylerBudget);
        recycleViewAdapter = new RecyclerAdapter();
        recycleViewAdapter.setListbudget(listBudget);
        myRecyclerView.setLayoutManager((new LinearLayoutManager(getActivity())));
        myRecyclerView.setAdapter(recycleViewAdapter);
        recycleViewAdapter.setOnItemClickListener(onItemClickListener);
        return v;
    }

    Button btntest;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            bViewModel = new budgetViewModel(getActivity().getApplication());
            bViewModel = ViewModelProviders.of(this).get(budgetViewModel.class);
            bViewModel.getListBudget().observe(this, new Observer<List<budget>>() {
                @Override
                public void onChanged(@Nullable List<budget> budgets) {
                    if(budgets!=null){
                        listBudget = budgets;
                        recycleViewAdapter.setListbudget(listBudget);
                        Toast.makeText(getActivity().getApplication(), "on change: " + budgets.size(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (ExecutionException |InterruptedException e) {
            e.printStackTrace();
        }

    }
}
