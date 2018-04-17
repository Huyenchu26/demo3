package com.example.admin.demo3.model;

import java.util.List;

public class Vehicle {
    public Vehicle() {
    }

    public String imei;
    public String date;
    public String time;
    public double longitude;
    public double latitude;
    public int sos;
    public int trunk; // cốp xe
    public int engine; // động cơ
    public int gps;
    public int status; // cờ dừng đỗ
    public int frontCamera;
    public int behindCamera;
    public String positionStatus; // trạng thái định vị
    public String locate; // định vị
    public String firmware;
    public String CPUtime;

    public List<String> rfid;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getRfid() {
        return rfid;
    }

    public String getPositionStatus() {
        return positionStatus;
    }

    public void setPositionStatus(String positionStatus) {
        this.positionStatus = positionStatus;
    }

    public String getFirmware() {
        return firmware;
    }

    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    public String getCPUtime() {
        return CPUtime;
    }

    public void setCPUtime(String CPUtime) {
        this.CPUtime = CPUtime;
    }

    public void setRfid(List<String> rfid) {
        this.rfid = rfid;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int isSos() {
        return sos;
    }

    public int isTrunk() {
        return trunk;
    }

    public int isEngine() {
        return engine;
    }

    public int isGps() {
        return gps;
    }

    public int getFrontCamera() {
        return frontCamera;
    }

    public int getBehindCamera() {
        return behindCamera;
    }

    public String getLocate() {
        return locate;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setSos(int sos) {
        this.sos = sos;
    }

    public void setTrunk(int trunk) {
        this.trunk = trunk;
    }

    public void setEngine(int engine) {
        this.engine = engine;
    }

    public int getSos() {
        return sos;
    }

    public int getTrunk() {
        return trunk;
    }

    public int getEngine() {
        return engine;
    }

    public int getGps() {
        return gps;
    }

    public void setGps(int gps) {
        this.gps = gps;
    }

    public void setFrontCamera(int frontCamera) {
        this.frontCamera = frontCamera;
    }

    public void setBehindCamera(int behindCamera) {
        this.behindCamera = behindCamera;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
