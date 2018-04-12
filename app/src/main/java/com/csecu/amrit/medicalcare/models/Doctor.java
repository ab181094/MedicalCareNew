package com.csecu.amrit.medicalcare.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Amrit on 25/03/2018.
 */

public class Doctor implements Parcelable {
    int id;
    String name, designation, speciality, gender, contact, phone, reg, password, description, degree, email;

    public Doctor() {
    }

    public Doctor(String name, String designation, String speciality, String gender, String contact, String phone, String reg, String password, String description, String degree, String email) {
        this.name = name;
        this.designation = designation;
        this.speciality = speciality;
        this.gender = gender;
        this.contact = contact;
        this.phone = phone;
        this.reg = reg;
        this.password = password;
        this.description = description;
        this.degree = degree;
        this.email = email;
    }

    public Doctor(int id, String name, String designation, String speciality, String gender, String contact, String phone, String reg, String password, String description, String degree, String email) {
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.speciality = speciality;
        this.gender = gender;
        this.contact = contact;
        this.phone = phone;
        this.reg = reg;
        this.password = password;
        this.description = description;
        this.degree = degree;
        this.email = email;
    }

    protected Doctor(Parcel in) {
        id = in.readInt();
        name = in.readString();
        designation = in.readString();
        speciality = in.readString();
        gender = in.readString();
        contact = in.readString();
        phone = in.readString();
        reg = in.readString();
        password = in.readString();
        description = in.readString();
        degree = in.readString();
        email = in.readString();
    }

    public static final Creator<Doctor> CREATOR = new Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel in) {
            return new Doctor(in);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(designation);
        parcel.writeString(speciality);
        parcel.writeString(gender);
        parcel.writeString(contact);
        parcel.writeString(phone);
        parcel.writeString(reg);
        parcel.writeString(password);
        parcel.writeString(description);
        parcel.writeString(degree);
        parcel.writeString(email);
    }
}
