package com.nhom10.moneymanagement_android.adapters;

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

import com.nhom10.moneymanagement_android.R;
import com.nhom10.moneymanagement_android.infoExpense;
import com.nhom10.moneymanagement_android.models.catexpense;
import com.nhom10.moneymanagement_android.models.expense;
import com.nhom10.moneymanagement_android.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class ParenExpenseRecyclerViewAdapter extends RecyclerView.Adapter<ParenExpenseRecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private List<expense> expenseList;
    private View.OnClickListener itemClicklistener;
    private List<catexpense> catexpenseList;
    private List<expense> expenseChildList = new ArrayList<>();
    private ChildExpenseRecyclerViewAdapter childExpenseRecyclerViewAdapter;
    private List<Integer> integerListViTri = new ArrayList<>();
    private int SoLan = 0;
   // private static List<List<expense>> expensesCat =new ArrayList<>() ;

    public ParenExpenseRecyclerViewAdapter(Context context, List<catexpense> catexpenses, List<expense> list) {
        this.context = context;
        this.expenseList = list;
        this.catexpenseList = catexpenses;
        SoLan = 0;
    }

    public void setCatexpenseList(List<catexpense> catexpenseList) {
        this.catexpenseList.clear();
        this.catexpenseList = catexpenseList;
        this.notifyDataSetChanged();
        SoLan = 0;
    }

    public void setExpenseBudgetList(List<expense> expenseList) {
        this.expenseList.clear();
        this.expenseList = expenseList;
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
        int vitri = integerListViTri.get(SoLan++);

        catexpense catexpense = catexpenseList.get(vitri);
        expenseChildList.clear();
        int total = 0;
        for (int j = 0; j < expenseList.size(); j++) {
            expense expenseTemp = expenseList.get(j);
            if (expenseTemp.getIdcatex() == catexpense.getId()) {
                expenseChildList.add(expenseTemp);
                total += expenseTemp.getNmoney();
            }
        }

        if (expenseChildList.size() != 0) {
            myViewHolder.imgItem.setImageResource(catexpense.getImage());
            myViewHolder.txtName.setText(catexpense.getName());
            myViewHolder.txtSoluong.setText(expenseChildList.size() + " giao dịch");
            myViewHolder.txtPrice.setText(Util.formatCurrency(total));

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            myViewHolder.parentRV.setLayoutManager(layoutManager);
            myViewHolder.parentRV.setHasFixedSize(true);

            childExpenseRecyclerViewAdapter = new ChildExpenseRecyclerViewAdapter(this.context, expenseChildList);
            childExpenseRecyclerViewAdapter.setOnItemClickListener(onClickListener);
            myViewHolder.parentRV.setAdapter(childExpenseRecyclerViewAdapter);
        }

    }



    public void setOnItemClickListener(View.OnClickListener iClicklistener){
        this.itemClicklistener = iClicklistener;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ChildExpenseRecyclerViewAdapter.MyViewHolder vhd = (ChildExpenseRecyclerViewAdapter.MyViewHolder) view.getTag();
            int pos = vhd.getAdapterPosition();
            TextView tvcat = vhd.itemView.findViewById(R.id.txtCatexpense);
            expense e = new expense();

            for(int i=0;i<expenseList.size();i++){
                if(expenseList.get(i).getIdcatex()== Integer.parseInt(tvcat.getText().toString())){
                    e=expenseList.get(i+pos);
                    Log.d("i: "+(i+pos), " onClick: "+expenseList.get(i+pos).getId()+" pos: "+expenseList.get(i+pos).getNmoney());
                    break;
                }
            }
            Intent infoEx = new Intent(context.getApplicationContext(), infoExpense.class);
            infoEx.putExtra("infoexpense",e);
            context.startActivity(infoEx);
        }
    };

    @Override
    public int getItemCount() {
        int size = 0;
        integerListViTri.clear();
        for (int i = 0; i < catexpenseList.size(); i++) {
            catexpense catexpense = catexpenseList.get(i);
            for (int j = 0; j < expenseList.size(); j++) {
                expense expense = expenseList.get(j);
                if (expense.getIdcatex() == catexpense.getId()) {
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
            itemView.setOnClickListener(itemClicklistener);
            itemView.setTag(this);
        }
    }
}
