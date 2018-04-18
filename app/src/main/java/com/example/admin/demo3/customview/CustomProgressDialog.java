package com.example.admin.demo3.customview;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.admin.demo3.R;

public class CustomProgressDialog extends ProgressDialog {
    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void show() {
        super.show();
        setContentView(R.layout.x_layout_dialog_loading);
    }
}
