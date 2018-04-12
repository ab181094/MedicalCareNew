package com.csecu.amrit.medicalcare.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Amrit on 06/04/2018.
 */

public class Schedule implements Parcelable {
    private int id;
    String startDate, endDate, startTime1, endTime1, startTime2, endTime2, startTime3, endTime3, day, remain, doctor;

    protected Schedule(Parcel in) {
        id = in.readInt();
        startDate = in.readString();
        endDate = in.readString();
        startTime1 = in.readString();
        endTime1 = in.readString();
        startTime2 = in.readString();
        endTime2 = in.readString();
        startTime3 = in.readString();
        endTime3 = in.readString();
        day = in.readString();
        remain = in.readString();
        doctor = in.readString();
    }

    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        @Override
        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime1() {
        return startTime1;
    }

    public void setStartTime1(String startTime1) {
        this.startTime1 = startTime1;
    }

    public String getEndTime1() {
        return endTime1;
    }

    public void setEndTime1(String endTime1) {
        this.endTime1 = endTime1;
    }

    public String getStartTime2() {
        return startTime2;
    }

    public void setStartTime2(String startTime2) {
        this.startTime2 = startTime2;
    }

    public String getEndTime2() {
        return endTime2;
    }

    public void setEndTime2(String endTime2) {
        this.endTime2 = endTime2;
    }

    public String getStartTime3() {
        return startTime3;
    }

    public void setStartTime3(String startTime3) {
        this.startTime3 = startTime3;
    }

    public String getEndTime3() {
        return endTime3;
    }

    public void setEndTime3(String endTime3) {
        this.endTime3 = endTime3;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getRemain() {
        return remain;
    }

    public void setRemain(String remain) {
        this.remain = remain;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public Schedule() {
    }

    public Schedule(String startDate, String endDate, String startTime1, String endTime1,
                    String startTime2, String endTime2, String startTime3, String endTime3,
                    String day, String remain, String doctor) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime1 = startTime1;
        this.endTime1 = endTime1;
        this.startTime2 = startTime2;
        this.endTime2 = endTime2;
        this.startTime3 = startTime3;
        this.endTime3 = endTime3;
        this.day = day;
        this.remain = remain;
        this.doctor = doctor;
    }

    public Schedule(int id, String startDate, String endDate, String startTime1, String endTime1,
                    String startTime2, String endTime2, String startTime3, String endTime3,
                    String day, String remain, String doctor) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime1 = startTime1;
        this.endTime1 = endTime1;
        this.startTime2 = startTime2;
        this.endTime2 = endTime2;
        this.startTime3 = startTime3;
        this.endTime3 = endTime3;
        this.day = day;
        this.remain = remain;
        this.doctor = doctor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(startDate);
        parcel.writeString(endDate);
        parcel.writeString(startTime1);
        parcel.writeString(endTime1);
        parcel.writeString(startTime2);
        parcel.writeString(endTime2);
        parcel.writeString(startTime3);
        parcel.writeString(endTime3);
        parcel.writeString(day);
        parcel.writeString(remain);
        parcel.writeString(doctor);
    }
}
