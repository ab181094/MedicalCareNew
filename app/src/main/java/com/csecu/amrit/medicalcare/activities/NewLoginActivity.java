package com.csecu.amrit.medicalcare.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.csecu.amrit.medicalcare.R;
import com.csecu.amrit.medicalcare.asyncTasks.Login;
import com.csecu.amrit.medicalcare.controllers.InputController;
import com.csecu.amrit.medicalcare.controllers.NetworkController;
import com.csecu.amrit.medicalcare.interfaces.AsyncResponse;
import com.csecu.amrit.medicalcare.models.Admin;
import com.csecu.amrit.medicalcare.models.Diagnostics;
import com.csecu.amrit.medicalcare.models.Doctor;
import com.csecu.amrit.medicalcare.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewLoginActivity extends AppCompatActivity implements AsyncResponse {
    String type = "User", phone, password;
    EditText etPhone, etPassword;
    View newView;
    Login login;
    Admin admin;
    User user;
    Doctor doctor;
    Diagnostics diagnostics;
    Boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getString("type");
        }

        linkAll();
        login = new Login();
        login.delegate = this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getAndCheckValue()) {
                    try {
                        NetworkController controller = new NetworkController(NewLoginActivity.this);
                        if (controller.isNetworkAvailable()) {
                            if (type != null) {
                                login.execute(type);
                            }
                        } else {
                            Toast.makeText(NewLoginActivity.this, "Check your Internet connection", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(NewLoginActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                    newView = view;
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private boolean getAndCheckValue() {
        InputController controller = new InputController();
        phone = etPhone.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if (phone != null && phone.length() != 0) {
            if (password != null && password.length() != 0) {
                return true;
            } else {
                controller.showError(etPassword, "This field can't be empty");
            }
        } else {
            controller.showError(etPhone, "This field can't be empty");
        }

        return false;
    }

    private void linkAll() {
        etPhone = findViewById(R.id.login_et_contact);
        etPassword = findViewById(R.id.login_et_password);

        View focusView = etPhone;
        focusView.requestFocus();
    }

    @Override
    public void processFinish(Object output) {
        parseAll(output.toString());
        if (!status) {
            recreate();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(NewLoginActivity.this, AppointmentActivity.class);
                    intent.putExtra("user", user.getId());
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        }
    }

    private void parseAll(String result) {
        JSONObject jsonObject;
        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(result);
            jsonArray = jsonObject.getJSONArray("server_response");
            for (int count = 0; count < jsonArray.length(); count++) {
                if (type.equals("Admin")) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    int id = 0;
                    try {
                        id = object.getInt("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String name = object.getString("name");
                    String age = object.getString("age");
                    String gender = object.getString("gender");
                    String contact = object.getString("contact");
                    String email = object.getString("email");
                    String pass = object.getString("password");
                    String address = object.getString("address");


                    admin = new Admin();
                    admin.setId(id);
                    admin.setName(name);
                    admin.setAge(age);
                    admin.setGender(gender);
                    admin.setContact(contact);
                    admin.setEmail(email);
                    admin.setPassword(pass);
                    admin.setAddress(address);

                    if (phone.equals(contact) && password.equals(pass)) {
                        Toast.makeText(this, "Welcome " + admin.getName(), Toast.LENGTH_LONG).show();
                        status = true;
                        break;
                    }
                } else if (type.equals("User")) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    int id = 0;
                    try {
                        id = object.getInt("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String name = object.getString("name");
                    String age = object.getString("age");
                    String gender = object.getString("gender");
                    String contact = object.getString("contact");
                    String phone = object.getString("phone");
                    String email = object.getString("email");
                    String pass = object.getString("password");
                    String address = object.getString("address");


                    user = new User();
                    user.setId(id);
                    user.setName(name);
                    user.setAge(age);
                    user.setGender(gender);
                    user.setContact(contact);
                    user.setPhone(phone);
                    user.setEmail(email);
                    user.setPassword(pass);
                    user.setAddress(address);

                    if (this.phone.equals(contact) && password.equals(pass)) {
                        Toast.makeText(this, "Welcome " + user.getName(), Toast.LENGTH_LONG).show();
                        status = true;
                        break;
                    }
                } else if (type.equals("Doctor")) {
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


                    doctor = new Doctor();
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

                    if (this.phone.equals(contact) && password.equals(pass)) {
                        Toast.makeText(this, "Welcome " + doctor.getName(), Toast.LENGTH_LONG).show();
                        status = true;
                        break;
                    }
                } else if (type.equals("Diagnostics")) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    int id = 0;
                    try {
                        id = object.getInt("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String name = object.getString("name");
                    String phone = object.getString("phone");
                    String contact = object.getString("contact");
                    String email = object.getString("email");
                    String pass = object.getString("password");
                    String address = object.getString("address");
                    String reg = object.getString("reg");


                    diagnostics = new Diagnostics();
                    diagnostics.setId(id);
                    diagnostics.setName(name);
                    diagnostics.setContact(contact);
                    diagnostics.setPhone(phone);
                    diagnostics.setEmail(email);
                    diagnostics.setPassword(pass);
                    diagnostics.setAddress(address);
                    diagnostics.setReg(reg);

                    if (this.phone.equals(contact) && password.equals(pass)) {
                        Toast.makeText(this, "Welcome " + diagnostics.getName(), Toast.LENGTH_LONG).show();
                        status = true;
                        break;
                    }
                }
            }
            if (!status) {
                Toast.makeText(this, "Wrong Username / Password", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
