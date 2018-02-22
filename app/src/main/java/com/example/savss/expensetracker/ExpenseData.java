package com.example.savss.expensetracker;

import java.util.ArrayList;

public class ExpenseData {
    private ArrayList<String> categories = new ArrayList<>();
    private ArrayList<Integer> expenseAmounts = new ArrayList<>();

    public void add(String category, int expenseAmount) {
        categories.add(category);
        expenseAmounts.add(expenseAmount);
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public ArrayList<Integer> getExpenseAmounts() {
        return expenseAmounts;
    }
}
