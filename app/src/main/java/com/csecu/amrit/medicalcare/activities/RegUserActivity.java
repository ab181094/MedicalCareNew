package com.csecu.amrit.medicalcare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.csecu.amrit.medicalcare.R;
import com.csecu.amrit.medicalcare.asyncTasks.RegUser;
import com.csecu.amrit.medicalcare.controllers.InputController;
import com.csecu.amrit.medicalcare.controllers.NetworkController;
import com.csecu.amrit.medicalcare.interfaces.AsyncResponse;
import com.csecu.amrit.medicalcare.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class RegUserActivity extends AppCompatActivity implements AsyncResponse {
    private EditText etName, etAge, etPhone, etCode, etEmail, etContact, etAddress, etPassword, etRepassword;
    private Spinner spGender;
    private String name, age, phone, email, contact, address, password, gender;
    private View newView;
    RegUser regUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        linkAll();
        regUser = new RegUser();
        regUser.delegate = this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getAndCheckValues()) {
                    User user = new User(name, age, gender, contact, phone, email, password, address);
                    try {
                        NetworkController controller = new NetworkController(RegUserActivity.this);
                        if (controller.isNetworkAvailable()) {
                            regUser.execute(user);
                        } else {
                            Toast.makeText(RegUserActivity.this, "Check your Internet connection", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(RegUserActivity.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
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
        age = controller.encodedValue(etAge);
        phone = controller.encodedValue(etPhone);
        String code = controller.encodedValue(etCode);
        email = controller.encodedValue(etEmail);
        contact = controller.encodedValue(etContact);
        address = controller.encodedValue(etAddress);
        password = controller.encodedValue(etPassword);
        String confirm = controller.encodedValue(etRepassword);
        gender = (String) spGender.getSelectedItem();

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
            controller.showError(etRepassword, "Passwords don't match");
        }

        return false;
    }

    private void linkAll() {
        etName = findViewById(R.id.reg_user_et_name);
        etAge = findViewById(R.id.reg_user_et_age);
        etPhone = findViewById(R.id.reg_user_et_phone);
        etCode = findViewById(R.id.reg_user_et_code);
        etEmail = findViewById(R.id.reg_user_et_email);
        etContact = findViewById(R.id.reg_user_et_contact);
        etAddress = findViewById(R.id.reg_user_et_address);
        etPassword = findViewById(R.id.reg_user_et_password);
        etRepassword = findViewById(R.id.reg_user_et_re_password);
        spGender = findViewById(R.id.reg_user_sp_gender);
    }

    public void onGetCode(View view) {
        Toast.makeText(this, "We will work on it later", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void processFinish(Object output) {
        Snackbar.make(newView, "" + output.toString(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(RegUserActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}
