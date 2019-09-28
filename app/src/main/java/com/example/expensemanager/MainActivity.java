package com.example.expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText mLoginEmail, mLoginPass;
    private Button mLoginBtn;
    private TextView mforgetPass, mDontHaveAccount;

    //working with firebase technology
    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginDetail();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setMessage("progressing...");
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.show();
                String email = mLoginEmail.getText().toString().trim();
                String password = mLoginPass.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    mLoginEmail.setError("invalid !! Please enter your Email id");

                }
                if (TextUtils.isEmpty(password)){
                    mLoginPass.setError("invalid !! Please enter your Password");

                }
                mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "login sucessful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }
                        else {
                            mProgressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "login failed" ,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        mDontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });
        mforgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ResetActivity.class));
            }
        });
    }

    public void loginDetail(){
        mLoginEmail = findViewById(R.id.email_login);
        mLoginPass = findViewById(R.id.password_login);
        mforgetPass = findViewById(R.id.forget_password_login);
        mDontHaveAccount = findViewById(R.id.dont_account_login);
        mLoginBtn = findViewById(R.id.btn_login);
    }

}

