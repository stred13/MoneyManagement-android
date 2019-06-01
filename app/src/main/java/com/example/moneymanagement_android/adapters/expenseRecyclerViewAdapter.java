package com.example.moneymanagement_android.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moneymanagement_android.R;
import com.example.moneymanagement_android.models.catexpense;
import com.example.moneymanagement_android.models.expense;
import com.example.moneymanagement_android.utils.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

public class expenseRecyclerViewAdapter extends RecyclerView.Adapter<expenseRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<expense> expenseList;
    private View.OnClickListener itemClicklistener;
    private List<catexpense> catexpenseList;
    

    public expenseRecyclerViewAdapter(Context context, List<expense> listExpense) {
        this.context = context;
        this.expenseList = listExpense;
    }

    public void setCatexpenseList(List<catexpense> catexpenseList) {
        this.catexpenseList = catexpenseList;
        notifyDataSetChanged();
    }

    public void setExpenseBudgetList(List<expense> expenseList) {
        this.expenseList.clear();
        this.expenseList = expenseList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_expense,viewGroup,false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        expense expense = expenseList.get(i);
        MyViewHolder MyViewHolder =  (MyViewHolder) myViewHolder;
        MyViewHolder.txtPrice.setText(Util.formatCurrency(expense.getNmoney()));
        MyViewHolder.txtDate.setText(Util.formatDate(expense.getDcreated()));
        for (catexpense catx : catexpenseList) {
            if (expense.getIdcatex() == catx.getId()) {
                Picasso.get().load(catx.getImage()).error(R.drawable.breakfast).into(MyViewHolder.imgItem);
                MyViewHolder.txtName.setText(catx.getName());
                break;
            }
        }
    }

    public void setOnItemClickListener(View.OnClickListener iClicklistener){
        this.itemClicklistener = iClicklistener;
    }

    @Override
    public int getItemCount() {

        return this.expenseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem;
        private TextView txtName, txtDate,txtPrice;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgExpenseBudgeImgage);
            txtName = itemView.findViewById(R.id.txtExpenseBudgeName);
            txtDate = itemView.findViewById(R.id.txtExpenseBudgeDate);
            txtPrice = itemView.findViewById(R.id.txtExpenseBudgeMoney);
            itemView.setOnClickListener(itemClicklistener);
            itemView.setTag(this);
        }
    }


}
