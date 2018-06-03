package com.example.admin.demo3.data;

import com.example.admin.demo3.model.Vehicle;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;

public interface ApiClient {

    String HEADER_CONTENT_TYPE = "Content-Type";
    String HEADER_CONTENT_TYPE_VALUE = "application/json";

    String HEADER_CONTENT_LENGTH = "Content-Length";
    String HEADER_CONTENT_LENGTH_VALUE = "length";

    @GET("ParseFile")
    Call<List<Vehicle>> loadVehicles();

    @GET("ParseFile")
    Call<List<Vehicle>> loadHistory(@Query("imei") String imei,
                                    @Query("startDate") String startDate,
                                    @Query("endDate") String endDate);
}
