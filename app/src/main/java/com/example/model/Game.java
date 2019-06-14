package com.example.model;

public class Game {

    private String gameID;
    private String gameName;
    private String gameGenre;
    private String gameDesc;
    private int gameStock;
    private int gamePrice;
    private double gameRating;

    public Game(String id, String title, String desc, String genre, double rating, int stock, int price) {
        gameID = id;
        gameName = title;
        gameDesc = desc;
        gameGenre = genre;
        gameStock = stock;
        gamePrice = price;
        gameRating = rating;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
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

    public double getGameRating() {
        return gameRating;
    }

    public void setGameRating(double gameRating) {
        this.gameRating = gameRating;
    }

}
