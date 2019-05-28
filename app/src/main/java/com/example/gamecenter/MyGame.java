package com.example.gamecenter;

public class MyGame {

    String gameID;
    String gameTitle;
    String gameGenre;
    String gameDesc;
    int gameHourPlay;

    MyGame(String id, String title, String genre, String desc, int hour) {
        this.gameID = id;
        gameTitle = title;
        gameGenre = genre;
        gameDesc = desc;
        gameHourPlay = hour;
    }

}
