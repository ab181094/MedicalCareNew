package com.csecu.amrit.medicalcare.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

import java.util.ArrayList;

public class DoctorListActivity extends AppCompatActivity implements AsyncResponse {
    ListView listView;
    String type;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<Doctor> doctors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.listView2);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getString("type");
            SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(getString(R.string.type), type);
            editor.commit();
        } else {
            SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
            type = sharedpreferences.getString(getString(R.string.type), "");
        }

        Login login = new Login();
        login.delegate = this;
        try {
            NetworkController controller = new NetworkController(DoctorListActivity.this);
            if (controller.isNetworkAvailable()) {
                login.execute("Doctor");
            } else {
                Toast.makeText(DoctorListActivity.this, "Check your Internet connection", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(DoctorListActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DoctorListActivity.this, DoctorDetailsActivity.class);
                intent.putExtra("1", doctors.get(i));
                startActivity(intent);
            }
        });
    }

    @Override
    public void processFinish(Object output) {
        parseAll(output.toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DoctorListActivity.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
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

                InputController controller = new InputController();

                if (controller.decodeString(type).equals(controller.decodeString(speciality))) {
                    list.add(controller.decodeString(name));
                    doctors.add(doctor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
