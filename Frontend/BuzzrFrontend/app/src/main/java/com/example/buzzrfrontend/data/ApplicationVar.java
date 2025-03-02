package com.example.buzzrfrontend.data;

import android.app.Application;
import android.util.Log;
import androidx.multidex.MultiDexApplication;

import com.example.buzzrfrontend.data.model.LoggedInUser;

public class ApplicationVar extends MultiDexApplication {
    private static final String TAG = "ApplicationVar";
    private LoggedInUser loggedInUser;
    private Navigation nav;

    @Override
    public void onCreate()
    {
        super.onCreate();
        // Initialize Navigation if null
        if (nav == null) {
            Log.d(TAG, "Initializing Navigation in ApplicationVar");
            nav = new Navigation(getApplicationContext());
        }
    }

    public LoggedInUser getLoggedInUser()
    {
        return loggedInUser;
    }
    
    public void setLoggedInUser(LoggedInUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public Navigation getNav() {
        // Always ensure nav is not null when requested
        if (nav == null) {
            Log.d(TAG, "Creating Navigation on-demand in getNav()");
            nav = new Navigation(getApplicationContext());
        }
        return nav;
    }
    
    public void setNav(Navigation nav)
    {
        this.nav = nav;
    }
}
