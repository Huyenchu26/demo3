package com.example.admin.demo2;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.admin.demo2.adapter.VehicleAdapter;
import com.example.admin.demo2.model.Vehicle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView text3;
    RecyclerView recyclerViewVehicle;
    VehicleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        references();
        setVehicleForList();
        adapter.addData(vehicles);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerViewVehicle.setLayoutManager(mLayoutManager);
        recyclerViewVehicle.setAdapter(adapter);
    }

    private void references() {
        text3 = (TextView) findViewById(R.id.text3);
        recyclerViewVehicle = (RecyclerView) findViewById(R.id.recyclerviewMain);
        adapter = new VehicleAdapter();
    }

    private String readFromFile(Context context) {
        String text = "";
        try {
            InputStream inputStream = getAssets().open("480000069229732.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            text = new String(buffer);
        } catch (IOException e) {
            //You'll need to add proper error handling here
            Log.e("login activity", "File not found: " + e.toString());
        }

        return text;
    }

    String data = readFromFile(this);
    List<Vehicle> vehicles = new ArrayList<>();

    private void setVehicleForList() {
        List<String> time = new ArrayList<>();
        List<String> imei = new ArrayList<>();
        for (int i = 0; i < data.split("\r\n").length; i++) {
            if (i % 2 == 0)
                imei.add(data.split("\r\n")[i]);
            else time.add(data.split("\r\n")[i]);
        }

        Vehicle vehicle = new Vehicle();
        for (int i = 0; i < imei.size(); i++) {
            vehicle.setImei(Integer.parseInt(imei.get(i).split(",")[0]));
            vehicle.setTime(imei.get(i).split(",")[1]);
            vehicles.add(vehicle);
        }
    }
}
