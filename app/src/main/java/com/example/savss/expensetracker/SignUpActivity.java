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

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
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

        if (!isPasswordValid(password.getText().toString())) {
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
    }

    private void displayError(String message, View view) {
        Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        view.setAnimation(animShake);
        view.startAnimation(animShake);

        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(120);

        toast.setText(message);
        toast.show();
    }

    private boolean isPasswordValid(String password)
    {
        EditText passwordEditText = findViewById(R.id.password);

        if (password.isEmpty()){
            displayError(R.string.emptyPasswordError, passwordEditText);
            return false;
        }

        int minLen = 8;
        int maxLen = 16;
        int numberCount = 0;
        int specialCount = 0;
        int upperCount = 0;
        int lowerCount = 0;

        // Count all types of characters
        for(int i = 0; i < password.length(); i++){
            char passwordChar = password.charAt(i);

            if(Character.isUpperCase(passwordChar)){
                upperCount++;
            }
            else if(Character.isLowerCase(passwordChar)){
                lowerCount++;
            }
            else if(Character.isDigit(passwordChar)){
                numberCount++;
            }
            else if((passwordChar >= 33 && passwordChar <= 46) || passwordChar == 64){
                specialCount++;
            }
            else {
                displayError(passwordChar + " character is not supported", passwordEditText);
                return false;
            }
        }

        String errorMessage = "";

        if (password.length() >= minLen && password.length() <= maxLen) {
            if(specialCount >= 1 && lowerCount >= 1 && upperCount >= 1 && numberCount >= 1) {
                return true;
            }
        }

        if (password.length() < minLen) {
            errorMessage += "Password must be at least " + minLen + " characters\n";
        }

        if (password.length() > maxLen) {
            errorMessage += "Password must be lesser than " + maxLen + " characters\n";
        }

        if (lowerCount == 0) {
            errorMessage += "You need at least one lower case character\n";
        }

        if (upperCount == 0) {
            errorMessage += "You need at least one upper case character\n";
        }

        if (numberCount == 0) {
            errorMessage += "You need at least one number\n";
        }

        if (specialCount == 0) {
            errorMessage += "You need at least one special character\n";
        }

        // Remove newlines from the end of the string
        errorMessage = errorMessage.trim();

        displayError(errorMessage, passwordEditText);

        return false;
    }
}
