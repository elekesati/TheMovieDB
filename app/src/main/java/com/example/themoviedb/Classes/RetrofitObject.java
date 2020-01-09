package com.example.themoviedb.Classes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitObject {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String KEY = "c1f6ce8cd23ef8759aa9082c941fc04e";

    public static Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
