package com.example.moneymanagement_android;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneymanagement_android.models.budget;
import com.example.moneymanagement_android.models.catexpense;
import com.example.moneymanagement_android.models.catincome;
import com.example.moneymanagement_android.models.expense;
import com.example.moneymanagement_android.models.income;
import com.example.moneymanagement_android.utils.Util;
import com.example.moneymanagement_android.viewmodels.CatExpenseViewModel;
import com.example.moneymanagement_android.viewmodels.CatIncomeViewModel;
import com.example.moneymanagement_android.viewmodels.IncomeViewModel;
import com.example.moneymanagement_android.viewmodels.budgetViewModel;
import com.example.moneymanagement_android.viewmodels.expenseViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class category_edit extends AppCompatActivity {

    MenuItem btnLuu;
    Button btnXoa;
    LinearLayout linearLayoutOption;

    ImageView imageViewIcon;
    EditText editTextTenNhom;
    String tennhom = "";
    int ImageResource;
    int imgViewChangeIcon;
    int flash = 0; // 1: expense, 2: income
    String stringEditText = "";

    private catincome catincome;
    private catexpense catexpense;
    private CatExpenseViewModel catExpenseViewModel;
    private CatIncomeViewModel catIncomeViewModel;
    private IncomeViewModel incomeViewModel;
    private expenseViewModel expenseViewModel;
    private List<expense> expenseList;
    private List<income> incomeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);

        //mapping
        btnXoa = (Button) findViewById(R.id.btnXoaCategory);
        linearLayoutOption = (LinearLayout) findViewById(R.id.lnOptionsIconGroup);
        imageViewIcon = (ImageView) findViewById(R.id.imgViewIcon);
        editTextTenNhom = (EditText) findViewById(R.id.editTextTenNhom);
        catExpenseViewModel = ViewModelProviders.of(this).get(CatExpenseViewModel.class);
        catIncomeViewModel = ViewModelProviders.of(this).get(CatIncomeViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chỉnh sửa nhóm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //receive data intent
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");

        if (category.equals("expense")) {
            //b = (budget) i.getSerializableExtra("budget");
            catexpense = (catexpense) intent.getSerializableExtra("catexpense");
            flash = 1;
            //Toast.makeText(getApplicationContext(),catexpense.getName(),Toast.LENGTH_SHORT).show();
            ImageResource = catexpense.getImage();
            tennhom = catexpense.getName();


        } else {
            catincome = (catincome) intent.getSerializableExtra("catincome");
            flash = 2;
            ImageResource = catincome.getImage();
            tennhom = catincome.getName();

        }
        imageViewIcon.setImageResource(ImageResource);
        editTextTenNhom.setText(tennhom);
        editTextTenNhom.requestFocus();
        //set enable button Lưu
        imgViewChangeIcon = ImageResource;
        stringEditText = tennhom;


        linearLayoutOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), option_icon.class);
                startActivityForResult(intent, 1);
            }
        });

        editTextTenNhom.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                stringEditText = editTextTenNhom.getText().toString();
                if ((stringEditText.equals(tennhom) && imgViewChangeIcon == ImageResource) || stringEditText.equals("")) {
                    btnLuu.setEnabled(false);
                } else {
                    btnLuu.setEnabled(true);
                }
                return false;
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveCategory();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_category_add_menu, menu);
        menu.getItem(0).setEnabled(false);
        btnLuu = (MenuItem) menu.findItem(R.id.menuSaveCategory);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSaveCategory:
                if (flash == 1) {
                    catexpense.setImage(imgViewChangeIcon);
                    catexpense.setName(stringEditText);
                    catExpenseViewModel.updateCatExpense(catexpense);
                    finish();
                } else if (flash == 2) {
                    catincome.setImage(imgViewChangeIcon);
                    catincome.setName(stringEditText);
                    catIncomeViewModel.updateCatIncome(catincome);
                    finish();
                }


                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                imgViewChangeIcon = data.getIntExtra("resrc", 1);
                imageViewIcon.setImageResource((imgViewChangeIcon));
                if ((stringEditText.equals(tennhom) && imgViewChangeIcon == ImageResource) || stringEditText.equals("")) {
                    btnLuu.setEnabled(false);
                } else {
                    btnLuu.setEnabled(true);
                }
            }
        }
    }

    private void RemoveCategory() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_remove);
        dialog.setCanceledOnTouchOutside(false);
        Button btnDongY = (Button) dialog.findViewById(R.id.btnDongy);
        Button btnHuy = (Button) dialog.findViewById(R.id.btnHuy);
        TextView txtRemoveCategory = (TextView) dialog.findViewById(R.id.txtRemoveCategory);

        if(flash == 1){
            txtRemoveCategory.setText("Bạn sẽ mất toàn bộ giao dịch trong nhóm " + catexpense.getName());
        }
        else if(flash == 2){
            txtRemoveCategory.setText("Bạn sẽ mất toàn bộ giao dịch trong nhóm " + catincome.getName());
        }

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (flash == 1) {
                        catExpenseViewModel.deleteCatExpense(catexpense);
                    } else if (flash == 2) {
                        catIncomeViewModel.deleteCatIncome(catincome);
                    }
                    dialog.cancel();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        dialog.show();
    }
}