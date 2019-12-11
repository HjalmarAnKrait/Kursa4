package com.example.myapplication.POJO;

public class AdvertPOJO
{
    private String title;
    private String description;
    private String imagePath;
    private int userID;
    private int advertID;

    public AdvertPOJO(String title, String description, String imagePath, int userID, int advertID) {
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.userID = userID;
        this.advertID = advertID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAdvertID() {
        return advertID;
    }

    public void setAdvertID(int advertID) {
        this.advertID = advertID;
    }
}
