package com.example.moneymanagement_android.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moneymanagement_android.R;
import com.example.moneymanagement_android.models.expense;
import com.example.moneymanagement_android.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class ChildExpenseRecyclerViewAdapter extends RecyclerView.Adapter<ChildExpenseRecyclerViewAdapter.MyViewHolder>{

    List<expense> expenseArrayList;


    public ChildExpenseRecyclerViewAdapter(List<expense> expenseArrayList) {
        this.expenseArrayList = expenseArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_child_expense_rowlayout,viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        expense expense = expenseArrayList.get(i);
        //myViewHolder.imgItem.setImageResource(exp);
        myViewHolder.txtDate.setText(Util.formatDate(expense.getDcreated()));
        myViewHolder.txtPrice.setText(expense.getNmoney()+"");


    }

    @Override
    public int getItemCount() {
        return expenseArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem;
        private TextView txtDate,txtPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgChildExpenseBudgeImgage);
            txtDate = itemView.findViewById(R.id.txtChildExpenseBudgeDate);
            txtPrice = itemView.findViewById(R.id.txtChildExpenseBudgeMoney);
            itemView.setTag(this);
        }
    }
}
