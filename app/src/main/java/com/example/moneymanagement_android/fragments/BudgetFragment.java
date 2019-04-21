package com.example.moneymanagement_android.fragments;


import android.app.Activity;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.R;
import com.example.moneymanagement_android.RecyclerAdapter;
import com.example.moneymanagement_android.budget_creating;
import com.example.moneymanagement_android.viewmodels.budgetViewModel;

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
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bViewModel = new budgetViewModel(getActivity().getApplication());

    }



}
