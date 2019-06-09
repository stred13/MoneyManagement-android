package com.example.moneymanagement_android.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneymanagement_android.budget_update;
import com.example.moneymanagement_android.infobudget;
import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.R;
import com.example.moneymanagement_android.adapters.budgetRecyclerViewAdapter;
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
    private budgetRecyclerViewAdapter recycleViewAdapter;
    private List<budget> listBudget = new ArrayList<>();
    private TextView txtTotal_m;
    private long sum_m =0;

    public BudgetFragment() {
        // Required empty public constructor
    }

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            budgetRecyclerViewAdapter.MyViewHolder viewHolder = (budgetRecyclerViewAdapter.MyViewHolder) v.getTag();
            int pos = viewHolder.getAdapterPosition();
            budget b = listBudget.get(pos);

            Intent infoBudget = new Intent(getContext().getApplicationContext(), infobudget.class);
            infoBudget.putExtra("budget", b);
            startActivity(infoBudget);
        }
    };

    private View.OnLongClickListener onItemLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            budgetRecyclerViewAdapter.MyViewHolder viewHolder = (budgetRecyclerViewAdapter.MyViewHolder) v.getTag();
            int pos = viewHolder.getAdapterPosition();
            budget b = listBudget.get(pos);

            ShowPopupMenu(v);
            return true;
        }
    };

    private void ShowPopupMenu(final View v) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(), v, Gravity.CENTER, 0, R.style.PopupMenuMoreCentralized);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                budgetRecyclerViewAdapter.MyViewHolder viewHolder = (budgetRecyclerViewAdapter.MyViewHolder) v.getTag();
                int pos = viewHolder.getAdapterPosition();
                budget b = listBudget.get(pos);
                switch (menuItem.getItemId()) {
                    case R.id.popup_menu_update:
                        Toast.makeText(v.getContext(), "update", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(v.getContext(), budget_update.class);

                        intent.putExtra("b", b);
                        startActivity(intent);
                        break;
                    case R.id.popup_menu_delete:
                        Toast.makeText(v.getContext(), "delete", Toast.LENGTH_SHORT).show();
                        bViewModel = ViewModelProviders.of(getActivity()).get(budgetViewModel.class);
                        bViewModel.deleteBudget(b);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_budget, container, false);
        myRecyclerView = (RecyclerView) v.findViewById(R.id.recylerBudget);
        txtTotal_m = v.findViewById(R.id.total_m);
        recycleViewAdapter = new budgetRecyclerViewAdapter();
        recycleViewAdapter.setListbudget(listBudget);
        myRecyclerView.setLayoutManager((new LinearLayoutManager(getActivity())));
        myRecyclerView.setAdapter(recycleViewAdapter);
        recycleViewAdapter.setOnItemClickListener(onItemClickListener);
        recycleViewAdapter.setOnItemLongClickListener(onItemLongClickListener);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //txtExpenseBudget = v.findViewById(R.id.txtExpenseBudget);

        try {
            bViewModel = new budgetViewModel(getActivity().getApplication());
            bViewModel = ViewModelProviders.of(this).get(budgetViewModel.class);

            bViewModel.getListBudget().observe(this, new Observer<List<budget>>() {
                @Override
                public void onChanged(@Nullable List<budget> budgets) {
                    if (budgets != null) {
                        sum_m=0;
                        listBudget = budgets;
                        for (budget bud : listBudget) {
                            sum_m+=bud.getBmoney();
                        }
                        recycleViewAdapter.setListbudget(listBudget);
                        txtTotal_m.setText(""+sum_m);
                        Toast.makeText(getActivity().getApplication(), "on change: " + budgets.size(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
