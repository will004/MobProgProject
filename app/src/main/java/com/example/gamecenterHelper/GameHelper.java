package com.example.gamecenterHelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.model.Game;

import java.util.ArrayList;

public class GameHelper {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    private Context context;

    private static String COL_GAMES_ID = "game_id";
    private static String COL_GAMES_NAME = "game_name";
    private static String COL_GAMES_DESC = "game_desc";
    private static String COL_GAMES_GENRE = "game_genre";
    private static String COL_GAMES_RATING = "game_rating";
    private static String COL_GAMES_STOCK = "game_stock";
    private static String COL_GAMES_PRICE = "game_price";

    public GameHelper(Context context){
        this.context = context;
    }

    public void open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public void close() {
        sqLiteDatabase.close();
    }

    public void insertGame(Game game){
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_GAMES_ID, game.getGameID());
        contentValues.put(COL_GAMES_NAME, game.getGameName());
        contentValues.put(COL_GAMES_DESC, game.getGameDesc());
        contentValues.put(COL_GAMES_GENRE, game.getGameGenre());
        contentValues.put(COL_GAMES_RATING, game.getGameRating());
        contentValues.put(COL_GAMES_STOCK, game.getGameStock());
        contentValues.put(COL_GAMES_PRICE, game.getGamePrice());

        sqLiteDatabase.insert("games", null, contentValues);
    }

    public ArrayList<Game> getGames(){

        ArrayList<Game> output = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM games", null);
        cursor.moveToFirst();

        do{
            output.add(new Game(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getDouble(4), cursor.getInt(5), cursor.getInt(6)));
            cursor.moveToNext();
        }while(!cursor.isAfterLast());
        cursor.close();

        return output;
    }

    public ArrayList<Game> getGameDetails(String game_id){
        //used in HomeActivity
        ArrayList<Game> output = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM games WHERE game_id = ?", new String[]{game_id});
        cursor.moveToFirst();

        if(cursor.getCount() != 0) output.add(new Game(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getDouble(4), cursor.getInt(5), cursor.getInt(6)));
        cursor.close();


        return output;
    }
}
