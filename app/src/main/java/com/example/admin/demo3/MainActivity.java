package com.example.admin.demo3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.admin.demo3.adapter.VehicleAdapter;
import com.example.admin.demo3.customview.OnClickListener;
import com.example.admin.demo3.dialog.RFIDDialog;
import com.example.admin.demo3.model.Vehicle;
import com.example.admin.demo3.util.GetRFID;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.editSearchQuery)
    EditText editSearchQuery;
    @BindView(R.id.recyclerviewMain)
    RecyclerView recyclerViewVehicle;
    @BindView(R.id.imageBack)
    ImageView imageBack;
    @BindView(R.id.relativeSearchLayout)
    RelativeLayout relativeSearchLayout;
    @BindView(R.id.imageSearchCancel)
    ImageView imageSearchCancel;
    @BindView(R.id.imageSearchClearText)
    ImageView imageSearchClearText;

    VehicleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, MainActivity.this);
        init();
        readFromFile();
        setVehicleForList();
        setupAdapter();
        setupSearch();
    }

    private void setupSearch() {
        imageSearchClearText.setOnClickListener(new OnClickListener() {
            @Override
            public void onDelayedClick(View v) {
                editSearchQuery.setText(null);
            }
        });
        imageSearchCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onDelayedClick(View v) {

            }
        });
    }

    private void setupAdapter() {
        adapter = new VehicleAdapter();
        adapter.addData(vehicles);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerViewVehicle.setLayoutManager(mLayoutManager);
        recyclerViewVehicle.setAdapter(adapter);
        adapter.setItemListener(new VehicleAdapter.ItemListener() {
            @Override
            public void onRetryClick() {
            }

            @Override
            public void onImageLocationClick(double longitude, double latitude) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("longitude", longitude);
                intent.putExtra("latitude", latitude);
                startActivity(intent);
            }

            @Override
            public void onOpenDialogRfid(List<String> rfid) {
                openDialogRfid(rfid);
            }
        });
    }

    RFIDDialog rfidDialog;

    private void openDialogRfid(List<String> rfid) {
        List<String> newRFID = new ArrayList<>();
        if (rfid != null)
            for (int i = 0; i < rfid.size(); i++) {
                newRFID.add(String.valueOf(Long.parseLong(rfid.get(i), 16)));
            }

        if (rfidDialog != null && rfidDialog.isShowing()) return;
        rfidDialog = new RFIDDialog(this, newRFID);
        rfidDialog.setCanceledOnTouchOutside(true);
        rfidDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                rfidDialog.release();
            }
        });
        rfidDialog.show();
    }

    private void init() {
        imageBack.setVisibility(View.GONE);
        relativeSearchLayout.setVisibility(View.VISIBLE);
    }

    List<String> data = new ArrayList<>();

    private void readFromFile() {
        String mLine = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("480000069229732.txt"), "UTF-8"));
            while ((mLine = reader.readLine()) != null) {
                data.add(reader.readLine());
            }
        } catch (IOException e) {
            Log.e("Huyenchu", "readFromFile: null");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

    }

    List<Vehicle> vehicles = new ArrayList<>();

    private void setVehicleForList() {
        List<String> time = new ArrayList<>();
        List<String> imei = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (i % 2 == 0)
                imei.add(data.get(i));
            else time.add(data.get(i));
        }


        Vehicle vehicle = new Vehicle();
        for (int i = 0; i < imei.size(); i++) {
            vehicle.setImei(imei.get(i).split(",")[0]);
            vehicle.setTime(imei.get(i).split(",")[1]);
            vehicle.setLongitude(Double.parseDouble(imei.get(i).split(",")[2]));
            vehicle.setLatitude(Double.parseDouble(imei.get(i).split(",")[3]));
            vehicle.setEngine(Integer.parseInt(imei.get(i).split(",")[10]));
            vehicle.setFirmware(imei.get(i).split(",")[17]);
            vehicle.setCPUtime(imei.get(i).split(",")[18]);
            try {
                vehicle.setPositionStatus(imei.get(i).split(";")[3].split(",")[1]);
                vehicle.setRfid(GetRFID.getRFID(imei.get(i).split(",")[14]));
                Log.e("string: ", imei.get(i).split(",")[14]);
            } catch (Exception e) {
                Log.e("exception", e.toString());
            }
            vehicles.add(vehicle);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return false;
    }
}
