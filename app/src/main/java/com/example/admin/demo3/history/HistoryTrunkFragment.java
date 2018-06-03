package com.example.admin.demo3.history;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.demo3.R;

import butterknife.ButterKnife;

public class HistoryTrunkFragment extends Fragment {


    String startDate, endDate;
    String imei;

    public static HistoryTrunkFragment newInstance(String imei, String startDate, String endDate) {
        return new HistoryTrunkFragment().setDate(imei, startDate, endDate);
    }

    public HistoryTrunkFragment setDate(String imei, String startDate, String endDate){
        this.startDate = startDate;
        this.endDate = endDate;
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
