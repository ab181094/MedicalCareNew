package com.csecu.amrit.medicalcare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.csecu.amrit.medicalcare.R;
import com.csecu.amrit.medicalcare.asyncTasks.GetDepartments;
import com.csecu.amrit.medicalcare.interfaces.AsyncResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DepartmentActivity extends AppCompatActivity implements AsyncResponse {
    private ArrayList<String> arrayList = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.listView);

        GetDepartments getDepartments = new GetDepartments();
        getDepartments.delegate = this;
        getDepartments.execute();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String type = arrayList.get(i);
                Intent intent = new Intent(DepartmentActivity.this, DoctorListActivity.class);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });
    }

    @Override
    public void processFinish(Object output) {
        parseAll(output.toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DepartmentActivity.this, android.R.layout.simple_list_item_1, arrayList);
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
                    id = object.getInt("1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String name = object.getString("2");
                String age = object.getString("3");
                String gender = object.getString("4");
                arrayList.add(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
