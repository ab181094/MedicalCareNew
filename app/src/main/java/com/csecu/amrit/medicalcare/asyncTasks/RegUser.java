package com.csecu.amrit.medicalcare.asyncTasks;

import android.os.AsyncTask;

import com.csecu.amrit.medicalcare.interfaces.AsyncResponse;
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

public class RegUser extends AsyncTask {
    public AsyncResponse delegate = null;
    User user;

    @Override
    protected Object doInBackground(Object[] objects) {
        // String reg_url = "http://10.2.3.100/medical/reg_user.php";
        String reg_url = "http://192.168.0.103/medical/reg_user.php";
        user = (User) objects[0];
        try {
            String data = URLEncoder.encode("name", "UTF-8") + "=" +
                    URLEncoder.encode(user.getName(), "UTF-8")
                    + "&" + URLEncoder.encode("age", "UTF-8") + "=" +
                    URLEncoder.encode(user.getAge(), "UTF-8") + "&" +
                    URLEncoder.encode("gender", "UTF-8") + "=" +
                    URLEncoder.encode(user.getGender(), "UTF-8") + "&" +
                    URLEncoder.encode("contact", "UTF-8") + "=" +
                    URLEncoder.encode(user.getContact(), "UTF-8") + "&" +
                    URLEncoder.encode("phone", "UTF-8") + "=" +
                    URLEncoder.encode(user.getPhone(), "UTF-8") + "&" +
                    URLEncoder.encode("email", "UTF-8") + "=" +
                    URLEncoder.encode(user.getEmail(), "UTF-8") + "&" +
                    URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(user.getPassword(), "UTF-8") + "&" +
                    URLEncoder.encode("address", "UTF-8") + "=" +
                    URLEncoder.encode(user.getAddress(), "UTF-8");

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
