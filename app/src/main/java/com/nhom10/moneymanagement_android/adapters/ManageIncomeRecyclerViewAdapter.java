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

import java.util.ArrayList;
import java.util.List;

public class ManageIncomeRecyclerViewAdapter extends RecyclerView.Adapter<ManageIncomeRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<catincome> iData = new ArrayList<>();
    private View.OnClickListener itemClickIncomelistener;


    public ManageIncomeRecyclerViewAdapter(){}

    public void setListCatIncome(List<catincome> lst) {
        this.iData = lst;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_manage_income, viewGroup, false);
        ManageIncomeRecyclerViewAdapter.MyViewHolder vHolder = new ManageIncomeRecyclerViewAdapter.MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.img_Icon.setImageResource(iData.get(i).getImage());
        myViewHolder.tv_NameIncome.setText(iData.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return iData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_Icon;
        private TextView tv_NameIncome;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            img_Icon = (ImageView) itemView.findViewById(R.id.imgExIn);
            tv_NameIncome = (TextView) itemView.findViewById(R.id.tvNameExIn);
            itemView.setTag(this);
            itemView.setOnClickListener(itemClickIncomelistener);
        }
    }

    public void setOnItemIncomeClickListener(View.OnClickListener iClicklistener){
        this.itemClickIncomelistener = iClicklistener;
    }
}
