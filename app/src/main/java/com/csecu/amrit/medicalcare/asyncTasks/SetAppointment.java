package com.csecu.amrit.medicalcare.asyncTasks;

import android.os.AsyncTask;

import com.csecu.amrit.medicalcare.interfaces.AsyncResponse;
import com.csecu.amrit.medicalcare.models.Appointment;
import com.csecu.amrit.medicalcare.models.User;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Amrit on 25/03/2018.
 */

public class SetAppointment extends AsyncTask {
    public AsyncResponse delegate = null;
    Appointment appointment;

    @Override
    protected Object doInBackground(Object[] objects) {
        // String reg_url = "http://10.2.3.100/medical/appointment.php";
        String reg_url = "http://192.168.0.103/medical/appointment.php";
        appointment = (Appointment) objects[0];
        try {
            String data = URLEncoder.encode("doctor", "UTF-8") + "=" +
                    URLEncoder.encode(appointment.getDoctor(), "UTF-8")
                    + "&" + URLEncoder.encode("date", "UTF-8") + "=" +
                    URLEncoder.encode(appointment.getDate(), "UTF-8") + "&" +
                    URLEncoder.encode("id", "UTF-8") + "=" +
                    URLEncoder.encode(appointment.getType(), "UTF-8") + "&" +
                    URLEncoder.encode("start", "UTF-8") + "=" +
                    URLEncoder.encode(appointment.getStart(), "UTF-8") + "&" +
                    URLEncoder.encode("end", "UTF-8") + "=" +
                    URLEncoder.encode(appointment.getEnd(), "UTF-8") + "&" +
                    URLEncoder.encode("transaction", "UTF-8") + "=" +
                    URLEncoder.encode(appointment.getTransaction(), "UTF-8");

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
