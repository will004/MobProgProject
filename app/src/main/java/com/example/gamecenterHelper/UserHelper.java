package com.example.gamecenterHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.model.User;

public class UserHelper {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    private Context context;

    private static String COL_USER_ID = "user_id";
    private static String COL_USER_NAME = "user_name";
    private static String COL_USER_EMAIL = "user_email";
    private static String COL_USER_PASSWORD = "user_password";
    private static String COL_USER_BIRTHDAY = "user_birthday";
    private static String COL_USER_PHONE = "user_phone";
    private static String COL_USER_GENDER = "user_gender";
    private static String COL_USER_BALANCE = "user_balance";
    private static String COL_USER_STATUS = "user_status";

    public UserHelper(Context context) {
        this.context = context;
    }

    private String generateId(){
        int digit1 = (int) (Math.random()*(9-0)+1);
        int digit2 = (int) (Math.random()*(9-0)+1);
        int digit3 = (int) (Math.random()*(9-0)+1);

        return "US"+digit1+""+digit2+""+digit3;
    }

    //open DB
    public void open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public void close() {
        sqLiteDatabase.close();
    }

    public void insertUser(User user){

        //cek user_id udah ada di database atau belum

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM users WHERE user_id = ?", new String[]{user.getUserID()});

        while(cursor.getCount() != 0){
            user.setUserID(generateId());
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM users WHERE user_id = ?", new String[]{user.getUserID()});

            if(cursor.getCount() == 0) break;
        }
        cursor.close();
        close();

        //end
        open();

        //insert to database
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USER_ID, user.getUserID());
        contentValues.put(COL_USER_NAME, user.getName());
        contentValues.put(COL_USER_EMAIL, user.getEmail());
        contentValues.put(COL_USER_PASSWORD, user.getPassword());
        contentValues.put(COL_USER_BIRTHDAY, user.getBirthday());
        contentValues.put(COL_USER_PHONE, user.getPhone());
        contentValues.put(COL_USER_GENDER, user.getGender());
        contentValues.put(COL_USER_BALANCE, user.getBalance());
        contentValues.put(COL_USER_STATUS, user.getStatus());

        sqLiteDatabase.insert("users", null, contentValues);
    }

    //return nya ID dari user
    public String login(String email, String password){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM users WHERE user_email = ? AND user_password = ?", new String[]{email, password});
        cursor.moveToFirst();

        String user_id;
        if(cursor.getCount() == 0){
            user_id = "";
            return user_id;
        }
        user_id = cursor.getColumnName(0);

        cursor.close();

        return user_id;
    }

}
