package com.example.rr.firebaseapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity implements View.OnClickListener{
    Button reg_btn;
    EditText signup_etxt_1,signup_etxt_2;
    TextView forgotpass_signup_tv,aa_login_signup_tv;
    RelativeLayout signup_relative;

    private FirebaseAuth auth;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        reg_btn = findViewById(R.id.Registration_btn);
        signup_etxt_1 = findViewById(R.id.signup_et_1);
        signup_etxt_2 = findViewById(R.id.signup_et_2);
        forgotpass_signup_tv =findViewById(R.id.forgot_pass_su_tv);
        aa_login_signup_tv = findViewById(R.id.aa_login_su_tv);
        signup_relative = findViewById(R.id.signup_relative);

        reg_btn.setOnClickListener(this);
        forgotpass_signup_tv.setOnClickListener(this);
        aa_login_signup_tv.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.forgot_pass_su_tv){
            startActivity(new Intent(Signup.this,ForgotPassword.class));
            finish();
        }else if (view.getId() == R.id.aa_login_su_tv){
            startActivity(new Intent(Signup.this,MainActivity.class));
            finish();
        }else if (view.getId() == R.id.Registration_btn){
           signUpUser(signup_etxt_1.getText().toString(),signup_etxt_2.getText().toString());
        }
    }

    private void signUpUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful())
                        {
                            snackbar = Snackbar.make(signup_relative, "Error: " + task.getException(), Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                    else

                    {
                        snackbar = snackbar.make(signup_relative, "Register success! ", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                }
                });

    }
}
