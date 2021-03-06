package com.nhom10.moneymanagement_android;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.nhom10.moneymanagement_android.models.catexpense;
import com.nhom10.moneymanagement_android.models.catincome;
import com.nhom10.moneymanagement_android.viewmodels.CatExpenseViewModel;
import com.nhom10.moneymanagement_android.viewmodels.CatIncomeViewModel;

public class category_add extends AppCompatActivity {

    private CatIncomeViewModel catIncomeViewModel;
    private CatExpenseViewModel catExpenseViewModel;
    LinearLayout linearLayoutOption;
    ImageView imageViewIcon;
    EditText editTextTenNhom;
    MenuItem btnLuu;
    RadioButton radioButtonThu;
    RadioButton radioButtonChi;

    int ImageResource = R.drawable.money_bag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);
        linearLayoutOption = (LinearLayout) findViewById(R.id.lnOptionsIconGroup);
        imageViewIcon = (ImageView) findViewById(R.id.imgViewIcon);
        editTextTenNhom = (EditText) findViewById(R.id.editTextTenNhom);
        radioButtonThu = (RadioButton) findViewById(R.id.radioThu);
        radioButtonChi = (RadioButton) findViewById(R.id.radioChi);
        catIncomeViewModel = ViewModelProviders.of(this).get(CatIncomeViewModel.class);
        catExpenseViewModel = ViewModelProviders.of(this).get(CatExpenseViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thêm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        linearLayoutOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), option_icon.class);
                startActivityForResult(intent, 1);
            }
        });

//        editTextTenNhom.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                String stringEditText = editTextTenNhom.getText().toString();
//                if (stringEditText.equals("")) {
//                    btnLuu.setEnabled(false);
//                } else {
//                    btnLuu.setEnabled(true);
//                }
//                return false;
//            }
//        });


    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.custom_category_add_menu, menu);
        //menu.getItem(0).setEnabled(false);
        btnLuu = (MenuItem) menu.findItem(R.id.menuSaveCategory);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSaveCategory:
                String TenNhom = editTextTenNhom.getText().toString().trim();
                if (radioButtonChi.isChecked()) {
                    if (!checkCatExpenseByName(TenNhom)) {
                        break;
                    }

                    catexpense catexpense = new catexpense(TenNhom, ImageResource);
                    catExpenseViewModel.insertCatExpense(catexpense);

                    finish();
                } else {
                    if (!checkCatIncomeByName(TenNhom)) {
                        break;
                    }

                    catincome catI = new catincome(TenNhom, ImageResource);
                    catIncomeViewModel.insertCatIncome(catI);

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
                ImageResource = data.getIntExtra("resrc", 1);
                imageViewIcon.setImageResource((ImageResource));
            }
        }
    }

    private boolean checkCatExpenseByName(String TenNhom) {
        try {
            if(TenNhom.equals("")){
                Toast.makeText(getApplicationContext(), "Nhập tên nhóm !!", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!catExpenseViewModel.checkCatExpenseByName(TenNhom)) {
                Toast.makeText(getApplicationContext(), "Tên nhóm đã tồn tại !!", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkCatIncomeByName(String TenNhom) {
        try {
            if(TenNhom.equals("")){
                Toast.makeText(getApplicationContext(), "Nhập tên nhóm !!", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!catIncomeViewModel.checkCatIncomeByName(TenNhom)) {
                Toast.makeText(getApplicationContext(), "Tên nhóm đã tồn tại !!", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
