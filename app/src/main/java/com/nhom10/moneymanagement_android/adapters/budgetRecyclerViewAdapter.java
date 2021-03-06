package com.nhom10.moneymanagement_android.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhom10.moneymanagement_android.R;
import com.nhom10.moneymanagement_android.models.budget;

import java.util.ArrayList;
import java.util.List;

public class budgetRecyclerViewAdapter extends RecyclerView.Adapter<budgetRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<budget> bData = new ArrayList<>();
    private View.OnClickListener itemClicklistener;
    private View.OnLongClickListener iLongClicklistener;

    public budgetRecyclerViewAdapter(Context context, List<budget> bData) {
        this.context = context;
        this.bData = bData;
    }

    public budgetRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public budgetRecyclerViewAdapter() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_budget,viewGroup,false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_bName.setText(bData.get(i).getName());
        myViewHolder.tv_bNote.setText(bData.get(i).getNote());
       // myViewHolder.tv_bdate.setText(String.valueOf(bData.get(i).getBdate()));
    }

    public void setListbudget(List<budget> lst){
        this.bData = lst;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(View.OnClickListener iClicklistener){
        this.itemClicklistener = iClicklistener;
    }
    public void setOnItemLongClickListener(View.OnLongClickListener iLongClicklistener){
        this.iLongClicklistener = iLongClicklistener;
    }


    @Override
    public int getItemCount() {
        if(bData==null)
            return 0;
        return bData.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_bName;
        private TextView tv_bNote;
        private TextView tv_bdate;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_bName = (TextView)itemView.findViewById(R.id.tv_bName);
            tv_bNote = (TextView)itemView.findViewById(R.id.tv_bNote);
         //   tv_bdate = (TextView) itemView.findViewById(R.id.tvbdate);
            itemView.setTag(this);
            itemView.setOnClickListener(itemClicklistener);

            itemView.setOnLongClickListener(iLongClicklistener);
        }
    }


}
