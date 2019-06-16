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

public class StatisticExpenseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<expense> expenseList;
    private Context context;
    private List<catexpense> catexpenseList;

    public StatisticExpenseAdapter(List<expense> expenseList, Context context) {
        this.expenseList = expenseList;
        this.context = context;
    }

    public void setCatexpenseList(List<catexpense> catexpenseList) {
        this.catexpenseList = catexpenseList;
        this.notifyDataSetChanged();
    }

    public void setExpenseList(List<expense> expenseList) {
        this.expenseList.clear();
        this.expenseList = expenseList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_statistic_expense_layout, viewGroup, false);
        return new StatisticExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        expense expense = expenseList.get(i);
        StatisticExpenseViewHolder expenseViewHolder = (StatisticExpenseViewHolder) viewHolder;
        expenseViewHolder.txtPrice.setText(Util.formatCurrency(expense.getNmoney()));
        for (catexpense catx : catexpenseList) {
            if (expense.getIdcatex() == catx.getId()) {
                expenseViewHolder.txtName.setText(catx.getName());
                Picasso.get().load(catx.getImage()).error(R.drawable.breakfast).into(expenseViewHolder.imgItem);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    class StatisticExpenseViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgItem;
        private TextView txtName, txtPrice;

        public StatisticExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            txtName = itemView.findViewById(R.id.txtNameItem);
            txtPrice = itemView.findViewById(R.id.txtPriceItem);
        }


    }
}
