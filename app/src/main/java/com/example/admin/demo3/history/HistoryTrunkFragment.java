package com.example.admin.demo3.history;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.demo3.R;
import com.example.admin.demo3.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class HistoryTrunkFragment extends Fragment {


    String startDate, endDate;
    List<Vehicle> vehicleList = new ArrayList<>();
    String imei;

    public static HistoryTrunkFragment newInstance(String imei, List<Vehicle> vehicleList) {
        return new HistoryTrunkFragment().setDate(imei, vehicleList);
    }

    public HistoryTrunkFragment setDate(String imei, List<Vehicle> vehicleList){
        this.vehicleList = vehicleList;
        this.imei = imei;
        return this;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ButterKnife.bind(this, getActivity());
        return inflater.inflate(R.layout.fragment_history_trunk, container, false);
    }

}
