package com.nhom10.moneymanagement_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.nhom10.moneymanagement_android.adapters.ImageAdapter;
import com.nhom10.moneymanagement_android.models.image;

import java.util.ArrayList;

public class option_icon extends AppCompatActivity {
    GridView gridView;
    ArrayList<image> listImage;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_icon);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chọn icon");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AnhXa();
        adapter = new ImageAdapter(this,R.layout.item_image_row,listImage);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),listImage.get(position).getName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("resrc", listImage.get(position).getHinh());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
    private void AnhXa(){
        gridView = (GridView) findViewById(R.id.gridView);
        listImage = new ArrayList<>();
        listImage.add(new image("Hình số 1",R.drawable.money_bag));
        listImage.add(new image("Hình số 2",R.drawable.automobile));
        listImage.add(new image("Hình số 3",R.drawable.biscuit));
        listImage.add(new image("Hình số 4",R.drawable.burger));
        listImage.add(new image("Hình số 5",R.drawable.bycicle));
        listImage.add(new image("Hình số 6",R.drawable.camera));
        listImage.add(new image("Hình số 7",R.drawable.cart));
        listImage.add(new image("Hình số 8",R.drawable.chickenwings));
        listImage.add(new image("Hình số 9",R.drawable.christmastree));
        listImage.add(new image("Hình số 10",R.drawable.church));
        listImage.add(new image("Hình số 11",R.drawable.coffee));
        listImage.add(new image("Hình số 12",R.drawable.delivery));
        listImage.add(new image("Hình số 13",R.drawable.development));
        listImage.add(new image("Hình số 14",R.drawable.dice));
        listImage.add(new image("Hình số 15",R.drawable.diet));

        listImage.add(new image("Hình số 16",R.drawable.doghouse));
        listImage.add(new image("Hình số 17",R.drawable.drink));
        listImage.add(new image("Hình số 18",R.drawable.email));
        listImage.add(new image("Hình số 19",R.drawable.fishing));
        listImage.add(new image("Hình số 20",R.drawable.friedchicken));
        listImage.add(new image("Hình số 21",R.drawable.gamecontroller));
        listImage.add(new image("Hình số 22",R.drawable.guitar));
        listImage.add(new image("Hình số 23",R.drawable.gummybear));
        listImage.add(new image("Hình số 24",R.drawable.house));
        listImage.add(new image("Hình số 25",R.drawable.icecream));
        listImage.add(new image("Hình số 26",R.drawable.icetea));
        listImage.add(new image("Hình số 27",R.drawable.icon));
        listImage.add(new image("Hình số 28",R.drawable.icream));
        listImage.add(new image("Hình số 29",R.drawable.image));
        listImage.add(new image("Hình số 30",R.drawable.income));

        listImage.add(new image("Hình số 31",R.drawable.jogging));
        listImage.add(new image("Hình số 32",R.drawable.lifeinsurance));
        listImage.add(new image("Hình số 33",R.drawable.login));
        //listImage.add(new image("Hình số 34",R.drawable.male_user));
        listImage.add(new image("Hình số 35",R.drawable.newspaper));
        listImage.add(new image("Hình số 36",R.drawable.noodles));
        listImage.add(new image("Hình số 37",R.drawable.onlineshop));
        listImage.add(new image("Hình số 38",R.drawable.onlineshop1));
        listImage.add(new image("Hình số 39",R.drawable.openbook));
        listImage.add(new image("Hình số 40",R.drawable.plan));
        listImage.add(new image("Hình số 41",R.drawable.plane));
        listImage.add(new image("Hình số 42",R.drawable.playingcards));
        listImage.add(new image("Hình số 43",R.drawable.ramen));
        listImage.add(new image("Hình số 44",R.drawable.shelter));
        listImage.add(new image("Hình số 45",R.drawable.shopping));

        listImage.add(new image("Hình số 46",R.drawable.shoppingbasket));
        listImage.add(new image("Hình số 47",R.drawable.smartphone));
        listImage.add(new image("Hình số 48",R.drawable.strategy));
        listImage.add(new image("Hình số 49",R.drawable.toffee));
        listImage.add(new image("Hình số 50",R.drawable.violin));
        listImage.add(new image("Hình số 51",R.drawable.watermelon));
        listImage.add(new image("Hình số 52",R.drawable.wheelchair));


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
