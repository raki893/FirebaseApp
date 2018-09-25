package com.example.rr.firebaseapplication;

import android.arch.core.executor.TaskExecutor;
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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    EditText forgotpass_etxt;
    Button reset_btnn;
    TextView back_txtv;

    RelativeLayout forgotpass_relative;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotpass_etxt = findViewById(R.id.forgotpass_et);
        reset_btnn = findViewById(R.id.reset_btn);
        back_txtv = findViewById(R.id.back_tv);
        forgotpass_relative = findViewById(R.id.forgotpass_relative);

        reset_btnn.setOnClickListener(this);
        back_txtv.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_tv)
        {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else if (view.getId() == R.id.reset_btn)
        {
            resetPassword(forgotpass_etxt.getText().toString());
        }
    }

    private void resetPassword(final String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                        Snackbar snackbar = Snackbar.make(forgotpass_relative,"We have sent password to email: "+email,Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }else{
                            Snackbar snackbar = Snackbar.make(forgotpass_relative,"Failed to send password",Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }

                    }
                });

    }
}
