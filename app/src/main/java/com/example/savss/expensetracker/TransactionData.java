package com.example.savss.expensetracker;

public class TransactionData {
    private int id;
    private String amount;
    private String dateTime;
    private String category;
    private String description;
    private String transactionType;

    public  TransactionData(int id, String amount, String dateTime, String category, String description, String transactionType) {
        this.id = id;
        this.amount = amount;
        this.dateTime = dateTime;
        this.category = category;
        this.description = description;
        this.transactionType = transactionType.substring(0,1).toUpperCase() + transactionType.substring(1).toLowerCase();
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

    public String getTransactionType() {
        return transactionType;
    }
}
