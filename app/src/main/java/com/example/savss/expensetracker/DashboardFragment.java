package com.example.savss.expensetracker;

import android.os.Bundle;
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

import java.util.ArrayList;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    private LocalDatabaseHelper localDatabaseHelper;
    private View dashboardView;
    private DatePicker datePicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        localDatabaseHelper = new LocalDatabaseHelper(dashboardView.getContext(), null, null, 1);

        setTodayPieChart();
        displayTransactionlistview();

        return dashboardView;
    }



    private void setTodayPieChart() {
        PieChart todayPieChart = dashboardView.findViewById(R.id.todayPieChart);
        todayPieChart.setDescription(null);
        todayPieChart.setRotationEnabled(true);
        todayPieChart.setHoleRadius(15f);
        todayPieChart.setTransparentCircleAlpha(0);
        todayPieChart.setDrawEntryLabels(true);
        //todayPieChart.setHoleColor(R.color.transparent);

        ExpenseData expenseData = localDatabaseHelper.getTodaysExpenses(HomeActivity.userID);

        Legend legend = todayPieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        PieData pieData = new PieData(expenseData.getPieDataSet());
        todayPieChart.setData(pieData);
        todayPieChart.animateXY(500, 500);
        todayPieChart.invalidate();
    }

    private void displayTransactionlistview() {
        ListView transactionListView = dashboardView.findViewById(R.id.transactionListView);
        transactionListView.setAdapter(new transactionListViewAdapter(localDatabaseHelper.getTransactionData()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fromDayTextView:

        }

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
            amountTextView.setText(String.valueOf(transactionData.getAmount()));
            catagoryTextView.setText(transactionData.getCategory());
            return view;
        }
    }
}
