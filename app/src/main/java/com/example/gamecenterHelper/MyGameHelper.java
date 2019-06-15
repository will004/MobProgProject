package com.example.gamecenterHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.model.MyGame;

import java.util.ArrayList;

public class MyGameHelper {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    private Context context;



    public MyGameHelper(Context context){
        this.context = context;
    }

    public void open(){
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public void close(){
        sqLiteDatabase.close();
    }

    public ArrayList<MyGame> searchMyGame(String user_id){
        ArrayList<MyGame> temp = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM mygames WHERE user_id = ?", new String[]{user_id});
        cursor.moveToFirst();

        if(cursor.getCount() == 0){
            cursor.close();
            return temp;
        }

        MyGame mygame;

        do{
            mygame = new MyGame(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3));
            temp.add(mygame);
            cursor.moveToNext();
        }while(!cursor.isAfterLast());

        return temp;
    }

    public void addMyGame(String user_id, String game_id){
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", user_id);
        contentValues.put("game_id", game_id);
        contentValues.put("playinghour", 0);

        sqLiteDatabase.insert("mygames", null, contentValues);
    }

    public void updatePlayingHour(int mygame_id, int playHour){
        Cursor cursor = sqLiteDatabase.rawQuery("UPDATE mygames SET 'playinghour' = " + playHour + "WHERE 'mygame_id' = "+ mygame_id, null);
        cursor.close();
    }
}
