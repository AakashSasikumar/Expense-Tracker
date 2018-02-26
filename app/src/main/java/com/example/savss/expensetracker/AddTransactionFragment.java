package com.example.savss.expensetracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class AddTransactionFragment extends Fragment {

    private String transactionType = "";
    private Button income;
    private Button expense;
    private TextView typeDisplay;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View addTransactionView = inflater.inflate(R.layout.fragment_add_transaction, container, false);
        income = addTransactionView.findViewById(R.id.income_button);
        expense = addTransactionView.findViewById(R.id.expense_button);
        typeDisplay = addTransactionView.findViewById(R.id.typeDisplay);
        income.setOnClickListener(incomeOnClickListener);
        expense.setOnClickListener(expenseOnClickListener);
        return addTransactionView;
    }

    private View.OnClickListener incomeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            transactionType = "income";
            typeDisplay.setText(transactionType.toUpperCase());
        }
    };

    private View.OnClickListener expenseOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            transactionType = "expense";
            typeDisplay.setText(transactionType.toUpperCase());
        }
    };
}
