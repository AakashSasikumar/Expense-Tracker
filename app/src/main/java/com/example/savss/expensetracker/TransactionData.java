package com.example.savss.expensetracker;

public class TransactionData {
    private int id;
    private int amount;
    private String dateTime;
    private String catagory;
    private String description;

    private  TransactionData(int id, int amount, String dateTime, String catagory, String description) {
        this.id = id;
        this.amount = amount;
        this.dateTime = dateTime;
        this.catagory = catagory;
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

    public String getCatagory() {
        return catagory;
    }

    public String getDescription() {
        return description;
    }
}
