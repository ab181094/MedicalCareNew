package com.csecu.amrit.medicalcare.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Amrit on 30/03/2018.
 */

public class Appointment implements Parcelable {
    int id;
    String doctor, date, type, start, end, transaction;

    protected Appointment(Parcel in) {
        id = in.readInt();
        doctor = in.readString();
        date = in.readString();
        type = in.readString();
        start = in.readString();
        end = in.readString();
        transaction = in.readString();
    }

    public static final Creator<Appointment> CREATOR = new Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel in) {
            return new Appointment(in);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public Appointment() {
    }

    public Appointment(String doctor, String date, String type, String start, String end, String transaction) {
        this.doctor = doctor;
        this.date = date;
        this.type = type;
        this.start = start;
        this.end = end;
        this.transaction = transaction;
    }

    public Appointment(int id, String doctor, String date, String type, String start, String end, String transaction) {
        this.id = id;
        this.doctor = doctor;
        this.date = date;
        this.type = type;
        this.start = start;
        this.end = end;
        this.transaction = transaction;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(doctor);
        parcel.writeString(date);
        parcel.writeString(type);
        parcel.writeString(start);
        parcel.writeString(end);
        parcel.writeString(transaction);
    }
}
