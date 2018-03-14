package com.example.savss.expensetracker;

import android.app.DatePickerDialog;
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
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DashboardFragment extends Fragment {
    private LocalDatabaseHelper localDatabaseHelper;
    private View dashboardView;
    private TextView fromDayTextView;
    private TextView toDayTextView;
    private ListView transactionListView;

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
        transactionListView.setAdapter(new transactionListViewAdapter(localDatabaseHelper.getTransactionData(UserData.userID, fromDate , toDate)));
    }

    private void setLastMonthPieChart() {
        TextView lastMonthAmountTextView = dashboardView.findViewById(R.id.lastMonthAmountTextView);
        PieChart todayPieChart = dashboardView.findViewById(R.id.todayPieChart);
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
        transactionListView.setAdapter(new transactionListViewAdapter(localDatabaseHelper.getTransactionData(UserData.userID, today.getTime(), Calendar.getInstance().getTime())));
    }

    class transactionListViewAdapter extends BaseAdapter {

        private ArrayList<TransactionData> transactionDatas;

        private transactionListViewAdapter(ArrayList<TransactionData> transactionDatas) {
            this.transactionDatas = transactionDatas;
        }

        @Override
        public int getCount() {
            return transactionDatas.size();
        }

        @Override
        public Object getItem(int i) {
            return transactionDatas.get(i);
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

            TransactionData transactionData = transactionDatas.get(i);

            dateTextView.setText(transactionData.getDateTime());
            amountTextView.setText(transactionData.getAmount());
            catagoryTextView.setText(transactionData.getCategory());
            return view;
        }
    }
}
