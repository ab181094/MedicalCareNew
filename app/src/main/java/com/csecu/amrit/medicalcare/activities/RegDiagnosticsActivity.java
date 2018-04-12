package com.csecu.amrit.medicalcare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.csecu.amrit.medicalcare.R;
import com.csecu.amrit.medicalcare.asyncTasks.RegDiagnostics;
import com.csecu.amrit.medicalcare.controllers.InputController;
import com.csecu.amrit.medicalcare.controllers.NetworkController;
import com.csecu.amrit.medicalcare.interfaces.AsyncResponse;
import com.csecu.amrit.medicalcare.models.Diagnostics;
import com.csecu.amrit.medicalcare.models.User;

public class RegDiagnosticsActivity extends AppCompatActivity implements AsyncResponse {
    private EditText etName, etReg, etPhone, etCode, etEmail, etContact, etAddress, etPassword, etRePassword;
    private String name, reg, phone, email, contact, address, password;
    private View newView;
    RegDiagnostics regDiagnostics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_diagnostics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linkAll();
        regDiagnostics = new RegDiagnostics();
        regDiagnostics.delegate = this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getAndCheckValues()) {
                    Diagnostics diagnostics = new Diagnostics(name, reg, contact, phone, email, password, address);
                    try {
                        NetworkController controller = new NetworkController(RegDiagnosticsActivity.this);
                        if (controller.isNetworkAvailable()) {
                            regDiagnostics.execute(diagnostics);
                        } else {
                            Toast.makeText(RegDiagnosticsActivity.this, "Check your Internet connection", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(RegDiagnosticsActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                    newView = view;
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private boolean getAndCheckValues() {
        InputController controller = new InputController();
        name = controller.encodedValue(etName);
        reg = controller.encodedValue(etReg);
        phone = controller.encodedValue(etPhone);
        String code = controller.encodedValue(etCode);
        email = controller.encodedValue(etEmail);
        contact = controller.encodedValue(etContact);
        address = controller.encodedValue(etAddress);
        password = controller.encodedValue(etPassword);
        String confirm = controller.encodedValue(etRePassword);

        if (confirm.equals(password)) {
            if (name != null && name.length() != 0) {
                if (contact != null && contact.length() != 0) {
                    return true;
                } else {
                    controller.showError(etPhone, "This field can't be empty");
                }
            } else {
                controller.showError(etName, "This field can't be empty");
            }
        } else {
            controller.showError(etRePassword, "Passwords don't match");
        }

        return false;
    }

    private void linkAll() {
        etName = findViewById(R.id.reg_diag_et_name);
        etReg = findViewById(R.id.reg_diag_et_registration);
        etPhone = findViewById(R.id.reg_diag_et_phone);
        etCode = findViewById(R.id.reg_diag_et_code);
        etEmail = findViewById(R.id.reg_diag_et_email);
        etContact = findViewById(R.id.reg_diag_et_contact);
        etAddress = findViewById(R.id.reg_diag_et_address);
        etPassword = findViewById(R.id.reg_diag_et_password);
        etRePassword = findViewById(R.id.reg_diag_et_re_password);
    }

    public void onGetCode(View view) {

    }

    @Override
    public void processFinish(Object output) {
        Snackbar.make(newView, "" + output.toString(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(RegDiagnosticsActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}
