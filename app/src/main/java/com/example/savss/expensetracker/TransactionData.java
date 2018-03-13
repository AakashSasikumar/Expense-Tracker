package com.example.savss.expensetracker;

public class TransactionData {
    private int id;
    private String amount;
    private String dateTime;
    private String category;
    private String description;

    public  TransactionData(int id, String amount, String dateTime, String category, String description, String transactionType) {
        this.id = id;
        this.amount = (transactionType.toLowerCase().equals("income") ? "+" : "-") + " " + amount;
        this.dateTime = dateTime;
        this.category = category;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getAmount() {
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
