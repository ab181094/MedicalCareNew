package com.csecu.amrit.medicalcare.asyncTasks;

import android.os.AsyncTask;

import com.csecu.amrit.medicalcare.interfaces.AsyncResponse;
import com.csecu.amrit.medicalcare.models.Schedule;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Amrit on 25/03/2018.
 */

public class SetSchedule extends AsyncTask {
    public AsyncResponse delegate = null;
    Schedule schedule;

    @Override
    protected Object doInBackground(Object[] objects) {
        // String reg_url = "http://10.2.3.100/medical/set_schedule.php";
        String reg_url = "http://192.168.0.103/medical/set_schedule.php";
        schedule = (Schedule) objects[0];
        try {
            String data = URLEncoder.encode("1", "UTF-8") + "=" +
                    URLEncoder.encode(schedule.getDoctor(), "UTF-8")
                    + "&" + URLEncoder.encode("2", "UTF-8") + "=" +
                    URLEncoder.encode(schedule.getDay(), "UTF-8") + "&" +
                    URLEncoder.encode("3", "UTF-8") + "=" +
                    URLEncoder.encode(schedule.getRemain(), "UTF-8") + "&" +
                    URLEncoder.encode("4", "UTF-8") + "=" +
                    URLEncoder.encode(schedule.getStartDate(), "UTF-8") + "&" +
                    URLEncoder.encode("5", "UTF-8") + "=" +
                    URLEncoder.encode(schedule.getEndDate(), "UTF-8") + "&" +
                    URLEncoder.encode("6", "UTF-8") + "=" +
                    URLEncoder.encode(schedule.getStartTime1(), "UTF-8") + "&" +
                    URLEncoder.encode("7", "UTF-8") + "=" +
                    URLEncoder.encode(schedule.getEndTime1(), "UTF-8") + "&" +
                    URLEncoder.encode("8", "UTF-8") + "=" +
                    URLEncoder.encode(schedule.getStartTime2(), "UTF-8") + "&" +
                    URLEncoder.encode("9", "UTF-8") + "=" +
                    URLEncoder.encode(schedule.getEndTime2(), "UTF-8") + "&" +
                    URLEncoder.encode("10", "UTF-8") + "=" +
                    URLEncoder.encode(schedule.getStartTime3(), "UTF-8") + "&" +
                    URLEncoder.encode("11", "UTF-8") + "=" +
                    URLEncoder.encode(schedule.getEndTime3(), "UTF-8");

            URL url = new URL(reg_url);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }

            return line;
        } catch (Exception e) {
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        delegate.processFinish(o);
    }
}
