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
import butterknife.Unbinder;


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
    Unbinder unbinder;

    public static HistoryCPUFragment newInstance(String imei, List<Vehicle> vehicleList,
                                                 String startDate, String endDate) {
        return new HistoryCPUFragment().setDate(imei, vehicleList, startDate, endDate);
    }

    public HistoryCPUFragment setDate(String imei, List<Vehicle> vehicleList, String startDate, String endDate){
        this.vehicleList = vehicleList;
        this.imei = imei;
        this.startDate = startDate;
        this.endDate = endDate;
        return this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history_cpu, container, false);
        unbinder = ButterKnife.bind(this, view);
        txtCPUtime.setText("Time: " + startDate + " - " + endDate);
        setupAdapter();
        return view;
    }

    private void setupAdapter() {
        adapter = new CPUtimeAdapter();
        List<Vehicle> vehicles = HistoryUtil.getListRestartCPU(vehicleList);
        adapter.addData(vehicles);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        listViewCPUtime.setLayoutManager(mLayoutManager);
        listViewCPUtime.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        unbinder = null;
    }

}
