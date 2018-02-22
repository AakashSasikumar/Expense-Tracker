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

        if (id.getText().toString().isEmpty()){
            displayError(R.string.emptyIDError, id);
            return;
        }

        if (password.getText().toString().isEmpty()){
            displayError(R.string.emptyPasswordError, password);
            return;
        }

        // TODO: Remove this if in final product
        if (id.getText().toString().equals("a") && password.getText().toString().equals("a")) {
            Intent toDashboard = new Intent(this, HomeActivity.class);
            startActivity(toDashboard);
        }

        LocalDatabaseHelper localDatabaseHelper = new LocalDatabaseHelper(this, null, null, 1);
        LocalDatabaseHelper.IDType idType;

        if (id.getText().toString().contains("@")) {
            idType = LocalDatabaseHelper.IDType.Email;
        }
        else {
            idType = LocalDatabaseHelper.IDType.PhoneNumber;
        }

        if (password.getText().toString().equals(localDatabaseHelper.getPassword(id.getText().toString(), idType))) {
            Intent toDashboard = new Intent(this, HomeActivity.class);
            toDashboard.putExtra(LocalDatabaseHelper.USERS_ID, localDatabaseHelper.getUserID(id.getText().toString(), idType));
            startActivity(toDashboard);
        }
        else {
            displayError(R.string.loginErrorMessage);
        }
    }

    private void displayError(int message) {
        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(120);

        toast.setText(message);
        toast.show();
    }

    private void displayError(int message, View view) {
        Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        view.setAnimation(animShake);
        view.startAnimation(animShake);

        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(120);

        toast.setText(message);
        toast.show();
    }
}
