package com.example.myapplication.Networking;

import com.google.gson.JsonElement;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRequests
{
    @POST("register")
    Call<JsonElement> registration (@Body Map<String, String> map);

    @GET("login/")
    Call<JsonElement> authorization(@Query("login") String login, @Query("password") String paswword);


}
