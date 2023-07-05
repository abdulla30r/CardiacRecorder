package com.example.cardiacrecorder;

public class Measurement {
    private String id;
    private int systolic;
    private int diastolic;
    private String date;
    private String time;
    private int heartRate;
    private String comment;

    public  Measurement(){}
    public Measurement(int systolic, int diastolic, String date, String time, int heartRate, String comment) {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.date = date;
        this.time = time;
        this.heartRate = heartRate;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSystolic() {
        return systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public String getComment() {
        return comment;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
