package com.example.savss.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.support.design.widget.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");/*
        getSupportActionBar().hide();*/
    }

    public void loginValidation(View v) {
        EditText id = findViewById(R.id.emailAddress);
        EditText password = findViewById(R.id.password);
        TextInputLayout idInputLayout = (TextInputLayout) findViewById(R.id.emailAddressInputLayout);
        TextInputLayout passwordInputLayout = (TextInputLayout) findViewById(R.id.passwordInputLayout);
        TextView invText = findViewById(R.id.invalidText);


        if (id.getText().toString().equals("a") && password.getText().toString().equals("a")) {
            //Intent toDashboard = new Intent(v.getContext(), DashboardActivity.class);
            //v.getContext().startActivity(toDashboard);
            System.out.println("Valid");
            invText.setVisibility(View.GONE);
        }
        else {
            //idInputLayout.setError("Invalid password");
            //idInputLayout.setErrorEnabled(true);

            Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vib.vibrate(120);

            Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
            id.setAnimation(animShake);
            id.startAnimation(animShake);

            System.out.println("Invalid");
            invText.setVisibility(View.VISIBLE);
        }
    }
}
