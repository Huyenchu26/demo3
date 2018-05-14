package com.example.admin.demo3.data;

import com.example.admin.demo3.model.Vehicle;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiClient {

    String HEADER_CONTENT_TYPE = "Content-Type";
    String HEADER_CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded";

    String HEADER_CONTENT_LENGTH = "Content-Length";
    String HEADER_CONTENT_LENGTH_VALUE = "length";

    @POST("WebService.asmx/parse_file")
    Call<List<Vehicle>> loadVehicles(@HeaderMap HashMap<String, Object> header);

}
