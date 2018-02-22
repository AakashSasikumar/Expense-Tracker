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

    public static final String USERS_ID = "id";
    public static final String USERS_NAME = "name";
    public static final String USERS_EMAIL = "email";
    public static final String USERS_PHONENUMBER = "phonenumber";
    public static final String USERS_PASSWORD = "password";

    public static enum IDType { Email, PhoneNumber }

    public LocalDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String userTableCreationQuery = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT);",
                TABLE_USERS, USERS_ID, USERS_NAME, USERS_EMAIL, USERS_PHONENUMBER, USERS_PASSWORD);
        sqLiteDatabase.execSQL(userTableCreationQuery);
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

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_USERS;
        sqLiteDatabase.execSQL(dropTableQuery);
        onCreate(sqLiteDatabase);
    }
}
