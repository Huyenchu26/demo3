package com.example.admin.demo3.data;

import com.example.admin.demo3.model.Vehicle;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;

public interface ApiClient {

    String HEADER_CONTENT_TYPE = "Content-Type";
    String HEADER_CONTENT_TYPE_VALUE = "application/json";

    String HEADER_CONTENT_LENGTH = "Content-Length";
    String HEADER_CONTENT_LENGTH_VALUE = "length";

    @GET("ParseFile")
    Call<List<Vehicle>> loadVehicles();

    @GET("ParseFile?imei={imei}&startDate={startDate}&endDate={endDate}")
    Call<List<Vehicle>> loadHistory(@Path("imei") String imei,
                                    @Path("startDate") String startDate,
                                    @Path("endDate") String endDate);
}
