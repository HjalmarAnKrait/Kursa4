package com.example.myapplication.POJO;

import java.io.Serializable;

public class AdvertPOJO implements Serializable
{
    private String title;
    private String userName;
    private String date;
    private String description;
    private String imagePath;
    private String category;
    private int cost;
    private int advertID;
    private int phone_number;
    private int typeId;
    private int categoryId;

    public AdvertPOJO(int advertID,
                      String date,
                      String category,
                      String title,
                      String description,
                      String userName,
                      String imagePath,
                      int cost,
                      int phone_number,
                      int typeId,
                      int categoryId
                      ) {
        this.title = title;
        this.userName = userName;
        this.date = date;
        this.description = description;
        this.imagePath = imagePath;
        this.category = category;
        this.advertID = advertID;
        this.cost = cost;
        this.phone_number = phone_number;
        this.typeId = typeId;
        this.categoryId = categoryId;
    }


    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public int getPhone_number() {
        return phone_number;
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
