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

import com.csecu.amrit.medicalcare.R;
import com.csecu.amrit.medicalcare.adapters.HomeAdapter;
import com.csecu.amrit.medicalcare.models.Icons;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    GridView gridView;
    ArrayList<Icons> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gridView = findViewById(R.id.home_gridView);
        list = new ArrayList<>();
        list.add(new Icons(R.mipmap.ic_launcher, "Departments"));
        list.add(new Icons(R.mipmap.ic_launcher, "Services"));
        list.add(new Icons(R.mipmap.ic_launcher, "Laboratories"));
        list.add(new Icons(R.mipmap.ic_launcher, "Contact"));
        list.add(new Icons(R.mipmap.ic_launcher, "Sign-in"));
        list.add(new Icons(R.mipmap.ic_launcher, "Sign-up"));

        HomeAdapter adapter = new HomeAdapter(this, list);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent intent = new Intent(HomeActivity.this, DepartmentActivity.class);
                    startActivity(intent);
                } else if (i == 1) {

                } else if (i == 2) {

                } else if (i == 3) {

                } else if (i == 4) {
                    Intent intent = new Intent(HomeActivity.this, ChoiceActivity.class);
                    intent.putExtra("type", "signin");
                    startActivity(intent);
                } else if (i == 5) {
                    Intent intent = new Intent(HomeActivity.this, ChoiceActivity.class);
                    intent.putExtra("type", "signup");
                    startActivity(intent);
                }
            }
        });
    }
}
