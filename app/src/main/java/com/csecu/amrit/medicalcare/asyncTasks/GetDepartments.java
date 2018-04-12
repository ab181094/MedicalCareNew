package com.csecu.amrit.medicalcare.asyncTasks;


import android.os.AsyncTask;

import com.csecu.amrit.medicalcare.interfaces.AsyncResponse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Amrit on 11/03/2018.
 */

public class GetDepartments extends AsyncTask {
    String JSON_STRING = null;
    public AsyncResponse delegate = null;
    // String login_url = "http://10.2.3.100/";
    String login_url = "http://192.168.0.103/";

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            // String login_url = "http://10.2.3.100/medical/login_admin.php";
            login_url = login_url + "medical/get_department.php";
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();

            while ((JSON_STRING = bufferedReader.readLine()) != null) {
                stringBuilder.append(JSON_STRING + "\n");
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return stringBuilder.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        delegate.processFinish(o);
    }
}
