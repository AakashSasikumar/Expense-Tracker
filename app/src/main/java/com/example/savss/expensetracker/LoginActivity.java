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
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
    }

    public void loginValidation(View v) {
        EditText id = findViewById(R.id.emailAddress);
        EditText password = findViewById(R.id.password);
        // TextInputLayout idInputLayout = (TextInputLayout) findViewById(R.id.emailAddressInputLayout);
        // TextInputLayout passwordInputLayout = (TextInputLayout) findViewById(R.id.passwordInputLayout);

        if (id.getText().toString().isEmpty()){
            displayError(R.string.emptyIDError, id);
            return;
        }

        if (password.getText().toString().isEmpty()){
            displayError(R.string.emptyPasswordError, password);
            return;
        }


        if (id.getText().toString().equals("a") && password.getText().toString().equals("a")) {
            // Intent toDashboard = new Intent(this, DashboardActivity.class);
            // startActivity(toDashboard);
            System.out.println("Valid");
        }
        else {
            // idInputLayout.setError("Invalid password");
            // idInputLayout.setErrorEnabled(true);

            displayError(R.string.loginErrorMessage);

            System.out.println("Invalid");
        }
    }

    private void displayError(int message) {
        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(120);

        toast.setText(message);
        toast.show();

        // Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void displayError(int message, View view) {
        Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        view.setAnimation(animShake);
        view.startAnimation(animShake);

        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(120);

        toast.setText(message);
        toast.show();

        // Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
