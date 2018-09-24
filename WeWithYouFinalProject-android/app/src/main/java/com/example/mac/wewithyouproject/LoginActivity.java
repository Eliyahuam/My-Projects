package com.example.mac.wewithyouproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity implements View.OnClickListener {

    private TextView buttonSingIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSingUp;
    private ProgressDialog progresDialog;
    private FireBaseAuthModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this,ChooseAvtivity.class));
            finish();

        }

        editTextEmail = (EditText) findViewById(R.id.loginEditTextEmail);
        editTextPassword = (EditText) findViewById(R.id.loginEditTextPassword);
        buttonSingIn = (TextView) findViewById(R.id.loginButtonLogin);
        textViewSingUp = (TextView) findViewById(R.id.singUpTextView);
        progresDialog = new ProgressDialog(this);
        buttonSingIn.setOnClickListener(this);
        textViewSingUp.setOnClickListener(this);
        user = new FireBaseAuthModel();
    }
    private void userLogin() {

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



        progresDialog.setMessage("Loginning");
        progresDialog.show();

        user.loginUser(email, password, new FireBaseAuthModel.MyCallBack() {
            @Override
            public void callbackcall(String result) {
                progresDialog.hide();
                if (result !="true") {
                    Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LoginActivity.this, "successfully login", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(LoginActivity.this,ChooseAvtivity.class));
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == buttonSingIn) {
            userLogin();
        }
        if (v == textViewSingUp) {
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}