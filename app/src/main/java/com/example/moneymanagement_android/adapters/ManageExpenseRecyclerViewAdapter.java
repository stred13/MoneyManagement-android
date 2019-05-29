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
import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.models.catexpense;
import com.example.moneymanagement_android.models.expense;

import java.util.ArrayList;
import java.util.List;

public class ManageExpenseRecyclerViewAdapter extends RecyclerView.Adapter<ManageExpenseRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<catexpense> eData = new ArrayList<>();
    private int layout;
    private View.OnClickListener itemClickExpenselistener;

    public ManageExpenseRecyclerViewAdapter(Context context, int layout) {
        this.context = context;
        this.layout = layout;
    }

    public ManageExpenseRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public ManageExpenseRecyclerViewAdapter() {
    }

    public void setListCatExpense(List<catexpense> lst) {
        this.eData = lst;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_manage_expense, viewGroup, false);
        MyViewHolder vHolder = new MyViewHolder(v);
//        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        v = layoutInflater.inflate(layout, null);
//        MyViewHolder vHolder = new MyViewHolder(v);

        return vHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.img_Icon.setImageResource(eData.get(i).getImage());
        myViewHolder.tv_NameExpense.setText(eData.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return eData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_Icon;
        private TextView tv_NameExpense;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            img_Icon = (ImageView) itemView.findViewById(R.id.imgExIn);
            tv_NameExpense = (TextView) itemView.findViewById(R.id.tvNameExIn);
            itemView.setTag(this);
            itemView.setOnClickListener(itemClickExpenselistener);
        }
    }

    public void setOnItemExpenseClickListener(View.OnClickListener iClicklistener){
        this.itemClickExpenselistener = iClicklistener;
    }

}
