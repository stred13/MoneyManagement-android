package com.example.moneymanagement_android;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    Context mContent;
    List<budget> bData;

    public RecyclerAdapter(Context mContent, List<budget> bData) {
        this.mContent = mContent;
        this.bData = bData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v= LayoutInflater.from(mContent).inflate(R.layout.item_budget,viewGroup,false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_bName.setText(bData.get(i).getbName());
        myViewHolder.imgBudget.setImageResource(bData.get(i).getUrlImage());
    }

    @Override
    public int getItemCount() {
        return bData.size();
    }

    public static class  MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_bName;
        private ImageView imgBudget;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_bName = (TextView)itemView.findViewById(R.id.tv_bName);
            imgBudget = (ImageView) itemView.findViewById(R.id.imgBudget);

        }
    }
}
