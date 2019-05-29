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

public class incomeRecyclerViewAdapter extends RecyclerView.Adapter<incomeRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<income> incomeList;
    private List<catincome> catincomeList;

    public incomeRecyclerViewAdapter(Context context, List<income> incomeList) {
        this.context = context;
        this.incomeList = incomeList;
    }

    public void setCatincomeList(List<catincome> catincomeList) {
        this.catincomeList = catincomeList;
        notifyDataSetChanged();
    }

    public void setIncomeBudgetList(List<income> incomeList) {
        this.incomeList.clear();
        this.incomeList = incomeList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_expense, viewGroup, false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull incomeRecyclerViewAdapter.MyViewHolder myViewHolder, int i) {
        income expense = incomeList.get(i);
        MyViewHolder MyViewHolder =  (MyViewHolder) myViewHolder;
        MyViewHolder.txtPrice.setText(Util.formatCurrency(expense.getNmoney()));
        MyViewHolder.txtDate.setText(Util.formatDate(expense.getDcreated()));
        for (catincome catx : catincomeList) {
            if (expense.getIdcatin() == catx.getId()) {
                Picasso.get().load(catx.getImage()).error(R.drawable.breakfast).into(MyViewHolder.imgItem);
                MyViewHolder.txtName.setText(catx.getName());
                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return this.incomeList.size();
    }

    public void setOnClickCardViewListener(View.OnClickListener iClickCardViewlistener) {
        //this.cardViewClicklistener = iClickCardViewlistener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem;
        private TextView txtName, txtDate, txtPrice;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgExpenseBudgeImgage);
            txtName = itemView.findViewById(R.id.txtExpenseBudgeName);
            txtDate = itemView.findViewById(R.id.txtExpenseBudgeDate);
            txtPrice = itemView.findViewById(R.id.txtExpenseBudgeMoney);
            itemView.setTag(this);
            //
            //            itemView.setOnLongClickListener(iLongClicklistener);

        }
    }
}
