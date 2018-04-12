package com.csecu.amrit.medicalcare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.csecu.amrit.medicalcare.R;
import com.csecu.amrit.medicalcare.asyncTasks.RegDoctor;
import com.csecu.amrit.medicalcare.controllers.InputController;
import com.csecu.amrit.medicalcare.controllers.NetworkController;
import com.csecu.amrit.medicalcare.interfaces.AsyncResponse;
import com.csecu.amrit.medicalcare.models.Doctor;

public class RegDoctorActivity extends AppCompatActivity implements AsyncResponse {
    private EditText etName, etDesignation, etPhone, etCode, etEmail, etContact, etReg, etPassword, etRePassword, etDescription, etDegree;
    private Spinner spSpeciality, spGender;
    private String name, designation, phone, code, email, contact, reg, password, rePassword, description, degree, speciality, gender;
    private View newView;
    RegDoctor regDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_doctor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linkAll();
        regDoctor = new RegDoctor();
        regDoctor.delegate = this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getAndCheckValues()) {
                    Doctor doctor = new Doctor(name, designation, speciality, gender, contact, phone, reg, password, description, degree, email);
                    try {
                        NetworkController controller = new NetworkController(RegDoctorActivity.this);
                        if (controller.isNetworkAvailable()) {
                            regDoctor.execute(doctor);
                        } else {
                            Toast.makeText(RegDoctorActivity.this, "Check your Internet connection", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(RegDoctorActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                    newView = view;
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private boolean getAndCheckValues() {
        InputController controller = new InputController();
        name = controller.encodedValue(etName);
        designation = controller.encodedValue(etName);
        phone = controller.encodedValue(etName);
        code = controller.encodedValue(etName);
        email = controller.encodedValue(etName);
        contact = controller.encodedValue(etName);
        reg = controller.encodedValue(etName);
        password = controller.encodedValue(etName);
        rePassword = controller.encodedValue(etName);
        description = controller.encodedValue(etName);
        degree = controller.encodedValue(etName);
        speciality = (String) spSpeciality.getSelectedItem();
        gender = (String) spGender.getSelectedItem();

        if (rePassword.equals(password)) {
            if (name != null && name.length() != 0) {
                if (contact != null && contact.length() != 0) {
                    return true;
                } else {
                    controller.showError(etPhone, "This field can't be empty");
                }
            } else {
                controller.showError(etName, "This field can't be empty");
            }
        } else {
            controller.showError(etRePassword, "Passwords don't match");
        }

        return false;
    }

    private void linkAll() {
        etName = findViewById(R.id.reg_doctor_et_name);
        etDesignation = findViewById(R.id.reg_doctor_et_designation);
        etPhone = findViewById(R.id.reg_doctor_et_phone);
        etCode = findViewById(R.id.reg_doctor_et_code);
        etEmail = findViewById(R.id.reg_doctor_et_email);
        etContact = findViewById(R.id.reg_doctor_et_contact);
        etReg = findViewById(R.id.reg_doctor_et_registration);
        etPassword = findViewById(R.id.reg_doctor_et_password);
        etRePassword = findViewById(R.id.reg_doctor_et_re_password);
        etDescription = findViewById(R.id.reg_doctor_et_description);
        etDegree = findViewById(R.id.reg_doctor_et_degree);

        spGender = findViewById(R.id.reg_doctor_sp_gender);
        spSpeciality = findViewById(R.id.reg_doctor_sp_speciality);
    }

    public void onGetCode(View view) {
    }

    @Override
    public void processFinish(Object output) {
        Snackbar.make(newView, "" + output.toString(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(RegDoctorActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}
