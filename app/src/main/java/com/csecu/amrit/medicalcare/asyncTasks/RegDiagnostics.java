package com.csecu.amrit.medicalcare.asyncTasks;

import android.os.AsyncTask;

import com.csecu.amrit.medicalcare.interfaces.AsyncResponse;
import com.csecu.amrit.medicalcare.models.Diagnostics;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Amrit on 25/03/2018.
 */

public class RegDiagnostics extends AsyncTask {
    public AsyncResponse delegate = null;
    Diagnostics diagnostics;

    @Override
    protected Object doInBackground(Object[] objects) {
        // String reg_url = "http://10.2.3.100/medical/reg_diagnostics.php";
        String reg_url = "http://192.168.0.103/medical/reg_diagnostics.php";
        diagnostics = (Diagnostics) objects[0];
        try {
            String data = URLEncoder.encode("name", "UTF-8") + "=" +
                    URLEncoder.encode(diagnostics.getName(), "UTF-8")
                    + "&" + URLEncoder.encode("reg", "UTF-8") + "=" +
                    URLEncoder.encode(diagnostics.getReg(), "UTF-8") + "&" +
                    URLEncoder.encode("contact", "UTF-8") + "=" +
                    URLEncoder.encode(diagnostics.getContact(), "UTF-8") + "&" +
                    URLEncoder.encode("phone", "UTF-8") + "=" +
                    URLEncoder.encode(diagnostics.getPhone(), "UTF-8") + "&" +
                    URLEncoder.encode("email", "UTF-8") + "=" +
                    URLEncoder.encode(diagnostics.getEmail(), "UTF-8") + "&" +
                    URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(diagnostics.getPassword(), "UTF-8") + "&" +
                    URLEncoder.encode("address", "UTF-8") + "=" +
                    URLEncoder.encode(diagnostics.getAddress(), "UTF-8");

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
