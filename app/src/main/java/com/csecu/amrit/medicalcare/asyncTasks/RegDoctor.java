package com.csecu.amrit.medicalcare.asyncTasks;

import android.os.AsyncTask;

import com.csecu.amrit.medicalcare.interfaces.AsyncResponse;
import com.csecu.amrit.medicalcare.models.Doctor;
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

public class RegDoctor extends AsyncTask {
    public AsyncResponse delegate = null;
    Doctor doctor;

    @Override
    protected Object doInBackground(Object[] objects) {
        // String reg_url = "http://10.2.3.100/medical/reg_doctor.php";
        String reg_url = "http://192.168.0.103/medical/reg_doctor.php";
        doctor = (Doctor) objects[0];
        try {
            String data = URLEncoder.encode("name", "UTF-8") + "=" +
                    URLEncoder.encode(doctor.getName(), "UTF-8")
                    + "&" + URLEncoder.encode("designation", "UTF-8") + "=" +
                    URLEncoder.encode(doctor.getDesignation(), "UTF-8") + "&" +
                    URLEncoder.encode("speciality", "UTF-8") + "=" +
                    URLEncoder.encode(doctor.getSpeciality(), "UTF-8") + "&" +
                    URLEncoder.encode("gender", "UTF-8") + "=" +
                    URLEncoder.encode(doctor.getGender(), "UTF-8") + "&" +
                    URLEncoder.encode("contact", "UTF-8") + "=" +
                    URLEncoder.encode(doctor.getContact(), "UTF-8") + "&" +
                    URLEncoder.encode("phone", "UTF-8") + "=" +
                    URLEncoder.encode(doctor.getPhone(), "UTF-8") + "&" +
                    URLEncoder.encode("reg", "UTF-8") + "=" +
                    URLEncoder.encode(doctor.getReg(), "UTF-8") + "&" +
                    URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(doctor.getPassword(), "UTF-8") + "&" +
                    URLEncoder.encode("description", "UTF-8") + "=" +
                    URLEncoder.encode(doctor.getDescription(), "UTF-8") + "&" +
                    URLEncoder.encode("degree", "UTF-8") + "=" +
                    URLEncoder.encode(doctor.getDegree(), "UTF-8") + "&" +
                    URLEncoder.encode("email", "UTF-8") + "=" +
                    URLEncoder.encode(doctor.getEmail(), "UTF-8");

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
