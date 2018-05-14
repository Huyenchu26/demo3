package com.example.admin.demo3.data;

import android.support.annotation.NonNull;
import android.telecom.Call;

import com.example.admin.demo3.AppConfigs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHelper {

    private static Retrofit retrofit;

    /**
     * Create an instance of Retrofit object
     * */
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(AppConfigs.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static HashMap<String, Object> getHeaders() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(ApiClient.HEADER_CONTENT_TYPE, ApiClient.HEADER_CONTENT_TYPE_VALUE_JSON);
        return headers;
    }
}
