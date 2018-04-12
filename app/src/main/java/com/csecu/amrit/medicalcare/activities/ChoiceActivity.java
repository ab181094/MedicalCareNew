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

public class ChoiceActivity extends AppCompatActivity {
    String type;
    GridView gridView;
    ArrayList<Icons> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        gridView = findViewById(R.id.choice_gridView);
        list = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            type = bundle.getString("type");

            if (type.equals("signin")) {
                list.add(new Icons(R.mipmap.ic_launcher, "Admin"));
                list.add(new Icons(R.mipmap.ic_launcher, "User"));
                list.add(new Icons(R.mipmap.ic_launcher, "Doctor"));
                list.add(new Icons(R.mipmap.ic_launcher, "Diagnostics"));

                HomeAdapter adapter = new HomeAdapter(this, list);
                gridView.setAdapter(adapter);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            Intent intent = new Intent(ChoiceActivity.this, LoginActivity.class);
                            intent.putExtra("type", "Admin");
                            startActivity(intent);
                            finish();
                        } else if (i == 1) {
                            Intent intent = new Intent(ChoiceActivity.this, LoginActivity.class);
                            intent.putExtra("type", "User");
                            startActivity(intent);
                            finish();
                        } else if (i == 2) {
                            Intent intent = new Intent(ChoiceActivity.this, LoginActivity.class);
                            intent.putExtra("type", "Doctor");
                            startActivity(intent);
                            finish();
                        } else if (i == 3) {
                            Intent intent = new Intent(ChoiceActivity.this, LoginActivity.class);
                            intent.putExtra("type", "Diagnostics");
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            } else if (type.equals("signup")) {
                list.add(new Icons(R.mipmap.ic_launcher, "User"));
                list.add(new Icons(R.mipmap.ic_launcher, "Doctor"));
                list.add(new Icons(R.mipmap.ic_launcher, "Diagnostics"));

                HomeAdapter adapter = new HomeAdapter(this, list);
                gridView.setAdapter(adapter);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            Intent intent = new Intent(ChoiceActivity.this, RegUserActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (i == 1) {
                            Intent intent = new Intent(ChoiceActivity.this, RegDoctorActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (i == 2) {
                            Intent intent = new Intent(ChoiceActivity.this, RegDiagnosticsActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        }
    }
}
