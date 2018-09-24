package com.example.mac.wewithyouproject;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac on 24.4.2017.
 */

public class FireBaseAuthModel {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public interface MyCallBack {
        void callbackcall(String result);
    }


    public void createUser(final String email, String password,final MyCallBack callback) {
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("users").child(firebaseAuth.getCurrentUser().getUid());
                    String s = firebaseAuth.getCurrentUser().getUid();
                    Map values = new HashMap();
                    values.put("email",email);
                    values.put("uid",s);
                    myRef.setValue(values);
                    callback.callbackcall("true");
                }
                else {
                    callback.callbackcall(task.getException().getLocalizedMessage());
                }
            }
        });
    }

    public void loginUser(String email,String password, final  MyCallBack callBack) {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    callBack.callbackcall("true");
                }
                else {
                    callBack.callbackcall(task.getException().getLocalizedMessage());
                }
            }
        });
    }

}