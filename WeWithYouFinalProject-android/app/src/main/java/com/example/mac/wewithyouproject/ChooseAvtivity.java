package com.example.mac.wewithyouproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ChooseAvtivity extends AppCompatActivity {

    ImageView searchBtn;
    ImageView travelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_avtivity);
        searchBtn = (ImageView) findViewById(R.id.image_search);
        travelBtn = (ImageView) findViewById(R.id.image_travel);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ChooseAvtivity.this,SecondViewActivity.class));
            }
        });
        travelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ChooseAvtivity.this,FirstViewActivity.class));
            }
        });
    }

}
