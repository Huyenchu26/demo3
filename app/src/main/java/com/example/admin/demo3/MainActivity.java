package com.example.admin.demo3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.admin.demo3.adapter.VehicleAdapter;
import com.example.admin.demo3.customview.OnClickListener;
import com.example.admin.demo3.dialog.RFIDDialog;
import com.example.admin.demo3.model.Vehicle;
import com.example.admin.demo3.util.GetRFID;
import com.example.admin.demo3.util.LogUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

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
    @BindView(R.id.imageRight)
    ImageView imageRight;

    VehicleAdapter adapter;
    List<Vehicle> vehiclesSearch = new ArrayList<>();

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
        imageRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onDelayedClick(View v) {
                String strSearch = editSearchQuery.getText().toString().trim();
                clearData();
                for (Vehicle vehicle : vehicles) {
                    if (vehicle.getImei().contains(strSearch)) {
                        vehiclesSearch.add(vehicle);
                    }
                }
                adapter.addData(vehiclesSearch);
            }
        });
        imageSearchClearText.setOnClickListener(new OnClickListener() {
            @Override
            public void onDelayedClick(View v) {
                editSearchQuery.setText(null);
            }
        });
        imageSearchCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onDelayedClick(View v) {
                editSearchQuery.setText(null);
                clearData();
                adapter.addData(vehicles);
            }
        });
    }

    private void clearData() {
        vehiclesSearch.clear();
        adapter.clearData();
    }

    private void setupAdapter() {
        adapter = new VehicleAdapter();
        adapter.addData(vehicles);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerViewVehicle.setLayoutManager(mLayoutManager);
        SlideInUpAnimator animator = new SlideInUpAnimator(new OvershootInterpolator(1f));
        recyclerViewVehicle.setItemAnimator(animator);
        recyclerViewVehicle.getItemAnimator().setAddDuration(1000);
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
        if (rfidDialog != null && rfidDialog.isShowing()) return;
        rfidDialog = new RFIDDialog(this, rfid);
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
        imageRight.setVisibility(View.VISIBLE);
    }

    List<String> data = new ArrayList<>();

    private void readFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("260699.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<Vehicle> vehicles = new ArrayList<>();

    private void setVehicleForList() {
        List<String> time = new ArrayList<>();
        List<String> imei = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (i % 2 == 0)
                time.add(data.get(i));
            else imei.add(data.get(i));
        }

        for (int i = 0; i < imei.size(); i++) {
            Vehicle vehicle = new Vehicle();
            String[] splitStr = imei.get(i).split(",");
            setValue(vehicle, splitStr);
        }
    }
//7:cờ SOS (0: không có, 1: có SOS), 8: cờ mở cửa két xe (0: đóng/1 : mở),
// 9:cờ động cơ (bật/tắt), 10: cờ dừng đỗ, 11: cờ GPS (0: có, 1:mất GPS),
    private void setValue(Vehicle vehicle, String[] splitStr) {
        try {
            vehicle.setImei(splitStr[0]);
            vehicle.setTime(splitStr[1]);
            vehicle.setLongitude(Double.parseDouble(splitStr[2]));
            vehicle.setLatitude(Double.parseDouble(splitStr[3]));
            vehicle.setSos(Integer.parseInt(splitStr[7]));
            vehicle.setTrunk(Integer.parseInt(splitStr[8]));
            vehicle.setEngine(Integer.parseInt(splitStr[9]));
            vehicle.setStatus(Integer.parseInt(splitStr[10]));
            vehicle.setGps(Integer.parseInt(splitStr[11]));
            vehicle.setFrontCamera(Integer.parseInt(splitStr[12]));
            vehicle.setBehindCamera(Integer.parseInt(splitStr[13]));
            vehicle.setRfid(GetRFID.getRFID(splitStr[14]));
            vehicle.setPositionStatus(splitStr[16]);
            vehicle.setFirmware(splitStr[17]);
            vehicle.setCPUtime(splitStr[18]);
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }
        vehicles.add(vehicle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
//danh sách liên kết đơn, viết ct c/c++ tối ưu tìm phần tử thứ n từ dưới lên (gợi ý: 1 vòng for)

}
