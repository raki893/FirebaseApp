package com.example.rr.firebaseapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button login_btnn;
    EditText email_input,password_input;
    TextView forgotpass_tv_btn,signup_tv_btn;

    RelativeLayout main_relative;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //view
        login_btnn = findViewById(R.id.login_btn);
        email_input = findViewById(R.id.email_et);
        password_input = findViewById(R.id.password_et);
        forgotpass_tv_btn = findViewById(R.id.forgotpass_tv);
        signup_tv_btn = findViewById(R.id.signup_tv);
        main_relative = findViewById(R.id.main_relative);

        signup_tv_btn.setOnClickListener(this);
        forgotpass_tv_btn.setOnClickListener(this);
        login_btnn.setOnClickListener(this);

        //init firebase auth
        auth = FirebaseAuth.getInstance();
        //check already session , if ok-> Dashboard
        if (auth.getCurrentUser() != null)
            startActivity(new Intent(MainActivity.this,Dashboard.class));

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.forgotpass_tv)
        {
            startActivity(new Intent(MainActivity.this,ForgotPassword.class));
            finish();
        }
        else if (view.getId() == R.id.signup_tv)
        {
            startActivity(new Intent(MainActivity.this,Signup.class));
            finish();
        }
        else if (view.getId() == R.id.login_btn)
        {
            loginUser(email_input.getText().toString(),password_input.getText().toString());
        }
    }

    private void loginUser(String email, final String password) {
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful())
                        {
                            if (password.length() < 6)
                            {
                                Snackbar snackbar = Snackbar.make(main_relative,"Password length must be over 6 ",Snackbar.LENGTH_SHORT);
                                snackbar.show();
                            }
                        }
                        else{
                            startActivity(new Intent(MainActivity.this,Dashboard.class));
                        }
                    }
                });
    }
}
