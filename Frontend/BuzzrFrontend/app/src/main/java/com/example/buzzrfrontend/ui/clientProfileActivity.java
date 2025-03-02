package com.example.buzzrfrontend.ui.clientProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.buzzrfrontend.R;
import com.example.buzzrfrontend.data.ApplicationVar;

public class clientProfileActivity extends AppCompatActivity {

    ApplicationVar appVar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);
        appVar = (ApplicationVar) getApplicationContext();
        appVar.getNav().setContext(this);

        Button goBack = findViewById(R.id.returnToDash);
        Button logout = findViewById(R.id.logoutButton);
        Button appointment = findViewById(R.id.clientAppointmentInfo);
        TextView name = findViewById(R.id.clientName);
        TextView email = findViewById(R.id.clientEmail);
        TextView phoneNum = findViewById(R.id.clientPhoneNum);
        TextView loggedInAs = findViewById(R.id.loggedInAsName);

        name.setText("Name: " + appVar.getLoggedInUser().getName());
        email.setText("Email: " + appVar.getLoggedInUser().getEmail());
        phoneNum.setText("Phone number: " + appVar.getLoggedInUser().getPhoneNumber());
        loggedInAs.setText("Logged in as: " + appVar.getLoggedInUser().getUserName());

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appVar.getNav().openToDashboard();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appVar.getLoggedInUser().logout();
                appVar.getNav().openToLoginActivity();
            }
        });
        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appVar.getNav().openToClientAppointmentActivity();
            }
        });

    }
}