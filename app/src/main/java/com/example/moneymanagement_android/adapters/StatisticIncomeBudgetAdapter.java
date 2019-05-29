package com.example.moneymanagement_android.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moneymanagement_android.R;
import com.example.moneymanagement_android.models.catexpense;
import com.example.moneymanagement_android.models.catincome;
import com.example.moneymanagement_android.models.expense;
import com.example.moneymanagement_android.models.income;
import com.example.moneymanagement_android.utils.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StatisticIncomeBudgetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<catincome> catIncomeList;
    private Context context;
    private List<income> incomeList;

    public StatisticIncomeBudgetAdapter(Context context, List<income> incomeList) {
        this.context = context;
        this.incomeList = incomeList;
    }

    public void setCatIncomeList(List<catincome> catIncomeList){
        this.catIncomeList = catIncomeList;
    }
    public void setIncomeList(List<income> incomeList){
        this.incomeList.clear();
        this.incomeList = incomeList;
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_statistic_income_layout, viewGroup , false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return  myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        income income = incomeList.get(i);
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.txtPrice.setText(Util.formatCurrency(income.getNmoney()));
        for (catincome cati : catIncomeList) {
            if (income.getIdcatin() == cati.getId()) {
                Picasso.get().load(cati.getImage()).error(R.drawable.breakfast).into(myViewHolder.imgItem);
                myViewHolder.txtName.setText(cati.getName());
                break;
            }
        }



    }

    @Override
    public int getItemCount() {
        return incomeList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgItem;
        private TextView txtName, txtPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            txtName = itemView.findViewById(R.id.txtNameItem);
            txtPrice = itemView.findViewById(R.id.txtPriceItem);
            itemView.setTag(this);

        }
    }
}
