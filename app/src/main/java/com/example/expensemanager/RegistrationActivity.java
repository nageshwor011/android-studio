package com.example.expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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

public class RegistrationActivity extends AppCompatActivity {
    private EditText mSignupEmail, mSignUpPassword, mConformPasswordSignUP;
    private TextView mAlreadyHaveAccount;
    private Button mSignupBtn;
    //for Registration firebase
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        registration();
        mAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("processing...");

    }

    private void registration(){

        mSignupBtn = findViewById(R.id.btn_signup);
        mSignupEmail = findViewById(R.id.email_signup);
        mSignUpPassword = findViewById(R.id.password_signup);
        mConformPasswordSignUP = findViewById(R.id.repassword_signup);
        mAlreadyHaveAccount = findViewById(R.id.have_account_signup);

        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.show();
                String email = mSignupEmail.getText().toString().trim();
                String password = mSignUpPassword.getText().toString().trim();
                String conformPas = mConformPasswordSignUP.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    mSignupEmail.setError("Please enter your Email");
                }
                if (TextUtils.isEmpty(password)){
                    mSignUpPassword.setError("Please enter your password");
                }
                if (TextUtils.isEmpty(conformPas)){
                    mSignUpPassword.setError("Please enter your password");
                }
                if (!password.equals(conformPas)){
                    mConformPasswordSignUP.setError("Password not match");
                }
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegistrationActivity.this, "LoRegistration sucessful ", Toast.LENGTH_SHORT).show();
                                mProgressDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Registration failed ", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }
                    }
                });

            }
        });
        mAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        });




    }
}
