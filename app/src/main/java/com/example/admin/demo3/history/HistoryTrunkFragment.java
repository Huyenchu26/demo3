package com.example.admin.demo3.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.demo3.R;
import com.example.admin.demo3.adapter.TrunkAdapter;
import com.example.admin.demo3.model.Vehicle;
import com.example.admin.demo3.util.HistoryUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HistoryTrunkFragment extends Fragment {

    @BindView(R.id.recyclerViewTrunk)
    RecyclerView recyclerViewTrunk;
    @BindView(R.id.txtTrunktime)
    TextView txtTrunktime;

    String startDate, endDate;
    List<Vehicle> vehicleList = new ArrayList<>();
    String imei;

    TrunkAdapter trunkAdapter;
    private View view;
    Unbinder unbinder;

    public static HistoryTrunkFragment newInstance(String imei, List<Vehicle> vehicleList) {
        return new HistoryTrunkFragment().setDate(imei, vehicleList);
    }

    public HistoryTrunkFragment setDate(String imei, List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
        this.imei = imei;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history_trunk, container, false);
        unbinder = ButterKnife.bind(this, view);
        setupText();
        setupAdapter();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setupText() {
        startDate = vehicleList.get(0).data.getDateTime();
        endDate = vehicleList.get(vehicleList.size() - 1).data.getDateTime();
        txtTrunktime.setText("Time: " + startDate + " - " + endDate);
    }

    private void setupAdapter() {
        trunkAdapter = new TrunkAdapter();
        List<Vehicle> lists = HistoryUtil.getTimeLine(vehicleList);
        List<HistoryUtil.ItemTrunk> itemTrunks = new ArrayList<>();
        for (int i = 0; i < lists.size() - 2; i += 2) {
            itemTrunks.add(HistoryUtil.getItemTrunk(lists.get(i), lists.get(i + 1)));
        }
        trunkAdapter.addData(itemTrunks);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewTrunk.setLayoutManager(mLayoutManager);
        recyclerViewTrunk.setAdapter(trunkAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        unbinder = null;
    }
}
