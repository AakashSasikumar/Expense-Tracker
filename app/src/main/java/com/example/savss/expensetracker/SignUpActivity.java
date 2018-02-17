package com.example.savss.expensetracker;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void signUpButton_onClick(View view) {
        //TODO: OTP

        EditText yourName = findViewById(R.id.yourName);
        EditText emailAddress = findViewById(R.id.emailAddress);
        EditText phoneNumber = findViewById(R.id.phoneNumber);
        EditText password = findViewById(R.id.password);
        EditText confirmPassword = findViewById(R.id.confirmPassword);

        if (yourName.getText().toString().isEmpty()){
            displayError(R.string.emptyYourNameError, yourName);
            return;
        }

        if (emailAddress.getText().toString().isEmpty()){
            displayError(R.string.emptyEmailAddressError, emailAddress);
            return;
        }

        if (phoneNumber.getText().toString().isEmpty()){
            displayError(R.string.emptyPhoneNumberError, phoneNumber);
            return;
        }

        if (password.getText().toString().isEmpty()){
            displayError(R.string.emptyPasswordError, password);
            return;
        }

        if (confirmPassword.getText().toString().isEmpty()){
            displayError(R.string.emptyConfirmPasswordError, confirmPassword);
            return;
        }

        if (!password.getText().toString().equals(confirmPassword.getText().toString())){
            displayError(R.string.passwordNotMatchError, confirmPassword);
            return;
        }
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
