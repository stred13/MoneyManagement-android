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
        listImage.add(new image("Hình số 1",R.drawable.beach));
        listImage.add(new image("Hình số 2",R.drawable.gas));
        listImage.add(new image("Hình số 3",R.drawable.arrow));
        listImage.add(new image("Hình số 4",R.drawable.beach));
        listImage.add(new image("Hình số 5",R.drawable.beach));
        listImage.add(new image("Hình số 6",R.drawable.beach));
        listImage.add(new image("Hình số 7",R.drawable.beach));
        listImage.add(new image("Hình số 8",R.drawable.beach));
        listImage.add(new image("Hình số 9",R.drawable.beach));
        listImage.add(new image("Hình số 10",R.drawable.beach));
        listImage.add(new image("Hình số 11",R.drawable.beach));
        listImage.add(new image("Hình số 12",R.drawable.beach));
        listImage.add(new image("Hình số 13",R.drawable.beach));
        listImage.add(new image("Hình số 14",R.drawable.beach));
        listImage.add(new image("Hình số 15",R.drawable.beach));


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
