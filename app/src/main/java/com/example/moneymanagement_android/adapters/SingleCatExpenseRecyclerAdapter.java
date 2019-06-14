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

import java.util.List;

public class SingleCatExpenseRecyclerAdapter extends RecyclerView.Adapter<SingleCatExpenseRecyclerAdapter.MyViewHolder> {
    private Context context;
    private List<catexpense> catExpenses;
    private View.OnClickListener onItemCatClick;

    public SingleCatExpenseRecyclerAdapter(Context context) {
        this.context = context;
    }

    public SingleCatExpenseRecyclerAdapter(Context context, List<catexpense> catExpenses) {
        this.context = context;
        this.catExpenses = catExpenses;
    }

    public void setOnItemCatClickListener(View.OnClickListener iClicklistener){
        this.onItemCatClick = iClicklistener;
    }

    public void setCatExpenses(List<catexpense> catex){
        this.catExpenses=catex;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_manage_expense, viewGroup, false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.img_Icon.setImageResource(catExpenses.get(i).getImage());
        myViewHolder.tv_NameExpense.setText(catExpenses.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return catExpenses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_Icon;
        private TextView tv_NameExpense;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_Icon = (ImageView) itemView.findViewById(R.id.imgExIn);
            tv_NameExpense = (TextView) itemView.findViewById(R.id.tvNameExIn);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemCatClick);
        }
    }
}
