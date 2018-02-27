package com.example.savss.expensetracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;

public class DashboardFragment extends Fragment {

    private View dashboardView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        setTodayPieChart();

        return dashboardView;
    }

    private void setTodayPieChart() {
        PieChart todayPieChart = dashboardView.findViewById(R.id.todayPieChart);
        todayPieChart.setDescription(null);
        todayPieChart.setRotationEnabled(true);
        todayPieChart.setHoleRadius(15f);
        todayPieChart.setTransparentCircleAlpha(300);
        todayPieChart.setDrawEntryLabels(true);
        todayPieChart.setHoleColor(R.color.transparent);

        LocalDatabaseHelper localDatabaseHelper = new LocalDatabaseHelper(dashboardView.getContext(), null, null, 1);
        ExpenseData expenseData = localDatabaseHelper.getTodaysExpenses(HomeActivity.userID);

        Legend legend = todayPieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        PieData pieData = new PieData(expenseData.getPieDataSet());
        todayPieChart.setData(pieData);
        todayPieChart.animateXY(500, 500);
        todayPieChart.invalidate();
    }

}
