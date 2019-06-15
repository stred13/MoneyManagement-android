package com.example.moneymanagement_android.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moneymanagement_android.R;
import com.example.moneymanagement_android.infoExpense;
import com.example.moneymanagement_android.models.catexpense;
import com.example.moneymanagement_android.models.catincome;
import com.example.moneymanagement_android.models.expense;
import com.example.moneymanagement_android.models.income;
import com.example.moneymanagement_android.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class ParenIncomeRecyclerViewAdapter extends RecyclerView.Adapter<ParenIncomeRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<income> incomeList;
    private View.OnClickListener itemClicklistener;
    private List<catincome> catincomeList;
    private List<income> incomeChildList = new ArrayList<>();
    private ChildIncomeRecyclerViewAdapter childIncomeRecyclerViewAdapter;
    private List<Integer> integerListViTri = new ArrayList<>();
    private int SoLan = 0;

    public ParenIncomeRecyclerViewAdapter(Context context, List<catincome> catincomes, List<income> list) {
        this.context = context;
        this.incomeList = list;
        this.catincomeList = catincomes;
        SoLan = 0;
    }

    public void setCatincomeList(List<catincome> catincomeList) {
        this.catincomeList.clear();
        this.catincomeList = catincomeList;
        this.notifyDataSetChanged();
        SoLan = 0;
    }

    public void setIncomeBudgetList(List<income> incomeList) {
        this.incomeList.clear();
        this.incomeList = incomeList;
        this.notifyDataSetChanged();
        SoLan = 0;
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
        //get vi tri item co phan tu
        int vitri = integerListViTri.get(SoLan++);

        catincome catincome = catincomeList.get(vitri);
        incomeChildList.clear();
        int total = 0;
        for (int j = 0; j < incomeList.size(); j++) {
            if (incomeList.get(j).getIdcatin() == catincome.getId()) {
                incomeChildList.add(incomeList.get(j));
                total += incomeList.get(j).getNmoney();
            }
        }
        if (incomeChildList.size() != 0) {
            myViewHolder.imgItem.setImageResource(catincome.getImage());
            myViewHolder.txtName.setText(catincome.getName());
            myViewHolder.txtSoluong.setText(incomeChildList.size() + " giao dịch");
            myViewHolder.txtPrice.setText(Util.formatCurrency(total));

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            myViewHolder.parentRV.setLayoutManager(layoutManager);
            myViewHolder.parentRV.setHasFixedSize(true);

            childIncomeRecyclerViewAdapter = new ChildIncomeRecyclerViewAdapter(incomeChildList);
            childIncomeRecyclerViewAdapter.setOnItemClickListener(onClickListener);
            myViewHolder.parentRV.setAdapter(childIncomeRecyclerViewAdapter);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ChildIncomeRecyclerViewAdapter.MyViewHolder vhd = (ChildIncomeRecyclerViewAdapter.MyViewHolder) view.getTag();
            int pos = vhd.getAdapterPosition();
            TextView tvcat =(TextView) vhd.itemView.findViewById(R.id.txtCatexpense);
            income in = new income();
             for(int i=0;i<incomeList.size();i++){
                if(incomeList.get(i).getIdcatin()== Integer.parseInt(tvcat.getText().toString())){
                    in=incomeList.get(i+pos);
                    Log.d("i: "+(i+pos), " onClick: "+incomeList.get(i+pos).getId()+" pos: "+incomeList.get(i+pos).getNmoney());
                    break;
                }
            }

            Intent infoEx = new Intent(context.getApplicationContext(), infoExpense.class);
            infoEx.putExtra("infoincome",in);
            context.startActivity(infoEx);

           /* for(int i=0;i<incomeList.size();i++){

            }*/
        }
    };

    @Override
    public int getItemCount() {
        int size = 0;
        integerListViTri.clear();
        for (int i = 0; i < catincomeList.size(); i++) {
            catincome catincome = catincomeList.get(i);
            for (int j = 0; j < incomeList.size(); j++) {
                income income = incomeList.get(j);
                if (income.getIdcatin() == catincome.getId()) {
                    size++;
                    integerListViTri.add(i);
                    break;
                }
            }
        }
        return size;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem;
        private TextView txtName, txtSoluong, txtPrice;
        private RecyclerView parentRV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgParenExpenseBudgeImgage);
            txtName = itemView.findViewById(R.id.txtParenExpenseBudgeName);
            txtSoluong = itemView.findViewById(R.id.txtParenExpenseBudgeDate);
            txtPrice = itemView.findViewById(R.id.txtParenExpenseBudgeMoney);
            parentRV = itemView.findViewById(R.id.childRV);
            itemView.setTag(this);
        }
    }
}
