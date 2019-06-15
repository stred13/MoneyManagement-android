package com.nhom10.moneymanagement_android.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhom10.moneymanagement_android.R;
import com.nhom10.moneymanagement_android.models.catexpense;
import com.nhom10.moneymanagement_android.models.expense;
import com.nhom10.moneymanagement_android.utils.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StatisticExpenseBudgetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<expense> expenseList;
    private Context context;
    private List<catexpense> catexpenseList;

    public StatisticExpenseBudgetAdapter(List<expense> expenseList, Context context) {
        this.expenseList = expenseList;
        this.context = context;
    }

    public void setCatexpenseList(List<catexpense> catexpenseList) {
        this.catexpenseList = catexpenseList;
        notifyDataSetChanged();
    }

    public void setExpenseuBudgetList(List<expense> expenseList) {
        this.expenseList.clear();
        this.expenseList = expenseList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_statistic_expense_layout, viewGroup, false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        expense expense = expenseList.get(i);
        MyViewHolder MyViewHolder =  (MyViewHolder) viewHolder;
        MyViewHolder.txtPrice.setText(Util.formatCurrency(expense.getNmoney()));
        for (catexpense catx : catexpenseList) {
            if (expense.getIdcatex() == catx.getId()) {
                Picasso.get().load(catx.getImage()).error(R.drawable.breakfast).into(MyViewHolder.imgItem);
                MyViewHolder.txtName.setText(catx.getName());
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem;
        private TextView txtName, txtPrice;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            txtName = itemView.findViewById(R.id.txtNameItem);
            txtPrice = itemView.findViewById(R.id.txtPriceItem);
            itemView.setTag(this);
        }
    }
}
