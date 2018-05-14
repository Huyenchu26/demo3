package com.example.admin.demo3.data;

import com.example.admin.demo3.model.Vehicle;

import java.util.List;

import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiClient {

    @POST("WebService.asmx/parse_file")
    Call<List<Vehicle>> loadVehicles();

}
