package com.example.moneymanagement_android.fragments;


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
import android.widget.Button;
import android.widget.Toast;

import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.R;
import com.example.moneymanagement_android.RecyclerAdapter;
import com.example.moneymanagement_android.budget_creating;
import com.example.moneymanagement_android.viewmodels.budgetViewModel;
import com.example.moneymanagement_android.infobudger;


import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BudgetFragment extends Fragment {
    View v;
    budgetViewModel bViewModel;
    private RecyclerView myRecyclerView;
    RecyclerAdapter recycleViewAdapter;
    private List<budget> listBudget = new ArrayList<>();
    public BudgetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_budget, container, false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.recylerBudget);
        recycleViewAdapter = new RecyclerAdapter(getContext(), listBudget);
        myRecyclerView.setLayoutManager((new LinearLayoutManager(getActivity())));
        myRecyclerView.setAdapter(recycleViewAdapter);

        btntest = (Button) v.findViewById(R.id.btnTest);

        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), infobudger.class);
                // BudgetFragment.startActivityForResult(intent,2);
                startActivity(intent);
            }
        });

        return v;
    }

    Button btntest;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bViewModel = new budgetViewModel(getActivity().getApplication());
        bViewModel = ViewModelProviders.of(getActivity()).get(budgetViewModel.class);
        budget_creating bd;

        bViewModel.getListBudget().observe(this, new Observer<List<budget>>() {
            @Override
            public void onChanged(@Nullable List<budget> budgets) {
                Toast.makeText(getActivity().getApplication(), "thay doi", Toast.LENGTH_SHORT).show();

                if(budgets!=null){
                    recycleViewAdapter.setListbudget(budgets);
                    listBudget = budgets;
                    Toast.makeText(getActivity().getApplication(), "on change: " + listBudget.size(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}
