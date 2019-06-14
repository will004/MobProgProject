package com.example.gamecenterHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "gamecenter.db";
    private static int DB_VER = 1;

    private static String COL_USER_ID = "user_id";
    private static String COL_USER_NAME = "user_name";
    private static String COL_USER_EMAIL = "user_email";
    private static String COL_USER_PASSWORD = "user_password";
    private static String COL_USER_BIRTHDAY = "user_birthday";
    private static String COL_USER_PHONE = "user_phone";
    private static String COL_USER_GENDER = "user_gender";
    private static String COL_USER_BALANCE = "user_balance";
    private static String COL_USER_STATUS = "user_status";

    private static String COL_MYGAMES_ID = "mygame_id";
    private static String COL_MYGAMES_PLAYINGHOUR = "playinghour";
    private static String COL_MYGAMES_GAMEID = "game_id";
    private static String COL_MYGAMES_USERID = "user_id";

    private static String COL_GAMES_ID = "game_id";
    private static String COL_GAMES_NAME = "game_name";
    private static String COL_GAMES_DESC = "game_desc";
    private static String COL_GAMES_GENRE = "game_genre";
    private static String COL_GAMES_RATING = "game_rating";
    private static String COL_GAMES_STOCK = "game_stock";
    private static String COL_GAMES_PRICE = "game_price";


    private static String CREATE_TABLE_USER =
            String.format(
                    "CREATE TABLE %s (" +
                            "%s TEXT PRIMARY KEY," +
                            "%s TEXT," +
                            "%s TEXT," +
                            "%s TEXT," +
                            "%s TEXT," +
                            "%s TEXT," +
                            "%s TEXT," +
                            "%s INTEGER," +
                            "%s TEXT)",
            "users", COL_USER_ID, COL_USER_NAME, COL_USER_EMAIL, COL_USER_PASSWORD, COL_USER_BIRTHDAY, COL_USER_PHONE, COL_USER_GENDER, COL_USER_BALANCE, COL_USER_STATUS
            );

    private static String CREATE_TABLE_MYGAMES =
            String.format(
                    "CREATE TABLE %s (" +
                            "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "%s INTEGER," +
                            "%s TEXT," +
                            "%s TEXT)",
                    "mygames", COL_MYGAMES_ID, COL_MYGAMES_PLAYINGHOUR,  COL_MYGAMES_GAMEID, COL_MYGAMES_USERID
            );
    private static String CREATE_TABLE_GAMES =
            String.format(
                    "CREATE TABLE %s(" +
                            "%s TEXT PRIMARY KEY," +
                            "%s TEXT," +
                            "%s TEXT," +
                            "%s TEXT," +
                            "%s REAL," +
                            "%s INTEGER," +
                            "%s INTEGER)",
                    "games",COL_GAMES_ID,COL_GAMES_NAME, COL_GAMES_DESC, COL_GAMES_GENRE, COL_GAMES_RATING, COL_GAMES_STOCK, COL_GAMES_PRICE
            );

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_MYGAMES);
        db.execSQL(CREATE_TABLE_GAMES);

        //admin account
        db.execSQL("INSERT INTO users VALUES('US000', 'admin', 'admin@test.com', 'admin123', '02/03/1999', '1234567890', 'Male', 1000000, 'Employee')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS mygames");
        db.execSQL("DROP TABLE IF EXISTS games");

        onCreate(db);
    }
}
