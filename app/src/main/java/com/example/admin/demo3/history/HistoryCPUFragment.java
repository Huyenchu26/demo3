package com.example.admin.demo3.history;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.demo3.R;
import com.example.admin.demo3.adapter.CPUtimeAdapter;
import com.example.admin.demo3.model.Vehicle;
import com.example.admin.demo3.util.HistoryUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryCPUFragment extends Fragment {

    @BindView(R.id.listViewCPUtime)
    RecyclerView listViewCPUtime;
    @BindView(R.id.txtCPUtime)
    TextView txtCPUtime;

    View view;
    CPUtimeAdapter adapter;
    List<Vehicle> vehicleList = new ArrayList<>();

    String startDate, endDate;
    String imei;

    public static HistoryCPUFragment newInstance(String imei, List<Vehicle> vehicleList) {
        return new HistoryCPUFragment().setDate(imei, vehicleList);
    }

    public HistoryCPUFragment setDate(String imei, List<Vehicle> vehicleList){
        this.vehicleList = vehicleList;
        this.imei = imei;
        return this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history_cpu, container, false);
        ButterKnife.bind(this, view);
        setupText();
        setupList();
        return view;
    }

    private void setupText() {
        startDate = vehicleList.get(0).data.getDateTime();
        endDate = vehicleList.get(vehicleList.size() - 1).data.getDateTime();
        txtCPUtime.setText("Time: " + startDate + " - " + endDate);
    }

    private void setupList() {
        adapter = new CPUtimeAdapter();
        List<Vehicle> vehicles = HistoryUtil.getListRestartCPU(vehicleList);
        adapter.addData(vehicles);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        listViewCPUtime.setLayoutManager(mLayoutManager);
        listViewCPUtime.setAdapter(adapter);
    }

}
