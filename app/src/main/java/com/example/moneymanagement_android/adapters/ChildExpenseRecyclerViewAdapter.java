package com.example.moneymanagement_android.adapters;

import android.content.Context;
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

    Context context;
    List<expense> expenseArrayList;
    private View.OnClickListener itemClicklistener;



    public ChildExpenseRecyclerViewAdapter(Context context,List<expense> expenseList) {
        this.expenseArrayList = expenseList;
        this.context = context;
        notifyDataSetChanged();
    }
    public void setListExense(List<expense> listExense){
        this.expenseArrayList = listExense;
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
        myViewHolder.imgItem.setText(Util.formatDate(expense.getDcreated()).substring(0,2));
        myViewHolder.txtDate.setText(Util.getDayOfWeek(expense.getDcreated()));
        myViewHolder.txtPrice.setText(Util.formatCurrency(expense.getNmoney()));
        myViewHolder.txtNote.setText(expense.getNote());
        myViewHolder.txtCatex.setText(String.valueOf(expense.getIdcatex()) );
    }


    public void setOnItemClickListener(View.OnClickListener iClicklistener){
        this.itemClicklistener = iClicklistener;
    }

    @Override
    public int getItemCount() {
        return expenseArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView imgItem,txtDate,txtPrice, txtNote, txtCatex;
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
