package com.example.myapplication.Networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegAuth
{
    private static final RegAuth ourInstance = new RegAuth();
    private Retrofit retrofit;
    private String baseUrl = "http://dev.hakta.pro/o/silkway/api/";

    public static RegAuth getInstance()
    {
        return ourInstance;
    }


    public RegAuth()
    {
        this.retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl).build();
    }

    public ApiRequests requests()
    {
        return retrofit.create(ApiRequests.class);
    }
}
