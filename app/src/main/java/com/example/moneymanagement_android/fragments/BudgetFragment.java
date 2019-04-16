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

import com.example.moneymanagement_android.datamanagers.dataProvider;
import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.R;
import com.example.moneymanagement_android.RecyclerAdapter;
import com.example.moneymanagement_android.budget_creating;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BudgetFragment extends Fragment {
    View v;
    private RecyclerView myRecyclerView;
    RecyclerAdapter recycleViewAdapter;
    private List<budget> lstBudget = new ArrayList<>();

    public BudgetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_budget, container, false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.recylerBudget);
        recycleViewAdapter = new RecyclerAdapter(getContext(), lstBudget);
        myRecyclerView.setLayoutManager((new LinearLayoutManager(getActivity())));
        myRecyclerView.setAdapter(recycleViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataProvider dp = new dataProvider(getActivity().getApplicationContext());
        lstBudget=dp.getListBudget();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getActivity().getApplicationContext(), "vao fragment "+requestCode+": "+resultCode, Toast.LENGTH_SHORT).show();
        if(requestCode==2 && resultCode== 2){
            String re = data.getStringExtra("res");
            budget b =new budget();
            b.setbName(re);
             Toast.makeText(getActivity().getApplicationContext(), "data "+re, Toast.LENGTH_SHORT).show();
             recycleViewAdapter.insertItem(b);
        }

    }

    /* public void getdataintent(String dat){
        budget b = new budget();
        b.setbName(dat);
        //lstBudget.add(b);

        Toast.makeText(getActivity().getApplicationContext(), "data re: "+dat, Toast.LENGTH_SHORT).show();
        recycleViewAdapter.insertItem(b);
       // myRecyclerView.get
    }*/
}
