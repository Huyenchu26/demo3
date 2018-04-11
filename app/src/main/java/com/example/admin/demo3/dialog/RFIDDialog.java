package com.example.admin.demo3.dialog;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.admin.demo3.R;
import com.example.admin.demo3.customview.MyAlertDialog;
import com.example.admin.demo3.util.KeyboardUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RFIDDialog extends MyAlertDialog {


    @BindView(R.id.container)
    View container;
    @BindView(R.id.listRfid)
    ListView listRfid;

    private List<String> rfid = new ArrayList<>();

    public RFIDDialog(Context context, List<String> rfid) {
        super(context);
        this.rfid = rfid;
    }

    @Override
    protected int provideLayout() {
        return R.layout.dialog_rfid;
    }

    @Override
    protected void setupViews() {
        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                KeyboardUtils.hideKeyboard(view.getContext(), view);
                return false;
            }
        });

        setupList();
    }

    private void setupList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_list_item_1, rfid);
        listRfid.setAdapter(adapter);
    }

    public void release() {
        super.release();
    }

}