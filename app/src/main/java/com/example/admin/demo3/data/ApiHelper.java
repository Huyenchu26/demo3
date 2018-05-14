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

//    private OkHttpClient client;
//    private ApiClient apiClient;
//    private List<Call> callList = new ArrayList<>();
//
//    public ApiHelper(OkHttpClient client) {
//        this.client = client;
//    }
//
//    protected ApiClient getClient() {
//        if (apiClient == null) {
//            Gson gson = new GsonBuilder()
//                    .setLenient()
//                    .create();
//            Retrofit.Builder builder = new Retrofit.Builder()
//                    .baseUrl(AppConfigs.HOST)
//                    .addConverterFactory(GsonConverterFactory.create(gson));
//            if (AppConfigs.isEnableLog) builder = builder.client(client);
//            Retrofit retrofit = builder.build();
//            apiClient = retrofit.create(ApiClient.class);
//        }
//        return apiClient;
//    }
//
//    protected HashMap<String, String> getParams(Object object) {
//        Type type = new TypeToken<HashMap<String, String>>() {
//        }.getType();
//        return new Gson().fromJson(new Gson().toJson(object), type);
//    }
//
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
}
