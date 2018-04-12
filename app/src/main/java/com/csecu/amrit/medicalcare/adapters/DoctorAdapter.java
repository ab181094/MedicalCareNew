package com.csecu.amrit.medicalcare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csecu.amrit.medicalcare.R;
import com.csecu.amrit.medicalcare.models.Doctor;

import java.util.ArrayList;

/**
 * Created by Amrit on 30/03/2018.
 */

public class DoctorAdapter extends BaseAdapter {
    Context context;
    ArrayList<Doctor> doctors;

    public DoctorAdapter(Context context, ArrayList<Doctor> doctors) {
        this.context = context;
        this.doctors = doctors;
    }

    @Override
    public int getCount() {
        return doctors.size();
    }

    @Override
    public Object getItem(int i) {
        return doctors.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_doctor, viewGroup, false);
        }

        TextView textView = view.findViewById(R.id.row_doctor);
        textView.setText(doctors.get(i).getName());
        return view;
    }
}
