package com.csecu.amrit.medicalcare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.csecu.amrit.medicalcare.R;
import com.csecu.amrit.medicalcare.asyncTasks.GetSchedule;
import com.csecu.amrit.medicalcare.asyncTasks.SetAppointment;
import com.csecu.amrit.medicalcare.controllers.InputController;
import com.csecu.amrit.medicalcare.controllers.NetworkController;
import com.csecu.amrit.medicalcare.helpers.TimeInterval;
import com.csecu.amrit.medicalcare.interfaces.AsyncResponse;
import com.csecu.amrit.medicalcare.models.Appointment;
import com.csecu.amrit.medicalcare.models.Schedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PickTimeActivity extends AppCompatActivity implements AsyncResponse {
    EditText etTransaction;
    SetAppointment setAppointment;
    String user, date, transaction, start, end, doctorID;
    Schedule schedule;
    Boolean status = false;
    ArrayList<String> timeList = new ArrayList<>();
    Spinner spTime;
    Boolean getStatus = false;
    View newView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etTransaction = findViewById(R.id.app_et_transaction);
        spTime = findViewById(R.id.app_sp_doctor);

        setAppointment = new SetAppointment();
        setAppointment.delegate = this;

        GetSchedule getSchedule = new GetSchedule();
        getSchedule.delegate = this;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user = bundle.getString("1");
            date = bundle.getString("2");
            doctorID = bundle.getString("3");

            try {
                NetworkController controller = new NetworkController(PickTimeActivity.this);
                if (controller.isNetworkAvailable()) {
                    getSchedule.execute();
                } else {
                    Toast.makeText(PickTimeActivity.this, "Check your Internet connection", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(PickTimeActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newView = view;
                InputController controller = new InputController();
                transaction = controller.encodedValue(etTransaction);

                if (transaction == null || transaction.length() == 0) {
                    View view1 = etTransaction;
                    view1.requestFocus();
                    etTransaction.setError("This field can't be empty");
                }

                String time = (String) spTime.getSelectedItem();
                String[] values = time.split("-");

                start = values[0].trim();
                end = values[1].trim();

                Appointment appointment = new Appointment(doctorID, date, user, start, end, transaction);

                try {
                    NetworkController controller1 = new NetworkController(PickTimeActivity.this);
                    if (controller1.isNetworkAvailable()) {
                        getStatus = true;
                        setAppointment.execute(appointment);
                    } else {
                        Toast.makeText(PickTimeActivity.this, "Check your Internet connection", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(PickTimeActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void processFinish(Object output) {
        if (getStatus) {
            Snackbar.make(newView, "" + output.toString(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(PickTimeActivity.this,
                            UserActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 2000);
        } else {
            parseAll(output.toString());
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
                    id = object.getInt("1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String doctor = object.getString("2");
                String day = object.getString("3");
                String remain = object.getString("4");
                String startDate = object.getString("5");
                String endDate = object.getString("6");
                String startTime1 = object.getString("7");
                String endTime1 = object.getString("8");
                String startTime2 = object.getString("9");
                String endTime2 = object.getString("10");
                String startTime3 = object.getString("11");
                String endTime3 = object.getString("12");

                schedule = new Schedule(id, startDate, endDate, startTime1, endTime1, startTime2, endTime2, startTime3, endTime3, day, remain, doctor);
                if (doctorID.equals(doctor)) {
                    int date1 = getDateValue(startDate);
                    int date2 = getDateValue(endDate);
                    int date3 = getDateValue(date);

                    if ((date1 < date3) && (date3 < date2)) {
                        status = true;
                        break;
                    }
                }
            }

            if (status) {
                TimeInterval timeInterval = new TimeInterval();
                ArrayList<String> list = timeInterval.generateTags(schedule.getStartTime1(), schedule.getEndTime1());
                for (int i = 0; i < list.size(); i++) {
                    timeList.add(list.get(i));
                }
                ArrayList<String> list1 = timeInterval.generateTags(schedule.getStartTime2(), schedule.getEndTime2());
                for (int i = 0; i < list1.size(); i++) {
                    timeList.add(list1.get(i));
                }
                ArrayList<String> list2 = timeInterval.generateTags(schedule.getStartTime3(), schedule.getEndTime3());
                for (int i = 0; i < list2.size(); i++) {
                    timeList.add(list2.get(i));
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PickTimeActivity.this, android.R.layout.simple_spinner_item, timeList);
                spTime.setAdapter(adapter);
            } else {
                TimeInterval timeInterval = new TimeInterval();
                timeList = timeInterval.generateTags("00:00 AM", "11:59 PM");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PickTimeActivity.this, android.R.layout.simple_spinner_item, timeList);
                spTime.setAdapter(adapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private int getDateValue(String startDate) {
        String[] v = startDate.split("-");
        String s = "";
        for (int i = 0; i < v.length; i++) {
            s = s + v[i];
        }
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
