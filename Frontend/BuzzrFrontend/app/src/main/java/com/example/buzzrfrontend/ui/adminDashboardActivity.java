package com.example.buzzrfrontend.ui.adminDashboardView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buzzrfrontend.data.ApplicationVar;
import com.example.buzzrfrontend.R;
import com.example.buzzrfrontend.data.Navigation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class adminDashboardActivity extends AppCompatActivity {


    ApplicationVar appVar;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_admin_dashboard);

        appVar = (ApplicationVar) getApplicationContext();
        appVar.getNav().setContext(this);
        TextView loggedInAs = findViewById(R.id.loggedInAs);
        loggedInAs.setText("Logged in as: " + appVar.getLoggedInUser().getUserName());
        super.onCreate(savedInstanceState);

        Button clientDashboardButton = findViewById(R.id.clientDashboardButton);
        Button barberDashboardButton = findViewById(R.id.barberDashboardButton);
        Button logoutButton = findViewById(R.id.logoutButton);
        clientDashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appVar.getNav().openToDashboard();
            }
        });
        barberDashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appVar.getNav().openToBarberDashboard();
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appVar.getLoggedInUser().logout();
                appVar.getNav().openToLoginActivity();
            }
        });
    }
}