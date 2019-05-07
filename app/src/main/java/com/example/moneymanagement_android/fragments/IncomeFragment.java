package com.example.moneymanagement_android.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moneymanagement_android.R;
import com.example.moneymanagement_android.activity_expense;
import com.example.moneymanagement_android.activity_income;
import com.example.moneymanagement_android.adapters.budgetRecyclerViewAdapter;
import com.example.moneymanagement_android.adapters.incomeRecyclerViewAdapter;
import com.example.moneymanagement_android.infobudget;
import com.example.moneymanagement_android.models.budget;


/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends Fragment {

    CardView cardViewTongQuan;
    private RecyclerView myRecyclerView;
    private incomeRecyclerViewAdapter recycleViewAdapter;
    View v;

    public IncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_income, container, false);
        cardViewTongQuan = (CardView) v.findViewById(R.id.cardViewTongQuan);
        cardViewTongQuan.setOnClickListener(onCardViewClickListener);

        return v;
    }

    private View.OnClickListener onCardViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent infoBudget = new Intent(getContext().getApplicationContext(), activity_income.class);
            //infoBudget.putExtra("budget", b);
            startActivity(infoBudget);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


}
