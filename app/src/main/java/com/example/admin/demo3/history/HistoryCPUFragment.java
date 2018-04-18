package com.example.admin.demo3.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.demo3.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryCPUFragment extends Fragment {

//    @BindView(R.id.imageBack)
//    ImageView imageBack;
    @BindView(R.id.textTitle)
    TextView title;

    public HistoryCPUFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ButterKnife.bind(this, getActivity());
        return inflater.inflate(R.layout.fragment_history_cpu, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        imageBack.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onDelayedClick(View v) {
//
//            }
//        });
    }
}
