package com.example.admin.demo3.event;

public class ChangeDateEvent {
        public String startDate;
        public String endDate;

        public ChangeDateEvent() {
        }

    public ChangeDateEvent(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
