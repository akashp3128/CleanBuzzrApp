package com.example.buzzrfrontend.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.buzzrfrontend.data.ApplicationVar;
import com.example.buzzrfrontend.data.model.LoggedInUser;
import com.example.buzzrfrontend.ui.dashboardView.DashboardActivity;
import com.example.buzzrfrontend.R;
import com.example.buzzrfrontend.data.Navigation;

public class MainActivity extends AppCompatActivity {



    private Navigation nav = new Navigation(this);

    ApplicationVar appVar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appVar = (ApplicationVar) getApplicationContext();
        appVar.setNav(nav);
        //Toast.makeText(getApplicationContext(),"Main works", Toast.LENGTH_LONG).show();
        appVar.getNav().openToLoginActivity();
        finish();
    }
}