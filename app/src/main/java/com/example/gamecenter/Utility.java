package com.example.gamecenter;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

public class Utility {
    static ArrayList<UserData> data = new ArrayList<>();
    static ArrayList<GameData> games = new ArrayList<>();
    static boolean create10List = true;
    static ArrayList<Context> listContext = new ArrayList<Context>();
    static int idxUser = -1;


    static void finishActivities(){
        for (Context context : listContext){
            if(context!=null){
                ((Activity) context).finish();
            }
        }
        listContext.clear();
    }
}
