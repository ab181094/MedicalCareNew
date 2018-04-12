package com.csecu.amrit.medicalcare.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.csecu.amrit.medicalcare.R;
import com.csecu.amrit.medicalcare.models.Doctor;

public class DoctorDetailsActivity extends AppCompatActivity {
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10;
    View newView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linkAll();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Doctor doctor = bundle.getParcelable("1");

            textView1.setText(doctor.getName());
            textView2.setText(doctor.getDesignation());
            textView3.setText(doctor.getSpeciality());
            textView4.setText(doctor.getGender());
            textView5.setText(doctor.getContact());
            textView6.setText(doctor.getPhone());
            textView7.setText(doctor.getReg());
            textView8.setText(doctor.getDescription());
            textView9.setText(doctor.getDegree());
            textView10.setText(doctor.getEmail());
        }

        Button fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newView = view;
                Intent intent = new Intent(DoctorDetailsActivity.this, NewLoginActivity.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void linkAll() {
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);
        textView8 = findViewById(R.id.textView8);
        textView9 = findViewById(R.id.textView9);
        textView10 = findViewById(R.id.textView10);
    }
}
