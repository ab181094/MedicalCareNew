package com.csecu.amrit.medicalcare.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Amrit on 26/03/2018.
 */

public class Diagnostics implements Parcelable {
    int id;
    String name, reg, contact, phone, email, password, address;

    protected Diagnostics(Parcel in) {
        id = in.readInt();
        name = in.readString();
        reg = in.readString();
        contact = in.readString();
        phone = in.readString();
        email = in.readString();
        password = in.readString();
        address = in.readString();
    }

    public static final Creator<Diagnostics> CREATOR = new Creator<Diagnostics>() {
        @Override
        public Diagnostics createFromParcel(Parcel in) {
            return new Diagnostics(in);
        }

        @Override
        public Diagnostics[] newArray(int size) {
            return new Diagnostics[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Diagnostics() {
    }

    public Diagnostics(String name, String reg, String contact, String phone, String email, String password, String address) {
        this.name = name;
        this.reg = reg;
        this.contact = contact;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public Diagnostics(int id, String name, String reg, String contact, String phone, String email, String password, String address) {
        this.id = id;
        this.name = name;
        this.reg = reg;
        this.contact = contact;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(reg);
        parcel.writeString(contact);
        parcel.writeString(phone);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(address);
    }
}
