package com.example.gamecenter;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

public class Utility {

    static ArrayList<Context> listContext = new ArrayList<Context>();


    static void finishActivities(){
        for (Context context : listContext){
            if(context!=null){
                ((Activity) context).finish();
            }
        }
        listContext.clear();
    }
}
