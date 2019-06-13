package com.example.model;

public class User {
    private String userID;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String status;
    private String gender;
    private String birthday;
    private int balance;


    public User(String userID, String name, String password, String email, String phone, String status, String gender, String birthday) {
        this.userID = userID;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.gender = gender;
        this.birthday = birthday;
        balance = 1000000;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
