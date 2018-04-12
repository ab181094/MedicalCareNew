package com.csecu.amrit.medicalcare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;

import com.csecu.amrit.medicalcare.R;
import com.csecu.amrit.medicalcare.adapters.HomeAdapter;
import com.csecu.amrit.medicalcare.models.Doctor;
import com.csecu.amrit.medicalcare.models.Icons;

import java.util.ArrayList;

public class DoctorActivity extends AppCompatActivity {
    GridView gridView;
    ArrayList<Icons> list;
    Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            doctor = bundle.getParcelable("doctor");
        }

        gridView = findViewById(R.id.doctor_gridView);
        list = new ArrayList<>();
        list.add(new Icons(R.mipmap.ic_launcher, "Credentials"));
        list.add(new Icons(R.mipmap.ic_launcher, "Set Schedule"));
        list.add(new Icons(R.mipmap.ic_launcher, "View Appointments"));
        list.add(new Icons(R.mipmap.ic_launcher, "View All Patients"));
        list.add(new Icons(R.mipmap.ic_launcher, "Update Profile"));
        list.add(new Icons(R.mipmap.ic_launcher, "Change Image"));
        list.add(new Icons(R.mipmap.ic_launcher, "Sign out"));

        HomeAdapter adapter = new HomeAdapter(this, list);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {

                } else if (i == 1) {
                    Intent intent = new Intent(DoctorActivity.this, SetScheduleActivity.class);
                    intent.putExtra("doctor", doctor);
                    startActivity(intent);
                } else if (i == 2) {

                } else if (i == 3) {

                } else if (i == 4) {

                } else if (i == 5) {

                } else if (i == 6) {
                    Intent intent = new Intent(DoctorActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
