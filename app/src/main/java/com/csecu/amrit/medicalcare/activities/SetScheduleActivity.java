package com.csecu.amrit.medicalcare.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.csecu.amrit.medicalcare.R;
import com.csecu.amrit.medicalcare.asyncTasks.SetSchedule;
import com.csecu.amrit.medicalcare.controllers.NetworkController;
import com.csecu.amrit.medicalcare.interfaces.AsyncResponse;
import com.csecu.amrit.medicalcare.models.Doctor;
import com.csecu.amrit.medicalcare.models.Schedule;

import java.util.ArrayList;
import java.util.Calendar;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

public class SetScheduleActivity extends AppCompatActivity implements AsyncResponse {
    TextView tvStartDate, tvEndDate, tvStartTime1, tvEndTime1, tvStartTime2, tvEndTime2, tvStartTime3, tvEndTime3;
    Spinner spDays;
    TagContainerLayout mTagContainerLayout;
    String startDate, endDate, startTime1, endTime1, startTime2, endTime2, startTime3, endTime3, day = "", remain = "";
    Doctor doctor;
    SetSchedule setSchedule;
    View newView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linkAll();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            doctor = bundle.getParcelable("doctor");
        }

        setSchedule = new SetSchedule();
        setSchedule.delegate = this;

        spDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mTagContainerLayout.addTag(String.valueOf(spDays.getSelectedItem()));
                mTagContainerLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mTagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {

            }

            @Override
            public void onTagLongClick(int position, String text) {

            }

            @Override
            public void onTagCrossClick(int position) {
                mTagContainerLayout.removeTag(position);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newView = view;
                startDate = tvStartDate.getText().toString().trim();
                endDate = tvEndDate.getText().toString().trim();
                startTime1 = tvStartTime1.getText().toString().trim();
                endTime1 = tvEndTime1.getText().toString().trim();
                startTime2 = tvStartTime2.getText().toString().trim();
                endTime2 = tvEndTime2.getText().toString().trim();
                startTime3 = tvStartTime3.getText().toString().trim();
                endTime3 = tvEndTime3.getText().toString().trim();
                ArrayList<String> list = (ArrayList<String>) mTagContainerLayout.getTags();
                String[] days = getResources().getStringArray(R.array.days_array);
                for (int i = 0; i < list.size(); i++) {
                    String v = list.get(i);
                    for (int j = 0; j < days.length; j++) {
                        if (v.equals(days[j])) {
                            day = day + String.valueOf(j);
                            break;
                        }
                    }
                    if (i != (list.size() - 1)) {
                        day = day + ",";
                    }
                }

                ArrayList<Integer> values = new ArrayList<>();
                for (int i = 0; i < days.length; i++) {
                    String v = days[i];
                    int c = 0;
                    for (int j = 0; j < list.size(); j++) {
                        if (v.equals(list.get(j))) {
                            c++;
                        }
                    }
                    if (c == 0) {
                        values.add(i);
                    }
                }

                for (int i = 0; i < values.size(); i++) {
                    remain = remain + values.get(i);
                    if (i != (values.size() - 1)) {
                        remain = remain + ",";
                    }
                }

                Schedule schedule = new Schedule(startDate, endDate, startTime1, endTime1,
                        startTime2, endTime2, startTime3, endTime3, day, remain, String.valueOf(doctor.getId()));
                try {
                    NetworkController controller = new NetworkController(SetScheduleActivity.this);
                    if (controller.isNetworkAvailable()) {
                        setSchedule.execute(schedule);
                    } else {
                        Toast.makeText(SetScheduleActivity.this, "Check your Internet connection", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(SetScheduleActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void linkAll() {
        tvStartDate = findViewById(R.id.set_tv_start_date);
        tvEndDate = findViewById(R.id.set_tv_end_date);
        tvStartTime1 = findViewById(R.id.set_tv_start_time_1);
        tvEndTime1 = findViewById(R.id.set_tv_end_time_1);
        tvStartTime2 = findViewById(R.id.set_tv_start_time_2);
        tvEndTime2 = findViewById(R.id.set_tv_end_time_2);
        tvStartTime3 = findViewById(R.id.set_tv_start_time_3);
        tvEndTime3 = findViewById(R.id.set_tv_end_time_3);
        spDays = findViewById(R.id.set_sp_days);
        mTagContainerLayout = findViewById(R.id.tagView);
    }

    public void onPickStartDate(View view) {
        final Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        DatePickerDialog dialog =
                new DatePickerDialog(SetScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        String text = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        tvStartDate.setText(text);
                        tvStartDate.setVisibility(View.VISIBLE);
                    }
                }, year, month, day);
        dialog.setTitle("Select Date");
        dialog.show();
    }

    public void onPickEndDate(View view) {
        final Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        DatePickerDialog dialog =
                new DatePickerDialog(SetScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        String text = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        tvEndDate.setText(text);
                        tvEndDate.setVisibility(View.VISIBLE);
                    }
                }, year, month, day);
        dialog.setTitle("Select Date");
        dialog.show();
    }

    public void onPickStartTime1(View view) {
        pickTime(tvStartTime1);
    }

    private void pickTime(final TextView t) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(SetScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String period;
                if (selectedHour >= 12) {
                    if (selectedHour != 12) {
                        selectedHour = selectedHour - 12;
                    }
                    period = "PM";
                } else {
                    period = "AM";
                }

                String start;

                if (selectedMinute == 0) {
                    start = selectedHour + ":" + selectedMinute + "0 " + period;
                } else {
                    start = selectedHour + ":" + selectedMinute + " " + period;
                }

                if (selectedHour < 10) {
                    start = "0" + start;
                }
                t.setText(start);
                t.setVisibility(View.VISIBLE);
            }
        }, hour, minute, true);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void onPickStartTime2(View view) {
        pickTime(tvStartTime2);
    }

    public void onPickStartTime3(View view) {
        pickTime(tvStartTime3);
    }

    public void onPickEndTime1(View view) {
        pickTime(tvEndTime1);
    }

    public void onPickEndTime2(View view) {
        pickTime(tvEndTime2);
    }

    public void onPickEndTime3(View view) {
        pickTime(tvEndTime3);
    }

    @Override
    public void processFinish(Object output) {
        Snackbar.make(newView, "" + output.toString(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SetScheduleActivity.this,
                        DoctorActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}
