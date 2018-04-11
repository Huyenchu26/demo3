package com.example.admin.demo3.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.demo3.R;
import com.example.admin.demo3.model.VehicleChild;
import com.example.admin.demo3.viewholder.ChildViewholder;
import com.example.admin.demo3.viewholder.ParentViewholder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.util.List;
import java.util.zip.Inflater;

public class MyAdapder extends ExpandableRecyclerViewAdapter<ParentViewholder, ChildViewHolder> {

    public MyAdapder(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public ParentViewholder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        return new ParentViewholder(createView(parent, R.layout.item_vehicle_parent));
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        return new ChildViewHolder(createView(parent, R.layout.item_vehicle_child));
    }

    protected View createView(ViewGroup parent, int layoutResource) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutResource, parent, false);
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        if (holder instanceof ChildViewholder)
            ((ChildViewholder) holder).onBind(childIndex);

    }

    @Override
    public void onBindGroupViewHolder(ParentViewholder holder, int flatPosition, ExpandableGroup group) {
        if (holder instanceof ParentViewholder)
            ((ParentViewholder)holder).onBind(group);
    }

}
