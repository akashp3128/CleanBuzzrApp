package com.example.buzzrfrontend.data;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.buzzrfrontend.ui.loginView.LoginActivity;

public class Navigation {
    private static final String TAG = "Navigation";
    private Context context;
    
    public Navigation(Context context) {
        this.context = context;
        Log.d(TAG, "Navigation created with context: " + context);
    }
    
    public Navigation() {
        Log.d(TAG, "Navigation created without context");
    }
    
    public void setContext(Context context) {
        Log.d(TAG, "Setting context: " + context);
        this.context = context;
    }
    
    public Context getContext() {
        return context;
    }
    
    public void openToLoginActivity() {
        if (context != null) {
            Log.d(TAG, "Opening LoginActivity");
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            Log.e(TAG, "Cannot open LoginActivity - context is null");
        }
    }
    
    public void openToDashboard() {
        if (context != null) {
            Log.d(TAG, "Simulating opening Dashboard (not implemented)");
            // Placeholder for dashboard navigation
            // In a real app, we would have:
            // Intent intent = new Intent(context, DashboardActivity.class);
            // context.startActivity(intent);
            
            // For now, just show we attempted to navigate
            Log.d(TAG, "Dashboard navigation would happen here");
        } else {
            Log.e(TAG, "Cannot open Dashboard - context is null");
        }
    }
    
    public void openToRegisterActivity() {
        if (context != null) {
            Log.d(TAG, "Simulating opening RegisterActivity (not implemented)");
            // Placeholder for register navigation
        } else {
            Log.e(TAG, "Cannot open RegisterActivity - context is null");
        }
    }
    
    public void openToAdminDashboard() {
        if (context != null) {
            Log.d(TAG, "Simulating opening AdminDashboard (not implemented)");
            // Placeholder for admin dashboard navigation
        } else {
            Log.e(TAG, "Cannot open AdminDashboard - context is null");
        }
    }
    
    public void openToBarberDashboard() {
        if (context != null) {
            Log.d(TAG, "Simulating opening BarberDashboard (not implemented)");
            // Placeholder for barber dashboard navigation
        } else {
            Log.e(TAG, "Cannot open BarberDashboard - context is null");
        }
    }
} 