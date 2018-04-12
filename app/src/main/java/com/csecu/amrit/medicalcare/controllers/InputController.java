package com.csecu.amrit.medicalcare.controllers;

import android.view.View;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Amrit on 24/03/2018.
 */

public class InputController {
    public String encodedValue(EditText editText) {
        String value = editText.getText().toString().trim();
        value = encodeString(value);
        return value;
    }

    public String encodeString(String value) {
        try {
            value = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }

    public String decodeString(String value) {
        try {
            value = URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }

    public void showError(EditText editText, String msg) {
        editText.setError(msg);
        View focusView = editText;
        focusView.requestFocus();
    }
}
