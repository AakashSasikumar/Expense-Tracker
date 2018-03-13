package com.example.savss.expensetracker;

public class TransactionData {
    private int id;
    private int amount;
    private String dateTime;
    private String category;
    private String description;

    public  TransactionData(int id, int amount, String dateTime, String category, String description) {
        this.id = id;
        this.amount = amount;
        this.dateTime = dateTime;
        this.category = category;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }
}
