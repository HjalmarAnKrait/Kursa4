package com.example.myapplication.Networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AvitoApi
{

        private static final AvitoApi ourInstance = new AvitoApi();
        private Retrofit retrofit;
        private String baseUrl = "https://rest-app.net/api/ads/";


        public static AvitoApi getInstance()
        {
            return ourInstance;
        }


    public AvitoApi()
        {
            this.retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl).build();
        }

        public ApiRequests requests()
        {
            return retrofit.create(ApiRequests.class);
        }
}
