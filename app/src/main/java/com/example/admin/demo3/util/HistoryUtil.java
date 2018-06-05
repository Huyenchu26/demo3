package com.example.admin.demo3.util;

import com.example.admin.demo3.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class HistoryUtil {

    // TODO: 6/4/2018 trunk / camera
    // TODO: 6/4/2018 từ history chia ra từng đoạn mở két
    public static List<Vehicle> getTimeLine(List<Vehicle> vehicles) {
        List<Vehicle> vehiclesTrunk = new ArrayList<>();
        if (vehicles != null) {
            for (int i = 0; i < vehicles.size() - 1; i++) {
                if (vehicles.get(i).data.getTrunk().equals("0") && vehicles.get(i + 1).data.getTrunk().equals("1")) {
                    vehiclesTrunk.add(vehicles.get(i + 1));
                } else if (vehicles.get(i).data.getTrunk().equals("1") && vehicles.get(i + 1).data.getTrunk().equals("0")){
                    vehiclesTrunk.add(vehicles.get(i));
                }
            }
        }
        return vehiclesTrunk;
    }

    public static class ItemTrunk {
        String timeLine;
        int frontCam, backCam;
        long time;

        public String getTimeLine() {
            return timeLine;
        }

        public void setTimeLine(String timeLine) {
            this.timeLine = timeLine;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getFrontCam() {
            return frontCam;
        }

        public void setFrontCam(int frontCam) {
            this.frontCam = frontCam;
        }

        public int getBackCam() {
            return backCam;
        }

        public void setBackCam(int backCam) {
            this.backCam = backCam;
        }
    }

    // TODO: 6/4/2018 từ từng đoạn mở két lấy số camera
    public static ItemTrunk getItemTrunk(Vehicle vehicle1, Vehicle vehicle2) {
        ItemTrunk itemTrunk = new ItemTrunk();

        Vehicle.Data vehicleStart = vehicle1.data;
        Vehicle.Data vehicleEnd = vehicle2.data;

        itemTrunk.setTimeLine(vehicleStart.getDateTime());

        int frontCam = Integer.parseInt(vehicleEnd.getFrontCam()) - Integer.parseInt(vehicleStart.getFrontCam());
        itemTrunk.setFrontCam(frontCam);
        int backCam = Integer.parseInt(vehicleEnd.getBackCam()) - Integer.parseInt(vehicleStart.getBackCam());
        itemTrunk.setBackCam(backCam);
        long time = (DateUtils.stringToDate(vehicleEnd.getDateTime()).getTime()
                - DateUtils.stringToDate(vehicleStart.getDateTime()).getTime()) / 1000;
        itemTrunk.setTime(time);

        return itemTrunk;
    }

    // TODO: 6/4/2018 CPUtime
    // TODO: 6/4/2018 từ history chia ra từng đoạn khởi động lại <CPUtime lớn rồi bé đột ngột>
    public static List<Vehicle> getListRestartCPU(List<Vehicle> vehicles) {
        List<Vehicle> vehiclesCPU = new ArrayList<>();
        if (vehicles != null) {
            for (int i = 1; i < vehicles.size(); i++) {
                if (Integer.parseInt(vehicles.get(i).data.getCpuTime()) <
                        Integer.parseInt(vehicles.get(i - 1).data.getCpuTime())) {
                    vehiclesCPU.add(vehicles.get(i));
                }
            }
        }
        return vehiclesCPU;
    }

}
