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
import com.nhom10.moneymanagement_android.models.catincome;
import com.nhom10.moneymanagement_android.models.income;
import com.nhom10.moneymanagement_android.utils.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StatisticIncomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<income> incomeList;
    private Context context;
    private List<catincome> catincomes;

    public StatisticIncomeAdapter(List<income> incomeList, Context context) {
        this.incomeList = incomeList;
        this.context = context;
    }

    public void setIncomeList(List<income> incomeList) {
        this.incomeList.clear();
        this.incomeList = incomeList;
        this.notifyDataSetChanged();
    }

    public void setCatincomes(List<catincome> catincomes) {
        this.catincomes = catincomes;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_statistic_income_layout, viewGroup, false);
        return new StatisticExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        income income = incomeList.get(i);
        StatisticExpenseViewHolder expenseViewHolder = (StatisticExpenseViewHolder) viewHolder;
        expenseViewHolder.txtPrice.setText(Util.formatCurrency(income.getNmoney()));
        for (catincome cati : catincomes) {
            if (income.getIdcatin() == cati.getId()) {
                expenseViewHolder.txtName.setText(cati.getName());
                Picasso.get().load(cati.getImage()).error(R.drawable.breakfast).into(expenseViewHolder.imgItem);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return incomeList.size();
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
