package com.csecu.amrit.medicalcare.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.csecu.amrit.medicalcare.R;
import com.csecu.amrit.medicalcare.adapters.HomeAdapter;
import com.csecu.amrit.medicalcare.models.Icons;
import com.csecu.amrit.medicalcare.models.User;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    GridView gridView;
    ArrayList<Icons> list;
    User user;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user = bundle.getParcelable("user");
            SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt(getString(R.string.user), userID);
            editor.commit();
        } else {
            SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
            userID = sharedpreferences.getInt(getString(R.string.user), 0);
        }

        gridView = findViewById(R.id.user_gridView);
        list = new ArrayList<>();
        list.add(new Icons(R.mipmap.ic_launcher, "Update"));
        list.add(new Icons(R.mipmap.ic_launcher, "Appointment"));
        list.add(new Icons(R.mipmap.ic_launcher, "Appointment Status"));
        list.add(new Icons(R.mipmap.ic_launcher, "Prescription"));
        list.add(new Icons(R.mipmap.ic_launcher, "Diagnostic Appointment"));
        list.add(new Icons(R.mipmap.ic_launcher, "Diagnostic Appointment Status"));
        list.add(new Icons(R.mipmap.ic_launcher, "Diagnostic History"));
        list.add(new Icons(R.mipmap.ic_launcher, "Sign out"));

        HomeAdapter adapter = new HomeAdapter(this, list);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {

                } else if (i == 1) {
                    Intent intent = new Intent(UserActivity.this, AppointmentActivity.class);
                    intent.putExtra("user", userID);
                    startActivity(intent);
                } else if (i == 2) {

                } else if (i == 3) {

                } else if (i == 4) {

                } else if (i == 5) {

                } else if (i == 6) {

                } else if (i == 7) {
                    Intent intent = new Intent(UserActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

}
