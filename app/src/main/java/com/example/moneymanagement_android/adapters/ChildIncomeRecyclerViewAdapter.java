package com.example.moneymanagement_android.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moneymanagement_android.R;
import com.example.moneymanagement_android.models.expense;
import com.example.moneymanagement_android.models.income;
import com.example.moneymanagement_android.utils.Util;

import java.util.List;

public class ChildIncomeRecyclerViewAdapter extends RecyclerView.Adapter<ChildIncomeRecyclerViewAdapter.MyViewHolder> {
    List<income> incomeArrayList;
    private View.OnClickListener itemClicklistener;


    public ChildIncomeRecyclerViewAdapter(List<income> incomeArrayList) {
        this.incomeArrayList = incomeArrayList;
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_child_expense_rowlayout, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        income income = incomeArrayList.get(i);
        //myViewHolder.imgItem.setImageResource(exp);
        myViewHolder.imgItem.setText(Util.formatDate(income.getDcreated()).substring(0, 2));
        myViewHolder.txtDate.setText(Util.getDayOfWeek(income.getDcreated()));
        myViewHolder.txtPrice.setText(Util.formatCurrency(income.getNmoney()));
        myViewHolder.txtNote.setText(income.getNote());
        myViewHolder.txtCatex.setText(String.valueOf(income.getIdcatin()));

    }

    public void setOnItemClickListener(View.OnClickListener iClicklistener){
        this.itemClicklistener = iClicklistener;
    }

    @Override
    public int getItemCount() {
        return incomeArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView imgItem, txtDate, txtPrice, txtNote,txtCatex;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgChildExpenseBudgeImgage);
            txtDate = itemView.findViewById(R.id.txtChildExpenseBudgeDate);
            txtPrice = itemView.findViewById(R.id.txtChildExpenseBudgeMoney);
            txtNote = itemView.findViewById(R.id.txtParenExpenseBudgeNote);
            txtCatex = itemView.findViewById(R.id.txtCatexpense);

            itemView.setOnClickListener(itemClicklistener);

            itemView.setTag(this);
        }
    }
}
