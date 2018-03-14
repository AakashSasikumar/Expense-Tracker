package com.example.savss.expensetracker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class AddTransactionFragment extends Fragment {

    private String transactionType = "";
    private View addTransactionView;
    private LocalDatabaseHelper localDatabaseHelper;
    private Button income;
    private Button expense;
    private TextView incomeOrExpense;
    private EditText value;
    private Button clear;
    private EditText description;
    private Button add;
    private Spinner categorySpinner;
    private TextView dateTextView;
    private float defaultTextSize = 80;
    private float changedTextSize = 80;
    Toast toast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addTransactionView = inflater.inflate(R.layout.fragment_add_transaction, container, false);
        localDatabaseHelper = new LocalDatabaseHelper(addTransactionView.getContext(), null, null, 1);

        income = addTransactionView.findViewById(R.id.income_button);
        expense = addTransactionView.findViewById(R.id.expense_button);
        incomeOrExpense = addTransactionView.findViewById(R.id.income_or_expense);
        value = addTransactionView.findViewById(R.id.valueDisplay);
        value.addTextChangedListener(valueTextViewWatcher);
        clear = addTransactionView.findViewById(R.id.clearButton);
        clear.setOnClickListener(clearOnClickListener);
        add = addTransactionView.findViewById(R.id.addButton);
        toast = Toast.makeText(addTransactionView.getContext(), "", Toast.LENGTH_SHORT);
        add.setOnClickListener(addOnClickListener);
        income.setOnClickListener(incomeOnClickListener);
        description = addTransactionView.findViewById(R.id.descriptionView);
        categorySpinner = addTransactionView.findViewById(R.id.categorySpinner);
        expense.setOnClickListener(expenseOnClickListener);
        dateTextView = addTransactionView.findViewById(R.id.dateTextView);
        dateTextView.setOnClickListener(dateOnClickListener);

        ArrayList<String> categories = new ArrayList<>(UserData.categories);
        categories.add(0, "Choose a category");
        ArrayAdapter aa = new ArrayAdapter(addTransactionView.getContext(), R.layout.category_spinner_layout, categories.toArray());
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(aa);

        return addTransactionView;
    }

    private View.OnClickListener incomeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            transactionType = "+";
            incomeOrExpense.setTextSize(80);
            incomeOrExpense.setText(transactionType);
        }
    };

    private View.OnClickListener expenseOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            transactionType = "-";
            incomeOrExpense.setTextSize(100);
            incomeOrExpense.setText(transactionType);
        }
    };

    private View.OnClickListener clearOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            value.setText("0.0");
            transactionType = "+";
            incomeOrExpense.setText(transactionType);
            description.setText("");
        }
    };

    private View.OnClickListener addOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String valueOfTransactionType = transactionType.equals("+") ? "income" : "expense";

            if (Integer.parseInt(value.getText().toString()) == 0) {
                displayTosat("Add transaction amount");
                return;
            }

            if (categorySpinner.getSelectedItem().toString().equals("Choose a category")) {
                displayTosat("Choose a category for transaction");
                return;
            }

            localDatabaseHelper.addTransaction(String.valueOf(UserData.userID), UserData.categories.indexOf(categorySpinner.getSelectedItem().toString()) + 1, valueOfTransactionType, value.getText().toString(), description.getText().toString());
        }
    };

    private View.OnClickListener dateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(addTransactionView.getContext(), R.style.Theme_AppCompat_Light_Dialog, datePickerDateSetListener, year, month, day);
            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            datePickerDialog.show();
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            month++;
            String pickedDate = day + "/" + month + "/" + year;
            dateTextView.setText(pickedDate);
        }
    };

    private void displayTosat(String message) {
        Vibrator vib = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vib.vibrate(120);

        toast.setText(message);
        toast.show();
    }

    private TextWatcher valueTextViewWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (isTooLarge()) {
                changedTextSize  = defaultTextSize - (value.getText().toString().trim().length() - 8) * 6;
                value.setTextSize(TypedValue.COMPLEX_UNIT_DIP, changedTextSize);
            }
            else {
                value.setTextSize(TypedValue.COMPLEX_UNIT_DIP, defaultTextSize);
                changedTextSize = defaultTextSize;
            }
        }
        private boolean isTooLarge() {
            return value.getText().toString().trim().length() > 7;
        }
    };
}
