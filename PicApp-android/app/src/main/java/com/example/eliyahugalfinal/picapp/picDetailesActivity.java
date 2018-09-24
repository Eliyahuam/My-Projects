package com.example.eliyahugalfinal.picapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eliyahugalfinal.picapp.Model.Model;

public class picDetailesActivity extends Activity {

    TextView descriptionTV;
    ImageView myImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_detailes);
        descriptionTV = (TextView) findViewById(R.id.description);
        myImage = (ImageView) findViewById(R.id.image_from_fire_base);
        String description = getIntent().getStringExtra("DESCRIPTION");
        String imageUrl = getIntent().getStringExtra("IMAGE_URL");

        /*
        Model.getInstance().storeFireBase.getImage(imageUrl, new Model.GetImageListener() {
            @Override
            public void onSccess(Bitmap image) {
                myImage.setImageBitmap(image);
            }

            @Override
            public void onFail() {

            }
        });
        */
        //here need to implement get image

        Model.getInstance().getImage(imageUrl, new Model.GetImageListener() {
            @Override
            public void onSccess(Bitmap image) {
                myImage.setImageBitmap(image);
            }

            @Override
            public void onFail() {

            }
        });

        descriptionTV.setText(description);
    }
}
