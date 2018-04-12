package com.csecu.amrit.medicalcare.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Amrit on 29/03/2018.
 */

public class Admin implements Parcelable {
    int id;
    String name, age, gender, contact, email, password, address;

    public Admin() {
    }

    public Admin(String name, String age, String gender, String contact, String email, String password, String address) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public Admin(int id, String name, String age, String gender, String contact, String email, String password, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    protected Admin(Parcel in) {
        id = in.readInt();
        name = in.readString();
        age = in.readString();
        gender = in.readString();
        contact = in.readString();
        email = in.readString();
        password = in.readString();
        address = in.readString();
    }

    public static final Creator<Admin> CREATOR = new Creator<Admin>() {
        @Override
        public Admin createFromParcel(Parcel in) {
            return new Admin(in);
        }

        @Override
        public Admin[] newArray(int size) {
            return new Admin[size];
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(age);
        parcel.writeString(gender);
        parcel.writeString(contact);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(address);
    }
}
