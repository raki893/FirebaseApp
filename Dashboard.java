package com.example.rr.firebaseapplication;

import android.content.Intent;
import android.media.MediaPlayer;
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
import com.google.firebase.auth.FirebaseUser;

public class Dashboard extends AppCompatActivity implements View.OnClickListener{
    Button changepass_btnn,logout_btnn;
    EditText password_dash_et;
    TextView dashboard_txtv;

    RelativeLayout dashboard_relative;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        changepass_btnn = findViewById(R.id.change_pass_btn);
        logout_btnn = findViewById(R.id.logout_btn);
        password_dash_et = findViewById(R.id.dashboard_et);
        dashboard_txtv = findViewById(R.id.dashboard_tv);
        dashboard_relative = findViewById(R.id.dashboard_relative);

        changepass_btnn.setOnClickListener(this);
        logout_btnn.setOnClickListener(this);

        //init firebase auth
        auth = FirebaseAuth.getInstance();

        //session check
        if (auth.getCurrentUser() != null)
            dashboard_txtv.setText("Welcome , "+auth.getCurrentUser().getEmail());
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.change_pass_btn)
            changePassword(password_dash_et.getText().toString());
        else if (view.getId() == R.id.logout_btn)
            logoutUser();

    }

    private void logoutUser() {
        auth.signOut();
        if (auth.getCurrentUser() == null)
        {
            startActivity(new Intent(Dashboard.this,MainActivity.class));
            finish();
        }
    }

    private void changePassword(String newPassword) {
        FirebaseUser user = auth.getCurrentUser();
        user.updatePassword(newPassword).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Snackbar snackbar = Snackbar.make(dashboard_relative,"Password changed",Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });

    }
}
