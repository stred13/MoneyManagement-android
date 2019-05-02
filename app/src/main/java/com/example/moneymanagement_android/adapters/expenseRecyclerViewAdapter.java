package com.example.moneymanagement_android.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moneymanagement_android.R;
import com.example.moneymanagement_android.models.expense;

import java.util.List;

public class expenseRecyclerViewAdapter extends RecyclerView.Adapter<expenseRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<expense> listExpense;

    public expenseRecyclerViewAdapter(Context context, List<expense> listExpense) {
        this.context = context;
        this.listExpense = listExpense;
    }

    public expenseRecyclerViewAdapter() {
    }

    public expenseRecyclerViewAdapter(List<expense> listExpense) {
        this.listExpense = listExpense;
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
        myViewHolder.tv_eName.setText(listExpense.get(i).getName());
        myViewHolder.tv_eNote.setText(listExpense.get(i).getNote());
    }

    public void setListExpense(List<expense> liste){
        this.listExpense = liste;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(this.listExpense==null)
            return 0;
        return this.listExpense.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_eName;
        private TextView tv_eNote;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_eName = (TextView) itemView.findViewById(R.id.tv_exName);
            tv_eNote = (TextView) itemView.findViewById(R.id.tv_exNote);
        }
    }
}
