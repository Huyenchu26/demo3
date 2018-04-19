package com.example.admin.demo3.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.demo3.R;
import com.example.admin.demo3.adapter.CPUtimeAdapter;
import com.example.admin.demo3.model.Vehicle;
import com.example.admin.demo3.util.LogUtil;

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

    View view;
    CPUtimeAdapter adapter;
    List<Vehicle> vehicleList = new ArrayList<>();

    public HistoryCPUFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history_cpu, container, false);
        ButterKnife.bind(this, view);
        setupList();
        return view;
    }

    List<String> time = new ArrayList<>();

    public List<String> getTime() {
        time.add("2018/02/23 09:04:37");
        time.add("2018/02/23 09:05:00");
        time.add("2018/02/23 09:04:37");
        time.add("2018/02/23 09:05:00");
        time.add("2018/02/23 09:04:37");
        time.add("2018/02/23 09:04:37");
        time.add("2018/02/23 09:05:00");
        time.add("2018/02/23 09:04:37");
        time.add("2018/02/23 09:05:00");
        time.add("2018/02/23 09:04:37");
        return time;
    }

    private void setupList() {
        adapter = new CPUtimeAdapter();
        getTime();
        for (int i = 0; i < time.size(); i++){
            Vehicle vehicle = new Vehicle();
            vehicle.setTime(time.get(i));
            vehicleList.add(vehicle);
        }
        adapter.addData(vehicleList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        listViewCPUtime.setLayoutManager(mLayoutManager);
        listViewCPUtime.setAdapter(adapter);
        LogUtil.e("Size: " + vehicleList.size());
    }

}
