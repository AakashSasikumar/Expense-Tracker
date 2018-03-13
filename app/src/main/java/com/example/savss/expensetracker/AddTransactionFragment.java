package com.example.savss.expensetracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class AddTransactionFragment extends Fragment {

    private String transactionType = "";
    private LocalDatabaseHelper localDatabaseHelper;
    private Button income;
    private Button expense;
    private TextView incomeOrExpense;
    private EditText value;
    private Button clear;
    private EditText description;
    private Button add;
    private Spinner categories;
    private float defaultTextSize = 80;
    private float changedTextSize = 80;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View addTransactionView = inflater.inflate(R.layout.fragment_add_transaction, container, false);
        localDatabaseHelper = new LocalDatabaseHelper(addTransactionView.getContext(), null, null, 1);
        income = addTransactionView.findViewById(R.id.income_button);
        expense = addTransactionView.findViewById(R.id.expense_button);
        incomeOrExpense = addTransactionView.findViewById(R.id.income_or_expense);
        value = addTransactionView.findViewById(R.id.valueDisplay);
        value.addTextChangedListener(valueTextViewWatcher);
        clear = addTransactionView.findViewById(R.id.clearButton);
        clear.setOnClickListener(clearOnClickListener);
        add = addTransactionView.findViewById(R.id.addButton);
        add.setOnClickListener(addOnClickListener);
        income.setOnClickListener(incomeOnClickListener);
        description = addTransactionView.findViewById(R.id.descriptionView);
        categories = addTransactionView.findViewById(R.id.categorySpinner);
        ArrayAdapter aa = new ArrayAdapter(addTransactionView.getContext(), R.layout.category_spinner_layout, UserData.categories.toArray());
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(aa);
        expense.setOnClickListener(expenseOnClickListener);
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
            localDatabaseHelper.addTransaction(String.valueOf(UserData.userID), UserData.categories.indexOf(categories.getSelectedItem().toString()) + 1, valueOfTransactionType, value.getText().toString(), description.getText().toString());

        }
    };
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
