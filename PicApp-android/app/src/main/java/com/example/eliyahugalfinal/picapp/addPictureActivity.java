package com.example.eliyahugalfinal.picapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.eliyahugalfinal.picapp.Model.LocalFiles;
import com.example.eliyahugalfinal.picapp.Model.Model;
import com.example.eliyahugalfinal.picapp.Model.ModelFireBase;
import com.example.eliyahugalfinal.picapp.Model.MyFireBaseStorage;
import com.example.eliyahugalfinal.picapp.Model.Picture;

import java.io.IOException;


public class addPictureActivity extends Activity  {

    Button cancelBtn;
    Button saveBtn;
    EditText descriptionTV;
    ImageView cameraIcon;
    ImageView takenPhoto;
    private ProgressDialog progresDialog;

    private final static int CAM_REQUEST = 1313;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture);
        cancelBtn = (Button) findViewById(R.id.Cancel);
        saveBtn = (Button) findViewById(R.id.Save);
        cameraIcon = (ImageView) findViewById(R.id.getImage);
        cameraIcon.setImageResource(R.drawable.camera_icon);
        descriptionTV = (EditText) findViewById(R.id.description_editext);
        takenPhoto = (ImageView) findViewById(R.id.image_to_upload);
        progresDialog = new ProgressDialog(this);
        cameraIcon.setOnClickListener(new BtnTakePhotoClicker());

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Picture pic = new Picture();
                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();

                pic.imageName = descriptionTV.getText().toString();
                pic.descreption = descriptionTV.getText().toString() + "_" + ts;
                takenPhoto.buildDrawingCache();
                final Bitmap bmap = takenPhoto.getDrawingCache();
                 MyFireBaseStorage storeFireBase = new MyFireBaseStorage();
                progresDialog.setMessage("Uploading");
                progresDialog.show();

                storeFireBase.saveImage(bmap, pic.descreption, new Model.SaveImageListener() {
                    @Override
                    public void complete(String url) {
                        pic.url = url;
                        Model.getInstance().addPicture(bmap,pic);
                        setResult(Activity.RESULT_OK);
                        progresDialog.hide();
                        finish();
                    }
                    @Override
                    public void fail() {

                    }
                });


            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAM_REQUEST) {
            Bitmap btmp = (Bitmap)data.getExtras().get("data");
            takenPhoto.setImageBitmap(btmp);
        }

    }




    class BtnTakePhotoClicker implements Button.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent camerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camerIntent, CAM_REQUEST);
        }
    }


}
