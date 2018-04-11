package com.example.admin.demo3.viewholder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.demo3.R;
import com.example.admin.demo3.customview.OnClickListener;
import com.example.admin.demo3.model.VehicleChild;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChildViewholder extends ChildViewHolder {

    @BindView(R.id.imgLocation)
    ImageView imgLocation;

    protected List<VehicleChild> data = new ArrayList<>();
    private TextView artistName;
    protected ItemListener itemListener;

    public ChildViewholder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public boolean needUpdate = false;
    public int position = -1;
    protected boolean isBindData = true;

    public void onBind(int position) {
        this.position = position;
        needUpdate = false;
        isBindData = true;

        final VehicleChild vehicle = data.get(position);
        imgLocation.setOnClickListener(new OnClickListener() {
            @Override
            public void onDelayedClick(View v) {
                if (itemListener != null) itemListener.onImageLocationClick(vehicle.getLongitude(), vehicle.getLatitude());
            }
        });
    }

    public interface ItemListener {
        void onImageLocationClick(double longitude, double latitude);
    }
}
