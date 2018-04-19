package com.example.admin.demo3.dialog;


import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.admin.demo3.R;
import com.example.admin.demo3.customview.DatePickerDialog;
import com.example.admin.demo3.customview.MyAlertDialog;
import com.example.admin.demo3.customview.OnClickListener;
import com.example.admin.demo3.util.KeyboardUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DateDialog extends MyAlertDialog {

    @BindView(R.id.editStartTime)
    EditText editStartTime;
    @BindView(R.id.editEndTime)
    EditText editEndTime;
    @BindView(R.id.container)
    View container;


    private Calendar date = null;
    private OnChooseListener onChooseListener;

    public DateDialog(Context context) {
        super(context);
    }

    @Override
    protected int provideLayout() {
        return R.layout.dialog_select_time;
    }

    private int choose = 0;

    @Override
    protected void setupViews() {
        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                KeyboardUtils.hideKeyboard(view.getContext(), view);
                return false;
            }
        });
        editStartTime.setFocusable(false);
        editEndTime.setFocusable(false);
        editStartTime.setOnClickListener(new com.example.admin.demo3.customview.OnClickListener() {
            @Override
            public void onDelayedClick(View v) {
                openDatePicker();
                choose = 1;
            }
        });

        editEndTime.setOnClickListener(new com.example.admin.demo3.customview.OnClickListener() {
            @Override
            public void onDelayedClick(View v) {
                openDatePicker();
                choose = 2;
            }
        });
    }


    public void setOnChooseListener(OnChooseListener onChooseListener) {
        this.onChooseListener = onChooseListener;
    }

    public void release() {
        super.release();
        onChooseListener = null;
        date = null;
    }

    @OnClick(R.id.buttonSearchOrder)
    public void buttonSearchClicked() {
        if (onChooseListener != null)
            try {

            } catch (NumberFormatException ignored) {
            }

        onChooseListener = null;
        dismiss();
    }

    private void openDatePicker() {
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(getContext(), date, "Cancel");
        datePickerDialog.setListener(new DatePickerDialog.Listener() {
            @Override
            public void onDateSet(Calendar calendar) {
                date = calendar;
                if (choose == 1)
                    editStartTime.setText(com.example.admin.demo3.util.DateUtils.dateToString(date.getTime()));
                else if (choose == 2)
                    editEndTime.setText(com.example.admin.demo3.util.DateUtils.dateToString(date.getTime()));
                else return;
            }

            @Override
            public void onCancelClick() {
                editStartTime.setText(null);
                choose = 0;
            }

            @Override
            public void onDismiss() {
                release();
                choose = 0;
            }
        });
        datePickerDialog.show();
    }

    public interface OnChooseListener {
        void onDone(String startDate, String endDate);
    }
}
