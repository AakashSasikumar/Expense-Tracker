package com.example.savss.expensetracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

public class DashboardFragment extends Fragment {

    private PieChart todayPieChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dashboardView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        todayPieChart = dashboardView.findViewById(R.id.todayPieChart);
        todayPieChart.setRotationEnabled(true);
        todayPieChart.setHoleRadius(25f);
        todayPieChart.setTransparentCircleAlpha(128);
        todayPieChart.setDrawEntryLabels(true);

        Legend legend = todayPieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        LocalDatabaseHelper localDatabaseHelper = new LocalDatabaseHelper(dashboardView.getContext(), null, null, 1);
        ExpenseData expenseData = localDatabaseHelper.getTodaysExpenses();
        PieData pieData = new PieData(expenseData.getPieDataSet());
        todayPieChart.setData(pieData);
        todayPieChart.invalidate();

        todayPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                System.out.println(h.toString());
                System.out.println(h.getY());
            }

            @Override
            public void onNothingSelected() {

            }
        });

        return dashboardView;
    }


}
