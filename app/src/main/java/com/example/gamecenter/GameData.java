package com.example.gamecenter;

public class GameData {

    String gameID;
    String gameTitle;
    String gameGenre;
    String gameDesc;
    int gameStock;
    int gamePrice;
    float gameRating;

    GameData(String id, String title, String genre, String desc, int stock, int price, float rating) {
        gameID = id;
        gameTitle = title;
        gameDesc = desc;
        gameGenre = genre;
        gameStock = stock;
        gamePrice = price;
        gameRating = rating;
    }

}
