package com.example.savss.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "expensetrakerDB.db";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_CATEGORY = "categories";
    private static final String TABLE_TRANSACTION = "transactions";

    public static final String USERS_ID = "user_id";
    public static final String USERS_NAME = "name";
    public static final String USERS_EMAIL = "email";
    public static final String USERS_PHONENUMBER = "phonenumber";
    public static final String USERS_PASSWORD = "password";

    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_NAME = "name";

    public static final String TRANSACTION_ID = "transaction_id";
    public static final String TRANSACTION_FKEY_USERS_ID = "user_id";
    public static final String TRANSACTION_DATE = "tdate";
    public static final String TRANSACTION_FKEY_CATEGORY_ID = "category_id";
    public static final String TRANSACTION_TYPE = "type";
    public static final String TRANSACTION_AMOUNT = "amount";
    public static final String TRANSACTION_DESCRIPTION = "description";

    public static enum IDType { Email, PhoneNumber }
    public static enum TransactionType {Income, Expense;

        public String toString(TransactionType tType) {
            if (tType == TransactionType.Expense) {
                return "expense";
            }
            else {
                return "income";
            }
        }
    };

    public LocalDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String userTableCreationQuery = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT);",
                TABLE_USERS, USERS_ID, USERS_NAME, USERS_EMAIL, USERS_PHONENUMBER, USERS_PASSWORD);
        String categoryTableCreationQuery = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT);",
                TABLE_CATEGORY, CATEGORY_ID, CATEGORY_NAME);

        String transactionTableCreationQuery = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s DATETIME, %s INTEGER, %s TEXT, %s INTEGER, %s TEXT, FOREIGN KEY(%s) REFERENCES %s(%s), FOREIGN KEY (%s) REFERENCES %s(%s));",
                TABLE_TRANSACTION, TRANSACTION_ID, TRANSACTION_FKEY_USERS_ID, TRANSACTION_DATE, TRANSACTION_FKEY_CATEGORY_ID, TRANSACTION_TYPE, TRANSACTION_AMOUNT, TRANSACTION_DESCRIPTION, TRANSACTION_FKEY_USERS_ID, TABLE_USERS, USERS_ID, TRANSACTION_FKEY_CATEGORY_ID, TABLE_CATEGORY, CATEGORY_ID);

        sqLiteDatabase.execSQL(userTableCreationQuery);
        sqLiteDatabase.execSQL(categoryTableCreationQuery);
        sqLiteDatabase.execSQL(transactionTableCreationQuery);
    }

    public boolean tryAddUser(String name, String email, String phoneNumber, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_NAME, name);
        contentValues.put(USERS_EMAIL, email);
        contentValues.put(USERS_PHONENUMBER, phoneNumber);
        contentValues.put(USERS_PASSWORD, password);
        if (isExisting(phoneNumber)) {
            return false;
        }
        else {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            sqLiteDatabase.insert(TABLE_USERS, null, contentValues);
            sqLiteDatabase.close();
            return true;
        }
    }

    private boolean isExisting(String phoneNumber){
        String checkQuery = String.format("SELECT * FROM %s WHERE %s = '%s'", TABLE_USERS, USERS_PHONENUMBER, phoneNumber);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(checkQuery, null);
        if (cursor.getCount() == 0) {
            sqLiteDatabase.close();
            return false;
        }
        else {
            sqLiteDatabase.close();
            return true;
        }
    }

    public String getPassword(String loginID, IDType idType) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        String getPasswordQuery = "";

        if (idType == IDType.Email) {
            getPasswordQuery = String.format("SELECT %s FROM %s WHERE %s = '%s'", USERS_PASSWORD, TABLE_USERS, USERS_EMAIL, loginID);
        }
        else if (idType == IDType.PhoneNumber) {
            getPasswordQuery = String.format("SELECT %s FROM %s WHERE %s = '%s'", USERS_PASSWORD, TABLE_USERS, USERS_PHONENUMBER, loginID);
        }

        Cursor cursor = sqLiteDatabase.rawQuery(getPasswordQuery, null);
        String password = "";

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex(USERS_PASSWORD)) != null) {
                password = cursor.getString(cursor.getColumnIndex(USERS_PASSWORD));
            }
            cursor.moveToNext();
        }
        sqLiteDatabase.close();

        return password;
    }

    public int getUserID(String loginID) {
        return getUserID(loginID, IDType.Email);
    }

    public int getUserID(String loginID, IDType idType) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        String getUserIDQuery = "";

        if (idType == IDType.Email) {
            getUserIDQuery = String.format("SELECT %s FROM %s WHERE %s = '%s'", USERS_ID, TABLE_USERS, USERS_EMAIL, loginID);
        }
        else if (idType == IDType.PhoneNumber) {
            getUserIDQuery = String.format("SELECT %s FROM %s WHERE %s = '%s'", USERS_ID, TABLE_USERS, USERS_PHONENUMBER, loginID);
        }

        Cursor cursor = sqLiteDatabase.rawQuery(getUserIDQuery, null);
        String userID = "";

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if (cursor.getString(cursor.getColumnIndex(USERS_ID)) != null) {
                userID = cursor.getString(cursor.getColumnIndex(USERS_ID));
            }
            cursor.moveToNext();
        }
        sqLiteDatabase.close();

        return Integer.parseInt(userID);
    }

    public ExpenseData getTodaysExpenses(String userID) {
        ExpenseData ed = new ExpenseData();
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        TransactionType tType = TransactionType.Income;
        String amount;
        String category;
        String fetchDataQuery = String.format("SELECT SUM(%s), %s, FROM %s WHERE %s != '%s' AND %s.%s = %s.%s and %s = %s GROUP BY (%s.%s);",
                TRANSACTION_AMOUNT, CATEGORY_NAME, TABLE_TRANSACTION, TRANSACTION_TYPE, tType.toString(tType), TABLE_CATEGORY, CATEGORY_ID, TABLE_TRANSACTION, TRANSACTION_FKEY_CATEGORY_ID, TRANSACTION_FKEY_USERS_ID, userID, TABLE_TRANSACTION, TRANSACTION_FKEY_CATEGORY_ID);
        System.out.println();
        Cursor c = sqLiteDatabase.rawQuery(fetchDataQuery, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(c.getColumnNames()[0])) != null) {

            }
        }
        return ed;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_USERS;
        sqLiteDatabase.execSQL(dropTableQuery);
        onCreate(sqLiteDatabase);
    }
}
