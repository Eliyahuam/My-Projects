package com.example.eliyahugalfinal.picapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eliyahugalfinal.picapp.Model.FireBaseAuthModel;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSingIn;
    private ProgressDialog progresDialog;
    private FireBaseAuthModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSingIn = (TextView) findViewById(R.id.singInTextView);
        progresDialog = new ProgressDialog(this);
        user = new FireBaseAuthModel();

        buttonRegister.setOnClickListener(this);
        textViewSingIn.setOnClickListener(this);
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
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
                    Toast.makeText(MainActivity.this, "Registred Successfully", Toast.LENGTH_SHORT).show();
                    finish();

                    startActivity(new Intent(MainActivity.this,rootViewTabBarActivity.class));
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
}
