package com.example.admin.demo3;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Toast;

import com.example.admin.demo3.adapter.VehicleAdapter;
import com.example.admin.demo3.customview.OnClickListener;
import com.example.admin.demo3.data.ApiClient;
import com.example.admin.demo3.data.ApiHelper;
import com.example.admin.demo3.dialog.RFIDDialog;
import com.example.admin.demo3.history.HistoryContainerFragment;
import com.example.admin.demo3.model.Vehicle;
import com.example.admin.demo3.util.GetRFID;
import com.example.admin.demo3.util.LogUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        setupAdapter();
        setupSearch();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                setupConnect();
            }
        }, 500);
    }

    private void setupSearch() {
        imageRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onDelayedClick(View v) {
                String strSearch = editSearchQuery.getText().toString().trim();
                clearData();
                for (Vehicle vehicle : vehicles) {
                    if (vehicle.data.getImei().contains(strSearch)) {
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
            public void onImageLocationClick(String longitude, String latitude) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("longitude", longitude);
                intent.putExtra("latitude", latitude);
                startActivity(intent);
            }

            @Override
            public void onOpenDialogRfid(List<String> rfid) {
                openDialogRfid(rfid);
            }

            @Override
            public void onItemListener(Vehicle.Data vehicle) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(android.R.id.content, new HistoryContainerFragment(), "");
                transaction.addToBackStack(null);
                transaction.commit();
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

    List<Vehicle> vehicles = new ArrayList<>();

//7:cờ SOS (0: không có, 1: có SOS), 8: cờ mở cửa két xe (0: đóng/1 : mở),
// 9:cờ động cơ (bật/tắt), 10: cờ dừng đỗ, 11: cờ GPS (0: có, 1:mất GPS)

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    private void setupConnect() {
                ApiClient client = ApiHelper.getClient().create(ApiClient.class);
                /** Call the method with parameter in the interface to get the notice data*/
                Call<List<Vehicle>> call = client.loadVehicles();
                call.enqueue(new Callback<List<Vehicle>>() {
                    @Override
                    public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                        if(response.isSuccessful()) {
                            vehicles.addAll(response.body());
                            adapter.addData(response.body());
                            LogUtil.e("isSuccessful: " + response.toString());
                        } else {
                            LogUtil.e("" + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Vehicle>> call, Throwable t) {
                        t.printStackTrace();
                        LogUtil.e("onFailure: " + t.getMessage());
                    }
                });
    }

}
