package com.example.myapplication.POJO;

public class AdvertPOJO
{
    private String title;
    private String userName;
    private String date;
    private String description;
    private int advertID;

    public AdvertPOJO(String title, String userName, String date, String description, int advertID) {
        this.title = title;
        this.userName = userName;
        this.date = date;
        this.description = description;
        this.advertID = advertID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAdvertID() {
        return advertID;
    }

    public void setAdvertID(int advertID) {
        this.advertID = advertID;
    }
}
