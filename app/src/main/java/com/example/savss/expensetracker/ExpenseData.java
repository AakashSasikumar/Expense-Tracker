package com.example.savss.expensetracker;

import android.graphics.Color;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Random;

public class ExpenseData {
    private ArrayList<PieEntry> expenseAmounts = new ArrayList<>();

    public void add(String category, int expenseAmount) {
        expenseAmounts.add(new PieEntry((float) expenseAmount, category));
    }

    public PieDataSet getPieDataSet() {
        PieDataSet pieDataSet = new PieDataSet(expenseAmounts, "Today's expense");
        pieDataSet.setSliceSpace(5);
        pieDataSet.setValueTextSize(12f);

        /*ArrayList<Integer> chartColors = new ArrayList<>();
        Random random = new Random();
        float[] hsv = new float[3];

        for (PieEntry i : expenseAmounts) {
            hsv[0] = random.nextFloat() * 359;
            hsv[1] = 0.5f + (random.nextFloat() * 0.5f);
            hsv[2] = 0.5f + (random.nextFloat() * 0.5f);
            chartColors.add(Color.HSVToColor(hsv));
        }*/

        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        return pieDataSet;
    }
}
