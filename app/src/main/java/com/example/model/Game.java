package com.example.model;

public class Game {

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getGameGenre() {
        return gameGenre;
    }

    public void setGameGenre(String gameGenre) {
        this.gameGenre = gameGenre;
    }

    public String getGameDesc() {
        return gameDesc;
    }

    public void setGameDesc(String gameDesc) {
        this.gameDesc = gameDesc;
    }

    public int getGameStock() {
        return gameStock;
    }

    public void setGameStock(int gameStock) {
        this.gameStock = gameStock;
    }

    public int getGamePrice() {
        return gamePrice;
    }

    public void setGamePrice(int gamePrice) {
        this.gamePrice = gamePrice;
    }

    public float getGameRating() {
        return gameRating;
    }

    public void setGameRating(float gameRating) {
        this.gameRating = gameRating;
    }

    private String gameID;
    private String gameTitle;
    private String gameGenre;
    private String gameDesc;
    private int gameStock;
    private int gamePrice;
    private float gameRating;

    public Game(String id, String title, String genre, String desc, int stock, int price, float rating) {
        gameID = id;
        gameTitle = title;
        gameDesc = desc;
        gameGenre = genre;
        gameStock = stock;
        gamePrice = price;
        gameRating = rating;
    }
}
