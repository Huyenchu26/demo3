package com.example.admin.demo3.viewholder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.admin.demo3.R;
import com.example.admin.demo3.model.VehicleParent;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParentViewholder extends GroupViewHolder {

    @BindView(R.id.txtImei)
    TextView txtImei;
    @BindView(R.id.txtDateTime)
    TextView txtDatetime;

    protected List<VehicleParent> data = new ArrayList<>();
    private TextView artistName;
    protected ItemListener itemListener;

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public boolean needUpdate = false;
    public int position = -1;
    protected boolean isBindData = true;

    public ParentViewholder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void onBind(ExpandableGroup group) {
        this.position = position;
        needUpdate = false;
        isBindData = true;

        final VehicleParent vehicle = data.get(position);

//        txtImei.setText(vehicle.getImei() + "");
//        txtDatetime.setText(vehicle.getTime());

        txtImei.setText(group.getTitle());
    }

    public interface ItemListener {
        void onImageLocationClick(double longitude, double latitude);
    }

    @Override
    public void expand() {
//        animateExpand();
    }

    @Override
    public void collapse() {
//        animateCollapse();
    }
}
