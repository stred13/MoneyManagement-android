package com.example.moneymanagement_android.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.moneymanagement_android.R;
import com.example.moneymanagement_android.models.catexpense;
import com.example.moneymanagement_android.models.expense;
import com.example.moneymanagement_android.utils.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ParenExpenseRecyclerViewAdapter extends RecyclerView.Adapter<ParenExpenseRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<expense> expenseList;
    private View.OnClickListener itemClicklistener;
    private List<catexpense> catexpenseList;
    private List<expense> expenseChildList;

    public ParenExpenseRecyclerViewAdapter(Context context, List<catexpense> catexpenses) {
        this.context = context;
        //this.expenseList = expenseList;
        this.catexpenseList = catexpenses;
    }

    public void setCatexpenseList(List<catexpense> catexpenseList) {
        this.catexpenseList.clear();
        this.catexpenseList = catexpenseList;
        notifyDataSetChanged();
    }

    public void setExpenseBudgetList(List<expense> expenseList) {
        //this.expenseList.clear();
        this.expenseList = expenseList;
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_parent_expense_rowlayout, viewGroup, false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        catexpense catexpense = catexpenseList.get(i);

        myViewHolder.imgItem.setImageResource(catexpense.getImage());
        myViewHolder.txtName.setText(catexpense.getName());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        myViewHolder.parentRV.setLayoutManager(layoutManager);
        myViewHolder.parentRV.setHasFixedSize(true);
        expenseChildList = new ArrayList<>();
        for (int j = 0; j < expenseList.size(); j++) {
            if(expenseList.get(j).getIdcatex() == catexpense.getId()){
                expenseChildList.add(expenseList.get(j));
            }
        }
        ChildExpenseRecyclerViewAdapter childExpenseRecyclerViewAdapter = new ChildExpenseRecyclerViewAdapter(expenseChildList);
        myViewHolder.parentRV.setAdapter(childExpenseRecyclerViewAdapter);

    }

    @Override
    public int getItemCount() {
        return catexpenseList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem;
        private TextView txtName, txtDate, txtPrice;
        private RecyclerView parentRV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgParenExpenseBudgeImgage);
            txtName = itemView.findViewById(R.id.txtParenExpenseBudgeName);
            txtDate = itemView.findViewById(R.id.txtParenExpenseBudgeDate);
            txtPrice = itemView.findViewById(R.id.txtParenExpenseBudgeMoney);
            parentRV = itemView.findViewById(R.id.childRV);
            itemView.setTag(this);
        }
    }
}
