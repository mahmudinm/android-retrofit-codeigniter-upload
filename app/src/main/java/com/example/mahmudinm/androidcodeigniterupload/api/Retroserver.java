package com.example.mahmudinm.androidcodeigniterupload.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mahmudinm on 24/12/2018.
 */

public class Retroserver {

    private static String base_url = "http://mahmudin-android1.000webhostapp.com/api/";

    public static Retrofit getRetrofit() {

        return new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

}
