package com.example.moneymanagement_android.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moneymanagement_android.R;

public class incomeRecyclerViewAdapter extends RecyclerView.Adapter<incomeRecyclerViewAdapter.MyViewHolder> {

    private View.OnClickListener cardViewClicklistener;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View v;
//        v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_budget,viewGroup,false);
//        MyViewHolder vHolder = new MyViewHolder(v);
//        return vHolder;
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull incomeRecyclerViewAdapter.MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setOnClickCardViewListener(View.OnClickListener iClickCardViewlistener){
        this.cardViewClicklistener = iClickCardViewlistener;
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_bName;
        private TextView tv_bNote;

        public MyViewHolder(@NonNull final View itemView) {
                super(itemView);
    //            tv_bName = (TextView)itemView.findViewById(R.id.tv_bName);
    //            tv_bNote = (TextView)itemView.findViewById(R.id.tv_bNote);
    //            itemView.setTag(this);
    //            itemView.setOnClickListener(itemClicklistener);
    //
    //            itemView.setOnLongClickListener(iLongClicklistener);

        }
    }
}
