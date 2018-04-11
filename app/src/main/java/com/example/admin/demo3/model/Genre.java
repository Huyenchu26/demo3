package com.example.admin.demo3.model;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Genre extends ExpandableGroup<VehicleChild>{
    public Genre(String title, List<VehicleChild> items) {
        super(title, items);
        this.imei = title;
        this.childList = items;
    }


    public String imei;
    public List<VehicleChild> childList;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public List<VehicleChild> getChildList() {
        return childList;
    }

    public void setChildList(List<VehicleChild> childList) {
        this.childList = childList;
    }
}
