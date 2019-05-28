package com.example.gamecenter;

import java.util.ArrayList;
import java.util.HashMap;

public class UserData {

    String userID;
    String name;
    String password;
    String email;
    String phone;
    String role;
    String gender;
    String birthday;
    int balance;
    ArrayList<MyGame> myGames;


    UserData(String userID,String name, String password, String email, String phone, String role, String gender, String birthday) {
        this.userID = userID;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.gender = gender;
        this.birthday = birthday;
        myGames = new ArrayList<>();
        balance = 1000000;
    }
}
