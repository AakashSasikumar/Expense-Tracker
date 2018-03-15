package com.example.savss.expensetracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DashboardFragment extends Fragment {
    private LocalDatabaseHelper localDatabaseHelper;
    private View dashboardView;
    private TextView fromDayTextView;
    private TextView toDayTextView;
    private ListView transactionListView;
    private TransactionListViewAdapter transactionListViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        localDatabaseHelper = new LocalDatabaseHelper(dashboardView.getContext(), null, null, 1);

        setDatePicker();
        setLastMonthPieChart();
        displayTransactionlistview();

        return dashboardView;
    }

    private void setDatePicker() {
        fromDayTextView = dashboardView.findViewById(R.id.fromDayTextView);
        toDayTextView = dashboardView.findViewById(R.id.toDayTextView);

        Calendar today = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        toDayTextView.setText(simpleDateFormat.format(today.getTime()));
        today.add(Calendar.MONTH, -1);
        fromDayTextView.setText(simpleDateFormat.format(today.getTime()));

        fromDayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(dashboardView.getContext(), R.style.Theme_AppCompat_Light_Dialog, fromDatePickerDateSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });

        toDayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(dashboardView.getContext(), R.style.Theme_AppCompat_Light_Dialog, toDatePickerDateSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.show();
            }
        });
    }

    private DatePickerDialog.OnDateSetListener fromDatePickerDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            month++;
            String pickedDate = day + "/" + month + "/" + year;
            fromDayTextView.setText(pickedDate);
            setFromToDate(pickedDate, toDayTextView.getText().toString());
        }
    };

    private DatePickerDialog.OnDateSetListener toDatePickerDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            month++;
            String pickedDate = day + "/" + month + "/" + year;
            toDayTextView.setText(pickedDate);
            setFromToDate(fromDayTextView.getText().toString(), pickedDate);
        }
    };

    private AdapterView.OnItemClickListener transactionListViewItemClickListener = new AdapterView.OnItemClickListener() {
        private Dialog transactionDataPopUp;
        private TextView transactionIDTextView;
        private TextView transactionTypeTextView;
        private TextView transactionAmountTextView;
        private TextView transactionCategoryTextView;
        private TextView transactionDateTextView;
        private EditText transactionDescriptionEditText;
        private Button closeButton;
        private Button editButton;

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            TransactionData transactionData = (TransactionData)transactionListViewAdapter.getItem(i);

            initialisePopUp();

            transactionIDTextView.setText(String.valueOf(transactionData.getId()));
            transactionTypeTextView.setText(transactionData.getTransactionType());
            transactionAmountTextView.setText(transactionData.getAmount());
            transactionCategoryTextView.setText(transactionData.getCategory());
            transactionDateTextView.setText(transactionData.getDateTime());
            transactionDescriptionEditText.setText(transactionData.getDescription());
        }

        private void initialisePopUp() {
            transactionDataPopUp = new Dialog(dashboardView.getContext());
            transactionDataPopUp.requestWindowFeature(Window.FEATURE_NO_TITLE);
            transactionDataPopUp.setContentView(R.layout.transaction_details_popup);

            transactionIDTextView = transactionDataPopUp.findViewById(R.id.transactionIDTextView);
            transactionTypeTextView = transactionDataPopUp.findViewById(R.id.transactionTypeTextView);
            transactionAmountTextView = transactionDataPopUp.findViewById(R.id.transactionAmountTextView);
            transactionCategoryTextView = transactionDataPopUp.findViewById(R.id.transactionCategoryTextView);
            transactionDateTextView = transactionDataPopUp.findViewById(R.id.transactionDateTextView);
            transactionDescriptionEditText = transactionDataPopUp.findViewById(R.id.transactionDescriptionEditText);
            closeButton = transactionDataPopUp.findViewById(R.id.closeButton);
            editButton = transactionDataPopUp.findViewById(R.id.editButton);

            closeButton.setOnClickListener(closeButtonClickListener);
            editButton.setOnClickListener(editButtonClickListener);
        }

        private View.OnClickListener closeButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transactionDataPopUp.cancel();
            }
        };

        private View.OnClickListener editButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setFromToDate(String fromDateString, String toDateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date fromDate = null;
        Date toDate = null;
        try {
            fromDate = simpleDateFormat.parse(fromDateString);
            toDate = simpleDateFormat.parse(toDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        transactionListViewAdapter = new TransactionListViewAdapter(localDatabaseHelper.getTransactionData(UserData.userID, fromDate , toDate));
        transactionListView.setAdapter(transactionListViewAdapter);
    }

    private void setLastMonthPieChart() {
        TextView lastMonthAmountTextView = dashboardView.findViewById(R.id.lastMonthAmountTextView);
        PieChart todayPieChart = dashboardView.findViewById(R.id.lastMonthPieChart);
        todayPieChart.setDescription(null);
        todayPieChart.setRotationEnabled(true);
        todayPieChart.setHoleRadius(15f);
        todayPieChart.setTransparentCircleAlpha(0);
        todayPieChart.setDrawEntryLabels(true);
        //todayPieChart.setHoleColor(R.color.transparent);

        ExpenseData expenseData = localDatabaseHelper.getLastMonthExpenses(UserData.userID);

        lastMonthAmountTextView.setText(String.valueOf(expenseData.getTotalExpenseAmount()));

        Legend legend = todayPieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        PieData pieData = new PieData(expenseData.getPieDataSet());
        todayPieChart.setData(pieData);
        todayPieChart.animateXY(500, 500);
        todayPieChart.invalidate();
    }

    private void displayTransactionlistview() {
        transactionListView = dashboardView.findViewById(R.id.transactionListView);
        Calendar today = Calendar.getInstance();
        today.add(Calendar.MONTH, -1);

        transactionListViewAdapter = new TransactionListViewAdapter(localDatabaseHelper.getTransactionData(UserData.userID, today.getTime(), Calendar.getInstance().getTime()));
        transactionListView.setAdapter(transactionListViewAdapter);

        transactionListView.setOnItemClickListener(transactionListViewItemClickListener);
    }

    class TransactionListViewAdapter extends BaseAdapter {

        private ArrayList<TransactionData> transactionData;

        private TransactionListViewAdapter(ArrayList<TransactionData> transactionData) {
            this.transactionData = transactionData;
        }

        @Override
        public int getCount() {
            return transactionData.size();
        }

        @Override
        public Object getItem(int i) {
            return transactionData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.transaction_listviewitem_template, null);
            TextView dateTextView = view.findViewById(R.id.dateTextView);
            TextView amountTextView = view.findViewById(R.id.amountTextView);
            TextView catagoryTextView = view.findViewById(R.id.catagoryTextView);

            TransactionData transactionData = this.transactionData.get(i);

            dateTextView.setText(transactionData.getDateTime());
            String amount = (transactionData.getTransactionType().toLowerCase().equals("income") ? "+" : "-") + " " + transactionData.getAmount();
            amountTextView.setText(amount);
            catagoryTextView.setText(transactionData.getCategory());
            return view;
        }
    }
}
