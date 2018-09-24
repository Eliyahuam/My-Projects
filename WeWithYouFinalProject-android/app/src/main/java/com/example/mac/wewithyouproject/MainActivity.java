package com.example.mac.wewithyouproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSingIn;
    private ProgressDialog progresDialog;
    private FireBaseAuthModel user;
    private TextView getImageButton;
    private ImageView takenImage;
    private final static int CAM_REQUEST = 1313;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getImageButton = (TextView) findViewById(R.id.get_image_button);
        takenImage = (ImageView) findViewById(R.id.image_view);

        buttonRegister = (TextView) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSingIn = (TextView) findViewById(R.id.singInTextView);
        progresDialog = new ProgressDialog(this);
        user = new FireBaseAuthModel();

        buttonRegister.setOnClickListener(this);
        textViewSingIn.setOnClickListener(this);

        getImageButton.setOnClickListener(new BtnTakePhotoClicker());
    }

    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();



        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            return;
        }



        progresDialog.setMessage("Registering User");
        progresDialog.show();
        //if validation is ok

        user.createUser(email, password, new FireBaseAuthModel.MyCallBack() {
            @Override
            public void callbackcall(String result) {
                progresDialog.hide();
                if (result =="true") {
                    takenImage.buildDrawingCache();
                    final Bitmap bmap = takenImage.getDrawingCache();
                    MyFireBaseStorage storeFireBase = new MyFireBaseStorage();
                    progresDialog.setMessage("Uploading");
                    progresDialog.show();


                    storeFireBase.saveImage(bmap,"image", new MyFireBaseStorage.SaveImageListener() {
                        @Override
                        public void complete(String url) {

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            Map values = new HashMap();

                            values.put("imageUrl",url);
                            myRef.updateChildren(values);

                            setResult(Activity.RESULT_OK);
                            progresDialog.hide();
                            finish();
                        }
                        @Override
                        public void fail() {

                        }
                    });


                    Toast.makeText(MainActivity.this, "Registred Successfully", Toast.LENGTH_SHORT).show();
                    finish();

                    startActivity(new Intent(MainActivity.this,ChooseAvtivity.class));
                }
                else {
                    Toast.makeText(MainActivity.this,result, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onClick(View v) {

        if (v == buttonRegister) {
            registerUser();
        }
        if (v == textViewSingIn) {
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAM_REQUEST) {
            Bitmap btmp = (Bitmap)data.getExtras().get("data");
            takenImage.setImageBitmap(btmp);
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
