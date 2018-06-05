package com.example.admin.demo3.data;

import com.example.admin.demo3.AppConfigs;

import java.util.HashMap;

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
                    .baseUrl(AppConfigs.HOST_MQ)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static HashMap<String, Object> getHeaders() {
        HashMap<String, Object> headers = new HashMap<>();
        headers.put(ApiClient.HEADER_CONTENT_TYPE, ApiClient.HEADER_CONTENT_TYPE_VALUE);
        headers.put(ApiClient.HEADER_CONTENT_LENGTH, ApiClient.HEADER_CONTENT_LENGTH_VALUE);
        return headers;
    }
}
