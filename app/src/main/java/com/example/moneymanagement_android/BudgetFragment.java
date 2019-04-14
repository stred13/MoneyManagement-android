package com.example.moneymanagement_android;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BudgetFragment extends Fragment {
    View v;
    private RecyclerView myRecyclerView;
    private List<budget> lstBudget;

    public BudgetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.fragment_budget, container, false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.recylerBudget);
        RecyclerAdapter recycleViewAdapter = new RecyclerAdapter(getContext(),lstBudget);
        myRecyclerView.setLayoutManager((new LinearLayoutManager(getActivity())));

        myRecyclerView.setAdapter(recycleViewAdapter);
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstBudget = new ArrayList<>();
        lstBudget.add(new budget("abc","xyz",R.drawable.hand_card_50));
        lstBudget.add(new budget("xyz","xyz",R.drawable.chart_filled));
    }
}
