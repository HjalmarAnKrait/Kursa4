package com.example.myapplication.POJO;

public class AdvertPOJO
{
    private String title;
    private String userName;
    private String date;
    private String description;
    private String imagePath;
    private String category;
    private int cost;
    private int advertID;

    public AdvertPOJO(int advertID,
                      String date,
                      String category,
                      String title,
                      String description,
                      String userName,
                      String imagePath,
                      int cost
                      ) {
        this.title = title;
        this.userName = userName;
        this.date = date;
        this.description = description;
        this.imagePath = imagePath;
        this.category = category;
        this.advertID = advertID;
        this.cost = cost;
    }


    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAdvertID() {
        return advertID;
    }

    public void setAdvertID(int advertID) {
        this.advertID = advertID;
    }
}
