package com.csecu.amrit.medicalcare.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.csecu.amrit.medicalcare.R;
import com.csecu.amrit.medicalcare.adapters.DoctorAdapter;
import com.csecu.amrit.medicalcare.asyncTasks.Login;
import com.csecu.amrit.medicalcare.controllers.NetworkController;
import com.csecu.amrit.medicalcare.interfaces.AsyncResponse;
import com.csecu.amrit.medicalcare.models.Doctor;
import com.csecu.amrit.medicalcare.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class AppointmentActivity extends AppCompatActivity implements AsyncResponse {
    TextView tvDate;
    Spinner spDoctor;
    RadioButton rbReturn, rbVisit;
    Login login;
    ArrayList<Doctor> doctors = new ArrayList<>();
    User user;
    String doctor, date, type;
    int doctorID, userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linkAll();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userID = bundle.getInt("user");
        }

        login = new Login();
        login.delegate = this;
        try {
            NetworkController controller = new NetworkController(AppointmentActivity.this);
            if (controller.isNetworkAvailable()) {
                login.execute("Doctor");
            } else {
                Toast.makeText(AppointmentActivity.this, "Check your Internet connection", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(AppointmentActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getAndCheckValues()) {
                    doctor = String.valueOf(doctors.get(doctorID).getId());
                    String patient = String.valueOf(userID);

                    Intent intent = new Intent(AppointmentActivity.this, PickTimeActivity.class);
                    intent.putExtra("1", patient);
                    intent.putExtra("2", date);
                    intent.putExtra("3", doctor);
                    startActivity(intent);
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private boolean getAndCheckValues() {
        try {
            doctorID = spDoctor.getSelectedItemPosition();
            date = tvDate.getText().toString().trim();
            if (rbReturn.isChecked()) {
                type = rbReturn.getText().toString().trim();
            } else {
                type = rbVisit.getText().toString().trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "" + e.toString(), Toast.LENGTH_LONG).show();
        }


        if (date != null && date.length() != 0) {
            return true;
        } else {
            Toast.makeText(this, "Select a date", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    private void linkAll() {
        tvDate = findViewById(R.id.app_tv_date);
        spDoctor = findViewById(R.id.app_sp_doctor);
        rbReturn = findViewById(R.id.app_rb_return);
        rbVisit = findViewById(R.id.app_rb_visit);
    }

    public void onPickDate(View view) {
        final Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        DatePickerDialog dialog =
                new DatePickerDialog(AppointmentActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        String month, day;
                        if (monthOfYear < 9) {
                            month = "0" + String.valueOf((monthOfYear + 1));
                        } else {
                            month = String.valueOf((monthOfYear + 1));
                        }

                        if (dayOfMonth < 10) {
                            day = "0" + String.valueOf((dayOfMonth));
                        } else {
                            day = String.valueOf((dayOfMonth));
                        }
                        String text = year + "-" + month + "-" + day;
                        tvDate.setText(text);
                        tvDate.setVisibility(View.VISIBLE);
                    }
                }, year, month, day);
        dialog.setTitle("Select Date");
        dialog.show();
    }

    @Override
    public void processFinish(Object output) {
        if (doctors.size() == 0) {
            parseAll(output.toString());
            DoctorAdapter doctorAdapter = new DoctorAdapter(AppointmentActivity.this, doctors);
            spDoctor.setAdapter(doctorAdapter);
        }
    }

    private void parseAll(String result) {
        JSONObject jsonObject;
        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(result);
            jsonArray = jsonObject.getJSONArray("server_response");
            for (int count = 0; count < jsonArray.length(); count++) {

                JSONObject object = jsonArray.getJSONObject(count);
                int id = 0;
                try {
                    id = object.getInt("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String name = object.getString("name");
                // String area = object.getString("area");
                String designation = object.getString("designation");
                String speciality = object.getString("speciality");
                String gender = object.getString("gender");
                String contact = object.getString("contact");
                String phone = object.getString("phone");
                String reg = object.getString("reg");
                String pass = object.getString("password");
                String description = object.getString("description");
                String degree = object.getString("degree");
                String email = object.getString("email");


                Doctor doctor = new Doctor();
                doctor.setId(id);
                doctor.setName(name);
                doctor.setDesignation(designation);
                doctor.setSpeciality(speciality);
                doctor.setGender(gender);
                doctor.setContact(contact);
                doctor.setPhone(phone);
                doctor.setReg(reg);
                doctor.setEmail(email);
                doctor.setPassword(pass);
                doctor.setDescription(description);
                doctor.setDegree(degree);

                doctors.add(doctor);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
