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
import com.example.moneymanagement_android.models.catincome;

import java.util.List;

public class SingleCatIncomeRecyclerAdapter extends RecyclerView.Adapter<SingleCatIncomeRecyclerAdapter.MyViewHolder> {
    private Context context;
    private List<catincome> catIncomes;
    private View.OnClickListener onItemCatClick;

    public SingleCatIncomeRecyclerAdapter(Context context, List<catincome> catIncomes) {
        this.context = context;
        this.catIncomes = catIncomes;
    }

    public SingleCatIncomeRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setOnItemCatClickListener(View.OnClickListener iClicklistener){
        this.onItemCatClick = iClicklistener;
    }

    public void setCatIncomes(List<catincome> catins){
        this.catIncomes = catins;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_manage_income, viewGroup, false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.img_Icon.setImageResource(catIncomes.get(i).getImage());
        myViewHolder.tv_NameIncome.setText(catIncomes.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return catIncomes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_Icon;
        private TextView tv_NameIncome;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_Icon = (ImageView) itemView.findViewById(R.id.imgExIn);
            tv_NameIncome = (TextView) itemView.findViewById(R.id.tvNameExIn);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemCatClick);
        }
    }
}
